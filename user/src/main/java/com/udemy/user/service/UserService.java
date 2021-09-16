package com.udemy.user.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.udemy.user.entity.User;
import com.udemy.user.exception.ErrorCode;
import com.udemy.user.exception.UserServiceException;
import com.udemy.user.model.UserDTO;
import com.udemy.user.repository.UserRepository;

@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public Boolean userExist(String userId, String pwd) throws UserServiceException {
		logger.info("started getting user details for userId {} ", userId);
		Optional<User> user = userRepository.findByUserIdAndUserPwd(userId, pwd);
		if (!user.isPresent()) {
			logger.error("User id {} not found", userId);
			throw new UserServiceException(ErrorCode.USER_NOT_FOUND);
		}
		logger.info("completed getting user details for userId {} ", userId);
		return true;
	}

	public void createUser(String userId, UserDTO userDTO) throws UserServiceException {
		logger.info("started creating user details for userId {} ", userId);
		User user = new User();
		user.setUserId(userId);
		user.setUserPwd(userDTO.getPwd());
		userRepository.save(user);
		logger.info("completed creating user details for userId {} ", userId);
	}

}
