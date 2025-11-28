package com.cdac.entities;

import java.beans.Transient;

import org.springframework.boot.autoconfigure.condition.ConditionalOnCloudPlatform;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class User extends BaseEntity {
	
	@Column(name = "first_name",length = 50, nullable = false)
	private String firstName;
	@Column(name = "last_name" ,length = 50 )
	private String lastName;
	@Column(name = "email_id",length = 100 , nullable = false, unique = true)
	private String emailId;
	@Column(length = 255,nullable = false)
	private String password;
	@Column(length = 10,nullable = false,unique = true)
	private Long phone;
	@Enumerated(EnumType.STRING)
	private UserRole role;
	@Column(length = 150)
	private String address;
	@Column(name = "is_active")
	private boolean isActive;

}
