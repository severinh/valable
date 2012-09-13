/**
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.model;

import junit.framework.TestCase;

import org.junit.Test;

import valable.ValaPluginConstants;

/**
 * Tests {@link ValaEntityImageProvider}.
 */
public class ValaEntityImageProviderTest extends TestCase {

	private static final String NAME = "foo";

	@Test
	public void test() {
		ValaEntity entity = new ValaEntity(NAME);
		assertEquals(ValaPluginConstants.IMG_OBJECT_UNKNOWN,
				ValaEntityImageProvider.getKey(entity));

		ValaField field;
		field = makeStubField(ValaSymbolAccessibility.PRIVATE);
		assertEquals(ValaPluginConstants.IMG_OBJECT_FIELD_PRIVATE,
				ValaEntityImageProvider.getKey(field));
		field = makeStubField(ValaSymbolAccessibility.INTERNAL);
		assertEquals(ValaPluginConstants.IMG_OBJECT_FIELD_DEFAULT,
				ValaEntityImageProvider.getKey(field));
		field = makeStubField(ValaSymbolAccessibility.PROTECTED);
		assertEquals(ValaPluginConstants.IMG_OBJECT_FIELD_PROTECTED,
				ValaEntityImageProvider.getKey(field));
		field = makeStubField(ValaSymbolAccessibility.PUBLIC);
		assertEquals(ValaPluginConstants.IMG_OBJECT_FIELD_PUBLIC,
				ValaEntityImageProvider.getKey(field));

		ValaMethod method;
		method = makeStubMethod(ValaSymbolAccessibility.PRIVATE);
		assertEquals(ValaPluginConstants.IMG_OBJECT_METHOD_PRIVATE,
				ValaEntityImageProvider.getKey(method));
		method = makeStubMethod(ValaSymbolAccessibility.INTERNAL);
		assertEquals(ValaPluginConstants.IMG_OBJECT_METHOD_DEFAULT,
				ValaEntityImageProvider.getKey(method));
		method = makeStubMethod(ValaSymbolAccessibility.PROTECTED);
		assertEquals(ValaPluginConstants.IMG_OBJECT_METHOD_PROTECTED,
				ValaEntityImageProvider.getKey(method));
		method = makeStubMethod(ValaSymbolAccessibility.PUBLIC);
		assertEquals(ValaPluginConstants.IMG_OBJECT_METHOD_PUBLIC,
				ValaEntityImageProvider.getKey(method));

		ValaLocalVariable localVariable = new ValaLocalVariable(NAME);
		assertEquals(ValaPluginConstants.IMG_OBJECT_LOCAL_VARIABLE,
				ValaEntityImageProvider.getKey(localVariable));

		ValaType type = new ValaType(NAME);
		assertEquals(ValaPluginConstants.IMG_OBJECT_CLASS,
				ValaEntityImageProvider.getKey(type));
	}

	/**
	 * Creates a new stub {@link ValaField} with a given
	 * {@link ValaSymbolAccessibility}.
	 */
	private ValaField makeStubField(ValaSymbolAccessibility accessibility) {
		ValaField field = new ValaField(NAME);
		field.getModifiers().add(accessibility.toString());
		return field;
	}

	/**
	 * Creates a new stub {@link ValaMethod} with a given
	 * {@link ValaSymbolAccessibility}.
	 */
	private ValaMethod makeStubMethod(ValaSymbolAccessibility accessibility) {
		ValaMethod method = new ValaMethod(NAME);
		method.getModifiers().add(accessibility.toString());
		return method;
	}

}
