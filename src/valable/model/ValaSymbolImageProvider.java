/**
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.model;

import org.eclipse.swt.graphics.Image;
import org.gnome.vala.Class;
import org.gnome.vala.Field;
import org.gnome.vala.LocalVariable;
import org.gnome.vala.Method;
import org.gnome.vala.Symbol;
import org.gnome.vala.SymbolAccessibility;

import valable.ValaPlugin;
import valable.ValaPluginConstants;

/**
 * Provides the images used to visually represent symbols.
 * 
 * This class is a singleton.
 */
public class ValaSymbolImageProvider {

	private static final ValaSymbolImageProvider instance = new ValaSymbolImageProvider();

	public String visitSymbol(Symbol symbol) {
		return ValaPluginConstants.IMG_OBJECT_UNKNOWN;
	}

	public String visitField(Field field) {
		SymbolAccessibility accessibility = field.getAccessibility();
		if (accessibility.equals(SymbolAccessibility.PRIVATE)) {
			return ValaPluginConstants.IMG_OBJECT_FIELD_PRIVATE;
		} else if (accessibility.equals(SymbolAccessibility.PROTECTED)) {
			return ValaPluginConstants.IMG_OBJECT_FIELD_PROTECTED;
		} else if (accessibility.equals(SymbolAccessibility.PUBLIC)) {
			return ValaPluginConstants.IMG_OBJECT_FIELD_PUBLIC;
		} else {
			return ValaPluginConstants.IMG_OBJECT_FIELD_DEFAULT;
		}
	}

	public String visitMethod(Method method) {
		SymbolAccessibility accessibility = method.getAccessibility();
		if (accessibility.equals(SymbolAccessibility.PRIVATE)) {
			return ValaPluginConstants.IMG_OBJECT_METHOD_PRIVATE;
		} else if (accessibility.equals(SymbolAccessibility.PROTECTED)) {
			return ValaPluginConstants.IMG_OBJECT_METHOD_PROTECTED;
		} else if (accessibility.equals(SymbolAccessibility.PUBLIC)) {
			return ValaPluginConstants.IMG_OBJECT_METHOD_PUBLIC;
		} else {
			return ValaPluginConstants.IMG_OBJECT_METHOD_DEFAULT;
		}
	}

	public String visitClass(Class cls) {
		SymbolAccessibility accessibility = cls.getAccessibility();
		if (accessibility.equals(SymbolAccessibility.PRIVATE)) {
			return ValaPluginConstants.IMG_OBJECT_CLASS_PRIVATE;
		} else if (accessibility.equals(SymbolAccessibility.PROTECTED)) {
			return ValaPluginConstants.IMG_OBJECT_CLASS_PROTECTED;
		} else if (accessibility.equals(SymbolAccessibility.PUBLIC)) {
			return ValaPluginConstants.IMG_OBJECT_CLASS_PUBLIC;
		} else {
			return ValaPluginConstants.IMG_OBJECT_CLASS_DEFAULT;
		}
	}

	public String visitLocalVariable(LocalVariable localVariable) {
		return ValaPluginConstants.IMG_OBJECT_LOCAL_VARIABLE;
	}

	public static ValaSymbolImageProvider getInstance() {
		return instance;
	}

	public static String getKey(Symbol symbol) {
		if (symbol == null) {
			throw new IllegalArgumentException("Symbol must not be null");
		}
		String key;
		// TODO: Make it possible to implement the CodeVisitor interface.
		// Thus, use the following work-around.
		// String key = symbol.accept(getInstance());
		if (symbol instanceof Method) {
			key = getInstance().visitMethod((Method) symbol);
		} else if (symbol instanceof Field) {
			key = getInstance().visitField((Field) symbol);
		} else if (symbol instanceof LocalVariable) {
			key = getInstance().visitLocalVariable((LocalVariable) symbol);
		} else if (symbol instanceof Class) {
			key = getInstance().visitClass((Class) symbol);
		} else {
			key = getInstance().visitSymbol(symbol);
		}

		return key;
	}

	public static Image getImage(ValaPlugin valaPlugin, Symbol symbol) {
		if (valaPlugin == null) {
			throw new IllegalArgumentException("ValaPlugin must not be null");
		}
		String key = getKey(symbol);
		Image image = valaPlugin.getImageRegistry().get(key);
		return image;
	}

}
