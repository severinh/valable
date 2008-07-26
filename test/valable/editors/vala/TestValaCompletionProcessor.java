/* TestValaCompletionProcessor.java
 *
 * Copyright (C) 2008  Andrew Flegg <andrew@bleb.org>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package valable.editors.vala;

import java.util.regex.Matcher;

import valable.model.ValaEntity;

import junit.framework.TestCase;

/**
 * Test that {@link ValaCompletionProcessor} works correctly.
 */
public class TestValaCompletionProcessor extends TestCase {
	
	
	/**
	 * Test that {@link ValaEntity#IDENTIFIER}
	 * and {@link ValaCompletionProcessor#LAST_IDENTIFIER} are
	 * correct.
	 */
	public void testRegexps() {
		String text = "foo";
		assertTrue("Simple identifier", ValaEntity.IDENTIFIER.matcher(text).matches());

		text = "foo bar";
		assertFalse("Two identifiers", ValaEntity.IDENTIFIER.matcher(text).matches());

		text = "hello {\n  wor";
		Matcher matcher = ValaCompletionProcessor.LAST_IDENTIFIER.matcher(text);
		assertTrue("Last identifier found", matcher.matches());
		assertEquals("Last identifier value", "wor", matcher.group(1));
	}
}
