/**
 * Copyright (C) 2007  Johann Prieur <johann.prieur@gmail.com>
 * Copyright (C) 2011  Marco Trevisan (Treviño) <mail@3v1n0.net>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.editors.vala;

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

import valable.editors.util.ColorManager;
import valable.editors.util.IColorConstants;
import valable.editors.util.WhitespaceDetector;

public class ValaCodeScanner extends RuleBasedScanner implements
		IValaLanguageWords {

	public ValaCodeScanner(ColorManager manager) {
		IToken keywordToken = new Token(new TextAttribute(
				manager.getColor(IColorConstants.KEYWORD), null, SWT.BOLD));
		IToken typeToken = new Token(new TextAttribute(
				manager.getColor(IColorConstants.TYPE)));
		IToken numberToken = new Token(new TextAttribute(
				manager.getColor(IColorConstants.NUMBER)));
		IToken characterToken = new Token(new TextAttribute(
				manager.getColor(IColorConstants.NUMBER)));
		IToken commentToken = new Token(new TextAttribute(
				manager.getColor(IColorConstants.COMMENT)));
		IToken defaultToken = new Token(new TextAttribute(
				manager.getColor(IColorConstants.DEFAULT)));

		setDefaultReturnToken(defaultToken);

		ArrayList<IRule> rules = new ArrayList<IRule>();

		// Rule for single line comment
		rules.add(new EndOfLineRule("//", commentToken));

		// Rule for char
		rules.add(new SingleLineRule("'", "'", characterToken, '\\'));

		// Rule for whitespaces
		rules.add(new WhitespaceRule(new WhitespaceDetector()));

		// Rule for numbers
		rules.add(new NumberRule(numberToken));

		// Rule for keywords, types and constants
		WordRule wordRule = new ValaWordRule(new ValaWordDetector(),
				defaultToken);
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

		IRule[] rulesArray = new IRule[rules.size()];
		rules.toArray(rulesArray);
		setRules(rulesArray);
	}

}
