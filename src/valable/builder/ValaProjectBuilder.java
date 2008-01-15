/* ValaProjectBuilder.java
 *
 * Copyright (C) 2008  Johann Prieur <johann.prieur@gmail.com>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package valable.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;

import valable.job.ValaBuildJob;

public class ValaProjectBuilder extends IncrementalProjectBuilder {

	public static final String ID = "valable.builder.ValaProjectBuilder";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IncrementalProjectBuilder#build(int,
	 *      java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
			throws CoreException {
		IProject[] built = null;
		if (kind == FULL_BUILD) {
			fullBuild(monitor);
			built = new IProject[1];
			built[0] = getProject();
		} else {
			// TODO : incremental or delta build
		}

		if (built == null)
			built = new IProject[0];
		return built;
	}

	/**
	 * Performs a full build of the project, reporting to <code>monitor</code>.
	 * 
	 * @param monitor
	 * @throws CoreException
	 */
	private void fullBuild(IProgressMonitor monitor) throws CoreException {
		List<IFile> filesToCompile = new ArrayList<IFile>();

		monitor.beginTask("ValaProjectBuilding", IProgressMonitor.UNKNOWN);
		IResource[] members = getProject().members();

		for (IResource resource : members) {
			if (resource.getType() == IResource.FILE) {
				addValaCompilerCompliantFile((IFile) resource, filesToCompile);
			} else if (resource.getType() == IResource.FOLDER) {
				for (IFile file : getFileChildren((IFolder) resource)) {
					addValaCompilerCompliantFile(file, filesToCompile);
				}
			}
		}

		ValaBuildJob job = new ValaBuildJob(filesToCompile);
		job.schedule();

		monitor.done();
	}

	/**
	 * Adds the given <code>file</code> resource to the given list only if its
	 * extension complies with the possible inputs for the valac compiler.
	 * 
	 * @param file
	 *            the file resource
	 * @param files
	 *            the list in which the file should be added
	 */
	private static void addValaCompilerCompliantFile(IFile file,
			List<IFile> files) {
		if (file.getName().endsWith(".vala")
				|| file.getName().endsWith(".vapi")
				|| file.getName().endsWith(".c")) {
			files.add((IFile) file);
		}
	}

	/**
	 * Computes a list of all children files in a folder.
	 * 
	 * @param folder
	 *            the folder resource
	 * @return the list of children files
	 */
	private static List<IFile> getFileChildren(IFolder folder) {
		final ArrayList<IFile> files = new ArrayList<IFile>();
		try {
			folder.accept(new IResourceVisitor() {

				public boolean visit(IResource resource) {
					if (resource instanceof IFile) {
						files.add((IFile) resource);
						return false;
					}
					return true;
				}

			});
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
		return files;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IncrementalProjectBuilder#clean(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected void clean(IProgressMonitor monitor) throws CoreException {
		// TODO : clean the build directory
		super.clean(monitor);
	}

}
