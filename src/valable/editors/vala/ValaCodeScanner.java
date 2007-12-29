/* ValaCodeScanner.java
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
package valable.editors.vala;

import java.util.ArrayList;

import org.eclipse.jface.text.rules.*;
import org.eclipse.jface.text.*;
import org.eclipse.swt.SWT;

import valable.editors.util.ValaColorManager;
import valable.editors.util.ValaWhitespaceDetector;
import valable.editors.util.ValaWordDetector;

public class ValaCodeScanner extends RuleBasedScanner 
	implements IValaLanguageWords {

	public ValaCodeScanner(ValaColorManager manager) {
		IToken keyword = new Token(new TextAttribute(manager.getColor(ValaColorManager.KEYWORD), 
													 null, SWT.BOLD));
		IToken type = new Token(new TextAttribute(manager.getColor(ValaColorManager.TYPE), 
												  null, SWT.BOLD));
		IToken string = new Token(new TextAttribute(manager.getColor(ValaColorManager.STRING)));
		IToken comment = new Token(new TextAttribute(manager.getColor(ValaColorManager.COMMENT)));
		IToken other = new Token(new TextAttribute(manager.getColor(ValaColorManager.DEFAULT)));
		
		setDefaultReturnToken(other);
		
		ArrayList<IRule> rules = new ArrayList<IRule>();
		
		// Rule for single line comment
		rules.add(new EndOfLineRule("//", comment));
		
		// Rule for strings
		rules.add(new SingleLineRule("\"", "\"", string));
		
		// Rule for whitespaces
		rules.add(new WhitespaceRule(new ValaWhitespaceDetector()));
		
		// Rule for keywords, types and constants
		WordRule wordRule = new WordRule(new ValaWordDetector(), other);
		for(String kw : keywords)
			wordRule.addWord(kw, keyword);
		for(String t : types)
			wordRule.addWord(t, type);
		for(String c : constants)
			wordRule.addWord(c, type);
		rules.add(wordRule);

		IRule[] r = new IRule[rules.size()];
		rules.toArray(r);
		setRules(r);
	}
}
