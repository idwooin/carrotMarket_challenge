package com.numble.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TempTest {
	@Autowired
	ThreadTest threadTest;

	@Autowired
	HeavyWorkBean heavyWorkBean;

	@Test
	public void threadtest() {
		for (int i = 0; i < 1000000; i++) {
			threadTest.increment();
		}

		System.out.println(threadTest.getCount());
	}

	@Test
	public void asyncTest() throws Exception {
		for (int i = 0; i < 10; i++) {
			heavyWorkBean.heavyWork(i);
		}
		Thread.sleep(2000);
		System.out.println("종료");
	}
}
