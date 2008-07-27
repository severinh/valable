/* ValaLabelProvider.java
 *
 * Copyright (C) 2008  Andrew Flegg <andrew@bleb.org>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package valable.outline;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.swt.graphics.Image;

import valable.ValaPlugin;
import valable.ValaPlugin.ImageType;
import valable.model.ValaField;
import valable.model.ValaMethod;
import valable.model.ValaPackage;
import valable.model.ValaSource;
import valable.model.ValaType;
import valable.model.ValaEntity.Visibility;


/**
 * Provide a label for a given element.
 */
class ValaLabelProvider extends LabelProvider {
	
	/**
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		if (element instanceof TreeNode)
			element = ((TreeNode)element).getValue();
		
		if (element instanceof ValaSource) {
			return ValaPlugin.findImage(ImageType.FILE, Visibility.DEFAULT);
			
		} else if (element instanceof ValaType) {
			return ValaPlugin.findImage(ImageType.CLASS, Visibility.DEFAULT);
			
		} else if (element instanceof ValaPackage) {
			return ValaPlugin.findImage(ImageType.PACKAGE, Visibility.DEFAULT);
			
		} else if (element instanceof ValaField) {
			return ValaPlugin.findImage(ImageType.FIELD, ((ValaField)element).getVisibility());
			
		} else if (element instanceof ValaMethod) {
			return ValaPlugin.findImage(ImageType.METHOD, ((ValaMethod)element).getVisibility());
		}
		
		return super.getImage(element);
	}
	
	
	/**
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof TreeNode)
			element = ((TreeNode)element).getValue();
		
		return super.getText(element);
	}
}
