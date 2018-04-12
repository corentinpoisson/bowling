


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

@Tag("Test")
public class StringTest {
	
	@Test
	@DisplayName("Test")
	public void testString(TestInfo testInfo) throws Exception {
		assertEquals("This is a string", retournerThisIsAString());
	}

	private String retournerThisIsAString() {
		return "This is a string";
	}
}
