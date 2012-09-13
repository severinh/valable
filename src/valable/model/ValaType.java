/**
 * Copyright (C) 2008  Andrew Flegg <andrew@bleb.org>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Encapsulates information about a Vala class.
 */
public class ValaType extends ValaSymbol {

	/**
	 * Dependencies: build up during analysis.
	 */
	private final List<ValaType> dependencies = new ArrayList<ValaType>();

	private final SortedSet<ValaField> fields = new TreeSet<ValaField>(
			ValaSymbol.SOURCE_ORDER);
	private final SortedSet<ValaMethod> methods = new TreeSet<ValaMethod>(
			ValaSymbol.SOURCE_ORDER);
	private final Set<ValaType> inherits = new LinkedHashSet<ValaType>();

	/**
	 * Create a new instance for the named type.
	 * 
	 * @param source
	 */
	public ValaType(String name) {
		super(name);
	}

	/**
	 * Find the method which contains the given line number.
	 * 
	 * @param type
	 * @param lineNumber
	 * @return
	 */
	public ValaMethod findMethodForLine(int lineNumber) {
		ValaMethod lastMethod = null;
		for (ValaMethod method : methods) {
			if (lastMethod != null
					&& lineNumber >= lastMethod.getSourceLocation().getLine()
					&& lineNumber < method.getSourceLocation().getLine())
				return lastMethod;

			lastMethod = method;
		}

		return lastMethod;
	}

	public List<ValaType> getDependencies() {
		return dependencies;
	}

	public SortedSet<ValaField> getFields() {
		return fields;
	}

	public ValaField getField(String name) {
		for (ValaField field : getFields()) {
			if (field.getName().equals(name)) {
				return field;
			}
		}
		return null;
	}

	public SortedSet<ValaMethod> getMethods() {
		return methods;
	}

	public ValaMethod getMethod(String name) {
		for (ValaMethod method : getMethods()) {
			if (method.getName().equals(name)) {
				return method;
			}
		}
		return null;
	}

	public Set<ValaType> getInherits() {
		return inherits;
	}

	/**
	 * Resets the contents of this class, ready for rebuilding.
	 */
	public void reset() {
		fields.clear();
		methods.clear();
		inherits.clear();
	}

	@Override
	public <R> R accept(ValaSymbolVisitor<R> visitor) {
		return visitor.visitType(this);
	}

}
