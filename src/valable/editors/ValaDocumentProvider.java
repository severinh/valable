/* ValaDocumentProvider.java
 *
 * Copyright (C) 2007-2008  Johann Prieur <johann.prieur@gmail.com>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package valable.editors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;

import valable.editors.vala.ValaPartitionScanner;

public class ValaDocumentProvider extends FileDocumentProvider {

	protected IDocument createDocument(Object element) throws CoreException {
		IDocument document = super.createDocument(element);
		if (document != null) {
			IDocumentPartitioner partitioner =
				new FastPartitioner(
					new ValaPartitionScanner(),
					new String[] {
						ValaPartitionScanner.GTKDOC_COMMENT,
						ValaPartitionScanner.VALA_MULTILINE_COMMENT,
						ValaPartitionScanner.VALA_MULTILINE_STRING,
						ValaPartitionScanner.VALA_VERBATIM_STRING
					});
			partitioner.connect(document);
			document.setDocumentPartitioner(partitioner);
		}
		return document;
	}
}