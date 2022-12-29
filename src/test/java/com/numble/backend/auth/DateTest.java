package com.numble.backend.auth;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DateTest {

	@Test
	public void datetest() {
		Date currtime = new Date();
		try {

			Thread.sleep(1000); //1초 대기

		} catch (InterruptedException e) {
		}
		Assertions.assertEquals(Boolean.TRUE, currtime.before(new Date()));
		System.out.println(currtime);
		System.out.println(new Date());
	}
}
