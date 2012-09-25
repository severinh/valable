/**
 * Copyright (C) 2008  Andrew Flegg <andrew@bleb.org>
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.valable.outline;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.swt.graphics.Image;
import org.gnome.vala.Constant;
import org.gnome.vala.CreationMethod;
import org.gnome.vala.DataType;
import org.gnome.vala.Field;
import org.gnome.vala.Method;
import org.gnome.vala.NopCodeVisitor;
import org.gnome.vala.Parameter;
import org.gnome.vala.Property;
import org.gnome.vala.Signal;
import org.gnome.vala.Symbol;

import org.valable.ValaPlugin;
import org.valable.ValaPluginConstants;
import org.valable.model.ValaSymbolImageProvider;

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
		if (element instanceof Symbol) {
			Symbol symbol = (Symbol) element;
			key = ValaSymbolImageProvider.getKey(symbol);
		} else {
			key = ValaPluginConstants.IMG_OBJECT_UNKNOWN;
		}
		return key;
	}

	@Override
	public String getText(Object element) {
		element = maybeGetTreeNodeValue(element);

		String primaryText = getPrimaryText(element);
		String typeText = getTypeText(element);
		if (typeText != null) {
			primaryText += TYPE_SEPARATOR + typeText;
		}
		return primaryText;
	}

	@Override
	public StyledString getStyledText(Object element) {
		element = maybeGetTreeNodeValue(element);

		String primaryText = getPrimaryText(element);
		String typeText = getTypeText(element);

		StyledString styledText = new StyledString(primaryText);
		if (typeText != null) {
			styledText.append(TYPE_SEPARATOR, StyledString.QUALIFIER_STYLER);
			styledText.append(typeText, StyledString.QUALIFIER_STYLER);
		}
		return styledText;
	}

	/**
	 * Returns the primary text for an element.
	 */
	private String getPrimaryText(Object element) {
		String result;
		if (element instanceof Symbol) {
			Symbol symbol = (Symbol) element;
			result = symbol.accept(PrimaryLabelTextProvider.getInstance());
		} else {
			result = element.toString();
		}
		return result;
	}

	/**
	 * Returns the type text for an element.
	 * 
	 * @return the type text of <code>null</code> if no type should be displayed
	 */
	private String getTypeText(Object element) {
		String result = null;
		if (element instanceof Symbol) {
			Symbol symbol = (Symbol) element;
			result = symbol.accept(TypeLabelTextProvider.getInstance());
		}
		return result;
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

	private static class PrimaryLabelTextProvider extends
			NopCodeVisitor<String> {

		private static final PrimaryLabelTextProvider instance;

		static {
			instance = new PrimaryLabelTextProvider();
		}

		public static PrimaryLabelTextProvider getInstance() {
			return instance;
		}

		@Override
		public String visitSymbol(Symbol symbol) {
			// Ensure that in the case of unnamed construction methods, the
			// "ClassName(...)" is shown rather than ".new()".
			String result = symbol.getNameInSourceFile();
			return result;
		}

		@Override
		public String visitMethod(Method method) {
			List<Parameter> parameters = method.getParameters();
			String result = visitSymbolWithParameterList(method, parameters);
			return result;
		}

		@Override
		public String visitSignal(Signal signal) {
			List<Parameter> parameters = signal.getParameters();
			String result = visitSymbolWithParameterList(signal, parameters);
			return result;
		}

		private String visitSymbolWithParameterList(Symbol symbol,
				List<Parameter> parameters) {
			StringBuilder builder = new StringBuilder();
			builder.append(visitSymbol(symbol));
			List<String> parameterTypeNames = new ArrayList<String>(
					parameters.size());
			for (Parameter parameter : parameters) {
				DataType parameterType = parameter.getVariableType();
				parameterTypeNames.add(parameterType.toString());
			}
			builder.append('(');
			builder.append(StringUtils.join(parameterTypeNames, ", "));
			builder.append(')');
			return builder.toString();
		}

	}

	private static class TypeLabelTextProvider extends NopCodeVisitor<String> {

		private static final TypeLabelTextProvider instance;

		static {
			instance = new TypeLabelTextProvider();
		}

		public static TypeLabelTextProvider getInstance() {
			return instance;
		}

		@Override
		public String visitConstant(Constant constant) {
			String result = constant.getConstantType().toString();
			return result;
		}

		@Override
		public String visitField(Field field) {
			String result = field.getVariableType().toString();
			return result;
		}

		@Override
		public String visitMethod(Method method) {
			String result = super.visitMethod(method);
			// Do not display the return type in the case of a creation method
			if (!(method instanceof CreationMethod)) {
				result = method.getReturnType().toString();
			}
			return result;
		}

		@Override
		public String visitProperty(Property property) {
			String result = property.getPropertyType().toString();
			return result;
		}

		@Override
		public String visitSignal(Signal signal) {
			String result = signal.getReturnType().toString();
			return result;
		}

	}

}
