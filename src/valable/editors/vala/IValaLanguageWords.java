/* IValaLanguageWords.java
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
