/**
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.model;

import org.osgi.framework.Version;

/**
 * Encapsulates information about a Vala version.
 */
public class ValaVersion extends Version {

	public ValaVersion(int major, int minor, int micro) {
		super(major, minor, micro);
	}

	public ValaVersion(int major, int minor) {
		this(major, minor, 0);
	}

	/**
	 * Returns whether this {@link ValaVersion} is stable, i.e. whether the
	 * minor version is even.
	 */
	public boolean isStable() {
		boolean isStable = getMinor() % 2 == 0;
		return isStable;
	}

	/**
	 * Returns this {@link ValaVersion} if it is stable, or the one with the
	 * next-higher even minor version otherwise.
	 */
	public ValaVersion getStableVersion() {
		if (isStable()) {
			return this;
		} else {
			return new ValaVersion(getMajor(), getMinor() + 1, 0);
		}
	}

	public String toShortString() {
		return String.format("%s.%s", getMajor(), getMinor());
	}

	/**
	 * Converts a {@link String} such as i.e. "0.15.3" to a {@link ValaVersion}.
	 */
	public static ValaVersion parseVersion(String versionString) {
		Version version = Version.parseVersion(versionString);
		ValaVersion valaVersion = new ValaVersion(version.getMajor(),
				version.getMinor(), version.getMicro());
		return valaVersion;
	}

}
