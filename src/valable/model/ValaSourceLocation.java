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

/**
 * Holds information on an location in the source code.
 */
public class ValaSourceLocation {

	private final ValaSource source;
	private final int line;
	private final int column;

	/**
	 * Creates a new location in the given {@link ValaSource}.
	 * 
	 * @param valaSource
	 *            the source
	 * @param line
	 *            the line number, starting at 0
	 * @param column
	 *            the column number, starting at 0
	 */
	public ValaSourceLocation(ValaSource valaSource, int line, int column) {
		this.source = valaSource;
		this.line = line;
		this.column = column;
	}

	/**
	 * Returns the line number, starting at 0.
	 */
	public int getLine() {
		return line;
	}

	/**
	 * Returns the column number, starting at 0.
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Returns the {@link ValaSource} containing this {@link ValaSourceLocation}
	 * .
	 */
	public ValaSource getSource() {
		return this.source;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		} else if (!(other instanceof ValaSourceLocation)) {
			return false;
		}

		ValaSourceLocation otherLocation = (ValaSourceLocation) other;
		return getLine() == otherLocation.getLine()
				&& getColumn() == otherLocation.getColumn()
				&& getSource().equals(otherLocation.getSource());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return line * 1000 + column + this.source.hashCode();
	}

}