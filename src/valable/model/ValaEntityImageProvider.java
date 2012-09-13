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

import valable.ValaPlugin;
import valable.ValaPluginConstants;

/**
 * Provides the {@link Image}s used to visually represent {@link ValaEntity}
 * objects.
 * 
 * This class is a singleton.
 */
public class ValaEntityImageProvider implements ValaEntityVisitor<String> {

	private static final ValaEntityImageProvider instance = new ValaEntityImageProvider();

	@Override
	public String visitEntity(ValaEntity entity) {
		return ValaPluginConstants.IMG_OBJECT_UNKNOWN;
	}

	@Override
	public String visitField(ValaField field) {
		switch (field.getAccessibility()) {
		case PRIVATE:
			return ValaPluginConstants.IMG_OBJECT_FIELD_PRIVATE;
		case PROTECTED:
			return ValaPluginConstants.IMG_OBJECT_FIELD_PROTECTED;
		case PUBLIC:
			return ValaPluginConstants.IMG_OBJECT_FIELD_PUBLIC;
		case INTERNAL:
			// Fall-through
		default:
			return ValaPluginConstants.IMG_OBJECT_FIELD_DEFAULT;
		}
	}

	@Override
	public String visitMethod(ValaMethod method) {
		switch (method.getAccessibility()) {
		case PRIVATE:
			return ValaPluginConstants.IMG_OBJECT_METHOD_PRIVATE;
		case PROTECTED:
			return ValaPluginConstants.IMG_OBJECT_METHOD_PROTECTED;
		case PUBLIC:
			return ValaPluginConstants.IMG_OBJECT_METHOD_PUBLIC;
		case INTERNAL:
			// Fall-through
		default:
			return ValaPluginConstants.IMG_OBJECT_METHOD_DEFAULT;
		}
	}

	@Override
	public String visitType(ValaType type) {
		return ValaPluginConstants.IMG_OBJECT_CLASS;
	}

	@Override
	public String visitLocalVariable(ValaLocalVariable localVariable) {
		return ValaPluginConstants.IMG_OBJECT_LOCAL_VARIABLE;
	}

	public static ValaEntityImageProvider getInstance() {
		return instance;
	}

	public static String getKey(ValaEntity entity) {
		if (entity == null) {
			throw new IllegalArgumentException("ValaEntity must not be null");
		}
		String key = entity.accept(getInstance());
		return key;
	}

	public static Image getImage(ValaPlugin valaPlugin, ValaEntity entity) {
		if (valaPlugin == null) {
			throw new IllegalArgumentException("ValaPlugin must not be null");
		}
		String key = getKey(entity);
		Image image = valaPlugin.getImageRegistry().get(key);
		return image;
	}

}
