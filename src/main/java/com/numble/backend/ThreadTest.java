package com.numble.backend;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadTest {
	int count = 0;
	@Async
	public void increment() {
		count++;
	}

	public int getCount() {
		return count;
	}
}
