package com.cdac.entities;

import java.time.LocalDateTime;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "categories")
@AttributeOverride(name = "id" , column = @Column(name = "category_id"))
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Category extends BaseEntity{
	
	@Column(length = 100,nullable=false,unique = true)
	private String name;
	
	@Column(length = 200)
	private String description;

	public Category(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	
	
}
