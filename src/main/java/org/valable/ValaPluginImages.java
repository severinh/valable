/*******************************************************************************
 * Copyright (c) 2000, 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Ferenc Hechler, ferenc_hechler@users.sourceforge.net - 83258 [jar exporter] Deploy java application as executable jar
 *******************************************************************************/
package org.valable;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.osgi.framework.Bundle;

/**
 * Bundle of most images used by the Vala plug-in.
 */
public class ValaPluginImages {

	private static final String NAME_PREFIX = "org.valable.";
	private static final int NAME_PREFIX_LENGTH = NAME_PREFIX.length();

	public static final IPath ICONS_PATH = new Path("src/main/resources/icons/full");

	// The plug-in registry
	private static ImageRegistry imageRegistry = null;
	private static HashMap<String, ImageDescriptor> avoidSWTErrorMap = null;

	private static final String T_OBJ = "obj16";
	private static final String T_OVR = "ovr16";

	/*
	 * Keys for images available from the Vala plug-in image registry.
	 */
	public static final String IMG_MISC_PUBLIC= NAME_PREFIX + "methpub_obj.gif";	
	public static final String IMG_MISC_PROTECTED= NAME_PREFIX + "methpro_obj.gif";
	public static final String IMG_MISC_PRIVATE= NAME_PREFIX + "methpri_obj.gif";
	public static final String IMG_MISC_DEFAULT= NAME_PREFIX + "methdef_obj.gif";
	
	public static final String IMG_FIELD_PUBLIC= NAME_PREFIX + "field_public_obj.gif";	
	public static final String IMG_FIELD_PROTECTED= NAME_PREFIX + "field_protected_obj.gif";
	public static final String IMG_FIELD_PRIVATE= NAME_PREFIX + "field_private_obj.gif";
	public static final String IMG_FIELD_DEFAULT= NAME_PREFIX + "field_default_obj.gif";

	public static final String IMG_OBJS_CLASS = NAME_PREFIX + "class_obj.gif";
	public static final String IMG_OBJS_CLASS_DEFAULT = NAME_PREFIX + "class_default_obj.gif"; 

	public static final String IMG_OBJS_INNER_CLASS_PUBLIC = NAME_PREFIX + "innerclass_public_obj.gif"; 
	public static final String IMG_OBJS_INNER_CLASS_DEFAULT= NAME_PREFIX + "innerclass_default_obj.gif";
	public static final String IMG_OBJS_INNER_CLASS_PROTECTED = NAME_PREFIX + "innerclass_protected_obj.gif"; 
	public static final String IMG_OBJS_INNER_CLASS_PRIVATE = NAME_PREFIX + "innerclass_private_obj.gif"; 

	public static final String IMG_OBJS_INTERFACE = NAME_PREFIX + "int_obj.gif"; 
	public static final String IMG_OBJS_INTERFACE_DEFAULT = NAME_PREFIX + "int_default_obj.gif"; 

	public static final String IMG_OBJS_INNER_INTERFACE_PROTECTED = NAME_PREFIX + "innerinterface_protected_obj.gif"; 
	public static final String IMG_OBJS_INNER_INTERFACE_PRIVATE = NAME_PREFIX + "innerinterface_private_obj.gif"; 

	public static final String IMG_OBJS_ENUM = NAME_PREFIX + "enum_obj.gif"; 
	public static final String IMG_OBJS_ENUM_DEFAULT = NAME_PREFIX + "enum_default_obj.gif"; 
	public static final String IMG_OBJS_ENUM_PROTECTED = NAME_PREFIX + "enum_protected_obj.gif"; 
	public static final String IMG_OBJS_ENUM_PRIVATE = NAME_PREFIX + "enum_private_obj.gif"; 

	public static final String IMG_OBJS_VALA_FILE = NAME_PREFIX + "vala_obj.gif"; 
	public static final String IMG_OBJS_PACKAGE = NAME_PREFIX + "package_obj.gif"; 
	public static final String IMG_OBJS_SIGNAL = NAME_PREFIX + "signal_obj.gif"; 

	public static final String IMG_OBJS_UNKNOWN= NAME_PREFIX + "unknown_obj.gif"; 
	public static final String IMG_OBJS_LOCAL_VARIABLE = NAME_PREFIX + "localvariable_obj.gif"; 

	/*
	 * Set of predefined image descriptors.
	 */
	public static final ImageDescriptor DESC_MISC_PUBLIC= createManagedFromKey(T_OBJ, IMG_MISC_PUBLIC);
	public static final ImageDescriptor DESC_MISC_PROTECTED= createManagedFromKey(T_OBJ, IMG_MISC_PROTECTED);
	public static final ImageDescriptor DESC_MISC_PRIVATE= createManagedFromKey(T_OBJ, IMG_MISC_PRIVATE);
	public static final ImageDescriptor DESC_MISC_DEFAULT= createManagedFromKey(T_OBJ, IMG_MISC_DEFAULT);
	
