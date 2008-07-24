/* ValaField.java
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

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


/**
 * Encapsulate information about a method in a {@link ValaType}.
 */
public class ValaMethod extends ValaEntity {
	private Set<String>  modifiers = new HashSet<String>();
	private String       type;
	private Map<String, String> signature = new LinkedHashMap<String, String>();
	
	
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
	public Set<String> getModifiers() {
		return modifiers;
	}


	/**
	 * @return the signature
	 */
	public Map<String, String> getSignature() {
		return signature;
	}
}
