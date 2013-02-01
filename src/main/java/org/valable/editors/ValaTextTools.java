/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.valable.editors;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.rules.IPartitionTokenScanner;
import org.valable.editors.vala.IValaPartitions;
import org.valable.editors.vala.ValaPartitionScanner;

/**
 * Tools required to configure a Vala text viewer. The color manager and all
 * scanner exist only one time, i.e. the same instances are returned to all
 * clients. Thus, clients share those tools.
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
public class ValaTextTools {

	/**
	 * Array with legal content types.
	 */
	private final static String[] LEGAL_CONTENT_TYPES = new String[] {
			IValaPartitions.GTKDOC_COMMENT,
			IValaPartitions.VALA_MULTILINE_COMMENT,
			IValaPartitions.VALA_MULTILINE_STRING,
			IValaPartitions.VALA_VERBATIM_STRING,
			IValaPartitions.VALA_STRING_TEMPLATES,
			IValaPartitions.VALA_CHARACTER };

	/** The preference store. */
	private IPreferenceStore preferenceStore;

	public ValaTextTools(IPreferenceStore store) {
		this.preferenceStore = store;
	}

	/**
	 * Returns a scanner which is configured to scan Vala-specific partitions,
	 * which are multi-line comments, gtk-doc comments, and regular Vala source
	 * code.
	 * 
	 * @return a Vala partition scanner
	 */
	public IPartitionTokenScanner getPartitionScanner() {
		return new ValaPartitionScanner();
	}

	/**
	 * Factory method for creating a Vala-specific document partitioner using
	 * this object's partitions scanner. This method is a convenience method.
	 * 
	 * @return a newly created Vala document partitioner
	 */
	public IDocumentPartitioner createDocumentPartitioner() {
		return new FastPartitioner(getPartitionScanner(), LEGAL_CONTENT_TYPES);
	}

	/**
	 * Sets up the Vala document partitioner for the given document for the
	 * default partitioning.
	 * 
	 * @param document
	 *            the document to be set up
	 */
	public void setupValaDocumentPartitioner(IDocument document) {
		setupValaDocumentPartitioner(document,
				IDocumentExtension3.DEFAULT_PARTITIONING);
	}

	/**
	 * Sets up the Vala document partitioner for the given document for the
	 * given partitioning.
	 * 
	 * @param document
	 *            the document to be set up
	 * @param partitioning
	 *            the document partitioning
	 * @since 3.0
	 */
	public void setupValaDocumentPartitioner(IDocument document,
			String partitioning) {
		IDocumentPartitioner partitioner = createDocumentPartitioner();
		if (document instanceof IDocumentExtension3) {
			IDocumentExtension3 extension3 = (IDocumentExtension3) document;
			extension3.setDocumentPartitioner(partitioning, partitioner);
		} else {
			document.setDocumentPartitioner(partitioner);
		}
		partitioner.connect(document);
	}

	/**
	 * Returns this text tool's preference store.
	 * 
	 * @return the preference store
	 */
	protected IPreferenceStore getPreferenceStore() {
		return preferenceStore;
	}

}
