package com.numble.backend.user.auth.repository;

import static com.numble.backend.user.auth.domain.QCustomUser.customUser;
import static com.numble.backend.user.auth.domain.QUserInfo.userInfo;

import com.numble.backend.user.auth.repository.dto.UserLookUpResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<UserLookUpResponse> findUserDetailInfoById(String userId) {
		UserLookUpResponse userLookUpResponse = queryFactory
			.select(Projections.constructor(UserLookUpResponse.class,
				customUser.id,
				customUser.email,
				customUser.password,
				userInfo.phone,
				userInfo.name,
				userInfo.nickname,
				userInfo.profileImage))
			.from(customUser)
			.join(userInfo).on(customUser.id.eq(userInfo.userId))
			.where(customUser.id.eq(userId))
			.fetchOne();

		return Optional.of(userLookUpResponse);
	}
}
