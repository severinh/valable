/**
 * Copyright (C) 2007  Johann Prieur <johann.prieur@gmail.com>
 * Copyright (C) 2011  Marco Trevisan (Treviño) <mail@3v1n0.net>
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.valable.editors.vala;

import java.util.ArrayList;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.NumberRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import org.valable.editors.util.ColorManager;
import org.valable.editors.util.IValaColorConstants;
import org.valable.editors.util.WhitespaceDetector;

public class ValaCodeScanner extends RuleBasedScanner implements
		IValaLanguageWords {

	public ValaCodeScanner(ColorManager manager) {
		Color keywordColor = manager.getColor(IValaColorConstants.VALA_KEYWORD_COLOR);
		Color typeColor = manager.getColor(IValaColorConstants.VALA_TYPE_COLOR);
		Color numberColor = manager.getColor(IValaColorConstants.VALA_NUMBER_COLOR);
		Color commentColor = manager.getColor(IValaColorConstants.VALA_COMMENT_COLOR);
		Color defaultColor = manager.getColor(IValaColorConstants.VALA_DEFAULT_COLOR);
		Color ccodeColor = manager.getColor(IValaColorConstants.VALA_CCODE_COLOR);

		IToken keywordToken = new Token(new TextAttribute(keywordColor, null,
				SWT.BOLD));
		IToken typeToken = new Token(new TextAttribute(typeColor, null,
				SWT.BOLD));
		IToken numberToken = new Token(new TextAttribute(numberColor));
		IToken characterToken = new Token(new TextAttribute(numberColor));
		IToken commentToken = new Token(new TextAttribute(commentColor));
		IToken defaultToken = new Token(new TextAttribute(defaultColor));
		IToken ccodeToken = new Token(new TextAttribute(ccodeColor));

		setDefaultReturnToken(defaultToken);

		ArrayList<IRule> rules = new ArrayList<IRule>();

		// Rule for single line comment
		rules.add(new EndOfLineRule("//", commentToken));

		// Rule for char
		rules.add(new SingleLineRule("'", "'", characterToken, '\\'));

		// Rule for whitespace
		rules.add(new WhitespaceRule(new WhitespaceDetector()));

		// Rule for numbers
		rules.add(new NumberRule(numberToken));

		// Rule for keywords, types and constants
		ValaWordDetector wordDetector = new ValaWordDetector();
		WordRule wordRule = new ValaWordRule(wordDetector, defaultToken);
		for (String keyword : KEYWORDS) {
			wordRule.addWord(keyword, keywordToken);
		}
		for (String type : TYPES) {
			wordRule.addWord(type, typeToken);
		}
		for (String constant : CONSTANTS) {
			wordRule.addWord(constant, typeToken);
		}
		rules.add(wordRule);

		// Rule for CCode
		rules.add(new SingleLineRule("[", "]", ccodeToken, '\\'));

		IRule[] rulesArray = new IRule[rules.size()];
		rules.toArray(rulesArray);
		setRules(rulesArray);
	}

}
