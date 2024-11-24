package com.anjeemant.e_comm.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Data
@Table(name = "roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer Id;
	
	@NotEmpty
	@Column(nullable = false, unique = true)
	private String name;
	
	@ManyToMany(mappedBy = "roles")
	private List<User> users;
}

