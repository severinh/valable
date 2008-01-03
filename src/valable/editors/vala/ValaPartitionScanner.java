/* ValaPartitionScanner.java
 *
 * Copyright (C) 2007  Johann Prieur <johann.prieur@gmail.com>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
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
