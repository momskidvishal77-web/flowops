package com.flowops.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flowops.core.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

	List<Task> findByCreatedBy(String createdBy);
}
