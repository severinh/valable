/* ValaPartitionScanner.java
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

public class ValaPartitionScanner extends RuleBasedPartitionScanner {
	
	public final static String VALA_MULTILINE_COMMENT = "__vala_multiline_comment";
	public final static String GTKDOC_COMMENT = "__gtkdoc_comment";

	public ValaPartitionScanner() {
		IToken valaMultilineComment = new Token(VALA_MULTILINE_COMMENT);
		IToken gtkdocComment = new Token(GTKDOC_COMMENT);
		
		ArrayList<IPredicateRule> rules = new ArrayList<IPredicateRule>();
		
		// Rule for gtk-doc comments
		rules.add(new MultiLineRule("/**", "*/", gtkdocComment));
		
		// Rule for multi line comments
		rules.add(new MultiLineRule("/*", "*/", valaMultilineComment));
		
		// Rule for single line comments
		rules.add(new EndOfLineRule("//", Token.UNDEFINED));
		
		IPredicateRule[] r = new IPredicateRule[rules.size()];
		rules.toArray(r);
		setPredicateRules(r);
	}
}
