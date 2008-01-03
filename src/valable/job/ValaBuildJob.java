/* ValaBuildJob.java
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
	
	public ValaBuildJob(String name) {
		super(name);
	}
	
	public ValaBuildJob(List<IFile> filesToCompile) {
		this("ValaBuildJob");
		this.filesToCompile = filesToCompile;
	}

	/**
	 * Builds the vala compiler command to compile the files from the given list
	 * @param filesToCompile the list of files to compile with that command
	 * @return the valac command to execute to compile the files
	 */
	private String buildValacCommand(List<IFile> filesToCompile) {
		IPreferenceStore store = ValaPlugin.getDefault().getPreferenceStore();
		StringBuffer command = new StringBuffer();
		
		// Vala compiler program
		command.append(store.getString(PreferenceConstants.P_VALAC_EXE));
		
		// VAPI directory
		command.append(String.format(" --vapidir=%s", 
				store.getString(PreferenceConstants.P_VAPI_PATH)));
		
		// Vala files to compile
		for(IFile file : filesToCompile) {
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
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		Runtime runtime = Runtime.getRuntime();
		
		String command = buildValacCommand(filesToCompile);
		try {
			System.out.println(command);
			Process process = runtime.exec(command);
			
			InputStream stream = process.getInputStream();
			System.out.println(getStringFromStream(stream));
			
		} catch (IOException e) {
			return Status.CANCEL_STATUS;
		}
		
		return Status.OK_STATUS;
	}

}
