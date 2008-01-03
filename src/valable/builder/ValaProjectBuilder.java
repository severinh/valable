/* ValaProjectBuilder.java
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
package valable.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;

import valable.job.ValaBuildJob;

public class ValaProjectBuilder extends IncrementalProjectBuilder {
	
	public static final String ID = "valable.builder.ValaProjectBuilder";
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IncrementalProjectBuilder#build(int, java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
			throws CoreException {
		if(kind == FULL_BUILD) {
			fullBuild(monitor);
		} else {
			// TODO : incremental build
		}
		return null;
	}

	private void fullBuild(IProgressMonitor monitor) throws CoreException {
		List<IFile> filesToCompile = new ArrayList<IFile>();
		
		monitor.beginTask("ValaProjectBuilding", IProgressMonitor.UNKNOWN);
		IResource[] members = getProject().members();
		
		for(IResource resource : members) {
			if(resource.getType() == IResource.FILE) {
				filesToCompile.add((IFile)resource);
			} else if(resource.getType() == IResource.FOLDER) {
				// TODO : grab vala files from sub folders
			}
		}
		
		ValaBuildJob job = new ValaBuildJob(filesToCompile);
		job.schedule();
		
		monitor.done();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IncrementalProjectBuilder#clean(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected void clean(IProgressMonitor monitor) throws CoreException {
		System.out.println("cleaning...");
		super.clean(monitor);
	}
	
}
