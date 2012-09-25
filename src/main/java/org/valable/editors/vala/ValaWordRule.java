/**
 * Copyright (C) 2011  Marco Trevisan (Treviño) <mail@3v1n0.net>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.valable.editors.vala;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;

/**
 * @author bb (imported from emonic) This class extends a WordRule: It assures
 *         that the words to find are not part of other words but surrounded
 *         with spaces and brackets
 */
public class ValaWordRule extends WordRule {

	private final char[] VALABOUNDS = { ' ', '(', ')', ';', '{', '}', '[', ']',
			'\n', '\t', '.', '/', '+', '-', '*', '=', '\r', ',' };
	private final StringBuffer fBuffer;

	public ValaWordRule(IWordDetector detector) {
		super(detector);
		fBuffer = new StringBuffer();
	}

	public ValaWordRule(IWordDetector detector, IToken defaultToken) {
		super(detector, defaultToken);
		fBuffer = new StringBuffer();
	}

	// This method is basically copied from the super class,
	// but looks whether the word stands alone
	@Override
	public IToken evaluate(ICharacterScanner scanner) {
		boolean isWordStart = false;
		scanner.unread();
		int b = scanner.read();
		if (b == -1) {
			// if read() returns -1, we are at beginning of document, so this
			// could be a word start
			isWordStart = true;
		} else {
			for (int i = 0; i < VALABOUNDS.length; i++) {
				if (b == VALABOUNDS[i]) {
					isWordStart = true;
				}
			}
		}

		int c = scanner.read();
		isWordStart = fDetector.isWordStart((char) c) && isWordStart;

		if (isWordStart) {
			if (fColumn == UNDEFINED || fColumn == scanner.getColumn() - 1) {
				fBuffer.setLength(0);
				do {
					fBuffer.append((char) c);
					c = scanner.read();
				} while (c != ICharacterScanner.EOF
						&& fDetector.isWordPart((char) c));
				// Assure the next token is a boundary
				boolean realWordEnd = false;
				scanner.unread();
				if (c == ICharacterScanner.EOF) {
					realWordEnd = true;
				} else {
					// Look ahead
					c = scanner.read();
					scanner.unread();
					if (c == ICharacterScanner.EOF) {
						realWordEnd = true;
					} else {
						for (int i = 0; i < VALABOUNDS.length; i++) {
							if (c == VALABOUNDS[i])
								realWordEnd = true;
						}
					}
				}

				if (realWordEnd) {
					IToken token = (IToken) fWords.get(fBuffer.toString());
					if (token != null) {
						return token;
					}
				}
				if (fDefaultToken.isUndefined()) {
					unreadBuffer(scanner);
				}

				return fDefaultToken;
			}
		}
		scanner.unread();
		return Token.UNDEFINED;

	}

}
