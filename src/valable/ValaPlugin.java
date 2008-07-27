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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import valable.builder.ValaProjectBuilder;
import valable.editors.ValaEditor;
import valable.model.ValaEntity.Visibility;

public class ValaPlugin extends AbstractUIPlugin {

	public enum ImageType  { FILE, CLASS, INTERFACE, ENUM, FIELD, METHOD, PACKAGE, VARIABLE, UNKNOWN; }
	public static final String PLUGIN_ID = "valable";

	private static ValaPlugin plugin;
	private static ResourceBundle resourceBundle = 
			ResourceBundle.getBundle("valable.data.ValaPluginMessages");
	
	/**
	 * A map of images for entities with a particular visibility. 
	 */
	private static final Map<ImageType, Map<Visibility, Image>> images = new HashMap<ImageType, Map<Visibility,Image>>();
	
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);

		// -- If the plugin's being initialised twice, only run the setup code once...
		//
		synchronized (ValaPlugin.class) {
			if (plugin != null)
				return;
	
			plugin = this;
			
			loadIcons();
			
			// -- Compile all Vala projects from scratch...
			//
			Set<String> scheduledTobuild = new HashSet<String>(); 
			project: for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
				if (!project.isOpen() || scheduledTobuild.contains(project.getName()))
					continue;
				
				IProjectDescription desc = project.getDescription();
				for (ICommand builder : desc.getBuildSpec()) {
					if (builder.getBuilderName().equals(ValaProjectBuilder.class.getName())) {
						scheduledTobuild.add(project.getName());
						final IProject valaProject = project;
						WorkspaceJob job = new WorkspaceJob("Rebuilding " + valaProject.getName()) {
							@Override
							public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
								try {
									valaProject.refreshLocal(IResource.DEPTH_INFINITE, monitor);
									valaProject.build(IncrementalProjectBuilder.FULL_BUILD, monitor);
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

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		unloadIcons();
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
	
	
	/**
	 * Find an image which can be used to represent the given type of object
	 * with the specified visibility. If no image for the given visibility
	 * can be found, the first for <var>type</var> is used. 
	 * 
	 * @param type
	 * @param visibility
	 * @return
	 */
	public static Image findImage(ImageType type, Visibility visibility) {
		if (images.get(type) == null)
			return type == ImageType.UNKNOWN ? null :findImage(ImageType.UNKNOWN, visibility);
		
		Image result = images.get(type).get(visibility);
		if (result == null)
			return images.get(type).values().iterator().next();
		
		return result;
	}
	
	
	/**
	 * Load all known icons from {@code icons}.
	 */
	private void loadIcons() {
		addIcon(ImageType.FILE,  Visibility.DEFAULT, "vala_obj.gif");
		addIcon(ImageType.UNKNOWN, Visibility.DEFAULT, "unknown_obj.gif");
		addIcon(ImageType.PACKAGE, Visibility.DEFAULT, "package_obj.gif");
		addIcon(ImageType.CLASS, Visibility.DEFAULT, "class_obj.gif");
		addIcon(ImageType.INTERFACE, Visibility.DEFAULT, "int_obj.gif");
		addIcon(ImageType.ENUM,  Visibility.DEFAULT, "enum_obj.gif");
		addIcon(ImageType.VARIABLE, Visibility.DEFAULT, "localvariable_obj.gif");
		addIcon(ImageType.FIELD, Visibility.DEFAULT, "field_default_obj.gif");
		addIcon(ImageType.FIELD, Visibility.PRIVATE, "field_private_obj.gif");
		addIcon(ImageType.FIELD, Visibility.PUBLIC,  "field_public_obj.gif");
		addIcon(ImageType.FIELD, Visibility.PROTECTED, "field_protected_obj.gif");
		addIcon(ImageType.METHOD, Visibility.DEFAULT, "methdef_obj.gif");
		addIcon(ImageType.METHOD, Visibility.PRIVATE, "methpri_obj.gif");
		addIcon(ImageType.METHOD, Visibility.PUBLIC,  "methpub_obj.gif");
		addIcon(ImageType.METHOD, Visibility.PROTECTED, "methpro_obj.gif");
	}
	
	
	/**
	 * Add an entry to {@link #images} for the given args.
	 * 
	 * @param type
	 * @param visibility
	 * @param name
	 */
	private void addIcon(ImageType type, Visibility visibility, String name) {
		Map<Visibility, Image> typeMap = images.get(type);
		if (typeMap == null) {
			typeMap = new HashMap<Visibility, Image>();
			images.put(type, typeMap);
		} else if (typeMap.containsKey(visibility)) {
			return;
		}
		
		Image image = imageDescriptorFromPlugin(PLUGIN_ID, "icons/" + name).createImage();
		System.out.println("Loaded image [" + image + "] for " + name);
		typeMap.put(visibility, image);
	}
	
	
	/**
	 * Unload all the icons in {@link #images}.
	 */
	private void unloadIcons() {
		for (Map<Visibility, Image> type : images.values()) {
			for (Image image : type.values()) {
				image.dispose();
			}
		}
		images.clear();
	}
}
