/**
 * Copyright (C) 2008  Johann Prieur <johann.prieur@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.wizard.file;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;

public abstract class AbstractValaFileWizard extends Wizard implements INewWizard {

	protected IWorkbench workbench;
	protected IStructuredSelection selection;
	protected String description;
	
	protected AbstractValaFileWizard(String description) {
		super();
		this.description = description;
	}

	@Override
	public boolean performFinish() {
		IFile file = performCreation();
		
		IWorkbenchWindow activeWindow = workbench.getActiveWorkbenchWindow();
		IWorkbenchPage activePage = activeWindow.getActivePage();
		try {
			IDE.openEditor(activePage, file, true);
		} catch (PartInitException e) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Physically performs the creation of the file resource being created.
	 * @return the created file resource
	 */
	protected abstract IFile performCreation();

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
	}

}
