/**
 * Copyright (C) 2008  Andrew Flegg <andrew@bleb.org>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.model;

import java.util.Comparator;
import java.util.regex.Pattern;


/**
 * Superclass for all Vala information derived from a line in a {@code .vala}
 * file.
 */
public class ValaSymbol {

	/**
	 * A comparator which ensures that two {@link ValaSymbol}s are returned in
	 * the order in which they are declared in the source files.
	 * 
	 * @see ValaSourceLocation#getLine()
	 */
	public static final Comparator<ValaSymbol> SOURCE_ORDER = new Comparator<ValaSymbol>() {
		@Override
		public int compare(ValaSymbol arg0, ValaSymbol arg1) {
			return arg0.getSourceLocation().getLine()
					- arg1.getSourceLocation().getLine();
		}
	};

	private final String name;
	private ValaSourceLocation sourceLocation;

	public static final Pattern IDENTIFIER = Pattern
			.compile("[A-Za-z_]([A-Za-z0-9_])*");

	/**
	 * Creates a new {@link ValaSymbol} from a {@code .vala} source file.
	 * 
	 * @param name
	 *            Name of this {@link ValaSymbol}.
	 */
	public ValaSymbol(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public ValaSourceLocation getSourceLocation() {
		return sourceLocation;
	}

	public void setSourceLocation(ValaSourceLocation sourceLocation) {
		this.sourceLocation = sourceLocation;
	}

	public <R> R accept(ValaSymbolVisitor<R> visitor) {
		return visitor.visitSymbol(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg) {
		if (arg == null || !arg.getClass().equals(this.getClass()))
			return false;

		ValaSymbol other = (ValaSymbol) arg;
		return name.equals(other.name)
				&& sourceLocation.equals(other.sourceLocation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (name != null ? name.hashCode() : 0)
				+ (sourceLocation != null ? sourceLocation.hashCode() : 0);
	}

}
