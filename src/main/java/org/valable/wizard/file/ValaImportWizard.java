/**
 * Copyright (c) 2006 IBM Corporation and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 */
package org.valable.wizard.file;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

public class ValaImportWizard extends Wizard implements IImportWizard {
	
	ValaImportWizardPage mainPage;

	public ValaImportWizard() {
		super();
	}

	@Override
	public boolean performFinish() {
		IFile file = mainPage.createNewFile();
        if (file == null)
            return false;
        return true;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("File Import Wizard"); //NON-NLS-1
		setNeedsProgressMonitor(true);
		mainPage = new ValaImportWizardPage("Import File",selection); //NON-NLS-1
	}
	
    @Override
	public void addPages() {
        super.addPages(); 
        addPage(mainPage);        
    }

}
