package com.numble.backend.user.auth.repository;

import java.util.Optional;

import com.numble.backend.user.auth.repository.dto.UserLookUpResponse;

public interface UserQueryRepository {
	Optional<UserLookUpResponse> findUserDetailInfoById(String userId);
}
