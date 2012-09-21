/**
 * Copyright (C) 2008  Andrew Flegg <andrew@bleb.org>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.preference.IPreferenceStore;
import org.gnome.vala.Class;

import valable.ValaPlugin;

/**
 * Encapsulates information about a Vala project. A project has a number of
 * source files, and indexes to other information contained therein.
 */
public class ValaProject {

	private static final Pattern NAMESPACE = Pattern
			.compile("^\\s*namespace (\\S+).*$");

	private final String name;
	private final Set<ValaSource> sources = new HashSet<ValaSource>();
	private final Map<String, Class> classes = new HashMap<String, Class>();

	private static Map<String, Set<ValaPackage>> knownPackages = null;
	private static Map<String, ValaProject> projects = new HashMap<String, ValaProject>();

	/**
	 * Creates a new instance containing no sources.
	 * 
	 * @param name
	 *            the name of the project.
	 */
	public ValaProject(String name) {
		super();
		this.name = name;
		projects.put(name, this);
	}

	/**
	 * Gets a previously created {@link ValaProject}, or create a new one.
	 * 
	 * @param name
	 *            the name of the project, corresponding to the name of the
	 *            project in the workspace.
	 * @return An existing, or new project.
	 */
	public synchronized static ValaProject getProject(String name) {
		if (projects.containsKey(name)) {
			return projects.get(name);
		}

		return new ValaProject(name);
	}

	/**
	 * Gets the project for the given file.
	 */
	public static ValaProject getProject(IFile file) {
		return getProject(file.getProject().getName());
	}

	/**
	 * Returns the map of packages. The key corresponds to
	 * <code>using {@var Name};</code> in Vala source files, and the
	 * {@link ValaPackage} provides information on the VAPI file required.
	 */
	public synchronized static Map<String, Set<ValaPackage>> getAvailablePackages() {
		if (knownPackages != null) {
			return knownPackages;
		}

		// Use the configuration if possible
		ValaBuildContext context;
		if (ValaPlugin.getDefault() != null) {
			IPreferenceStore store = ValaPlugin.getDefault()
					.getPreferenceStore();
			context = ValaBuildContext.of(store);
		} else {
			context = ValaBuildContext.getDefault();
		}

		// Read a list of all the VAPI files
		List<File> vapis = context.getVapiFiles();

		// Scan each one for namespaces
		knownPackages = new HashMap<String, Set<ValaPackage>>();
		for (File vapi : vapis) {
			Scanner scanner;
			try {
				scanner = new Scanner(vapi);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				continue;
			}

			while (scanner.hasNextLine()) {
				Matcher matcher = NAMESPACE.matcher(scanner.nextLine());
				if (matcher.matches()) {
					ValaPackage pkg = new ValaPackage(matcher.group(1));
					pkg.setPkgConfigName(vapi.getName().replaceFirst(
							"\\.vapi$", ""));
					pkg.setVapiFile(vapi);

					Set<ValaPackage> providers = knownPackages.get(pkg
							.getName());
					if (providers == null) {
						providers = new HashSet<ValaPackage>();
						knownPackages.put(pkg.getName(), providers);
					}

					providers.add(pkg);
				}
			}
			scanner.close();
		}

		return knownPackages;
	}

	/**
	 * Whether this project contains a class with the given name.
	 * 
	 * @return true if {@link #getClass(String)} has been called for
	 *         <var>name</var> previously.
	 */
	public synchronized boolean hasType(String name) {
		return classes.containsKey(name);
	}

	/**
	 * Returns the type definition for the given class name. This should be used
	 * to ensure that all references to a given class use the same instance.
	 * 
	 * @param name
	 *            the name of the class.
	 * @return existing, or new, instance of the class.
	 */
	public synchronized Class getClass(String name) {
		Class result = classes.get(name);
		return result;
	}

	/**
	 * Gets the {@link ValaSource} representation for the given workspace file,
	 * creating a new one if necessary.
	 * 
	 * @param file
	 *            File corresponding to {@link ValaSource#getSource()}
	 * @return New or existing {@linkplain ValaSource} in this project.
	 */
	public ValaSource getSource(IFile file) {
		for (ValaSource source : sources)
			if (source.getSource().equals(file))
				return source;

		return new ValaSource(this, file);
	}

	/**
	 * Get the {@link ValaSource} representation containing the given type.
	 * 
	 * @param cls
	 *            Class to find.
	 * @return Existing {@link ValaSource} or <var>null</var> if none.
	 */
	public ValaSource getSource(Class cls) {
		for (ValaSource source : sources)
			if (source.getClasses().containsValue(cls))
				return source;

		return null;
	}

	public String getName() {
		return name;
	}

	public Set<ValaSource> getSources() {
		return sources;
	}

	public Collection<Class> getClasses() {
		return classes.values();
	}

	/**
	 * Returns a list of all packages used in this project.
	 */
	public Set<ValaPackage> getUsedPackages() {
		Set<ValaPackage> result = new HashSet<ValaPackage>();

		for (ValaSource source : sources)
			result.addAll(source.getUses());

		// The GLib package is now implicit
		ValaPackage glibPackage = new ValaPackage("GLib");
		glibPackage.setPkgConfigName("gobject-2.0");
		result.add(glibPackage);

		return result;
	}

}
