/**
 * Copyright (C) 2007-2008  Johann Prieur <johann.prieur@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.valable.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.ContentAssistAction;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import org.valable.ValaPlugin;
import org.valable.editors.util.ColorManager;
import org.valable.outline.ValaOutlinePage;

public class ValaEditor extends TextEditor {

	private final ColorManager colorManager;
	private ValaOutlinePage outlinePage;

	/**
	 * Create a new ValaEditor.
	 */
	public ValaEditor() {
		super();

		colorManager = new ColorManager();
		setSourceViewerConfiguration(new ValaConfiguration(colorManager));
		setDocumentProvider(new ValaDocumentProvider());
	}

	@Override
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

	/**
	 * Return the required adapter.
	 * 
	 * @see org.eclipse.ui.editors.text.TextEditor#getAdapter(java.lang.Class)
	 * @see http://wiki.eclipse.org/
	 *      FAQ_How_do_I_create_an_Outline_view_for_my_own_language_editor%3F
	 */
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class required) {
		if (IContentOutlinePage.class.equals(required)) {
			if (outlinePage == null) {
				outlinePage = createOutlinePage();
			}
			return outlinePage;
		}

		return super.getAdapter(required);
	}

	/**
	 * Additional actions in the Vala editor.
	 * 
	 * @see org.eclipse.ui.editors.text.TextEditor#createActions()
	 */
	@Override
	protected void createActions() {
		super.createActions();

		Action action = new ContentAssistAction(ValaPlugin.getResourceBundle(),
				"ContentAssistProposal.", this);
		String id = ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS;
		action.setActionDefinitionId(id);
		setAction("ContentAssistProposal", action);
		markAsStateDependentAction("ContentAssistProposal", true);
	}

	/**
	 * Returns the file represented in this editor.
	 */
	public IFile getCurrentFile() {
		return (IFile) this.getEditorInput().getAdapter(IFile.class);
	}

	/**
	 * Creates the outline page used with this editor.
	 * 
	 * @return the created Vala outline page
	 */
	protected ValaOutlinePage createOutlinePage() {
		ValaOutlinePage page = new ValaOutlinePage(this);
		return page;
	}

	/**
	 * Informs the editor that its outline has been closed.
	 */
	public void outlinePageClosed() {
		if (outlinePage != null) {
			outlinePage = null;
			resetHighlightRange();
		}
	}

}