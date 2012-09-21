/**
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.model;

import static org.junit.Assert.assertEquals;

import org.gnome.vala.Class;
import org.gnome.vala.Field;
import org.gnome.vala.LocalVariable;
import org.gnome.vala.Method;
import org.gnome.vala.SymbolAccessibility;
import org.gnome.vala.VoidType;
import org.junit.Test;

import valable.AbstractTest;
import valable.ValaPluginConstants;

/**
 * Tests {@link ValaSymbolImageProvider}.
 */
public class ValaSymbolImageProviderTest extends AbstractTest {

	private static final String NAME = "foo";

	@Test
	public void test() {
		// TODO: It is not possible to instantiate the abstract class Symbol
		// Symbol symbol = new Symbol(NAME);
		// assertEquals(ValaPluginConstants.IMG_OBJECT_UNKNOWN,
		// ValaSymbolImageProvider.getKey(symbol));

		Field field;
		field = makeStubField(SymbolAccessibility.PRIVATE);
		assertEquals(ValaPluginConstants.IMG_OBJECT_FIELD_PRIVATE,
				ValaSymbolImageProvider.getKey(field));
		field = makeStubField(SymbolAccessibility.INTERNAL);
		assertEquals(ValaPluginConstants.IMG_OBJECT_FIELD_DEFAULT,
				ValaSymbolImageProvider.getKey(field));
		field = makeStubField(SymbolAccessibility.PROTECTED);
		assertEquals(ValaPluginConstants.IMG_OBJECT_FIELD_PROTECTED,
				ValaSymbolImageProvider.getKey(field));
		field = makeStubField(SymbolAccessibility.PUBLIC);
		assertEquals(ValaPluginConstants.IMG_OBJECT_FIELD_PUBLIC,
				ValaSymbolImageProvider.getKey(field));

		Method method;
		method = makeStubMethod(SymbolAccessibility.PRIVATE);
		assertEquals(ValaPluginConstants.IMG_OBJECT_METHOD_PRIVATE,
				ValaSymbolImageProvider.getKey(method));
		method = makeStubMethod(SymbolAccessibility.INTERNAL);
		assertEquals(ValaPluginConstants.IMG_OBJECT_METHOD_DEFAULT,
				ValaSymbolImageProvider.getKey(method));
		method = makeStubMethod(SymbolAccessibility.PROTECTED);
		assertEquals(ValaPluginConstants.IMG_OBJECT_METHOD_PROTECTED,
				ValaSymbolImageProvider.getKey(method));
		method = makeStubMethod(SymbolAccessibility.PUBLIC);
		assertEquals(ValaPluginConstants.IMG_OBJECT_METHOD_PUBLIC,
				ValaSymbolImageProvider.getKey(method));

		LocalVariable localVariable = new LocalVariable(NAME, new VoidType());
		assertEquals(ValaPluginConstants.IMG_OBJECT_LOCAL_VARIABLE,
				ValaSymbolImageProvider.getKey(localVariable));

		Class cls = new Class(NAME);
		assertEquals(ValaPluginConstants.IMG_OBJECT_CLASS,
				ValaSymbolImageProvider.getKey(cls));
	}

	/**
	 * Creates a new stub {@link Field} with a given {@link SymbolAccessibility}
	 * .
	 */
	private Field makeStubField(SymbolAccessibility accessibility) {
		Field field = new Field(NAME, new VoidType());
		field.setAccessibility(accessibility);
		return field;
	}

	/**
	 * Creates a new stub {@link Method} with a given
	 * {@link SymbolAccessibility}.
	 */
	private Method makeStubMethod(SymbolAccessibility accessibility) {
		Method method = new Method(NAME, new VoidType());
		method.setAccessibility(accessibility);
		return method;
	}

}
