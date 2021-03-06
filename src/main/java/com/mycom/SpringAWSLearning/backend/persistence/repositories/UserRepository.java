package com.mycom.SpringAWSLearning.backend.persistence.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mycom.SpringAWSLearning.backend.persistence.domain.backend.User;

@Repository
@Transactional(readOnly=true)
public interface UserRepository  extends CrudRepository<User, Long> {

	public User findByUsername( String username);
	
	public User findByEmail(String email);
	
	@Modifying
	@Query("Update User u set u.password=:password where u.id = :userId")
	public void updateUserPassword(@Param("userId") long userId, @Param("password") String password );
	
}
