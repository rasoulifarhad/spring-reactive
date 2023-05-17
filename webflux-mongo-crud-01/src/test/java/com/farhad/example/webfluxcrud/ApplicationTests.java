package com.farhad.example.webfluxcrud;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.farhad.example.webfluxcrud.domain.ShowEvent;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ApplicationTests {

	@Autowired
	private WebTestClient webTestClient;
	@Test
	void contextLoads() {
	}

	@Test
	public void showEventsTest() { 
		FluxExchangeResult<ShowEvent> result = 
						webTestClient
								.get()
								.uri("/shows/2/events")
								.accept(MediaType.TEXT_EVENT_STREAM)
								.exchange()
								.expectStatus().isOk()
								.returnResult(ShowEvent.class);
		Flux<ShowEvent> eventFlux =  result.getResponseBody();

		// eventFlux.subscribe(System.out::println);
		StepVerifier
				.create(eventFlux)
				.expectNextCount(10)
				.thenCancel()
				.verify();
	}

	@Test 
	public void droppedElements() {
		Flux<Integer> source = Flux.<Integer>create( emitter -> {
			emitter.next(1);
			emitter.next(2);
			emitter.next(3);
			emitter.complete();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			emitter.next(4);
		}).filter(n -> n % 2 ==0)
		;

		StepVerifier.create(source)
				.expectNext(2)
				.expectComplete()
				.verifyThenAssertThat()
				.hasDropped(4)
				.tookLessThan(Duration.ofMillis(1050))
				;
	}
}
