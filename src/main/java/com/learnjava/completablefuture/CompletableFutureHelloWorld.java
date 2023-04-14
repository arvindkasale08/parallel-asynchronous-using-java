package com.learnjava.completablefuture;

import static com.learnjava.util.CommonUtil.delay;
import static com.learnjava.util.CommonUtil.startTimer;

import java.util.concurrent.CompletableFuture;

import com.learnjava.service.HelloWorldService;
import com.learnjava.util.LoggerUtil;

public class CompletableFutureHelloWorld {

	private HelloWorldService service;

	public CompletableFutureHelloWorld(HelloWorldService service) {
		this.service = service;
	}

	public CompletableFuture<String> helloWorld() {
		return CompletableFuture.supplyAsync(service::helloWorld)
			.thenApply(String::toUpperCase); // blocks main thread
	}

	public CompletableFuture<String> helloworld_multiple_async_calls() {
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(service::hello);
		CompletableFuture<String> world = CompletableFuture.supplyAsync(service::world);

		return hello
			.thenCombine(world, (s, s2) -> s.concat(s2))
			.thenApply(String::toUpperCase);
	}

	public CompletableFuture<String> helloworld_multiple_3_async_calls() {
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(service::hello);
		CompletableFuture<String> world = CompletableFuture.supplyAsync(service::world);
		CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
			delay(1000);
			return "Hi Completable Future";
		});

		return hello
			.thenCombine(world, (s, s2) -> s.concat(s2))
			.thenCombine(hiCompletableFuture, (prev, curr) -> prev.concat(curr))
			.thenApply(String::toUpperCase);
	}

	public CompletableFuture<String> helloWorld_then_compose() {
		return CompletableFuture.supplyAsync(service::hello)
			.thenCompose(service::worldFuture)
			.thenApply(String::toUpperCase); // blocks main thread
	}

	public static void main(String[] args) {
		HelloWorldService service = new HelloWorldService();
		CompletableFuture.supplyAsync(service::helloWorld)
			.thenApply(String::toUpperCase)
			.thenAccept(s -> LoggerUtil.log("Result is "+ s))
			.join(); // blocks main thread
		LoggerUtil.log("Done");
		// delay(2000);
	}
}
