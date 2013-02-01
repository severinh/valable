/**
 * Copyright (C) 2007-2008  Johann Prieur <johann.prieur@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.valable.editors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.editors.text.FileDocumentProvider;

import org.valable.ValaPlugin;
import org.valable.editors.vala.IValaPartitions;

/**
 * A document provider for Vala files.
 */
public class ValaDocumentProvider extends FileDocumentProvider {

	/*
	 * @see AbstractDocumentProvider#createDocument(Object)
	 */
	@Override
	protected IDocument createDocument(Object element) throws CoreException {
		IDocument document = super.createDocument(element);
		if (document != null) {
			ValaTextTools tools = ValaPlugin.getDefault().getValaTextTools();
			tools.setupValaDocumentPartitioner(document, IValaPartitions.VALA_PARTITIONING);
		}
		return document;
	}

}