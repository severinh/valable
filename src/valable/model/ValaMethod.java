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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


/**
 * Encapsulate information about a method in a {@link ValaType}.
 */
public class ValaMethod extends ValaEntity implements HasModifiers {
	private final Set<String>  modifiers = new HashSet<String>();
	private String       type;
	private final Map<String, String> signature = new LinkedHashMap<String, String>();

	// This should really be a more complete AST, taking into account
	// blocks, scoping and types...
	private final Set<ValaLocalVariable> localVariables = new HashSet<ValaLocalVariable>();
	
	
	/**
	 * Create a new instance with the given name.
	 * 
	 * @param source
	 */
	public ValaMethod(String name) {
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
	 * @return the signature
	 */
	public Map<String, String> getSignature() {
		return signature;
	}
	
	
	/**
	 * @return the {@link ValaSymbolAccessibility} contained in
	 *         {@link #modifiers}.
	 */
	@Override
	public ValaSymbolAccessibility getAccessibility() {
		for (ValaSymbolAccessibility v : ValaSymbolAccessibility.values())
			if (modifiers.contains(v.toString().toLowerCase()))
				return v;
		
		return ValaSymbolAccessibility.INTERNAL;
	}


	/**
	 * @return the localVariables
	 */
	public Set<ValaLocalVariable> getLocalVariables() {
		return localVariables;
	}

	@Override
	public <R> R accept(ValaEntityVisitor<R> visitor) {
		return visitor.visitMethod(this);
	}

}
