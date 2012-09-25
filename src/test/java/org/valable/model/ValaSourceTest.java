/**
 * Copyright (C) 2008  Andrew Flegg <andrew@bleb.org>
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.valable.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.io.IOException;
import java.util.Iterator;

import org.eclipse.core.runtime.CoreException;
import org.gnome.vala.Class;
import org.gnome.vala.CodeNode;
import org.gnome.vala.Field;
import org.gnome.vala.Method;
import org.gnome.vala.SourceFile;
import org.gnome.vala.SourceLocation;
import org.gnome.vala.SourceReference;
import org.junit.Test;

import org.valable.AbstractTest;

/**
 * Test that {@link ValaSource} works correctly.
 */
public class ValaSourceTest extends AbstractTest {

	/**
	 * Test that simple Vala files can be parsed correctly. This actually checks
	 * multiple things:
	 * 
	 * <ol>
	 * <li>That parsing a file does not throw any exceptions.</li>
	 * <li>That 'using' lines are identified and {@link ValaPackage}s looked up
	 * in the central list.</li>
	 * <li>That multiple types in a Vala file can be identified.</li>
	 * <li>That inheritance information is resolved.</li>
	 * <li>That multiple references to the same {@link ValaType} all use the
	 * same instance.</li>
	 * <li>That 'Simple' contains 3 fields, and 'Foo' none.</li>
	 * </ol>
	 * 
	 * @throws IOException
	 * @throws CoreException
	 */
	@Test
	public void testParse() throws CoreException, IOException {
		ValaSource source = parseTestSource("simple.vala");
		SourceFile sourceFile = source.getSourceFile();

		Iterator<ValaPackage> usesIterator = source.getUses().iterator();
		assertEquals("Incorrect number of used packages", 1, source.getUses()
				.size());
		assertEquals("'Gee' package use not detected", "Gee", usesIterator
				.next().getName());

		assertEquals("Incorrect number of types", 2, sourceFile.getClasses()
				.size());

		Class simple = sourceFile.getClass("Simple");
		Class foo = sourceFile.getClass("Foo");
		assertNotNull("'Simple' type not found", simple);
		assertNotNull("'Foo' type not found", foo);

		assertEquals("Incorrect number of ancestors of 'Simple'", 1, simple
				.getBaseTypes().size());
		assertEquals("Ancestor of 'Simple' is not 'Object'", "Object", simple
				.getBaseTypes().get(0).getDataType().getName());

		assertEquals("Incorrect number of ancestors of 'Foo'", 1, foo
				.getBaseTypes().size());
		assertSame("Ancestor of 'Foo' is not 'Simple'", simple, foo
				.getBaseTypes().get(0).getDataType());
		assertEquals("Incorrect number of fields in 'Simple'", 3, simple
				.getFields().size());

		assertEquals("Incorrect number of fields in 'Foo'", 0, foo.getFields()
				.size());

		assertSourceLocation(7, 1, simple);

		Field ageField = simple.getField("age");
		assertField("int", ageField);
		assertSourceLocation(8, 3, ageField);

		Field nameField = simple.getField("name");
		assertField("string", nameField);
		assertSourceLocation(9, 3, nameField);

		Field countField = simple.getField("count");
		assertField("int", countField);
		assertSourceLocation(10, 3, countField);

		Method mainMethod = simple.getMethod("main");
		assertMethod("int", mainMethod);
		assertSourceLocation(12, 3, mainMethod);

		Method doThingMethod = simple.getMethod("doThing");
		assertMethod("void", doThingMethod);
		assertSourceLocation(23, 3, doThingMethod);

		Method sampleGetParentMethod = simple.getMethod("getParent");
		assertMethod("Simple", sampleGetParentMethod);
		assertSourceLocation(28, 3, sampleGetParentMethod);

		assertSourceLocation(34, 1, foo);

		Method fooGetParentMethod = foo.getMethod("getParent");
		assertMethod("Simple", fooGetParentMethod);
		assertSourceLocation(35, 3, fooGetParentMethod);

		Method removeParentMethod = foo.getMethod("removeParent");
		assertMethod("void", removeParentMethod);
		assertSourceLocation(39, 3, removeParentMethod);
	}

	public void assertField(String expectedType, Field field) {
		assertEquals("Incorrect type of '" + field + "'", expectedType, field
				.getVariableType().toString());
	}

	public void assertMethod(String expectedReturnType, Method method) {
		assertEquals("Incorrect return type of '" + method + "'",
				expectedReturnType, method.getReturnType().toString());
	}

	public void assertSourceLocation(int expectedLine, int expectedColumn,
			CodeNode codeNode) {
		SourceReference sourceReference = codeNode.getSourceReference();
		SourceLocation sourceLocation = sourceReference.getBegin();
		int line = sourceLocation.getLine();
		int column = sourceLocation.getColumn();
		assertEquals("Incorrect line number of '" + codeNode + "'",
				expectedLine, line);
		assertEquals("Incorrect column number of '" + codeNode + "'",
				expectedColumn, column);
	}

}
