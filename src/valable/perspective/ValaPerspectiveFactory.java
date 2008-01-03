/* ValaPerspective.java
 *
 * Copyright (C) 2008  Johann Prieur <johann.prieur@gmail.com>
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package valable.perspective;

import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.progress.IProgressConstants;

import valable.wizard.project.ValaProjectWizard;

public class ValaPerspectiveFactory implements IPerspectiveFactory {

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui.IPageLayout)
	 */
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
