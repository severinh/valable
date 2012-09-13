/**
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.model;

import java.io.File;
import java.util.Arrays;

import junit.framework.TestCase;

import org.junit.Test;

public class ValaBuildContextTest extends TestCase {

	@Test
	public void testDefault() {
		ValaBuildContext context = ValaBuildContext.getDefault();
		assertNotNull(context.getValaVersion());
		assertFalse(context.getVapiDirectories().isEmpty());
		assertFalse(context.getVapiFiles().isEmpty());
		for (File dataDirectory : context.getReadableDataDirectories()) {
			assertTrue(dataDirectory.canRead());
		}
	}

	@Test
	public void testMock() {
		File dataDirectory = new File("/foo/share");
		File vapiDirectory = new File("/foo/share/vala/vapi");
		File valacExecutable = new File("/foo/valac");
		ValaBuildContext context = new ValaBuildContext(
				Arrays.asList(dataDirectory), valacExecutable);
		assertNull(context.getValaVersion());
		assertEquals(Arrays.asList(vapiDirectory), context.getVapiDirectories());
		assertTrue(context.getVapiFiles().isEmpty());
	}

}
