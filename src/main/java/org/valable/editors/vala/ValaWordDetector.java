/**
 * Copyright (C) 2011  Marco Trevisan (Treviño) <mail@3v1n0.net>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.valable.editors.vala;

import org.eclipse.jface.text.rules.IWordDetector;

public class ValaWordDetector implements IWordDetector, IValaLanguageWords {

	@Override
	public boolean isWordPart(char c) {
		return Character.isJavaIdentifierPart(c);
	}

	@Override
	public boolean isWordStart(char c) {
		// In Vala you can use any keyword when using '@' as prefix.
		return Character.isJavaIdentifierStart(c) || c == '@' || c == '#';
	}

}