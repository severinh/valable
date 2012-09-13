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

	public static final String IMG_PATH_PREFIX = "icons/";

	public static final String IMG_OBJECT_VALA = IMG_PATH_PREFIX
			+ "vala_obj.gif";
	public static final String IMG_OBJECT_UNKNOWN = IMG_PATH_PREFIX
			+ "unknown_obj.gif";
	public static final String IMG_OBJECT_PACKAGE = IMG_PATH_PREFIX
			+ "package_obj.gif";
	public static final String IMG_OBJECT_CLASS = IMG_PATH_PREFIX
			+ "class_obj.gif";
	public static final String IMG_OBJECT_INTERFACE = IMG_PATH_PREFIX
			+ "int_obj.gif";
	public static final String IMG_OBJECT_ENUM = IMG_PATH_PREFIX
			+ "enum_obj.gif";
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

	public static final Set<String> IMG_KEYS = Collections
			.unmodifiableSet(new LinkedHashSet<String>(Arrays.asList(
					IMG_OBJECT_VALA, IMG_OBJECT_VALA, IMG_OBJECT_UNKNOWN,
					IMG_OBJECT_PACKAGE, IMG_OBJECT_CLASS, IMG_OBJECT_INTERFACE,
					IMG_OBJECT_ENUM, IMG_OBJECT_LOCAL_VARIABLE,
					IMG_OBJECT_FIELD_DEFAULT, IMG_OBJECT_FIELD_PRIVATE,
					IMG_OBJECT_FIELD_PUBLIC, IMG_OBJECT_FIELD_PROTECTED,
					IMG_OBJECT_METHOD_DEFAULT, IMG_OBJECT_METHOD_PRIVATE,
					IMG_OBJECT_METHOD_PUBLIC, IMG_OBJECT_METHOD_PROTECTED)));

}