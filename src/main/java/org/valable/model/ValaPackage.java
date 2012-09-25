/**
 * Copyright (C) 2008  Andrew Flegg <andrew@bleb.org>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.valable.model;

import java.io.File;

/**
 * Encapsulates information about a Vala package.
 */
public class ValaPackage {

	private final String name;
	private String pkgConfigName;
	private File vapiFile;

	/**
	 * Creates a new instance with the given name.
	 */
	public ValaPackage(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getPkgConfigName() {
		return pkgConfigName;
	}

	public void setPkgConfigName(String pkgConfigName) {
		this.pkgConfigName = pkgConfigName;
	}

	@Override
	public String toString() {
		return name + "->" + pkgConfigName;
	}

	public File getVapiFile() {
		return vapiFile;
	}

	public void setVapiFile(File vapiFile) {
		this.vapiFile = vapiFile;
	}

	@Override
	public final boolean equals(Object arg) {
		if (arg == null || !(arg instanceof ValaPackage))
			return false;

		ValaPackage other = (ValaPackage) arg;
		return name.equals(other.name)
				&& pkgConfigName.equals(other.pkgConfigName);
	}

	@Override
	public int hashCode() {
		return name.hashCode()
				+ (pkgConfigName != null ? pkgConfigName.hashCode() : 0);
	}

}
