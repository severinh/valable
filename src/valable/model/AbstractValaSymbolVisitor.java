/**
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.model;

public class AbstractValaSymbolVisitor<R> implements ValaSymbolVisitor<R> {

	@Override
	public R visitSymbol(ValaSymbol symbol) {
		return null;
	}

	@Override
	public R visitField(ValaField field) {
		return visitSymbol(field);
	}

	@Override
	public R visitMethod(ValaMethod method) {
		return visitSymbol(method);
	}

	@Override
	public R visitType(ValaType type) {
		return visitSymbol(type);
	}

	@Override
	public R visitLocalVariable(ValaLocalVariable localVariable) {
		return visitSymbol(localVariable);
	}

}
