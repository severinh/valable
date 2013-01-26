/**
 * Copyright (C) 2007  Johann Prieur <johann.prieur@gmail.com>
 * Copyright (C) 2011  Marco Trevisan (Treviño) <mail@3v1n0.net>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.valable.editors.vala;

import java.util.ArrayList;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;

/**
 * This scanner recognizes the gtk-doc comments and Vala multi-line comments.
 */
public class ValaPartitionScanner extends RuleBasedPartitionScanner implements
		IValaPartitions {

	/**
	 * Creates the partitioner and sets up the appropriate rules.
	 */
	public ValaPartitionScanner() {
		IToken multilineCommentToken = new Token(VALA_MULTILINE_COMMENT);
		IToken gtkdocCommentToken = new Token(GTKDOC_COMMENT);
		IToken multilineStringToken = new Token(VALA_MULTILINE_STRING);
		IToken verbatimStringToken = new Token(VALA_VERBATIM_STRING);
		IToken stringTemplatesToken = new Token(VALA_STRING_TEMPLATES);
		IToken characterToken = new Token(VALA_CHARACTER);

		ArrayList<IPredicateRule> rules = new ArrayList<IPredicateRule>();

		// Rule for gtk-doc comments
		rules.add(new MultiLineRule("/**", "*/", gtkdocCommentToken));

		// Rule for multi-line comments
		rules.add(new MultiLineRule("/*", "*/", multilineCommentToken));

		// Rule for verbatim strings
		rules.add(new MultiLineRule("\"\"\"", "\"\"\"", verbatimStringToken,
				'\\'));

		// Rule for multi-line strings
		rules.add(new MultiLineRule("@\"", "\"", stringTemplatesToken, '\\'));
		rules.add(new MultiLineRule("\"", "\"", multilineStringToken, '\\'));

		// Rule for character constants
		rules.add(new SingleLineRule("'", "'", characterToken, '\\'));

		IPredicateRule[] rulesArray = new IPredicateRule[rules.size()];
		rules.toArray(rulesArray);
		setPredicateRules(rulesArray);
	}

}