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
import org.gnome.vala.Symbol;
import org.gnome.vala.SymbolAccessibility;
import org.junit.Ignore;
import org.junit.Test;

import valable.ValaPluginConstants;

/**
 * Tests {@link ValaSymbolImageProvider}.
 * 
 * @todo Currently commented-out because the Vala parser binding does not
 *       support instantiating fields and methods yet.
 */
@Ignore
public class ValaSymbolImageProviderTest {

	@SuppressWarnings("unused")
	private static final String NAME = "foo";

	@Test
	public void test() {
		Symbol symbol = null;
		// Symbol symbol = new Symbol(NAME);
		assertEquals(ValaPluginConstants.IMG_OBJECT_UNKNOWN,
				ValaSymbolImageProvider.getKey(symbol));

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

		LocalVariable localVariable = null;
		// LocalVariable localVariable = new LocalVariable(NAME);
		assertEquals(ValaPluginConstants.IMG_OBJECT_LOCAL_VARIABLE,
				ValaSymbolImageProvider.getKey(localVariable));

		Class cls = null;
		// Class cls = new Class(NAME);
		assertEquals(ValaPluginConstants.IMG_OBJECT_CLASS,
				ValaSymbolImageProvider.getKey(cls));
	}

	/**
	 * Creates a new stub {@link Field} with a given {@link SymbolAccessibility}
	 * .
	 */
	@SuppressWarnings("null")
	private Field makeStubField(SymbolAccessibility accessibility) {
		Field field = null;
		// Field field = new Field(NAME);
		field.setAccessibility(accessibility);
		return field;
	}

	/**
	 * Creates a new stub {@link Method} with a given
	 * {@link SymbolAccessibility}.
	 */
	@SuppressWarnings("null")
	private Method makeStubMethod(SymbolAccessibility accessibility) {
		Method method = null;
		// Method method = new Method(NAME);
		method.setAccessibility(accessibility);
		return method;
	}

}
