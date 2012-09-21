/**
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.gnome.gtk.Gtk;

import valable.model.ValaProject;
import valable.model.ValaSource;

/**
 * Provides the infrastructure for various tests.
 */
public abstract class AbstractTest {

	public static final String TEST_DIRECTORY_NAME = "test";
	public static final String TEST_SOURCES_DIRECTORY_NAME = "sources";
	public static final File TEST_SOURCES_DIRECTORY = new File(
			TEST_DIRECTORY_NAME, TEST_SOURCES_DIRECTORY_NAME);
	public static final String TEST_PROJECT_NAME = "Test";

	static {
		Gtk.init(new String[] {});
	}

	/**
	 * Parses and returns the given test {@link SourceFile} referred to by its
	 * filename.
	 */
	public ValaSource parseTestSource(String filename) {
		File file = new File(TEST_SOURCES_DIRECTORY, filename);
		IFile localFile = new LocalFile(file);
		ValaProject project = new ValaProject(TEST_PROJECT_NAME);
		ValaSource source = new ValaSource(project, localFile);
		source.parse();
		return source;
	}

}
