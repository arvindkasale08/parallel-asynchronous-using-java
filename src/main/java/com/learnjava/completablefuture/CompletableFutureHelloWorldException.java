package com.learnjava.completablefuture;

import static com.learnjava.util.CommonUtil.delay;

import java.util.concurrent.CompletableFuture;

import com.learnjava.service.HelloWorldService;
import com.learnjava.util.LoggerUtil;

public class CompletableFutureHelloWorldException {

	private HelloWorldService service;

	public CompletableFutureHelloWorldException(HelloWorldService service) {
		this.service = service;
	}

	public CompletableFuture<String> handle() {
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(service::hello);
		CompletableFuture<String> world = CompletableFuture.supplyAsync(service::world);
		CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
			delay(1000);
			return "Hi Completable Future";
		});

		return hello
			.handle((s, throwable) -> {
				if (throwable != null) {
					LoggerUtil.log("Exception is " + throwable.getMessage());
					return "";
				} else {
					return s;
				}
			})
			.thenCombine(world, (s, s2) -> s.concat(s2))
			.handle((s, throwable) -> {
				if (throwable != null) {
					LoggerUtil.log("Exception after world " + throwable.getMessage());
					return "";
				} else {
					return s;
				}
			})
			.thenCombine(hiCompletableFuture, (prev, curr) -> prev.concat(curr))
			.thenApply(String::toUpperCase);
	}

	public CompletableFuture<String> handleExceptionally() {
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(service::hello);
		CompletableFuture<String> world = CompletableFuture.supplyAsync(service::world);
		CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
			delay(1000);
			return "Hi Completable Future";
		});

		return hello
			.exceptionally(throwable -> {
				LoggerUtil.log("Exception is " + throwable.getMessage());
					return "";
			})
			.thenCombine(world, (s, s2) -> s.concat(s2))
			.exceptionally(throwable -> {
				LoggerUtil.log("Exception after world " + throwable.getMessage());
				return "";
			})
			.thenCombine(hiCompletableFuture, (prev, curr) -> prev.concat(curr))
			.thenApply(String::toUpperCase);
	}

	public CompletableFuture<String> handleWhenComplete() {
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(service::hello);
		CompletableFuture<String> world = CompletableFuture.supplyAsync(service::world);
		CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
			delay(1000);
			return "Hi Completable Future";
		});

		return hello
			.whenComplete((s, throwable) -> {
				if (throwable != null)
				LoggerUtil.log("Exception is " + throwable.getMessage());
			})
			.thenCombine(world, (s, s2) -> s.concat(s2))
			.whenComplete((s, throwable) -> {
				if (throwable != null)
				LoggerUtil.log("Exception in world " + throwable.getMessage());
			})
			.exceptionally(throwable -> "")
			.thenCombine(hiCompletableFuture, (prev, curr) -> prev.concat(curr))
			.thenApply(String::toUpperCase);
	}
}
