package com.cdac.entities;

import jakarta.persistence.AttributeOverride;
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
@AttributeOverride(name = "id" , column = @Column(name = "user_id"))
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
	public User(String firstName, String lastName, String emailId, String password, Long phone, UserRole role,
			String address) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.password = password;
		this.phone = phone;
		this.role = role;
		this.address = address;
		isActive=true;
	}
	
	

}
