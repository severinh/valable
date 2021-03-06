/**
 * Copyright (C) 2011  Marco Trevisan (Treviño) <mail@3v1n0.net>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.valable.editors.vala;

import java.util.ArrayList;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordPatternRule;
import org.eclipse.swt.SWT;

import org.valable.editors.util.ColorManager;
import org.valable.editors.util.IValaColorConstants;
import org.valable.editors.util.ParentesizedRule;
import org.valable.editors.util.WhitespaceDetector;

public class ValaStringTemplateScanner extends RuleBasedScanner {

	public ValaStringTemplateScanner(ColorManager manager) {
		IToken defaultToken = new Token(new TextAttribute(
				manager.getColor(IValaColorConstants.VALA_STRING_COLOR)));
		IToken templateToken = new Token(new TextAttribute(
				manager.getColor(IValaColorConstants.VALA_STRING_TEMPLATE), null,
				SWT.BOLD));

		setDefaultReturnToken(defaultToken);

		ArrayList<IRule> rules = new ArrayList<IRule>();

		// Rule for whitespaces
		rules.add(new WhitespaceRule(new WhitespaceDetector()));

		rules.add(new ParentesizedRule("$", templateToken));
		rules.add(new WordPatternRule(new ValaWordDetector(), "$", "",
				templateToken));

		IRule[] rulesArray = new IRule[rules.size()];
		rules.toArray(rulesArray);
		setRules(rulesArray);
	}

}
