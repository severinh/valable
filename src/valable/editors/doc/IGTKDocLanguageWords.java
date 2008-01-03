/* IGTKDocLanguageWords.java
 *
 * Copyright (C) 2007  Johann Prieur <johann.prieur@gmail.com>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package valable.editors.doc;

public interface IGTKDocLanguageWords {
	
	String[] section = { "short_description",
					     "see_also",
					     "stability",
					     "include" };

	String[] function = { "Returns",
						  "Deprecated",
						  "Since" };
	
	String[] docbook = { "link",
						 "function", 
						 "example",
						 "title",
						 "programlisting",
						 "informalexample",
						 "itemizedlist",
						 "listitem",
						 "para",
						 "note",
						 "type",
						 "structname",
						 "structfield",
						 "classname",
						 "emphasis",
						 "filename" }; 	
}
