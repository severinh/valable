/**
 * Copyright (C) 2007  Johann Prieur <johann.prieur@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.editors.util;

import org.eclipse.swt.graphics.RGB;

public interface IColorConstants {

	public static final RGB DEFAULT_COLOR = new RGB(0, 0, 0);
	public static final RGB KEYWORD_COLOR = new RGB(127, 0, 85);
	public static final RGB NUMBER_COLOR = DEFAULT_COLOR;
	public static final RGB TYPE_COLOR = KEYWORD_COLOR;
	public static final RGB STRING_COLOR = new RGB(42, 0, 255);
	public static final RGB STRING_TEMPLATE = new RGB(46, 52, 175);
	public static final RGB COMMENT_COLOR = new RGB(63, 127, 95);
	public static final RGB CCODE_COLOR = new RGB(64, 64, 64);

	public static final RGB GTKDOC_DEFAULT_COLOR = COMMENT_COLOR;
	public static final RGB GTKDOC_TAG_COLOR = new RGB(127, 159, 191);
	public static final RGB GTKDOC_DOCBOOK_COLOR = new RGB(127, 127, 159);
	public static final RGB GTKDOC_OTHER_COLOR = new RGB(63, 95, 191);

}
