package com.numble.backend.user.auth.util;

public enum AuthorizationType {
	BASIC,
	BEARER;

	public String toLowerCase() {
		return this.name().toLowerCase();
	}
}
