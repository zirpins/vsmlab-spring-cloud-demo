package de.hska.iwi.vsys.microlab;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {

	@Autowired
	private UserRepo repo;

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<Iterable<User>> getUsers() {
		Iterable<User> allPolls = repo.findAll();
		return new ResponseEntity<Iterable<User>>(allPolls, HttpStatus.OK);
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<?> addUser(@RequestBody User user) {
		user = repo.save(user);
		// Set the location header for the newly created resource
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newUserUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId())
				.toUri();
		responseHeaders.setLocation(newUserUri);
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable Long userId) {
		User user = repo.findOne(userId);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable Long userId) {
		repo.delete(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
