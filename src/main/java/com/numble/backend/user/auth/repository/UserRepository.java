package com.numble.backend.user.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.numble.backend.user.auth.domain.CustomUser;

public interface UserRepository extends JpaRepository<CustomUser, String>, UserQueryRepository {

	Optional<CustomUser> findByEmail(String email);

	int countByEmail(String email);
}
