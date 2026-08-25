package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name ="DIT_ENQURIRY_STATUS")
@Data
public class EnqStatusEntity {
	
	@Id
	@GeneratedValue
	private Integer statusId;
	private String statusName;

}
