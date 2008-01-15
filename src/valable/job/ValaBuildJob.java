/* ValaBuildJob.java
 *
 * Copyright (C) 2008  Johann Prieur <johann.prieur@gmail.com>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package valable.job;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.preference.IPreferenceStore;

import valable.ValaPlugin;
import valable.preferences.PreferenceConstants;

public class ValaBuildJob extends Job {

	private List<IFile> filesToCompile;
	private String valac;
	private String vapi;
	private String output;

	public ValaBuildJob(String name) {
		super(name);
	}

	public ValaBuildJob(List<IFile> filesToCompile, String valac, String vapi,
			String output) {
		this("ValaBuildJob");

		// TODO : add --pkg
		this.filesToCompile = filesToCompile;
		this.valac = valac;
		this.vapi = vapi;
		this.output = output;
	}

	/**
	 * Builds the vala compiler command to compile the files from the given list
	 * 
	 * @param filesToCompile
	 *            the list of files to compile with that command
	 * @return the valac command to execute to compile the files
	 */
	private String buildValacCommand() {
		IPreferenceStore store = ValaPlugin.getDefault().getPreferenceStore();
		StringBuffer command = new StringBuffer();

		// Vala compiler program
		command.append(valac);

		// VAPI directory
		command.append(String.format(" --vapidir=%s", vapi));

		// Output directory
		command.append(String.format(" --directory=%s", output));

		// Vala files to compile
		for (IFile file : filesToCompile) {
			command.append(String.format(" %s", file.getLocation()));
		}

		return command.toString();
	}

	protected String getStringFromStream(InputStream stream) throws IOException {
		StringBuffer buffer = new StringBuffer();
		byte[] b = new byte[100];
		int finished = 0;
		while (finished != -1) {
			finished = stream.read(b);
			if (finished != -1) {
				String current = new String(b, 0, finished);
				buffer.append(current);
			}
		}
		return buffer.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		Runtime runtime = Runtime.getRuntime();

		String command = buildValacCommand();
		try {
			System.out.println(command);
			Process process = runtime.exec(command);

			// TODO : something better is needed here, output to console
			InputStream stream = process.getInputStream();
			System.out.println(getStringFromStream(stream));

		} catch (IOException e) {
			return Status.CANCEL_STATUS;
		}

		return Status.OK_STATUS;
	}

}
