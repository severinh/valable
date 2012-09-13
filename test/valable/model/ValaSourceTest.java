/**
 * Copyright (C) 2008  Andrew Flegg <andrew@bleb.org>
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.junit.Test;

/**
 * Test that {@link ValaSource} works correctly.
 */
public class ValaSourceTest {

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
		IFile file = new LocalFile(new File("test/simple.vala"));
		ValaSource source = new ValaSource(new ValaProject("Test"), file);
		source.parse();

		Iterator<ValaPackage> usesIterator = source.getUses().iterator();
		assertEquals("Incorrect number of used packages", 1, source.getUses()
				.size());
		assertEquals("'Gee' package use not detected", "Gee", usesIterator
				.next().getName());

		assertEquals("Incorrect number of types", 2, source.getTypes().size());

		ValaType simple = source.getTypes().get("Simple");
		ValaType foo = source.getTypes().get("Foo");
		assertNotNull("'Simple' type not found", simple);
		assertNotNull("'Foo' type not found", foo);

		assertEquals("Incorrect number of ancestors of 'Simple'", 1, simple
				.getInherits().size());
		assertEquals("Ancestor of 'Simple' is not 'Object'", "Object", simple
				.getInherits().iterator().next().getName());

		assertEquals("Incorrect number of ancestors of 'Foo'", 1, foo
				.getInherits().size());
		assertSame("Ancestor of 'Foo' is not 'Simple'", simple, foo
				.getInherits().iterator().next());

		assertEquals("Incorrect number of fields in 'Simple'", 3, simple
				.getFields().size());
		assertEquals("Incorrect field names of 'Simple'", "[age, name, count]",
				simple.getFields().toString());

		assertEquals("Incorrect number of fields in 'Foo'", 0, foo.getFields()
				.size());

		assertLine(6, simple);
		assertLine(7, simple.getField("age"));
		assertLine(8, simple.getField("name"));
		assertLine(9, simple.getField("count"));
		assertLine(11, simple.getMethod("main"));
		assertLine(22, simple.getMethod("doThing"));
		assertLine(27, simple.getMethod("getParent"));
		assertLine(33, foo);
		assertLine(34, foo.getMethod("getParent"));
		assertLine(38, foo.getMethod("removeParent"));
	}

	public void assertLine(int expectedLine, ValaEntity entity) {
		int line = entity.getSourceReference().getLine();
		assertEquals("Incorrect line number of '" + entity + "'", expectedLine,
				line);
	}

}
