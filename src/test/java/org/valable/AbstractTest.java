/**
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.valable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.gnome.gtk.Gtk;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.valable.model.ValaProject;
import org.valable.model.ValaSource;

/**
 * Provides the infrastructure for various tests.
 */
public abstract class AbstractTest {

	public static final String TEST_DIRECTORY_NAME = "src/test";
	public static final String TEST_SOURCES_DIRECTORY_NAME = "resources";
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
	public static ValaSource parseTestSource(String filename) {
		File file = new File(TEST_SOURCES_DIRECTORY, filename);
		IFile localFile = mockIFile(file);
		ValaProject project = new ValaProject(TEST_PROJECT_NAME);
		ValaSource source = new ValaSource(project, localFile);
		source.parse();
		return source;
	}

	/**
	 * Creates an object implementing the interface {@link IFile} from a plain
	 * file object.
	 * 
	 * @param file
	 *            the file to wrap
	 * @return the newly created wrapper
	 */
	public static IFile mockIFile(File file) {
		final File finalFile = file;
		IFile iFile = mock(IFile.class);

		try {
			when(iFile.getContents()).thenAnswer(new Answer<InputStream>() {

				@Override
				public InputStream answer(InvocationOnMock invocation)
						throws Throwable {
					try {
						return new FileInputStream(finalFile);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						throw new CoreException(Status.CANCEL_STATUS);
					}
				}

			});
			when(iFile.getFileExtension()).thenAnswer(new Answer<String>() {

				@Override
				public String answer(InvocationOnMock invocation)
						throws Throwable {
					String[] parts = finalFile.getName().split("\\.");
					return parts[parts.length - 1];
				}

			});
			when(iFile.getRawLocation()).thenAnswer(new Answer<IPath>() {

				@Override
				public IPath answer(InvocationOnMock invocation)
						throws Throwable {
					return new Path(finalFile.getAbsolutePath());
				}

			});
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return iFile;
	}
}