	public static final ImageDescriptor DESC_FIELD_PUBLIC = createManagedFromKey(T_OBJ, IMG_FIELD_PUBLIC);
	public static final ImageDescriptor DESC_FIELD_PROTECTED = createManagedFromKey(T_OBJ, IMG_FIELD_PROTECTED);
	public static final ImageDescriptor DESC_FIELD_PRIVATE = createManagedFromKey(T_OBJ, IMG_FIELD_PRIVATE);
	public static final ImageDescriptor DESC_FIELD_DEFAULT = createManagedFromKey(T_OBJ, IMG_FIELD_DEFAULT);

	public static final ImageDescriptor DESC_OBJS_VALA_FILE= createManagedFromKey(T_OBJ, IMG_OBJS_VALA_FILE);
	public static final ImageDescriptor DESC_OBJS_PACKAGE= createManagedFromKey(T_OBJ, IMG_OBJS_PACKAGE);
	public static final ImageDescriptor DESC_OBJS_SIGNAL= createManagedFromKey(T_OBJ, IMG_OBJS_SIGNAL);
	
	public static final ImageDescriptor DESC_OBJS_CLASS= createManagedFromKey(T_OBJ, IMG_OBJS_CLASS);
	public static final ImageDescriptor DESC_OBJS_CLASS_DEFAULT= createManagedFromKey(T_OBJ, IMG_OBJS_CLASS_DEFAULT);

	public static final ImageDescriptor DESC_OBJS_INNER_CLASS_PUBLIC = createManagedFromKey(T_OBJ, IMG_OBJS_INNER_CLASS_PUBLIC);
	public static final ImageDescriptor DESC_OBJS_INNER_CLASS_DEFAULT = createManagedFromKey(T_OBJ, IMG_OBJS_INNER_CLASS_DEFAULT);
	public static final ImageDescriptor DESC_OBJS_INNER_CLASS_PROTECTED= createManagedFromKey(T_OBJ, IMG_OBJS_INNER_CLASS_PROTECTED);
	public static final ImageDescriptor DESC_OBJS_INNER_CLASS_PRIVATE= createManagedFromKey(T_OBJ, IMG_OBJS_INNER_CLASS_PRIVATE);
	
	public static final ImageDescriptor DESC_OBJS_INTERFACE= createManagedFromKey(T_OBJ, IMG_OBJS_INTERFACE);
	public static final ImageDescriptor DESC_OBJS_INTERFACE_DEFAULT= createManagedFromKey(T_OBJ, IMG_OBJS_INTERFACE_DEFAULT);
	
	public static final ImageDescriptor DESC_OBJS_INNER_INTERFACE_PROTECTED= createManagedFromKey(T_OBJ, IMG_OBJS_INNER_INTERFACE_PROTECTED);
	public static final ImageDescriptor DESC_OBJS_INNER_INTERFACE_PRIVATE= createManagedFromKey(T_OBJ, IMG_OBJS_INNER_INTERFACE_PRIVATE);
	
	public static final ImageDescriptor DESC_OBJS_ENUM= createManagedFromKey(T_OBJ, IMG_OBJS_ENUM);
	public static final ImageDescriptor DESC_OBJS_ENUM_DEFAULT= createManagedFromKey(T_OBJ, IMG_OBJS_ENUM_DEFAULT);
	public static final ImageDescriptor DESC_OBJS_ENUM_PROTECTED= createManagedFromKey(T_OBJ, IMG_OBJS_ENUM_PROTECTED);
	public static final ImageDescriptor DESC_OBJS_ENUM_PRIVATE= createManagedFromKey(T_OBJ, IMG_OBJS_ENUM_PRIVATE);
	
	public static final ImageDescriptor DESC_OBJS_UNKNOWN= createManagedFromKey(T_OBJ, IMG_OBJS_UNKNOWN);
	public static final ImageDescriptor DESC_OBJS_LOCAL_VARIABLE= createManagedFromKey(T_OBJ, IMG_OBJS_LOCAL_VARIABLE);

	public static final ImageDescriptor DESC_OVR_STATIC= createUnManagedCached(T_OVR, "static_co.gif");
	public static final ImageDescriptor DESC_OVR_ABSTRACT= createUnManagedCached(T_OVR, "abstract_co.gif");
	
	public static final ImageDescriptor DESC_OVR_OVERRIDES= createUnManagedCached(T_OVR, "over_co.gif");
	public static final ImageDescriptor DESC_OVR_IMPLEMENTS= createUnManagedCached(T_OVR, "implm_co.gif");
	public static final ImageDescriptor DESC_OVR_CONSTRUCTOR= createUnManagedCached(T_OVR, "constr_ovr.gif");
	
