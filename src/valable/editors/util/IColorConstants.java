/* IValaColorConstants.java
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
package valable.editors.util;

import org.eclipse.swt.graphics.RGB;

public interface IColorConstants {
	
	public static final RGB DEFAULT = new RGB(0, 0, 0);
	public static final RGB KEYWORD = new RGB(127, 0, 85);
	public static final RGB TYPE = new RGB(64, 0, 200);
	public static final RGB STRING = new RGB(42, 0, 255);
	
	public static final RGB COMMENT = new RGB(63, 127, 95);
	
	public static final RGB GTKDOC_DEFAULT = new RGB(63, 127, 95);
	public static final RGB GTKDOC_TAG = new RGB(127, 159, 191);
	public static final RGB GTKDOC_DOCBOOK = new RGB(127, 127, 159);
	public static final RGB GTKDOC_OTHER = new RGB(63, 95, 191);

}
