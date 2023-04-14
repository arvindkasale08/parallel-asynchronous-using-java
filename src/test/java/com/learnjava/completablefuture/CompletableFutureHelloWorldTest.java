package com.learnjava.completablefuture;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;

import com.learnjava.service.HelloWorldService;

class CompletableFutureHelloWorldTest {

	private HelloWorldService helloWorldService = new HelloWorldService();
	private CompletableFutureHelloWorld completableFutureHelloWorld = new CompletableFutureHelloWorld(helloWorldService);

	@Test
	void helloWorld() {

		// given

		// when
		CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorld();

		// then
		completableFuture
			.thenAccept(s -> assertEquals(s, "HELLO WORLD"))
			.join();
	}

	@Test
	void helloworld_multiple_async_calls() {
		// given

		// when
		CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloworld_multiple_async_calls();

		// then
		startTimer();
		completableFuture
			.thenAccept(s -> assertEquals(s, "HELLO WORLD!"))
			.thenAccept(s -> timeTaken())
			.join();
	}

	@Test
	void helloworld_3_async_calls() {
		// given

		// when
		CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloworld_multiple_3_async_calls();

		// then
		startTimer();
		completableFuture
			.thenAccept(s -> assertEquals(s, "HELLO WORLD!HI COMPLETABLE FUTURE"))
			.thenAccept(s -> timeTaken())
			.join();
	}

	@Test
	void helloWorld_then_compose() {
		// given

		// when
		startTimer();
		CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorld_then_compose();

		// then
		completableFuture
			.thenAccept(s -> assertEquals(s, "HELLO WORLD!"))
			.thenAccept(s -> timeTaken())
			.join();
	}
}
