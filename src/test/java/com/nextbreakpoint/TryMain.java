package com.nextbreakpoint;

import java.io.FileInputStream;

public class TryMain {
	public static void main(String[] args) {
		// "X".charAt(1) will throw an unchecked exception !
		System.out.println(Try.of(() -> "X".charAt(1)).getOrElse('Y'));

		// new FileInputStream("") will throw a checked exception !
		System.out.println(Try.of(() -> new FileInputStream("")).getOrElse(null));
	}
}