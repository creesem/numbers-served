package com.canopy.numbers.served.deprecated.application.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.canopy.numbers.served.deprecated.UserRepositoryDeprecated;
import com.canopy.shared.data.User;

@Service
public class UserServiceDeprecated {

	private final UserRepositoryDeprecated repository;

	public UserServiceDeprecated(UserRepositoryDeprecated repository) {
		this.repository = repository;
	}

	public Optional<User> get(Long id) {
		return repository.findById(id);
	}

	public User update(User entity) {
		return repository.save(entity);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	public Page<User> list(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<User> list(Pageable pageable, Specification<User> filter) {
		return repository.findAll(filter, pageable);
	}

	public List<User> listAll() {
		return repository.findAll(); // Assuming your UserRepository has this method
	}

	public int count() {
		return (int) repository.count();
	}

}
