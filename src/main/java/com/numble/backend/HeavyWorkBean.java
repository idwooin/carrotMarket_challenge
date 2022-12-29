package com.numble.backend;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HeavyWorkBean {

	@Async
	public void heavyWork(int i) throws InterruptedException {
		Thread.sleep(1000);
		System.out.println("아주 무거운 작업"+i+" 진행 중");
		log.trace("아주 무거운 작업 {} 진행 중",i);
	}
}
