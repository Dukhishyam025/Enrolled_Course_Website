package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "DIT_COURSE_DTLS")
@Data
public class CourseEntity {

    @Id
    @GeneratedValue
    private Integer courseId;
    private String courseName;
    
}