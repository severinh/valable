/**
 * Copyright (C) 2007  Johann Prieur <johann.prieur@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.valable.editors.gtkdoc;

import java.util.ArrayList;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;

import org.valable.editors.util.ColorManager;
import org.valable.editors.util.IValaColorConstants;
import org.valable.editors.util.WhitespaceDetector;

public class GTKDocScanner extends RuleBasedScanner 
	implements IGTKDocLanguageWords {

	public GTKDocScanner(ColorManager manager) {
		IToken def = new Token(new TextAttribute(
				manager.getColor(IValaColorConstants.GTKDOC_DEFAULT_COLOR)));
		IToken tag = new Token(new TextAttribute(
				manager.getColor(IValaColorConstants.GTKDOC_TAG_COLOR), null,
				SWT.BOLD));
		IToken xml = new Token(new TextAttribute(
				manager.getColor(IValaColorConstants.GTKDOC_DOCBOOK_COLOR), null,
				SWT.BOLD));
		IToken other = new Token(new TextAttribute(
				manager.getColor(IValaColorConstants.GTKDOC_OTHER_COLOR), null,
				SWT.BOLD));

		setDefaultReturnToken(def);
		
		ArrayList<IRule> rules = new ArrayList<IRule>();
		
		// Rule for whitespaces
		rules.add(new WhitespaceRule(new WhitespaceDetector()));
		
		// Rule for tags
		WordRule tagWordRule = new WordRule(new GTKDocWordDetector('@'), tag);
		for(String s : section)
			tagWordRule.addWord(s, tag);
		rules.add(tagWordRule);
		
		// Rule for constants
		WordRule constantWordRule = new WordRule(new GTKDocWordDetector('%'), tag);
		rules.add(constantWordRule);
		
		// Rule for symbols
		WordRule symbolWordRule = new WordRule(new GTKDocWordDetector('#'), tag);
		rules.add(symbolWordRule);
		
		// Rules for docbook tags
		for(String d : docbook) {
			rules.add(new SingleLineRule(String.format("<%s", d), ">", xml));
			rules.add(new SingleLineRule(String.format("</%s", d), ">", xml));
		}
		
		// Rules for function tags
		for(String f : function)
			rules.add(new SingleLineRule(String.format("%s:", f), " ", other));
		
		rules.add(new SingleLineRule(" ", "()", tag));
		
		IRule[] r = new IRule[rules.size()];
		rules.toArray(r);
		setRules(r);
	}

	class GTKDocWordDetector implements IWordDetector {
		
		private final char begin;
		
		public GTKDocWordDetector(char begin) {
			this.begin = begin;
		}
		
		@Override
		public boolean isWordPart(char c) {
			return (new Character(c)).toString().matches("\\w");
		}

		@Override
		public boolean isWordStart(char c) {
			return (c == begin);
		}	
	}
}
