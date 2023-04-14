package com.learnjava.completablefuture;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.learnjava.service.HelloWorldService;

@ExtendWith(MockitoExtension.class)
class CompletableFutureHelloWorldExceptionTest {

	@Mock
	private HelloWorldService helloWorldService = Mockito.mock(HelloWorldService.class);

	@InjectMocks
	private CompletableFutureHelloWorldException hwcfe;

	@Test
	void helloworld_multiple_3_async_calls() {

		//given
		Mockito.when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception occured"));
		Mockito.when(helloWorldService.world()).thenCallRealMethod();

		// when
		hwcfe.handle()
			.thenAccept(s -> {
				assertEquals(s, " WORLD!HI COMPLETABLE FUTURE");
			})
			.join();

		// then
	}

	@Test
	void helloworld_multiple_3_async_calls_2() {

		//given
		Mockito.when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception occured"));
		Mockito.when(helloWorldService.world()).thenThrow(new RuntimeException("Exception occured"));

		// when
		hwcfe.handle()
			.thenAccept(s -> {
				assertEquals(s, "HI COMPLETABLE FUTURE");
			})
			.join();

		// then
	}

	@Test
	void helloworld_multiple_3_async_calls_3() {

		//given
		Mockito.when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception occured"));
		Mockito.when(helloWorldService.world()).thenThrow(new RuntimeException("Exception occured"));

		// when
		hwcfe.handleExceptionally()
			.thenAccept(s -> {
				assertEquals(s, "HI COMPLETABLE FUTURE");
			})
			.join();

		// then
	}

	@Test
	void helloworld_multiple_3_async_calls_4() {

		//given
		Mockito.when(helloWorldService.hello()).thenCallRealMethod();
		Mockito.when(helloWorldService.world()).thenCallRealMethod();

		// when
		hwcfe.handleExceptionally()
			.thenAccept(s -> {
				assertEquals(s, "HELLO WORLD!HI COMPLETABLE FUTURE");
			})
			.join();

		// then
	}

	@Test
	void helloworld_multiple_3_async_calls_5() {

		//given
		Mockito.when(helloWorldService.hello()).thenCallRealMethod();
		Mockito.when(helloWorldService.world()).thenCallRealMethod();

		// when
		hwcfe.handleWhenComplete()
			.thenAccept(s -> {
				assertEquals(s, "HELLO WORLD!HI COMPLETABLE FUTURE");
			})
			.join();

		// then
	}

	@Test
	void helloworld_multiple_3_async_calls_6() {

		//given
		Mockito.when(helloWorldService.hello()).thenThrow(new RuntimeException("Runtime exception"));
		Mockito.when(helloWorldService.world()).thenThrow(new RuntimeException("Runtime exception"));

		// when
		hwcfe.handleWhenComplete()
			.thenAccept(s -> {
				assertEquals(s, "HI COMPLETABLE FUTURE");
			})
			.join();

		// then
	}
}
