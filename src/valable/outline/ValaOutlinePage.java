/**
 * Copyright (C) 2008  Andrew Flegg <andrew@bleb.org>
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.outline;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.DecoratingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DecorationContext;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.gnome.vala.SourceFile;
import org.gnome.vala.SourceLocation;
import org.gnome.vala.SourceReference;
import org.gnome.vala.Symbol;

import valable.editors.ValaEditor;
import valable.model.ValaProject;
import valable.model.ValaSource;

/**
 * Provide an outline view for the current class.
 * 
 * @see ValaSource#parse()
 * @see http://wiki.eclipse.org/
 *      FAQ_How_do_I_create_an_Outline_view_for_my_own_language_editor%3F
 */
public class ValaOutlinePage extends ContentOutlinePage {

	private final ValaEditor editor;

	public ValaOutlinePage(ValaEditor editor) {
		super();

		this.editor = editor;
	}

	public ValaEditor getEditor() {
		return editor;
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);

		IFile currentFile = getEditor().getCurrentFile();
		ValaSource currentSource = ValaProject.getProject(currentFile)
				.getSource(currentFile);
		currentSource.parse();
		SourceFile currentSourceFile = currentSource.getSourceFile();

		TreeViewer viewer = getTreeViewer();
		viewer.setContentProvider(new ValaContentProvider());
		viewer.setLabelProvider(new DecoratingStyledCellLabelProvider(
				new ValaLabelProvider(), PlatformUI.getWorkbench()
						.getDecoratorManager().getLabelDecorator(),
				DecorationContext.DEFAULT_CONTEXT));
		viewer.setInput(currentSourceFile);
		viewer.addSelectionChangedListener(this);
	}

	/**
	 * Selects the {@link Symbol} name in the {@link ValaEditor} when the user
	 * selects it in the outline.
	 */
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		super.selectionChanged(event);

		ISelection selection = event.getSelection();
		if (!selection.isEmpty()) {
			TreeNode treeNode = (TreeNode) ((IStructuredSelection) selection)
					.getFirstElement();
			Object value = treeNode.getValue();
			if (value instanceof Symbol) {
				Symbol symbol = (Symbol) value;
				SourceReference sourceReference = symbol
						.getNameSourceReference();
				SourceLocation begin = sourceReference.getBegin();
				SourceLocation end = sourceReference.getEnd();
				int offset = getOffset(begin);
				int length = end.getColumn() - begin.getColumn() + 1;
				try {
					getEditor().selectAndReveal(offset, length);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Returns the offset of a {@link SourceLocation} within the document.
	 */
	private int getOffset(SourceLocation sourceLocation) {
		IDocumentProvider provider = getEditor().getDocumentProvider();
		IDocument document = provider.getDocument(getEditor().getEditorInput());

		int line = sourceLocation.getLine() - 1;
		int column = sourceLocation.getColumn() - 1;
		int offset;
		try {
			offset = document.getLineOffset(line);
		} catch (BadLocationException e) {
			e.printStackTrace();
			return 0;
		}
		offset += column;
		return offset;
	}

}
