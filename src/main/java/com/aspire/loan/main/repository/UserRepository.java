package com.aspire.loan.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aspire.loan.main.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User save(User user);

	User findByEmail(String email);

	Optional<User> findById(long l);

	List<User> findAll();

	void deleteById(long id);
}
