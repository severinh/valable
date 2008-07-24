/* ValaEntity.java
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

import java.util.Comparator;

import valable.model.ValaSource.SourceReference;

/**
 * Superclass for all Vala information derived from a line in a
 * {@code .vala} file.
 */
public class ValaEntity {
	
	/**
	 * A comparator which ensures that two {@link ValaEntity}s are returned
	 * in the order in which they are declared in the source files.
	 * 
	 * @see SourceReference#getLine()
	 */
	public static final Comparator<ValaEntity> SOURCE_ORDER = new Comparator<ValaEntity>() {
		public int compare(ValaEntity arg0, ValaEntity arg1) {
			return arg0.getSourceReference().getLine() -
			       arg1.getSourceReference().getLine();
		}
	};
	
	
	private final String    name;
	private SourceReference sourceReference;
	
	
	/**
	 * Create a new entity from a {@code .vala} source file.
	 * 
	 * @param name Name of this entity.
	 */
	public ValaEntity(String name) {
		super();
		this.name = name;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @return the sourceReference
	 */
	public SourceReference getSourceReference() {
		return sourceReference;
	}


	/**
	 * @param sourceReference the sourceReference to set
	 */
	public void setSourceReference(SourceReference sourceReference) {
		this.sourceReference = sourceReference;
	}
	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}
}
