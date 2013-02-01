/**
 * Copyright (C) 2007-2008  Johann Prieur <johann.prieur@gmail.com>
 * Copyright (C) 2013  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

/**
 * The following components are based on
 * {@link org.eclipse.jdt.internal.ui.javaeditor.JavaEditor}:
 * <ul><li>SmartLineStartAction</li></ul>
 * 
 * Copyright (c) 2000, 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Tom Eicher <eclipse@tom.eicher.name> - [formatting] 'Format Element' in JavaDoc does also format method body - https://bugs.eclipse.org/bugs/show_bug.cgi?id=238746
 *     Tom Eicher (Avaloq Evolution AG) - block selection mode
 */

package org.valable.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.ContentAssistAction;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import org.valable.ValaPlugin;
import org.valable.editors.util.ColorManager;
import org.valable.editors.vala.IValaPartitions;
import org.valable.outline.ValaOutlinePage;

public class ValaEditor extends TextEditor {

	class SmartLineStartAction extends LineStartAction {

		/**
		 * Creates a new smart line start action
		 *
		 * @param textWidget the styled text widget
		 * @param doSelect a boolean flag which tells if the text up to the beginning of the line should be selected
		 */
		public SmartLineStartAction(ValaEditor valaEditor, final StyledText textWidget, final boolean doSelect) {
			super(textWidget, doSelect);
		}

		/*
		 * @see org.eclipse.ui.texteditor.AbstractTextEditor.LineStartAction#getLineStartPosition(java.lang.String, int, java.lang.String)
		 */
		@Override
		protected int getLineStartPosition(final IDocument document, final String line, final int length, final int offset) {
			String type= IDocument.DEFAULT_CONTENT_TYPE;
			try {
				type= TextUtilities.getContentType(document, IValaPartitions.VALA_PARTITIONING, offset, true);
			} catch (BadLocationException exception) {
				// Should not happen
			}

			int index= super.getLineStartPosition(document, line, length, offset);
			if (type.equals(IValaPartitions.GTKDOC_COMMENT) || type.equals(IValaPartitions.VALA_MULTILINE_COMMENT)) {
				if (index < length - 1 && line.charAt(index) == '*' && line.charAt(index + 1) != '/') {
					do {
						++index;
					} while (index < length && Character.isWhitespace(line.charAt(index)));
				}
			} else {
				if (index < length - 1 && line.charAt(index) == '/' && line.charAt(index + 1) == '/') {
					index++;
					do {
						++index;
					} while (index < length && Character.isWhitespace(line.charAt(index)));
				}
			}
			return index;
		}
	}
	
	private final ColorManager colorManager;
	private ValaOutlinePage outlinePage;

	/**
	 * Create a new ValaEditor.
	 */
	public ValaEditor() {
		super();

		colorManager = new ColorManager();
		setSourceViewerConfiguration(new ValaConfiguration(colorManager, IValaPartitions.VALA_PARTITIONING));
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
	
	/*
	 * @see org.eclipse.ui.texteditor.AbstractTextEditor#createNavigationActions()
	 */
	@Override
	protected void createNavigationActions() {
		super.createNavigationActions();

		final StyledText textWidget = getSourceViewer().getTextWidget();

		IAction action = new SmartLineStartAction(this, textWidget, false);
		action.setActionDefinitionId(ITextEditorActionDefinitionIds.LINE_START);
		setAction(ITextEditorActionDefinitionIds.LINE_START, action);
		
		action= new SmartLineStartAction(this, textWidget, true);
		action.setActionDefinitionId(ITextEditorActionDefinitionIds.SELECT_LINE_START);
		setAction(ITextEditorActionDefinitionIds.SELECT_LINE_START, action);
	}

}