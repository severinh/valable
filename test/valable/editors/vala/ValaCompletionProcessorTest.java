/**
 * Copyright (C) 2008  Andrew Flegg <andrew@bleb.org>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.editors.vala;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;

import org.junit.Test;

/**
 * Test that {@link ValaCompletionProcessor} works correctly.
 */
public class ValaCompletionProcessorTest {

	/**
	 * Test that {@link ValaCompletionProcessor#IDENTIFIER} and
	 * {@link ValaCompletionProcessor#LAST_IDENTIFIER} are correct.
	 */
	@Test
	public void testRegexps() {
		String text = "foo";
		assertTrue("Simple identifier", ValaCompletionProcessor.IDENTIFIER
				.matcher(text).matches());

		text = "foo bar";
		assertFalse("Two identifiers", ValaCompletionProcessor.IDENTIFIER
				.matcher(text).matches());

		text = "hello {\n  wor";
		Matcher matcher = ValaCompletionProcessor.LAST_IDENTIFIER.matcher(text);
		assertTrue("Last identifier found", matcher.matches());
		assertEquals("Last identifier value", "wor", matcher.group(1));
	}

}
