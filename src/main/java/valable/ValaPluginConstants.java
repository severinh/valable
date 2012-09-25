/**
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Various global constants.
 */
public interface ValaPluginConstants {

	public static final String PLUGIN_ID = "valable";

	public static final String IMG_PATH_PREFIX = "src/main/resources/icons/";

	public static final String IMG_OBJECT_VALA = IMG_PATH_PREFIX
			+ "vala_obj.gif";
	public static final String IMG_OBJECT_UNKNOWN = IMG_PATH_PREFIX
			+ "unknown_obj.gif";
	public static final String IMG_OBJECT_PACKAGE = IMG_PATH_PREFIX
			+ "package_obj.gif";
	public static final String IMG_OBJECT_CLASS_DEFAULT = IMG_PATH_PREFIX
			+ "class_default_obj.gif";
	public static final String IMG_OBJECT_CLASS_PRIVATE = IMG_PATH_PREFIX
			+ "innerclass_private_obj.gif";
	public static final String IMG_OBJECT_CLASS_PUBLIC = IMG_PATH_PREFIX
			+ "class_obj.gif";
	public static final String IMG_OBJECT_CLASS_PROTECTED = IMG_PATH_PREFIX
			+ "innerclass_protected_obj.gif";
	public static final String IMG_OBJECT_INTERFACE_DEFAULT = IMG_PATH_PREFIX
			+ "int_default_obj.gif";
	public static final String IMG_OBJECT_INTERFACE_PRIVATE = IMG_PATH_PREFIX
			+ "innerinterface_private_obj.gif";
	public static final String IMG_OBJECT_INTERFACE_PUBLIC = IMG_PATH_PREFIX
			+ "int_obj.gif";
	public static final String IMG_OBJECT_INTERFACE_PROTECTED = IMG_PATH_PREFIX
			+ "innerinterface_protected_obj.gif";
	public static final String IMG_OBJECT_ENUM_DEFAULT = IMG_PATH_PREFIX
			+ "enum_default_obj.gif";
	public static final String IMG_OBJECT_ENUM_PRIVATE = IMG_PATH_PREFIX
			+ "enum_private_obj.gif";
	public static final String IMG_OBJECT_ENUM_PUBLIC = IMG_PATH_PREFIX
			+ "enum_obj.gif";
	public static final String IMG_OBJECT_ENUM_PROTECTED = IMG_PATH_PREFIX
			+ "enum_protected_obj.gif";
	public static final String IMG_OBJECT_LOCAL_VARIABLE = IMG_PATH_PREFIX
			+ "localvariable_obj.gif";
	public static final String IMG_OBJECT_FIELD_DEFAULT = IMG_PATH_PREFIX
			+ "field_default_obj.gif";
	public static final String IMG_OBJECT_FIELD_PRIVATE = IMG_PATH_PREFIX
			+ "field_private_obj.gif";
	public static final String IMG_OBJECT_FIELD_PUBLIC = IMG_PATH_PREFIX
			+ "field_public_obj.gif";
	public static final String IMG_OBJECT_FIELD_PROTECTED = IMG_PATH_PREFIX
			+ "field_protected_obj.gif";
	public static final String IMG_OBJECT_METHOD_DEFAULT = IMG_PATH_PREFIX
			+ "methdef_obj.gif";
	public static final String IMG_OBJECT_METHOD_PRIVATE = IMG_PATH_PREFIX
			+ "methpri_obj.gif";
	public static final String IMG_OBJECT_METHOD_PUBLIC = IMG_PATH_PREFIX
			+ "methpub_obj.gif";
	public static final String IMG_OBJECT_METHOD_PROTECTED = IMG_PATH_PREFIX
			+ "methpro_obj.gif";
	public static final String IMG_OBJECT_SIGNAL_PUBLIC = IMG_PATH_PREFIX
			+ "signal_obj.gif";

	public static final String IMG_OVERLAY_ABSTRACT = IMG_PATH_PREFIX
			+ "abstract_co.gif";
	public static final String IMG_OVERLAY_CONSTRUCTOR = IMG_PATH_PREFIX
			+ "constr_ovr.gif";
	public static final String IMG_OVERLAY_STATIC = IMG_PATH_PREFIX
			+ "static_co.gif";
	public static final String IMG_OVERLAY_IMPLEMENTS = IMG_PATH_PREFIX
			+ "implm_co.gif";
	public static final String IMG_OVERLAY_OVERRIDES = IMG_PATH_PREFIX
			+ "over_co.gif";

	public static final Set<String> IMG_KEYS = Collections
			.unmodifiableSet(new LinkedHashSet<String>(Arrays.asList(
					IMG_OBJECT_VALA, IMG_OBJECT_VALA, IMG_OBJECT_UNKNOWN,
					IMG_OBJECT_PACKAGE, IMG_OBJECT_CLASS_DEFAULT,
					IMG_OBJECT_CLASS_PRIVATE, IMG_OBJECT_CLASS_PUBLIC,
					IMG_OBJECT_CLASS_PROTECTED, IMG_OBJECT_INTERFACE_DEFAULT,
					IMG_OBJECT_INTERFACE_PRIVATE, IMG_OBJECT_INTERFACE_PUBLIC,
					IMG_OBJECT_INTERFACE_PROTECTED, IMG_OBJECT_ENUM_DEFAULT,
					IMG_OBJECT_ENUM_PRIVATE, IMG_OBJECT_ENUM_PUBLIC,
					IMG_OBJECT_ENUM_PROTECTED, IMG_OBJECT_LOCAL_VARIABLE,
					IMG_OBJECT_FIELD_DEFAULT, IMG_OBJECT_FIELD_PRIVATE,
					IMG_OBJECT_FIELD_PUBLIC, IMG_OBJECT_FIELD_PROTECTED,
					IMG_OBJECT_METHOD_DEFAULT, IMG_OBJECT_METHOD_PRIVATE,
					IMG_OBJECT_METHOD_PUBLIC, IMG_OBJECT_METHOD_PROTECTED,
					IMG_OBJECT_SIGNAL_PUBLIC, IMG_OVERLAY_ABSTRACT,
					IMG_OVERLAY_CONSTRUCTOR, IMG_OVERLAY_STATIC,
					IMG_OVERLAY_IMPLEMENTS, IMG_OVERLAY_OVERRIDES)));

}
