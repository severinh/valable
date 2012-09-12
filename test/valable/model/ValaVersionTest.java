/* ValaVersionTest.java
 *
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */

package valable.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ValaVersionTest {

	private static final ValaVersion VERSION_0_17_3 = new ValaVersion(0, 17, 3);
	private static final ValaVersion VERSION_0_18_1 = new ValaVersion(0, 18, 1);

	@Test
	public void testStability() {
		assertFalse(VERSION_0_17_3.isStable());
		assertTrue(VERSION_0_18_1.isStable());
		assertEquals(VERSION_0_18_1, VERSION_0_18_1.getStableVersion());
		assertEquals(new ValaVersion(0, 18, 0),
				VERSION_0_17_3.getStableVersion());
	}

	@Test
	public void testToString() {
		assertEquals("0.17.3", VERSION_0_17_3.toString());
		assertEquals("0.18.1", VERSION_0_18_1.toString());
		assertEquals("0.17", VERSION_0_17_3.toShortString());
		assertEquals("0.18", VERSION_0_18_1.toShortString());
	}

	@Test
	public void testOfString() {
		assertEquals(VERSION_0_17_3, ValaVersion.of("0.17.3"));
		assertEquals(VERSION_0_18_1, ValaVersion.of("0.18.1"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidMajorVersion() {
		new ValaVersion(-1, 1, 2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidMinorVersion() {
		new ValaVersion(0, -1, 2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidRevision() {
		new ValaVersion(0, 1, -1);
	}

}
