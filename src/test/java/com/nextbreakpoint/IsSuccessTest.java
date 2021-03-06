package com.nextbreakpoint;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IsSuccessTest {
	@Test
	public void shouldReturnFalseWhenFailure() {
		assertFalse(Try.failure(new Exception()).isSuccess());
	}

	@Test
	public void shouldReturnTrueWhenSuccessAndValueIsNull() {
		assertTrue(Try.success(null).isSuccess());
	}

	@Test
	public void shouldReturnTrueWhenSuccessAndValueIsNotNull() {
		assertTrue(Try.success("X").isSuccess());
	}

	@Test
	public void shouldReturnFalseWhenCallableThrowsException() {
		assertFalse(Try.of(() -> { throw new Exception(); }).isSuccess());
	}

	@Test
	public void shouldReturnTrueWhenCallableReturnsNull() {
		assertTrue(Try.of(() -> null).isSuccess());
	}

	@Test
	public void shouldReturnTrueWhenCallableReturnsValue() {
		assertTrue(Try.of(() -> "X").isSuccess());
	}
}
