/* ValaWordDetector.java
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

import org.eclipse.jface.text.rules.IWordDetector;

import valable.editors.vala.IValaLanguageWords;

public class ValaWordDetector implements IWordDetector, IValaLanguageWords {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.rules.IWordDetector#isWordPart(char)
	 */
	@Override
	public boolean isWordPart(char c) {
		for(String keyword : keywords) 
			if (keyword.indexOf(c) != -1)
				return true;	

		for(String type : types) 
			if (type.indexOf(c) != -1) 
				return true;

		for(String constant : constants) 
			if (constant.indexOf(c) != -1) 
				return true;
		
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.rules.IWordDetector#isWordStart(char)
	 */
	@Override
	public boolean isWordStart(char c) {
		for(String keyword : keywords) 
			if (keyword.charAt(0) == c)
				return true;	

		for (String type : types) 
			if (type.charAt(0) == c) 
				return true;

		for (String constant : constants) 
			if (constant.charAt(0) == c) 
				return true;	
	
		return false;
	}

}
