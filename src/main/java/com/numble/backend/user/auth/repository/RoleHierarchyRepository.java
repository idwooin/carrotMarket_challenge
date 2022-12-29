package com.numble.backend.user.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.numble.backend.user.auth.domain.RoleHierarchy;

public interface RoleHierarchyRepository extends JpaRepository<RoleHierarchy, String> {

	Optional<RoleHierarchy> findByChildName(String roleName);
}
