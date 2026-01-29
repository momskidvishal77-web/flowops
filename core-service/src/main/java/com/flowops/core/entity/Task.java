package com.flowops.core.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	private String title;
	
	private String description;
	
	private String status; // TODO , IN_PROGRESS , DONE
	
	private String createdBy; //User identity coming from JWT (email)

}
