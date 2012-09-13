/**
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Encapsulates information about a Vala version.
 */
public class ValaVersion {

	private final int majorVersion;
	private final int minorVersion;
	private final int revision;

	public ValaVersion(int majorVersion, int minorVersion, int revision) {
		if (majorVersion < 0) {
			throw new IllegalArgumentException(
					"expected non-negative major version, got " + majorVersion);
		}
		if (minorVersion < 0) {
			throw new IllegalArgumentException(
					"expected non-negative minor version, got " + minorVersion);
		}
		if (revision < 0) {
			throw new IllegalArgumentException(
					"expected non-negative revision, got " + revision);
		}
		this.majorVersion = majorVersion;
		this.minorVersion = minorVersion;
		this.revision = revision;
	}

	public ValaVersion(int majorVersion, int minorVersion) {
		this(majorVersion, minorVersion, 0);
	}

	public int getMajorVersion() {
		return majorVersion;
	}

	public int getMinorVersion() {
		return minorVersion;
	}

	public int getRevision() {
		return revision;
	}

	/**
	 * Returns whether this {@link ValaVersion} is stable, i.e. whether the
	 * minor version is even.
	 */
	public boolean isStable() {
		boolean isStable = getMinorVersion() % 2 == 0;
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
			return new ValaVersion(getMajorVersion(), getMinorVersion() + 1, 0);
		}
	}

	@Override
	public String toString() {
		return String.format("%s.%s.%s", getMajorVersion(), getMinorVersion(),
				getRevision());
	}

	public String toShortString() {
		return String.format("%s.%s", getMajorVersion(), getMinorVersion());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getMajorVersion())
				.append(getMinorVersion()).append(getRevision()).build();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		} else if (!(other instanceof ValaVersion)) {
			return false;
		}
		ValaVersion otherVersion = (ValaVersion) other;
		return new EqualsBuilder()
				.append(getMajorVersion(), otherVersion.getMajorVersion())
				.append(getMinorVersion(), otherVersion.getMinorVersion())
				.append(getRevision(), otherVersion.getRevision()).build();
	}

	/**
	 * Converts a {@link String} such as i.e. "0.15.3" to a {@link ValaVersion}.
	 */
	public static ValaVersion of(String versionString) {
		try {
			String[] fragment = versionString.split("\\.");
			int majorVersion = Integer.valueOf(fragment[0]);
			int minorVersion = Integer.valueOf(fragment[1]);
			int revision = 0;
			if (fragment.length > 2) {
				revision = Integer.valueOf(fragment[2]);
			}
			ValaVersion valaVersion = new ValaVersion(majorVersion,
					minorVersion, revision);
			return valaVersion;
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"cannot handle format of version '" + versionString + "'");
		}
	}

}
