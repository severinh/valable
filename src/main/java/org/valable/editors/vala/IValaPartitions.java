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

/**
 * Definition of Vala partitioning and its partitions.
 */
public interface IValaPartitions {

	/**
	 * The identifier of the Vala partitioning.
	 */
	public static final String VALA_PARTITIONING = "___vala_partitioning";

	/**
	 * The identifier of the multi-line comment partition content type.
	 */
	public static final String VALA_MULTILINE_COMMENT = "__vala_multiline_comment";

	/**
	 * The identifier of the gtk-doc partition content type.
	 */
	public static final String GTKDOC_COMMENT = "__gtkdoc_comment";

	/**
	 * The identifier of the multi-line string partition content type.
	 */
	public static final String VALA_MULTILINE_STRING = "__vala_multiline_string";

	/**
	 * The identifier of the verbatim string partition content type.
	 */
	public static final String VALA_VERBATIM_STRING = "__vala_verbatim_string";

	/**
	 * The identifier of the string templates partition content type.
	 */
	public static final String VALA_STRING_TEMPLATES = "__vala_string_templates";

	/**
	 * The identifier of the Vala character partition content type.
	 */
	public static final String VALA_CHARACTER = "__vala_character";

}
