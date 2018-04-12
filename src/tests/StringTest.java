package tests;

import junit.framework.*;

public class StringTest extends TestCase {
	public void testString() throws Exception {
		assertEquals("This is a string", retournerThisIsAString());
	}

	private String retournerThisIsAString() {
		return "This is a string";
	}
}
