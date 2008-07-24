/* ValaOutlinePage.java
 *
 * Copyright (C) 2008  Andrew Flegg <andrew@bleb.org>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package valable.editors.vala;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

import valable.editors.ValaEditor;

/**
 * Provide an outline view for the current class.
 * 
 * @see ValaSource#parse()
 * @see http://wiki.eclipse.org/FAQ_How_do_I_create_an_Outline_view_for_my_own_language_editor%3F
 */
public class ValaOutlinePage extends ContentOutlinePage {
	
	private ValaEditor        editor;
	

	/**
	 * @param editor
	 */
	public ValaOutlinePage(ValaEditor editor) {
		super();
		
		//this.documentProvider = documentProvider;
		this.editor = editor;
	}

	
	/**
	 * @see org.eclipse.ui.views.contentoutline.ContentOutlinePage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
	      super.createControl(parent);
//	      TreeViewer viewer = getTreeViewer();
//	      viewer.setContentProvider(new MyContentProvider());
//	      viewer.setLabelProvider(new MyLabelProvider());
//	      viewer.addSelectionChangedListener(this);
//	      viewer.setInput(myInput);
	}
}
