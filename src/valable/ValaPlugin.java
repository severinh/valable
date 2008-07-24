/* ValaPlugin.java
 *
 * Copyright (C) 2007-2008  Johann Prieur <johann.prieur@gmail.com>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package valable;

import java.util.ResourceBundle;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import valable.builder.ValaProjectBuilder;
import valable.editors.ValaEditor;

public class ValaPlugin extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "valable";

	private static ValaPlugin plugin;
	private static ResourceBundle resourceBundle = 
			ResourceBundle.getBundle("valable.data.ValaPluginMessages");
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		// -- Compile all Vala projects from scratch...
		//
		for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			IProjectDescription desc = project.getDescription();
			for (ICommand builder : desc.getBuildSpec()) {
				if (builder.getBuilderName().equals(ValaProjectBuilder.class.getName())) {
					// TODO This needs to be moved to some kind of action running in the background
					System.out.println(project + " is a Vala project. Rebuilding...");
					project.build(IncrementalProjectBuilder.FULL_BUILD, null);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
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
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        IEditorPart editor = window.getActivePage().getActiveEditor();
        if (!(editor instanceof ValaEditor))
        	return null;
        
        return (IFile)editor.getEditorInput().getAdapter(IFile.class);
	}
}
