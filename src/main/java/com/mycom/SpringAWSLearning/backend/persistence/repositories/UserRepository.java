package com.mycom.SpringAWSLearning.backend.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.User;

@Repository
public interface UserRepository  extends CrudRepository<User, Long> {

	public User findByUsername( String username);
	
}
