/**
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.valable.model;

import org.eclipse.swt.graphics.Image;
import org.gnome.vala.Class;
import org.gnome.vala.Constant;
import org.gnome.vala.Enum;
import org.gnome.vala.EnumValue;
import org.gnome.vala.Field;
import org.gnome.vala.Interface;
import org.gnome.vala.LocalVariable;
import org.gnome.vala.Method;
import org.gnome.vala.NopCodeVisitor;
import org.gnome.vala.Property;
import org.gnome.vala.Signal;
import org.gnome.vala.Symbol;
import org.gnome.vala.SymbolAccessibility;

import org.valable.ValaPlugin;
import org.valable.ValaPluginConstants;

/**
 * Provides the images used to visually represent symbols.
 * 
 * This class is a singleton.
 */
public class ValaSymbolImageProvider extends NopCodeVisitor<String> {

	private static final ValaSymbolImageProvider instance = new ValaSymbolImageProvider();

	@Override
	public String visitSymbol(Symbol symbol) {
		return ValaPluginConstants.IMG_OBJECT_UNKNOWN;
	}

	@Override
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

	@Override
	public String visitInterface(Interface interfce) {
		SymbolAccessibility accessibility = interfce.getAccessibility();
		if (accessibility.equals(SymbolAccessibility.PRIVATE)) {
			return ValaPluginConstants.IMG_OBJECT_INTERFACE_PRIVATE;
		} else if (accessibility.equals(SymbolAccessibility.PROTECTED)) {
			return ValaPluginConstants.IMG_OBJECT_INTERFACE_PROTECTED;
		} else if (accessibility.equals(SymbolAccessibility.PUBLIC)) {
			return ValaPluginConstants.IMG_OBJECT_INTERFACE_PUBLIC;
		} else {
			return ValaPluginConstants.IMG_OBJECT_INTERFACE_DEFAULT;
		}
	}

	@Override
	public String visitEnum(Enum enm) {
		SymbolAccessibility accessibility = enm.getAccessibility();
		if (accessibility.equals(SymbolAccessibility.PRIVATE)) {
			return ValaPluginConstants.IMG_OBJECT_ENUM_PRIVATE;
		} else if (accessibility.equals(SymbolAccessibility.PROTECTED)) {
			return ValaPluginConstants.IMG_OBJECT_ENUM_PROTECTED;
		} else if (accessibility.equals(SymbolAccessibility.PUBLIC)) {
			return ValaPluginConstants.IMG_OBJECT_ENUM_PUBLIC;
		} else {
			return ValaPluginConstants.IMG_OBJECT_ENUM_DEFAULT;
		}
	}

	@Override
	public String visitEnumValue(EnumValue enumValue) {
		return ValaPluginConstants.IMG_OBJECT_FIELD_PUBLIC;
	}

	@Override
	public String visitConstant(Constant constant) {
		return ValaPluginConstants.IMG_OBJECT_FIELD_PUBLIC;
	}

	@Override
	public String visitField(Field field) {
		SymbolAccessibility accessibility = field.getAccessibility();
		return visitFieldOrProperty(accessibility);
	}

	@Override
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

	@Override
	public String visitProperty(Property property) {
		SymbolAccessibility accessibility = property.getAccessibility();
		return visitFieldOrProperty(accessibility);
	}

	@Override
	public String visitSignal(Signal signal) {
		return ValaPluginConstants.IMG_OBJECT_SIGNAL_PUBLIC;
	}

	@Override
	public String visitLocalVariable(LocalVariable localVariable) {
		return ValaPluginConstants.IMG_OBJECT_LOCAL_VARIABLE;
	}

	public String visitFieldOrProperty(SymbolAccessibility accessibility) {
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

	public static ValaSymbolImageProvider getInstance() {
		return instance;
	}

	public static String getKey(Symbol symbol) {
		if (symbol == null) {
			throw new IllegalArgumentException("Symbol must not be null");
		}
		String key = symbol.accept(getInstance());
		if (key == null) {
			key = ValaPluginConstants.IMG_OBJECT_UNKNOWN;
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
