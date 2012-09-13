/**
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.model;

public interface ValaSymbolVisitor<R> {

	public R visitSymbol(ValaSymbol symbol);

	public R visitField(ValaField field);

	public R visitMethod(ValaMethod method);

	public R visitType(ValaType type);

	public R visitLocalVariable(ValaLocalVariable localVariable);

}
