/**
 * Copyright (C) 2007  Johann Prieur <johann.prieur@gmail.com>
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.valable.editors.util;

import org.eclipse.swt.graphics.RGB;

public interface IValaColorConstants {

	public static final RGB VALA_DEFAULT_COLOR = new RGB(0, 0, 0);
	public static final RGB VALA_KEYWORD_COLOR = new RGB(127, 0, 85);
	public static final RGB VALA_NUMBER_COLOR = VALA_DEFAULT_COLOR;
	public static final RGB VALA_TYPE_COLOR = VALA_KEYWORD_COLOR;
	public static final RGB VALA_STRING_COLOR = new RGB(42, 0, 255);
	public static final RGB VALA_STRING_TEMPLATE = new RGB(46, 52, 175);
	public static final RGB VALA_COMMENT_COLOR = new RGB(63, 127, 95);
	public static final RGB VALA_CCODE_COLOR = new RGB(64, 64, 64);

	public static final RGB GTKDOC_DEFAULT_COLOR = VALA_COMMENT_COLOR;
	public static final RGB GTKDOC_TAG_COLOR = new RGB(127, 159, 191);
	public static final RGB GTKDOC_DOCBOOK_COLOR = new RGB(127, 127, 159);
	public static final RGB GTKDOC_OTHER_COLOR = new RGB(63, 95, 191);

}
