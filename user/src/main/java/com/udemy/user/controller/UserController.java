package com.udemy.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.user.exception.UserServiceException;
import com.udemy.user.model.UserDTO;
import com.udemy.user.service.UserService;

@RestController
@RequestMapping("/v1/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService accountService) {
		super();
		this.userService = accountService;
	}

	@PostMapping("/")
	public ResponseEntity<Boolean> userExist(@RequestBody UserDTO userDTO) throws UserServiceException {
		return new ResponseEntity<>(userService.userExist(userDTO.getUserId(), userDTO.getPwd()), HttpStatus.OK);
	}

	@PostMapping("/{user-id}")
	public ResponseEntity<Void> createUser(@PathVariable(name = "user-id") String userId, @RequestBody UserDTO userDTO)
			throws UserServiceException {
		userService.createUser(userId, userDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
