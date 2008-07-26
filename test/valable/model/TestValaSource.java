/* TestValaSource.java
 *
 * Copyright (C) 2008  Andrew Flegg <andrew@bleb.org>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package valable.model;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

/**
 * Test that {@link ValaSource} works correctly.
 */
public class TestValaSource extends TestCase {
	
	/**
	 * Test that simple Vala files can be parsed correctly. This
	 * actually checks multiple things:
	 * 
	 * <ol>
	 * <li>That parsing a file does not throw any exceptions.</li>
	 * <li>That 'using' lines are identified and {@link ValaPackage}s
	 * looked up in the central list.</li>
	 * <li>That multiple types in a Vala file can be identified.</li>
	 * <li>That inheritance information is resolved.</li>
	 * <li>That multiple references to the same {@link ValaType} all use
	 * the same instance.</li>
	 * <li>That 'Simple' contains 3 fields, and 'Foo' none.</li>
	 * </ol>
	 * 
	 * @throws IOException 
	 * @throws CoreException 
	 */
	public void testParse() throws CoreException, IOException {
		IFile file = new LocalFile(new File("test/simple.vala"));
		
		ValaSource source = new ValaSource(new ValaProject("Test"), file);
		source.parse();
		
		assertEquals("Identified using packages", 2, source.getUses().size());
		assertEquals("Using libgee", "Gee", source.getUses().iterator().next().getName());
		assertEquals("Using vala VAPI", "vala-1.0", source.getUses().iterator().next().getPkgConfigName());
		
		assertEquals("Types found", 2, source.getTypes().size());
		
		ValaType simple = source.getTypes().get("Simple");
		ValaType foo    = source.getTypes().get("Foo");
		assertNotNull("Found 'Simple' type", simple);
		assertNotNull("Found 'Foo' type",    foo);
		
		assertEquals("Single ancestor for Simple", 1, simple.getInherits().size());
		assertEquals("Object ancestor for Simple", "Object", simple.getInherits().iterator().next().getName());
		
		assertEquals("Single ancestor for Foo", 1, foo.getInherits().size());
		assertSame("Foo ancestor == Simple", simple, foo.getInherits().iterator().next());
		
		assertEquals("Simple field count", 3, simple.getFields().size());
		assertEquals("Simple field names", "[age, name, count]", simple.getFields().toString());
		
		assertEquals("Foo field count", 0, foo.getFields().size());
	}
}


