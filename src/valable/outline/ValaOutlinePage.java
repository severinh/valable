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
package valable.outline;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

import valable.editors.ValaEditor;
import valable.model.ValaProject;
import valable.model.ValaSource;

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

		ValaSource currentSource = ValaProject.getProject(editor.getCurrentFile()).getSource(editor.getCurrentFile());
		try {
			currentSource.parse();
		} catch (CoreException e) {
			e.printStackTrace();
		}

		TreeViewer viewer = getTreeViewer();
		viewer.setContentProvider(new ValaContentProvider());
		viewer.setLabelProvider(new ValaLabelProvider());
		viewer.addSelectionChangedListener(this);
		viewer.setInput(currentSource);
	}
}
