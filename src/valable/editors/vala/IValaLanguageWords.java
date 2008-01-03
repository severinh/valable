/* IValaLanguageWords.java
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

public interface IValaLanguageWords {
	
	String[] keywords = { "abstract", 
						  "base", 
						  "break", 
						  "case", 
						  "catch", 
						  "class", 
						  "const", 
						  "construct", 
						  "continue", 
						  "default", 
						  "do", 
						  "else",
						  "enum", 
						  "finally", 
						  "for", 
						  "foreach", 
						  "get", 
						  "if", 
						  "in", 
						  "interface",
						  "is", 
						  "lock", 
						  "namespace", 
						  "new", 
						  "out", 
						  "override", 
						  "private",
						  "protected", 
						  "public", 
						  "ref", 
						  "return", 
						  "set", 
						  "sizeof",
						  "static", 
						  "struct", 
						  "switch", 
						  "this", 
						  "throw", 
						  "throws", 
						  "try",
						  "typeof", 
						  "using", 
						  "var", 
						  "virtual", 
						  "weak", 
						  "while" };	
	
	String[] types = { "char", 
					   "delegate", 
					   "double",
					   "float", 
					   "int", 
					   "signal",
					   "string",
					   "uchar",
					   "uint",
					   "unichar" };
	
	String[] constants = { "false", 
						   "null", 
						   "true" };

}
