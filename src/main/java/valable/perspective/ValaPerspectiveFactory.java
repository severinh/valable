/**
 * Copyright (C) 2008  Johann Prieur <johann.prieur@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.perspective;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.progress.IProgressConstants;

import valable.wizard.project.ValaProjectWizard;

public class ValaPerspectiveFactory implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(IPageLayout layout) {
        String editor = layout.getEditorArea();
        
        // Layout
        IFolderLayout topLeft = layout.createFolder("topLeft", IPageLayout.LEFT, (float)0.26, editor);
        topLeft.addView("valable.view.ValaResourceView");
		
        IFolderLayout outputFolder= layout.createFolder("bottom", IPageLayout.BOTTOM, (float)0.75, editor); 
		// outputFolder.addView(IPageLayout.ID_PROBLEM_VIEW);		
		// outputFolder.addPlaceholder(NewSearchUI.SEARCH_VIEW_ID);
		outputFolder.addPlaceholder(IConsoleConstants.ID_CONSOLE_VIEW);
		// outputFolder.addPlaceholder(IPageLayout.ID_BOOKMARKS);
		outputFolder.addPlaceholder(IProgressConstants.PROGRESS_VIEW_ID);
		
		// layout.addView(IPageLayout.ID_OUTLINE, IPageLayout.RIGHT, (float)0.75, editor);

		// Actions
		layout.addNewWizardShortcut(ValaProjectWizard.ID);
		layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder");
        layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.file");
        layout.addNewWizardShortcut("org.eclipse.ui.editors.wizards.UntitledTextFileWizard");
        
        // layout.addShowViewShortcut(NewSearchUI.SEARCH_VIEW_ID);
		layout.addShowViewShortcut(IConsoleConstants.ID_CONSOLE_VIEW);
		// layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
		// layout.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);
		// layout.addShowViewShortcut(IPageLayout.ID_RES_NAV);
        // layout.addShowViewShortcut(IPageLayout.ID_TASK_LIST);
        
        layout.addActionSet(IPageLayout.ID_NAVIGATE_ACTION_SET);
	}

}
