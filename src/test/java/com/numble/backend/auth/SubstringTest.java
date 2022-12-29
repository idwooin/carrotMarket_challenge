package com.numble.backend.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SubstringTest {

	@Test
	public void substrtest() {
		String value = "Bearer: token";
		value.substring(6).trim();
		Assertions.assertEquals(value.substring(6), ": token");
		System.out.println("value.substring(6).trim() = " + value);
	}
}
