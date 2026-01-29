package com.flowops.core.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.flowops.core.entity.Task;
import com.flowops.core.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {
	
	private final TaskRepository taskRepository;
	
	public Task createTask(Task task, String userEmail)
	{   
		task.setCreatedBy(userEmail);
		task.setStatus("TODO");
		return taskRepository.save(task);
		
	}
	
	public List<Task> getMyTasks(String userEmail){
		return taskRepository.findByCreatedBy(userEmail);
	}

}
