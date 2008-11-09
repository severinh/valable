/* TestValaWordDetector.java
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

import org.eclipse.jface.text.rules.IWordDetector;

import valable.editors.util.ValaWordDetector;
import junit.framework.TestCase;

/**
 * Test that {@link ValaWordDetector} works correctly.
 */
public class TestValaWordDetector extends TestCase {
	
	private IWordDetector detector = new ValaWordDetector();
	
	
	/**
	 * Test that all the keywords in {@link IValaLanguageWords}
	 * return true.
	 */
	public void testKeywords() {
		for (String kw : IValaLanguageWords.keywords)
			assertTrue("Keyword: " + kw, check(detector, kw));
	}

	
	/**
	 * Test that all the base types in {@link IValaLanguageWords}
	 * return true.
	 */
	public void testBaseTypes() {
		for (String type : IValaLanguageWords.types)
			assertTrue("Types: " + type, check(detector, type));
	}

	
	/**
	 * Test that all the constants in {@link IValaLanguageWords}
	 * return true.
	 */
	public void testConstants() {
		for (String constant : IValaLanguageWords.constants)
			assertTrue("Constants: " + constant, check(detector, constant));
	}

	
	/**
	 * Test that other arbitrary identfiers are correctly handled.
	 */
	public void testRandomStrings() {
		assertCheck(true, "Bob");
		assertCheck(false, "Bob and Me");
		assertCheck(true, "get_foo_bar");
		assertCheck(false, "1abc");
		assertCheck(true, "abc1");
	}
	
	
	/**
	 * In Vala, keywords can be used as identifiers if they start
	 * with '@'.
	 */
	public void testKeywordsAsIdentifiers() {
		assertCheck(true, "@bob");
		assertCheck(false, "!bob");
	}
	
	
	/**
	 * Convenience method for checking a string returns the correct
	 * result.
	 * 
	 * @param expected The expected result of {@link #check(IWordDetector, String)}
	 * @param name The value to pass to {@linkplain #check(IWordDetector, String)}
	 */
	private void assertCheck(boolean expected, String name) {
		if (expected) {
			assertTrue(name, check(detector, name));
		} else {
			assertFalse(name, check(detector, name));
		}
	}

	
	/**
	 * @param detector Detector to use.
	 * @param word Word to check.
	 * @return true if <var>detector</var> returns true for
	 *    {@code word[0]} as a start character, and true for
	 *    every following character as a part character.
	 */
	private boolean check(IWordDetector detector, String word) {
		boolean result = detector.isWordStart(word.charAt(0));
		for (char c : word.substring(1).toCharArray()) {
			result &= detector.isWordPart(c);
		}
		
		return result;
	}
}
