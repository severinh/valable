/**
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.eclipse.jface.preference.IPreferenceStore;

import valable.preferences.PreferenceConstants;

/**
 * Encapsulates the location of the Vala compiler and the data directories in
 * which to search for VAPI files.
 */
public class ValaBuildContext {

	/**
	 * The data directories to be used when the environment variable
	 * XDG_DATA_DIRS is not set.
	 */
	private static final List<File> DEFAULT_DATA_DIRECTORIES = Collections
			.unmodifiableList(Arrays.asList(new File("/usr/share"), new File(
					"/usr/local/share")));

	/**
	 * The name of the Vala compiler executable.
	 */
	private static final String VALAC_EXE_NAME = "valac";

	private static final FilenameFilter VAPI_FILENAME_FILTER = new FilenameFilter() {

		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(".vapi");
		}

	};

	private final List<File> dataDirectories;
	private final File valacExecutable;

	// Computed on-demand
	private List<File> vapiDirectories = null;
	private ValaVersion valaVersion = null;

	public ValaBuildContext(List<File> dataDirectories, File valacExecutable) {
		if (dataDirectories == null) {
			throw new NullPointerException(
					"list of data directories must not be null");
		}
		if (dataDirectories.isEmpty()) {
			throw new IllegalArgumentException(
					"list of data directories must not be empty");
		}
		if (valacExecutable == null) {
			throw new NullPointerException("valac executable must not be null");
		}
		// Defensive copy
		this.dataDirectories = new ArrayList<File>(dataDirectories);
		this.valacExecutable = valacExecutable;
	}

	public List<File> getDataDirectories() {
		return Collections.unmodifiableList(dataDirectories);
	}

	/**
	 * Returns the {@link List} of readable data directories.
	 */
	public List<File> getReadableDataDirectories() {
		List<File> dataDirectories = new ArrayList<File>();
		for (File dataDirectory : getDataDirectories()) {
			if (dataDirectory.canRead()) {
				dataDirectories.add(dataDirectory);
			}
		}
		return dataDirectories;
	}

	/**
	 * Returns the Vala compiler executable {@link File}.
	 */
	public File getValacExecutable() {
		return valacExecutable;
	}

	/**
	 * Returns the version of the Vala compiler if known, and <code>null</code>
	 * otherwise.
	 */
	public ValaVersion getValaVersion() {
		if (valaVersion == null) {
			valaVersion = getValaVersionImpl();
		}
		return valaVersion;
	}

	private ValaVersion getValaVersionImpl() {
		if (getValacExecutable().canExecute()) {
			try {
				Process process = new ProcessBuilder(getValacExecutable()
						.getAbsolutePath(), "--version").start();
				InputStream is = process.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String line = br.readLine();
				if (line != null) {
					line = line.replace("Vala ", "");
					try {
						ValaVersion valaVersion = ValaVersion.of(line);
						return valaVersion;
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public List<File> getVapiDirectories() {
		if (vapiDirectories == null) {
			vapiDirectories = getVapiDirectoriesImpl();
		}
		return vapiDirectories;
	}

	private List<File> getVapiDirectoriesImpl() {
		List<File> vapiDirectories = new ArrayList<File>();
		if (getValaVersion() != null) {
			ValaVersion stableValaVersion = getValaVersion().getStableVersion();
			List<String> valaDirectoryNames = Arrays.asList("vala-"
					+ stableValaVersion.toShortString(), "vala");
			for (File dataDirectory : getDataDirectories()) {
				for (String valaDirectoryName : valaDirectoryNames) {
					File valaDirectory = new File(dataDirectory,
							valaDirectoryName);
					File vapiDirectory = new File(valaDirectory, "vapi");
					if (vapiDirectory.canRead()) {
						vapiDirectories.add(vapiDirectory);
					}
				}
			}
		}
		if (vapiDirectories.isEmpty()) {
			File vapiDirectory = new File(new File(getDataDirectories().get(0),
					"vala"), "vapi");
			vapiDirectories.add(vapiDirectory);
		}
		return vapiDirectories;
	}

	public List<File> getVapiFiles() {
		List<File> vapiFiles = new ArrayList<File>();
		for (File vapiDirectory : getVapiDirectories()) {
			File[] partialVapiFiles = vapiDirectory
					.listFiles(VAPI_FILENAME_FILTER);
			if (partialVapiFiles != null) {
				vapiFiles.addAll(Arrays.asList(partialVapiFiles));
			}
		}
		return vapiFiles;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).build();
	}

	/**
	 * Returns the non-empty {@link List} of default data directories,
	 * regardless of whether they are readable or not.
	 */
	private static List<File> getDefaultDataDirectories() {
		List<File> result = new ArrayList<File>();
		String xdgDataDirs = System.getenv("XDG_DATA_DIRS");
		if (xdgDataDirs != null) {
			for (String dirName : xdgDataDirs.split(File.pathSeparator)) {
				if (!dirName.isEmpty()) {
					result.add(new File(dirName));
				}
			}
		}
		if (result.isEmpty()) {
			result.addAll(DEFAULT_DATA_DIRECTORIES);
		}
		return result;
	}

	public static File getDefaultValacExecutable() {
		try {
			Process process = new ProcessBuilder("which", VALAC_EXE_NAME)
					.start();
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = br.readLine();
			if (line != null) {
				File result = new File(line);
				if (result.canExecute()) {
					return result;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		File valacExecutable = new File("/usr/bin", VALAC_EXE_NAME);
		return valacExecutable;
	}

	public static ValaBuildContext getDefault() {
		List<File> dataDirectories = getDefaultDataDirectories();
		File valacExecutable = getDefaultValacExecutable();
		ValaBuildContext context = new ValaBuildContext(dataDirectories,
				valacExecutable);
		return context;
	}

	public static ValaBuildContext of(IPreferenceStore store) {
		// TODO: Do not ignore the value of PreferenceConstants.P_VAPI_PATH
		List<File> dataDirectories = getDefaultDataDirectories();
		File valacExecutable = new File(
				store.getString(PreferenceConstants.P_VALAC_EXE));
		ValaBuildContext context = new ValaBuildContext(dataDirectories,
				valacExecutable);
		return context;
	}

}
