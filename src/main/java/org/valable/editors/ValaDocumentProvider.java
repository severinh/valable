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
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;

import org.valable.editors.vala.IValaPartitions;
import org.valable.editors.vala.ValaPartitionScanner;

public class ValaDocumentProvider extends FileDocumentProvider {

	@Override
	protected IDocument createDocument(Object element) throws CoreException {
		IDocument document = super.createDocument(element);
		if (document != null) {
			IDocumentPartitioner partitioner = new FastPartitioner(
					new ValaPartitionScanner(), new String[] {
							IValaPartitions.GTKDOC_COMMENT,
							IValaPartitions.VALA_MULTILINE_COMMENT,
							IValaPartitions.VALA_MULTILINE_STRING,
							IValaPartitions.VALA_VERBATIM_STRING,
							IValaPartitions.VALA_STRING_TEMPLATES,
							IValaPartitions.VALA_CHARACTER });
			partitioner.connect(document);
			document.setDocumentPartitioner(partitioner);
		}
		return document;
	}

}