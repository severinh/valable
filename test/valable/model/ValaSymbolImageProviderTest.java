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

		Class cls;
		cls = makeStubClass(SymbolAccessibility.PRIVATE);
		assertEquals(ValaPluginConstants.IMG_OBJECT_CLASS_PRIVATE,
				ValaSymbolImageProvider.getKey(cls));
		cls = makeStubClass(SymbolAccessibility.INTERNAL);
		assertEquals(ValaPluginConstants.IMG_OBJECT_CLASS_DEFAULT,
				ValaSymbolImageProvider.getKey(cls));
		cls = makeStubClass(SymbolAccessibility.PROTECTED);
		assertEquals(ValaPluginConstants.IMG_OBJECT_CLASS_PROTECTED,
				ValaSymbolImageProvider.getKey(cls));
		cls = makeStubClass(SymbolAccessibility.PUBLIC);
		assertEquals(ValaPluginConstants.IMG_OBJECT_CLASS_PUBLIC,
				ValaSymbolImageProvider.getKey(cls));
	}

	/**
	 * Creates a new stub class with a given accessibility.
	 * 
	 * @param accessibility
	 *            the accessibility of the stub class
	 * @return the newly created class
	 */
	private Class makeStubClass(SymbolAccessibility accessibility) {
		Class cls = new Class(NAME);
		cls.setAccessibility(accessibility);
		return cls;
	}

	/**
	 * Creates a new stub field with a given accessibility.
	 * 
	 * @param accessibility
	 *            the accessibility of the stub field
	 * @return the newly created field
	 */
	private Field makeStubField(SymbolAccessibility accessibility) {
		Field field = new Field(NAME, new VoidType());
		field.setAccessibility(accessibility);
		return field;
	}

	/**
	 * Creates a new stub method with a given accessibility.
	 * 
	 * @param accessibility
	 *            the accessibility of the stub method
	 * @return the newly created method
	 */
	private Method makeStubMethod(SymbolAccessibility accessibility) {
		Method method = new Method(NAME, new VoidType());
		method.setAccessibility(accessibility);
		return method;
	}

}
