/**
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.model;

public class AbstractValaEntityVisitor<R> implements ValaEntityVisitor<R> {

	@Override
	public R visitEntity(ValaEntity entity) {
		return null;
	}

	@Override
	public R visitField(ValaField field) {
		return visitEntity(field);
	}

	@Override
	public R visitMethod(ValaMethod method) {
		return visitEntity(method);
	}

	@Override
	public R visitType(ValaType type) {
		return visitEntity(type);
	}

	@Override
	public R visitLocalVariable(ValaLocalVariable localVariable) {
		return visitEntity(localVariable);
	}

}
