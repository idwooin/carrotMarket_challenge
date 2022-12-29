package com.numble.backend.user.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.numble.backend.user.auth.domain.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

	Optional<UserInfo> findByNickname(String nickname);

	Optional<UserInfo> findByUserId(String userId);

	void deleteByUserId(String userId);
}
