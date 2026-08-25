package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.StudentEnqEntity;

public interface StudentEnqRepo extends JpaRepository<StudentEnqEntity, Integer> {

}
