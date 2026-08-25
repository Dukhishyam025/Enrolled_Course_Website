package com.example.entity;

import java.time.LocalDate;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "DIT_STUDENT_ENQ")
@Data
public class StudentEnqEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer enqId;
    private String studentName;
    private Long studentPhno;
    private String classMode;
    private String courseName;
    private String enqStatus;
    
    @CreationTimestamp
    private LocalDate createdDate;
    @UpdateTimestamp
    private LocalDate updatedDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDtlsEntity user;
}