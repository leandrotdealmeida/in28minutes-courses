package com.devjapa.in28minutes.user;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
	
	@Autowired
	private UserDaoService service;
	
	@GetMapping
	public List<User> retrieveAllUser() {
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public User retrieveUser(@PathVariable int id) {
		return service.findOne(id);
	}
	
	// input - details of user
	// output - CREATED and return the created URI
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		 User savedUser = service.save(user);
		 
		 URI location = ServletUriComponentsBuilder
		 .fromCurrentRequest()
		 .path("/{id}")
		 .buildAndExpand(savedUser.getId()).toUri();
		 
		return ResponseEntity.created(location).build();
	}
	

}
