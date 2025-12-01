package com.cdac.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cdac.dto.AuthRequest;
import com.cdac.dto.AuthResponse;
import com.cdac.dto.EventList;
import com.cdac.dto.UserProfileDTO;
import com.cdac.dto.UserRegistrationRequest;
import com.cdac.dto.UserRegistrationResponse;
import com.cdac.entities.EventStatus;
import com.cdac.entities.RegStatus;
import com.cdac.entities.User;
import com.cdac.entities.UserRole;
import com.cdac.exception.custom.AuthenticationException;
import com.cdac.exception.custom.DuplicateResourceException;
import com.cdac.exception.custom.InternalErrorException;
import com.cdac.exception.custom.InvalidActionException;
import com.cdac.exception.custom.InvalidDataException;
import com.cdac.exception.custom.NoDataFoundException;
import com.cdac.exception.custom.UserNotFoundException;
import com.cdac.repo.UserRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User getUserByEmailId(String emailId) {
        return userRepo.findUserByEmailId(emailId)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + emailId));
    }

    @Override
    public AuthResponse authenticate(AuthRequest dto) {

        User user = userRepo.findByEmailId(dto.getEmail())
                .orElseThrow(() -> new AuthenticationException("Invalid email or password"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Invalid email or password");
        }

        AuthResponse resp = modelMapper.map(user, AuthResponse.class);
        resp.setMessage("Login successful");

        return resp;
    }

    @Override
    public UserRegistrationResponse registerUser(UserRegistrationRequest req) {

        if (userRepo.existsByEmailId(req.getEmailId())) {
            throw new DuplicateResourceException("Email already registered: " + req.getEmailId());
        }
        if (userRepo.existsByPhone(req.getPhone())) {
            throw new DuplicateResourceException("Phone number already registered: " + req.getPhone());
        }

        // validate role string -> convert to enum safely
        UserRole role;
        try {
            role = UserRole.valueOf(req.getRole().toUpperCase());
        } catch (Exception e) {
            throw new InvalidDataException("Invalid role: " + req.getRole());
        }

        User user = new User();
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setEmailId(req.getEmailId());
        user.setPhone(req.getPhone());
        user.setAddress(req.getAddress());
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(req.getPassword()));

        User saved = userRepo.save(user);

        UserRegistrationResponse resp = new UserRegistrationResponse();
        resp.setUserId(saved.getId());
        resp.setFirstName(saved.getFirstName());
        resp.setLastName(saved.getLastName());
        resp.setEmailId(saved.getEmailId());
        resp.setPhone(saved.getPhone());
        resp.setRole(saved.getRole().name());
        resp.setMessage("User registered successfully");

        return resp;
    }

    @Override
    public UserProfileDTO getUserDetails(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        return modelMapper.map(user, UserProfileDTO.class);
    }

    @Override
    public String updateUserDetails(Long userId, UserProfileDTO userProfileDTO) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        // optional: validate input fields (example)
        if (userProfileDTO.getPassword() == null || userProfileDTO.getPassword().isBlank()) {
            throw new InvalidDataException("Password must not be empty");
        }

        user.setFirstName(userProfileDTO.getFirstName());
        user.setLastName(userProfileDTO.getLastName());
        user.setEmailId(userProfileDTO.getEmailId());
        user.setPassword(passwordEncoder.encode(userProfileDTO.getPassword()));
        user.setPhone(userProfileDTO.getPhone());
        user.setAddress(userProfileDTO.getAddress());

        return "Profile Updated";
    }

    @Override
    public List<EventList> getUpcomingEvents(Long userId) {

        if (!userRepo.existsById(userId)) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }

        List<EventList> list =
        		userRepo.findByEventsByUserIdAndEventStatus(
                        userId, RegStatus.REGISTERED, EventStatus.PUBLISHED);

        if (list.isEmpty()) {
            throw new NoDataFoundException("No upcoming events found for user: " + userId);
        }

        return list;
    }


    @Override
    public List<EventList> getCompletedEvents(Long userId) {

        if (!userRepo.existsById(userId)) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }

        List<EventList> list =
                userRepo.findByEventsByUserIdAndEventStatus(
                        userId, RegStatus.REGISTERED, EventStatus.COMPLETED);

        if (list.isEmpty()) {
            throw new NoDataFoundException("No completed events found for user: " + userId);
        }

        return list;
    }

    @Override
    public List<User> getAllUser() {

        List<User> users = userRepo.findAll();

        if (users.isEmpty()) {
            throw new NoDataFoundException("No users found in the system");
        }

        return users;
    }


    @Override
    public String deleteUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        if (user.isActive()) {
        	user.setActive(false);  
        	return "User Deleted";
        }
        else if (!user.isActive()) {
        	user.setActive(true);
        	return "User Activated";
        }

        throw new InternalErrorException("Unexpected error occurred");

    }

    @Override
    public String updateUser(User user) {
        User dbuser = userRepo.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + user.getId()));

        if (user.getEmailId() == null || user.getEmailId().isBlank()) {
            throw new InvalidDataException("Email must not be empty");
        }

        dbuser.setFirstName(user.getFirstName());
        dbuser.setLastName(user.getLastName());
        dbuser.setEmailId(user.getEmailId());
        dbuser.setPhone(user.getPhone());
        dbuser.setAddress(user.getAddress());
        dbuser.setRole(user.getRole());

        return "User Updated";
    }

    @Override
    public User getUserAllDetails(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }

    @Override
    public List<User> getAllManagers() {

        List<User> managers = userRepo.findByRole(UserRole.ROLE_MANAGER);

        if (managers.isEmpty()) {
            throw new NoDataFoundException("No managers found");
        }

        return managers;
    }

}
