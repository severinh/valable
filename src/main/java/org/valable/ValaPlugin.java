/**
 * Copyright (C) 2007-2008  Johann Prieur <johann.prieur@gmail.com>
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.valable;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.valable.builder.ValaProjectBuilder;
import org.valable.editors.ValaEditor;
import org.valable.editors.ValaTextTools;

public class ValaPlugin extends AbstractUIPlugin implements ValaPluginConstants {

	private static ValaPlugin plugin;
	private static ResourceBundle resourceBundle = ResourceBundle
			.getBundle("org.valable.data.ValaPluginMessages");

	private ValaTextTools valaTextTools;

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);

		// -- If the plugin's being initialised twice, only run the setup code
		// once...
		//
		synchronized (ValaPlugin.class) {
			if (plugin != null)
				return;

			plugin = this;

			// -- Compile all Vala projects from scratch...
			//
			Set<String> scheduledTobuild = new HashSet<String>();
			project: for (IProject project : ResourcesPlugin.getWorkspace()
					.getRoot().getProjects()) {
				if (!project.isOpen()
						|| scheduledTobuild.contains(project.getName()))
					continue;

				IProjectDescription desc = project.getDescription();
				for (ICommand builder : desc.getBuildSpec()) {
					if (builder.getBuilderName().equals(
							ValaProjectBuilder.class.getName())) {
						scheduledTobuild.add(project.getName());
						final IProject valaProject = project;
						WorkspaceJob job = new WorkspaceJob("Rebuilding "
								+ valaProject.getName()) {
							@Override
							public IStatus runInWorkspace(
									IProgressMonitor monitor)
									throws CoreException {
								try {
									valaProject.refreshLocal(
											IResource.DEPTH_INFINITE, monitor);
									valaProject
											.build(IncrementalProjectBuilder.FULL_BUILD,
													monitor);
								} catch (Throwable t) {
									t.printStackTrace();
									return Status.CANCEL_STATUS;
								}
								return Status.OK_STATUS;
							}
						};
						job.setRule(ResourcesPlugin.getWorkspace().getRoot());
						job.schedule();
						continue project;
					}
				}
			}
		}
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static ValaPlugin getDefault() {
		return plugin;
	}

	/**
	 * @return the resourceBundle
	 */
	public static ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	/**
	 * Find the active Vala text file editor, if any.
	 * 
	 * @return Current Vala file being edited, or null if none.
	 */
	public static IFile getCurrentFile() {
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		IEditorPart editor = window.getActivePage().getActiveEditor();
		if (!(editor instanceof ValaEditor))
			return null;

		return (IFile) editor.getEditorInput().getAdapter(IFile.class);
	}

	@Override
	protected void initializeImageRegistry(ImageRegistry registry) {
		for (String key : IMG_KEYS) {
			ImageDescriptor desc = imageDescriptorFromPlugin(PLUGIN_ID, key);
			if (desc == null) {
				desc = ImageDescriptor.getMissingImageDescriptor();
			}
			getImageRegistry().put(key, desc);
		}
	}

	public synchronized ValaTextTools getValaTextTools() {
		if (valaTextTools == null) {
			valaTextTools = new ValaTextTools(getPreferenceStore());
		}
		return valaTextTools;
	}

}
