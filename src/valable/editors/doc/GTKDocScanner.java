/* GTKDocScanner.java
 *
 * Copyright (C) 2007  Johann Prieur <johann.prieur@gmail.com>
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package valable.editors.doc;

import java.util.ArrayList;

import org.eclipse.jface.text.rules.*;
import org.eclipse.jface.text.*;
import org.eclipse.swt.SWT;

import valable.editors.util.ColorManager;
import valable.editors.util.WhitespaceDetector;

public class GTKDocScanner extends RuleBasedScanner 
	implements IGTKDocLanguageWords {

	public GTKDocScanner(ColorManager manager) {
		IToken def = new Token(new TextAttribute(manager.getColor(ColorManager.GTKDOC_DEFAULT)));
		IToken tag = new Token(new TextAttribute(manager.getColor(ColorManager.GTKDOC_TAG),
												 null, SWT.BOLD));
		IToken xml = new Token(new TextAttribute(manager.getColor(ColorManager.GTKDOC_DOCBOOK),
				   								 null, SWT.BOLD));
		IToken other = new Token(new TextAttribute(manager.getColor(ColorManager.GTKDOC_OTHER),
				       							   null, SWT.BOLD));

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
		
		private char begin;
		
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
