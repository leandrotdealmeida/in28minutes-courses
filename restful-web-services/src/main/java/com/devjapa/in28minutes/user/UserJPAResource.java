package com.devjapa.in28minutes.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserJPAResource {
	
	@Autowired
	private UserDaoService service;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUser() {
		return userRepository.findAll();
	}
	
	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		
		if(!user.isPresent())
			throw new UserNotFoundException("id-"+ id);
		
		EntityModel<User> resource = EntityModel.of(user.get());
		
		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUser());
		
		resource.add(linkTo.withRel("all-users"));
		
		return resource;
	}
	
	// input - details of user
	// output - CREATED and return the created URI
	@PostMapping
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		 User savedUser = service.save(user);
		 
		 URI location = ServletUriComponentsBuilder
		 .fromCurrentRequest()
		 .path("/{id}")
		 .buildAndExpand(savedUser.getId()).toUri();
		 
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable int id) {
		User user = service.deleteById(id);
		if(user == null)
			throw new UserNotFoundException("id-"+ id); 
	}
	

}
