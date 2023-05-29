package com.aspire.loan.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspire.loan.main.model.User;
import com.aspire.loan.main.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User registerUser(User user) {
		return userRepository.save(user);
	}

	public boolean authenticateUser(String email, String password) {
		User user = userRepository.findByEmail(email);
		return user != null && user.getPassword().equals(password);
	}

	public User getUserById(long l) {
		Optional<User> user = userRepository.findById(l);
		if (user.isPresent()) {
			return user.get();
		}
		return null;
	}

	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	public User updateUser(long l, User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	public void deleteUser(long l) {
		// TODO Auto-generated method stub
		userRepository.deleteById(l);
	}

}
