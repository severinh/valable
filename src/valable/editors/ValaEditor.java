/* ValaEditor.java
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

import org.eclipse.ui.editors.text.TextEditor;

import valable.editors.util.ColorManager;

public class ValaEditor extends TextEditor {

	private ColorManager colorManager;

	public ValaEditor() {
		super();
		
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new ValaConfiguration(colorManager));
		setDocumentProvider(new ValaDocumentProvider());
	}
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

}