	private static final class CachedImageDescriptor extends ImageDescriptor {

		private ImageDescriptor descriptor;
		private ImageData data;

		public CachedImageDescriptor(ImageDescriptor descriptor) {
			this.descriptor = descriptor;
		}

		@Override
		public ImageData getImageData() {
			if (data == null) {
				data = descriptor.getImageData();
			}
			return data;
		}

	}

	/**
	 * Returns the image managed under the given key in this registry.
	 * 
	 * @param key the image's key
	 * @return the image managed under the given key
	 */
	public static Image get(String key) {
		return getImageRegistry().get(key);
	}

	/**
	 * Returns the image descriptor for the given key in this registry. Might be
	 * called in a non-UI thread.
	 * 
	 * @param key the image's key
	 * @return the image descriptor for the given key
	 */
	public static ImageDescriptor getDescriptor(String key) {
		if (imageRegistry == null) {
			return avoidSWTErrorMap.get(key);
		}
		return getImageRegistry().getDescriptor(key);
	}

	/**
	 * Sets the three image descriptors for enabled, disabled, and hovered to an
	 * action. The actions are retrieved from the *tool16 folders.
	 * 
	 * @param action the action
	 * @param iconName the icon name
	 */
	public static void setToolImageDescriptors(IAction action, String iconName) {
		setImageDescriptors(action, "tool16", iconName); 
	}

	/**
	 * Sets the three image descriptors for enabled, disabled, and hovered to an
	 * action. The actions are retrieved from the *lcl16 folders.
	 * 
	 * @param action the action
	 * @param iconName the icon name
	 */
	public static void setLocalImageDescriptors(IAction action, String iconName) {
		setImageDescriptors(action, "lcl16", iconName); 
	}

	/*
	 * Helper method to access the image registry from the ValaPlugin class.
	 */
	static ImageRegistry getImageRegistry() {
		if (imageRegistry == null) {
			imageRegistry = new ImageRegistry();
			for (Iterator<String> iter = avoidSWTErrorMap.keySet().iterator(); iter.hasNext();) {
				String key = iter.next();
				imageRegistry.put(key, avoidSWTErrorMap.get(key));
			}
			avoidSWTErrorMap = null;
		}
		return imageRegistry;
	}

	// ---- Helper methods to access icons on the file system
	private static void setImageDescriptors(IAction action, String type, String relPath) {
		ImageDescriptor id = create("d" + type, relPath, false); 
		if (id != null) {
			action.setDisabledImageDescriptor(id);
		}

		ImageDescriptor descriptor = create("e" + type, relPath, true); 
		action.setHoverImageDescriptor(descriptor);
		action.setImageDescriptor(descriptor);
	}

	private static ImageDescriptor createManagedFromKey(String prefix, String key) {
		return createManaged(prefix, key.substring(NAME_PREFIX_LENGTH), key);
	}

	private static ImageDescriptor createManaged(String prefix, String name, String key) {
		ImageDescriptor result = create(prefix, name, true);

		if (avoidSWTErrorMap == null) {
			avoidSWTErrorMap = new HashMap<String, ImageDescriptor>();
		}
		avoidSWTErrorMap.put(key, result);
		return result;
	}

	/*
	 * Creates an image descriptor for the given prefix and name in the bundle.
	 * The path can contain variables like $NL$. If no image could be found,
	 * <code>useMissingImageDescriptor</code> decides if either the 'missing
	 * image descriptor' is returned or <code>null</code>. or <code>null</code>.
	 */
	private static ImageDescriptor create(String prefix, String name, boolean useMissingImageDescriptor) {
		IPath path = ICONS_PATH.append(prefix).append(name);
		return createImageDescriptor(ValaPlugin.getDefault().getBundle(), path, useMissingImageDescriptor);
	}

	/*
	 * Creates an image descriptor for the given prefix and name in the bundle
	 * and let type descriptor cache the image data. If no image could be found,
	 * the 'missing image descriptor' is returned.
	 */
	private static ImageDescriptor createUnManagedCached(String prefix, String name) {
		return new CachedImageDescriptor(create(prefix, name, true));
	}

	/*
	 * Creates an image descriptor for the given path in a bundle. The path can
	 * contain variables like $NL$. If no image could be found,
	 * <code>useMissingImageDescriptor</code> decides if either the 'missing
	 * image descriptor' is returned or <code>null</code>.
	 */
	public static ImageDescriptor createImageDescriptor(Bundle bundle, IPath path, boolean useMissingImageDescriptor) {
		URL url = FileLocator.find(bundle, path, null);
		if (url != null) {
			return ImageDescriptor.createFromURL(url);
		}
		if (useMissingImageDescriptor) {
			return ImageDescriptor.getMissingImageDescriptor();
		}
		return null;
	}

}
