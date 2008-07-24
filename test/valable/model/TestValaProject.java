/* TestValaProject.java
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

import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

/**
 * Test that {@link ValaProject} works correctly.
 */
public class TestValaProject extends TestCase {
	
	/**
	 * {@link ValaProject#getAvailablePackages()} should return a list
	 * of {@link ValaPackage} - which should always include
	 * {@code Gee} and {@code Vala}.
	 */
	public void testGetPackages() {
		Map<String, Set<ValaPackage>> packages = ValaProject.getAvailablePackages();
		
		assertNotNull("Packages set", packages);
		System.out.println(packages);
		
		assertNotNull("Found libgee", packages.get("Gee"));
		assertNotNull("Found vala", packages.get("Vala"));
		
		assertEquals("libgee part of vala", "vala-1.0", packages.get("Gee").iterator().next().getPkgConfigName());
		assertEquals("vala VAPI", "vala-1.0", packages.get("Vala").iterator().next().getPkgConfigName());
	}
}
