package com.numble.backend.common.dto;

import lombok.Getter;

@Getter
public enum ResponseEnum {

	USER_SIGNUP_SUCCESS("U001","회원가입에 성공하였습니다."),
	USER_LOGIN_SUCCESS("U002","로그인에 성공하였습니다."),
	USER_DELETE_SUCCESS("U003","회원탈퇴에 성공하였습니다."),
	USER_UNAUTHORIZED("u004", "허가받지 않은 유저입니다."),
	USER_ACCESS_DENIED("u005", "유저의 접근이 거부되었습니다."),
	USER_LOGOUT_SUCCESS("u006", "로그아웃에 성공하였습니다."),
	USER_DUPLICATED("u007", "유저가 중복되었습니다.");

	private final String code;
	private final String message;

	ResponseEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
