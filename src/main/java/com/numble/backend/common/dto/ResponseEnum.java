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
	USER_DUPLICATED("u007", "유저가 중복되었습니다."),
	MYPAGE_FIND_SUCCESS("u008", "마이 페이지 불러오기에 성공하였습니다."),
	MYSTOCK_FIND_SUCCESS("u009", "판매 내역 불러오기에 성공하였습니다."),
	MYLIKES_FIND_SUCCESS("u010", "관심 목록 불러오기에 성공하였습니다."),
	MYCHATS_FIND_SUCCESS("u011", "채팅 목록 불러오기에 성공하였습니다."),
	MYPAGE_UPDATE_SUCCESS("u012", "마이 페이지 수정에 성공하였습니다."),


	POST_CREATE_SUCCESS("p001", "상품 등록에 성공하였습니다."),
	POST_DELETE_SUCCESS("p002", "상품 삭제가 성공하였습니다."),
	POST_FIND_SUCCESS("p003", "상품 상세 페이지 찾기에 성공하였습니다."),
	POST_FIND_ALL_SUCCESS("p004", "상품 페이지 찾기에 성공하였습니다."),
	POST_LIKE_SUCCESS("p005", "상품 관심이 등록되었습니다."),
	POST_UNLIKE_SUCCESS("p006", "상품 관심이 취소되었습니다."),
	POST_CHANGE_STATUS_SUCCESS("p007", "상품 상태가 성공적으로 변경되었습니다.");

	private final String code;
	private final String message;

	ResponseEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
