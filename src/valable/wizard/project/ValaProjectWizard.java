/* ValaProjectWizard.java
 *
 * Copyright (C) 2007-2008  Johann Prieur <johann.prieur@gmail.com>
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
package valable.wizard.project;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;

import valable.nature.ValaNature;



public class ValaProjectWizard extends Wizard implements INewWizard {
	
	public static final String ID = "valable.wizard.project.ValaProject";
	
	private IWorkbench workbench;
	private IStructuredSelection selection;

	private ValaProjectWizardPage projectPage;
	
	public ValaProjectWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		projectPage = new ValaProjectWizardPage();
		this.addPage(projectPage);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		IProject project = createProject();
		if(project == null) {
			return false;
		}
		
		IWorkbenchWindow activeWindow = workbench.getActiveWorkbenchWindow();
		try {
			workbench.showPerspective("valable.perspective.ValaPerspective", activeWindow);
		} catch (WorkbenchException e) {}
		
		return true;
	}

	private IProject createProject() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		
		// Get a handle from the workspace for the new project
		String projectName = projectPage.projectNameText.getText().trim();
		final IProject project = workspace.getRoot().getProject(projectName);

		
		// Set the project location in the project description
		String projectPath = projectPage.locationText.getText().trim();
		final IProjectDescription description = workspace.newProjectDescription(projectName);
		// TODO : specify the real location of the project
		//description.setLocation(new Path(projectPath));
		
		// Instanciate a project creation operation
		WorkspaceModifyOperation projectCreation = new WorkspaceModifyOperation() {
			
			@Override
			protected void execute(IProgressMonitor monitor)
					throws CoreException, InvocationTargetException,
					InterruptedException {
				createProject(project, description, monitor);
			}
			
		};
		
		// Run the project creation operation
		try {
			getContainer().run(true, true, projectCreation);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return null;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
		
		return project;
	}
	
	private void createProject(IProject project, IProjectDescription description, 
			IProgressMonitor monitor) throws CoreException {
		try {
			monitor.beginTask("valaProjectCreation", 3);
			
			project.create(description, new SubProgressMonitor(monitor, 1));
			project.open(IResource.BACKGROUND_REFRESH, 
					new SubProgressMonitor(monitor, 1));
			
			// TODO : create generated files
			
			ValaNature.addNature(project, new SubProgressMonitor(monitor, 1));
		} finally {
			monitor.done();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
	}

}
