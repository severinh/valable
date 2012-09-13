/**
 * Copyright (C) 2008  Andrew Flegg <andrew@bleb.org>
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.outline;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.swt.graphics.Image;

import valable.ValaPlugin;
import valable.ValaPluginConstants;
import valable.model.ValaSymbol;
import valable.model.ValaSymbolImageProvider;
import valable.model.ValaField;
import valable.model.ValaMethod;
import valable.model.ValaPackage;
import valable.model.ValaSource;

/**
 * Provides a label for a given element in the outline.
 */
public class ValaLabelProvider extends LabelProvider implements
		IStyledLabelProvider {

	/**
	 * The {@link String} between the main part of the label and the type, if
	 * there is any.
	 */
	private static final String TYPE_SEPARATOR = " : ";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		ValaPlugin valaPlugin = ValaPlugin.getDefault();
		String key = getImageKey(element);
		ImageRegistry imageRegistry = valaPlugin.getImageRegistry();
		Image image = imageRegistry.get(key);

		return image;
	}

	public String getImageKey(Object element) {
		element = maybeGetTreeNodeValue(element);

		String key = null;
		if (element instanceof ValaSymbol) {
			ValaSymbol symbol = (ValaSymbol) element;
			key = ValaSymbolImageProvider.getKey(symbol);
		} else if (element instanceof ValaSource) {
			key = ValaPluginConstants.IMG_OBJECT_VALA;
		} else if (element instanceof ValaPackage) {
			key = ValaPluginConstants.IMG_OBJECT_PACKAGE;
		} else {
			key = ValaPluginConstants.IMG_OBJECT_UNKNOWN;
		}
		return key;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		element = maybeGetTreeNodeValue(element);

		String text = super.getText(element);
		String type = getType(element);
		if (type != null) {
			text += TYPE_SEPARATOR + type;
		}
		return text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.
	 * IStyledLabelProvider#getStyledText(java.lang.Object)
	 */
	@Override
	public StyledString getStyledText(Object element) {
		element = maybeGetTreeNodeValue(element);

		StyledString styledText = new StyledString(super.getText(element));
		String type = getType(element);
		if (type != null) {
			styledText.append(TYPE_SEPARATOR, StyledString.QUALIFIER_STYLER);
			styledText.append(type, StyledString.QUALIFIER_STYLER);
		}
		return styledText;
	}

	/**
	 * Returns the type of a {@link ValaField} or {@link ValaMethod}.
	 * 
	 * @return <code>null</code> if the given element is neither a
	 *         {@link ValaField} or {@link ValaMethod}
	 */
	private String getType(Object element) {
		String type = null;
		if (element instanceof ValaField) {
			ValaField field = (ValaField) element;
			type = field.getType();
		} else if (element instanceof ValaMethod) {
			ValaMethod method = (ValaMethod) element;
			type = method.getType();
		}
		return type;
	}

	/**
	 * Returns its value if the given element is a {@link TreeNode} and the
	 * element itself otherwise.
	 */
	private Object maybeGetTreeNodeValue(Object element) {
		if (element instanceof TreeNode) {
			element = ((TreeNode) element).getValue();
		}
		return element;
	}

}
