/**
 * Copyright (C) 2008  Andrew Flegg <andrew@bleb.org>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.model;

import java.util.HashSet;
import java.util.Set;


/**
 * Encapsulate information about a field in a {@link ValaType}.
 */
public class ValaField extends ValaEntity implements HasModifiers {
	private final Set<String>  modifiers = new HashSet<String>();
	private String       type;
	
	
	/**
	 * Create a new instance with the given name.
	 * 
	 * @param source
	 */
	public ValaField(String name) {
		super(name);
	}
	

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}


	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}


	/**
	 * @return the modifiers
	 */
	@Override
	public Set<String> getModifiers() {
		return modifiers;
	}
	
	
	/**
	 * @return the {@link ValaSymbolAccessibility} contained in
	 *         {@link #modifiers}.
	 */
	@Override
	public ValaSymbolAccessibility getAccessibility() {
		for (ValaSymbolAccessibility v : ValaSymbolAccessibility.values())
			if (modifiers.contains(v.toString()))
				return v;
		
		return ValaSymbolAccessibility.INTERNAL;
	}

	@Override
	public <R> R accept(ValaEntityVisitor<R> visitor) {
		return visitor.visitField(this);
	}

}
