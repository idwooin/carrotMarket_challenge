package com.numble.backend.post.domain;

public enum StockStatus {
	SELLING("판매중"),
	RESERVED("예약중"),
	SOLD("판매완료");

	private String status;
	StockStatus(String status) {
		this.status = status;
	}
}
