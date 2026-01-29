package com.flowops.core.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flowops.core.entity.Task;
import com.flowops.core.service.TaskService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/core/tasks")
@RequiredArgsConstructor
public class TaskController {
	
	private final TaskService taskService;
	
	 @PostMapping
	    public ResponseEntity<Task> createTask(
	            @RequestBody Task task,
	            @RequestHeader(value = "X-User-Email", required = false) String userEmail
	    ) {
	        return ResponseEntity.ok(taskService.createTask(task, userEmail));
	    }
	
	 @GetMapping
	    public ResponseEntity<List<Task>> getMyTasks(
	            @RequestHeader(value= "X-User-Email",required= false) String userEmail
	    ) {
	        return ResponseEntity.ok(taskService.getMyTasks(userEmail));
	    }

}
