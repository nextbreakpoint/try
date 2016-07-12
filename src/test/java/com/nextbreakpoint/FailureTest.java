package com.nextbreakpoint;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

public class FailureTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void shouldThrowNullPointerExceptionWhenExceptionIsNull() {
		exception.expect(NullPointerException.class);
		Try.failure(null);
	}

	@Test
	public void shouldNotBeNullWhenExceptionIsNotNull() {
		assertNotNull(Try.failure(new Exception()));
	}

	@Test
	public void shouldNotBeNullWhenExceptionIsNotNullAndMapperIsNotNull() {
		assertNotNull(Try.failure(new Exception()).withMapper(e -> e));
	}

	@Test
	public void shouldThrowNullPointerExceptionWhenMapperIsNull() {
		exception.expect(NullPointerException.class);
		Try.failure(new Exception()).withMapper(null);
	}

	@Test
	public void isFailureShouldReturnTrue() {
		assertTrue(Try.failure(new Exception()).isFailure());
	}

	@Test
	public void getShouldThrowNoSuchElementException() {
		exception.expect(NoSuchElementException.class);
		Try.failure(new Exception()).get();
	}

	@Test
	public void getOrElseShouldReturnDefaultValue() {
		assertEquals("X", Try.failure(new Exception()).getOrElse("X"));
	}

	@Test
	public void getOrThrowShouldThrowException() throws Exception {
		exception.expect(IllegalAccessException.class);
		Try.failure(new IllegalAccessException()).getOrThrow();
	}

	@Test
	public void getOrThrowWithDefaultValueShouldThrowException() throws Exception {
		exception.expect(IllegalAccessException.class);
		Try.failure(new IllegalAccessException()).getOrThrow("X");
	}

	@Test
	public void isPresentShouldReturnFalse() throws Exception {
		assertFalse(Try.failure(new Exception()).isPresent());
	}

	@Test
	public void ifPresentWithConsumerShouldNotCallConsumer() {
		Consumer<Object> consumer = mock(Consumer.class);
		Try.failure(new Exception()).ifPresent(consumer);
		verify(consumer, times(0)).accept(any());
	}

	@Test
	public void ifPresentOrThrowShouldThrowException() throws Exception {
		exception.expect(Exception.class);
		Consumer<Object> consumer = mock(Consumer.class);
		Try.failure(new Exception()).ifPresentOrThrow(consumer);
	}

	@Test
	public void ifFailureShouldCallConsumer() {
		Consumer<Exception> consumer = mock(Consumer.class);
		Try.failure(new Exception()).ifFailure(consumer);
		verify(consumer, times(1)).accept(any());
	}

	@Test
	public void throwExceptionShouldThrowException() throws Exception {
		exception.expect(Exception.class);
		Try.failure(new Exception()).throwException();
	}

	@Test
	public void valueShouldReturnEmptyOptional() {
		assertFalse(Try.failure(new Exception()).value().isPresent());
	}

	@Test
	public void mapShouldNotCallFunction() {
		Function<Object, Object> function = mock(Function.class);
		Try.failure(new Exception()).map(function).getOrElse(null);
		verify(function, times(0)).apply(any());
	}

	@Test
	public void flatMapShouldNotCallFunction() {
		Function<Object, Try<Object, Exception>> function = mock(Function.class);
		Try.failure(new Exception()).flatMap(function).getOrElse(null);
		verify(function, times(0)).apply(any());
	}

	@Test
	public void shouldNotCallSuccessConsumer() {
		Consumer<Object> consumer = mock(Consumer.class);
		Try.failure(new Exception()).onSuccess(consumer).getOrElse(null);
		verify(consumer, times(0)).accept(anyObject());
	}

	@Test
	public void shouldCallFailureConsumer() {
		Consumer<Exception> consumer = mock(Consumer.class);
		Try.failure(new Exception()).onFailure(consumer).getOrElse(null);
		verify(consumer, times(1)).accept(anyObject());
	}

	@Test
	public void shouldThrowsIOException() throws IOException {
		exception.expect(IOException.class);
		Try.failure(new Exception()).withMapper(testMapper()).throwException();
	}

	private static Function<Exception, IOException> testMapper() {
		return e -> (e instanceof IOException) ? (IOException)e : new IOException("IO Error", e);
	}
}
