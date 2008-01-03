/* ValaWordDetector.java
 *
 * Copyright (C) 2007  Johann Prieur <johann.prieur@gmail.com>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
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
