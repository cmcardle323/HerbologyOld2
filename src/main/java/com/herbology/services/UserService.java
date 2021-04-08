package com.herbology.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.herbology.models.User;
import com.herbology.repositories.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
	public List<User> listAll() {
		return userRepo.findAll();
	}
	
	public void save(User user) {
		userRepo.save(user);
	}
	
	public User get(long id) {
		return userRepo.findById(id).get();
	}
	
	public void delete(long id) {
		userRepo.deleteById(id);
	}

	public User findByEmail(String email) {
		return userRepo.findByEmail(email);
	}
}