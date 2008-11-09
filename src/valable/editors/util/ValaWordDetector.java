/* ValaWordDetector.java
 *
 * Copyright (C) 2007  Johann Prieur <johann.prieur@gmail.com>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package valable.editors.util;

import org.eclipse.jface.text.rules.IWordDetector;

import valable.editors.vala.IValaLanguageWords;

/**
 * Identify Vala words. The rules governing a Vala word are
 * identical to those governing a Java word. The methods on this class
 * should <em>not</em> return true for only characters which exist in
 * {@link IValaLanguageWords}: instead, this is used to identify
 * <em>any</em> possible Vala identifiers.
 * 
 * <p>Therefore, both methods delegate to Java identifier
 * methods on {@link Character}.</p>
 */
public class ValaWordDetector implements IWordDetector {

	/**
	 * Return <var>true</var> if <var>c</var> <strong>could</strong>
	 * be part of a Vala word. Delegates to {@link Character#isJavaIdentifierPart(char)}. 
	 * 
	 * @see org.eclipse.jface.text.rules.IWordDetector#isWordPart(char)
	 */
	@Override
	public boolean isWordPart(char c) {
		return Character.isJavaIdentifierPart(c);
	}

	
	/**
	 * Return <var>true</var> if <var>c</var> <strong>could</strong>
	 * be part of a Vala word. Delegates to {@link Character#isJavaIdentifierStart(char)}
	 * 
	 * @see org.eclipse.jface.text.rules.IWordDetector#isWordStart(char)
	 */
	@Override
	public boolean isWordStart(char c) {
		return Character.isJavaIdentifierStart(c);
	}
}
