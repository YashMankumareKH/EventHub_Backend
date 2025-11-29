package com.cdac.service;

import java.util.List;
import com.cdac.dto.RegistrationDto;
import com.cdac.custom_exception.ApiException;
import com.cdac.custom_exception.ResourceNotFoundException;

public interface EventRegistrationService {

    RegistrationDto registerForEvent(RegistrationDto dto);

    List<RegistrationDto> getUserEventRegistrations(Long userId);

    List<RegistrationDto> getEventRegistrations(Long eventId);

    String cancelEventRegistration(Long registrationId);
}
