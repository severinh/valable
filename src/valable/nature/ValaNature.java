/* ValaNature.java
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
package valable.nature;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import valable.builder.ValaProjectBuilder;

public class ValaNature implements IProjectNature {
	
	public final static String ID = "valable.nature.ValaNature";
	
	private IProject project;

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IProjectNature#configure()
	 */
	@Override
	public void configure() throws CoreException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IProjectNature#deconfigure()
	 */
	@Override
	public void deconfigure() throws CoreException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IProjectNature#getProject()
	 */
	@Override
	public IProject getProject() {
		return project;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core.resources.IProject)
	 */
	@Override
	public void setProject(IProject project) {
		this.project = project;
	}

	
	private static void addBuilders(IProject project, IProgressMonitor monitor) throws CoreException {
		IProjectDescription description = project.getDescription();
		ICommand[] commands = description.getBuildSpec();
		boolean found = false;

		for (ICommand command : commands) {
			if (command.getBuilderName().equals(ValaProjectBuilder.ID)) {
				found = true;
				break;
			}
		}
		
		if (!found) {
			// Add builder to the project
			ICommand command = description.newCommand();
			command.setBuilderName(ValaProjectBuilder.ID);
			ICommand[] newCommands = new ICommand[commands.length + 1];

			// Add it before other builders
			System.arraycopy(commands, 0, newCommands, 1, commands.length);
			newCommands[0] = command;
			description.setBuildSpec(newCommands);
			project.setDescription(description, null);
		}
	}
	
	/**
	 * Adds the Vala nature to the given project
	 * @param project to which the nature should be added
	 * @param monitor
	 * @return the added project nature
	 * @throws CoreException 
	 */
	public static IProjectNature addNature(IProject project, IProgressMonitor monitor) throws CoreException {
		if(monitor == null) {
            monitor = new NullProgressMonitor();
        }
		
		if(project.hasNature(ValaNature.ID)) {
			return project.getNature(ValaNature.ID);
		}
		
		// Update the project description
		IProjectDescription description = project.getDescription();
		String[] oldNatures = description.getNatureIds();
        String[] newNatures = new String[oldNatures.length + 1];
        System.arraycopy(oldNatures, 0, newNatures, 0, oldNatures.length);
        newNatures[oldNatures.length] = ValaNature.ID;
        description.setNatureIds(newNatures);
		project.setDescription(description, monitor);
		
		// Add the builders
		addBuilders(project, monitor);
		
		return project.getNature(ValaNature.ID);
	}
	
	
}
