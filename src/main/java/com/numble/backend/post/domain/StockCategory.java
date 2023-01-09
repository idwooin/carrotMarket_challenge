package com.numble.backend.post.domain;

public enum StockCategory {
	DIGITAL("디지털기기"),
	HOUSEHOLD_APPLIANCES("생활가전"),
	FURNITURE("가구/인테리어"),
	CHILD("유아동"),
	FOOD("생활/가공식품"),
	CHILD_BOOK("유아도서"),
	SPORTS("스포츠/레저"),
	FEMALE_STUFF("여성잡화"),
	FEMALE_CLOTHES("여성의류"),
	MALE_CLOTHES("남성패션/잡화"),
	GAME("게임/취미"),
	BEAUTY("뷰티/미용"),
	PET("반려동물용품"),
	BOOK("도서/티켓/음반"),
	PLANTS("식물"),
	USED("기타 중고물품"),
	USED_CAR("중고차");
	private String name;
	StockCategory(String name){
		this.name = name;
	};
}
