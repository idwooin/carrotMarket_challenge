package com.numble.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.AllArgsConstructor;

public class CallByReferenceTest {

	@Test
	void test() {
		//given
		Hello hello = new Hello("hi");

		//when
		changeValue(hello);

		//then
		Assertions.assertEquals("no hi", hello.value);
	}

	@AllArgsConstructor
	private class Hello {
		private String value;

		public void updateValue(String value) {
			this.value = value;
		}
	}

	private void changeValue(Hello hello) {
		hello.updateValue("no hi");
	}
}
