/**
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.valable.viewsupport;

import org.eclipse.jface.resource.ImageDescriptor;
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
import org.valable.ValaPluginImages;

/**
 * Provides the images used to visually represent symbols.
 * 
 * This class is a singleton.
 */
public class ValaSymbolImageProvider extends NopCodeVisitor<ImageDescriptor> {

	private static final ValaSymbolImageProvider instance = new ValaSymbolImageProvider();

	@Override
	public ImageDescriptor visitSymbol(Symbol symbol) {
		return ValaPluginImages.DESC_OBJS_UNKNOWN;
	}

	@Override
	public ImageDescriptor visitClass(Class cls) {
		SymbolAccessibility accessibility = cls.getAccessibility();
		if (accessibility.equals(SymbolAccessibility.PRIVATE)) {
			return ValaPluginImages.DESC_OBJS_INNER_CLASS_PRIVATE;
		} else if (accessibility.equals(SymbolAccessibility.PROTECTED)) {
			return ValaPluginImages.DESC_OBJS_INNER_CLASS_PROTECTED;
		} else if (accessibility.equals(SymbolAccessibility.PUBLIC)) {
			return ValaPluginImages.DESC_OBJS_CLASS;
		} else {
			return ValaPluginImages.DESC_OBJS_CLASS_DEFAULT;
		}
	}

	@Override
	public ImageDescriptor visitInterface(Interface interfce) {
		SymbolAccessibility accessibility = interfce.getAccessibility();
		if (accessibility.equals(SymbolAccessibility.PRIVATE)) {
			return ValaPluginImages.DESC_OBJS_INNER_INTERFACE_PRIVATE;
		} else if (accessibility.equals(SymbolAccessibility.PROTECTED)) {
			return ValaPluginImages.DESC_OBJS_INNER_INTERFACE_PROTECTED;
		} else if (accessibility.equals(SymbolAccessibility.PUBLIC)) {
			return ValaPluginImages.DESC_OBJS_INTERFACE;
		} else {
			return ValaPluginImages.DESC_OBJS_INTERFACE_DEFAULT;
		}
	}

	@Override
	public ImageDescriptor visitEnum(Enum enm) {
		SymbolAccessibility accessibility = enm.getAccessibility();
		if (accessibility.equals(SymbolAccessibility.PRIVATE)) {
			return ValaPluginImages.DESC_OBJS_ENUM_PRIVATE;
		} else if (accessibility.equals(SymbolAccessibility.PROTECTED)) {
			return ValaPluginImages.DESC_OBJS_ENUM_PROTECTED;
		} else if (accessibility.equals(SymbolAccessibility.PUBLIC)) {
			return ValaPluginImages.DESC_OBJS_ENUM;
		} else {
			return ValaPluginImages.DESC_OBJS_ENUM_DEFAULT;
		}
	}

	@Override
	public ImageDescriptor visitEnumValue(EnumValue enumValue) {
		return ValaPluginImages.DESC_FIELD_PUBLIC;
	}

	@Override
	public ImageDescriptor visitConstant(Constant constant) {
		return ValaPluginImages.DESC_FIELD_PUBLIC;
	}

	@Override
	public ImageDescriptor visitField(Field field) {
		SymbolAccessibility accessibility = field.getAccessibility();
		return visitFieldOrProperty(accessibility);
	}

	@Override
	public ImageDescriptor visitMethod(Method method) {
		SymbolAccessibility accessibility = method.getAccessibility();
		if (accessibility.equals(SymbolAccessibility.PRIVATE)) {
			return ValaPluginImages.DESC_MISC_PRIVATE;
		} else if (accessibility.equals(SymbolAccessibility.PROTECTED)) {
			return ValaPluginImages.DESC_MISC_PROTECTED;
		} else if (accessibility.equals(SymbolAccessibility.PUBLIC)) {
			return ValaPluginImages.DESC_MISC_PUBLIC;
		} else {
			return ValaPluginImages.DESC_MISC_DEFAULT;
		}
	}

	@Override
	public ImageDescriptor visitProperty(Property property) {
		SymbolAccessibility accessibility = property.getAccessibility();
		return visitFieldOrProperty(accessibility);
	}

	@Override
	public ImageDescriptor visitSignal(Signal signal) {
		return ValaPluginImages.DESC_OBJS_SIGNAL;
	}

	@Override
	public ImageDescriptor visitLocalVariable(LocalVariable localVariable) {
		return ValaPluginImages.DESC_OBJS_LOCAL_VARIABLE;
	}

	public ImageDescriptor visitFieldOrProperty(SymbolAccessibility accessibility) {
		if (accessibility.equals(SymbolAccessibility.PRIVATE)) {
			return ValaPluginImages.DESC_FIELD_PRIVATE;
		} else if (accessibility.equals(SymbolAccessibility.PROTECTED)) {
			return ValaPluginImages.DESC_FIELD_PROTECTED;
		} else if (accessibility.equals(SymbolAccessibility.PUBLIC)) {
			return ValaPluginImages.DESC_FIELD_PUBLIC;
		} else {
			return ValaPluginImages.DESC_FIELD_DEFAULT;
		}
	}

	public static ValaSymbolImageProvider getInstance() {
		return instance;
	}

	public static ImageDescriptor getImageDescriptor(Symbol symbol) {
		if (symbol == null) {
			throw new IllegalArgumentException("Symbol must not be null");
		}
		ImageDescriptor descriptor = symbol.accept(getInstance());
		if (descriptor == null) {
			descriptor = ValaPluginImages.DESC_OBJS_UNKNOWN;
		}
		return descriptor;
	}

	public static Image getImage(ValaPlugin valaPlugin, Symbol symbol) {
		if (valaPlugin == null) {
			throw new IllegalArgumentException("ValaPlugin must not be null");
		}
		ImageDescriptor descriptor = getImageDescriptor(symbol);
		ImageDescriptorRegistry registry = ValaPlugin.getImageDescriptorRegistry();
		Image image = registry.get(descriptor);
		return image;
	}

}
