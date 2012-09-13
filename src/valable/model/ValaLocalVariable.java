/**
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.model;

public class ValaLocalVariable extends ValaSymbol {

	private String type;

	public ValaLocalVariable(String name) {
		super(name);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public <R> R accept(ValaSymbolVisitor<R> visitor) {
		return visitor.visitLocalVariable(this);
	}

}
