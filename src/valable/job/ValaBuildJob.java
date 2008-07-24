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

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;

import valable.model.ValaPackage;
import valable.model.ValaProject;
import valable.model.ValaSource;
import valable.model.ValaType;

public class ValaBuildJob extends Job {
	private static final Pattern COMPILE_MSG  = Pattern.compile("(.*?):(\\d+).(\\d+)-(\\d+).(\\d+): (error|warning): (.*)");
	private static final Pattern UNKNOWN_NAME = Pattern.compile("The name `(\\S+?)' does not exist in the context of `([^\\.']+?)(\\..+?)?'");
	
	
	/**
	 * Model representing compilation failures & warnings.
	 */
	public enum MessageType {
		ERROR(IMarker.SEVERITY_ERROR), WARNING(IMarker.SEVERITY_WARNING);
		
		public final int severity;
		
		/**
		 * Construct a new message type.
		 * 
		 * @param severity Severity of the problem for the {@link IMarker}.
		 */
		private MessageType(int severity) {
			this.severity = severity;
		}
	}
	

	private ValaProject project;
	private String baseDir;
	private Set<IFile> filesToCompile;
	private String valac;
	private String vapi;
	private String output;
	private Set<String> packages = new HashSet<String>();
	
	/**
	 * Used to retrieve {@link IFile}s from leaf filenames.
	 */
	private Map<String, IFile> reverseFiles = new HashMap<String, IFile>();

	
	public ValaBuildJob(Set<IFile> filesToCompile, String valac, String vapi,
			String output) throws CoreException {
		super("Compiling Vala...");

		this.filesToCompile = filesToCompile;
		this.valac = valac;
		this.vapi = vapi;
		this.output = output;
		
		if (!filesToCompile.isEmpty())
			initialise();
	}
	
	
	/**
	 * Ensure each file is parsed and appropriate packages
	 * and dependencies added (recursively).
	 * 
	 * @throws CoreException 
	 */
	private void initialise() throws CoreException {
		for (IFile file : new HashSet<IFile>(filesToCompile)) {
			if (project == null) {
				project = ValaProject.getProject(file);
				baseDir = file.getProject().getLocation().toOSString();
			}
			
			// -- Remove any existing markers...
			//
			reverseFiles.put(file.getName(), file);
			for (IMarker marker : file.findMarkers(IMarker.PROBLEM, false, 0))
				marker.delete();
			
			// -- Parse the file...
			//
			ValaSource source = project.getSource(file);
			System.out.println("Parsing [" + source + "] for [" +file + "]");
			source.parse();
			
			// -- Add dependencies...
			//
			for (ValaPackage pkg : source.getUses())
				packages.add(pkg.getPkgConfigName());
			
			for (ValaType type : source.getTypes().values())
				for (ValaType dep : type.getDependencies())
					if (filesToCompile.add(project.getSource(dep).getSource()))
						initialise();
		}
		
		// baseDir is where the files from valac are written
		// TODO Get valac to follow --directory with --compile
		baseDir = new File("").getAbsolutePath();
	}
	

	/**
	 * Builds the vala compiler command to compile the files from the given list
	 * 
	 * @param filesToCompile
	 *            the list of files to compile with that command
	 * @return the valac command to execute to compile the files
	 */
	private String[] buildValacCommand() {
		List<String> bits = new ArrayList<String>();
		
		// Vala compiler program
		bits.add(valac);

		// VAPI directory
		bits.add(String.format("--vapidir=%s", vapi));

		// Output directory
		bits.add(String.format("--directory=%s", output));
		
		// Incremental building
		bits.add("--compile");
		
		// Packages
		for (String pkg : packages) {
			bits.add(String.format("--pkg=%s", pkg));
		}

		// Vala files to compile
		for (IFile file : filesToCompile) {
			bits.add(file.getLocation().toOSString());
		}

		return bits.toArray(new String[bits.size()]);
	}
	
	
	/**
	 * Used to facilitate incremental building, this provides the command
	 * to link together all the individual {@code .o} files.
	 * 
	 * @return Command to link the project.
	 */
	private String buildLinkCommand() {
		StringBuilder command = new StringBuilder();
		
		// Compiler
		command.append("gcc");
		
		// Output file
		command.append(" -o " + new File(output, project.getName().replaceAll("\\W+", "")).getAbsolutePath());
		
		// Packages
		for (ValaPackage pkg : project.getUsedPackages()) {
			command.append(" ").append(commandOutput("pkg-config", "--libs", pkg.getPkgConfigName()));
		}
		
		// Object files to build
		for (File obj : new File(output).listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".o");
			}
		})) {
			command.append(" ").append(obj.getAbsolutePath());
		}
		
		return command.toString();
	}
	
	
	/**
	 * Return the string output of a command.
	 * 
	 * @param args Command to execute
	 * @return Output of running <var>args</var>
	 */
	private String commandOutput(String... args) {
		Process process;
		try {
			System.out.println(Arrays.asList(args));
			process = Runtime.getRuntime().exec(args);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
		
		StringBuilder output = new StringBuilder();
		
		Scanner scanner = new Scanner(process.getInputStream());
		while (scanner.hasNextLine())
			output.append(scanner.nextLine()).append("\n");
		
		return output.toString();
	}
	

	/**
	 * Copy the given input streams to the given output streams. Once all
	 * the inputs have been completed read, the method returns.
	 * 
	 * @param out Target stream
	 * @param inputs Number of {@link InputStream}s to copy.
	 * @throws IOException
	 */
	protected void copyToStream(OutputStream out, InputStream... inputs) throws IOException {
		byte[] b = new byte[100];
		
		boolean done = false;
		while (!done) {
			done = true;
			for (InputStream in : inputs) {
				int read = in.read(b);
				if (read != -1) {
					out.write(b, 0, read);
					System.out.write(b, 0, read);
					done = false;
				} else {
					done &= true;
				}
			}
		}
	}
	

	/**
	 * Run the compilation for this build job, logging output to a console.
	 * 
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		if (filesToCompile.isEmpty())
			return Status.OK_STATUS;
		
		// TODO Early abort if last mods show no reason to
		
		Runtime runtime = Runtime.getRuntime();

//		MessageConsole console = findConsole(valac);
//		console.clearConsole();
//		Thread.sleep(100); // Otherwise the console can get cleared before any output
		
		PrintStream out = System.out;
//		PrintStream out = new PrintStream(console.newOutputStream());

		// -- Incrementally build the files, pulling in any dependencies necessary...
		//
		boolean doAgain = false;
		String[] command = buildValacCommand();
		try {
			out.println(Arrays.asList(command) + "\n");

			Process process = runtime.exec(command);
//			copyToStream(out, process.getInputStream(), process.getErrorStream());
			Scanner scanner = new Scanner(process.getErrorStream());
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				out.println(line);
				
				Matcher msg = COMPILE_MSG.matcher(line);
				if (!msg.matches())
					continue;
				
				String file      = msg.group(1);
				int    startLine = Integer.parseInt(msg.group(2));
				int    startCol  = Integer.parseInt(msg.group(3));
				int    endLine   = Integer.parseInt(msg.group(4));
				int    endCol    = Integer.parseInt(msg.group(5));
				MessageType type = MessageType.valueOf(msg.group(6).toUpperCase());
				String message   = msg.group(7);
				
				Matcher matcher = UNKNOWN_NAME.matcher(message);
				if (type == MessageType.ERROR && matcher.matches()) {
					String missingName    = matcher.group(1);
					String containingType = matcher.group(2);
					
					for (IFile compilingFile : filesToCompile) {
						if (project.hasType(missingName) && project.hasType(containingType))
							doAgain |= project.getType(containingType).
							                   getDependencies().
								               add(project.getType(missingName));
					}
					
				} else {
					IMarker marker = reverseFiles.get(file).createMarker(IMarker.PROBLEM);
					marker.setAttribute(IMarker.MESSAGE, message);
					marker.setAttribute(IMarker.LINE_NUMBER, startLine);
					marker.setAttribute(IMarker.SEVERITY, type.severity);
				}
			}

			// -- Wait for process to finish...
			//
			if (process.waitFor() == 0) {
				out.println("Success!");
				
				// Move .o files to the output dir
				for (IFile valaFile : filesToCompile) {
					String name = valaFile.getName().replaceAll("\\.vala$", ".o");
					out.println(commandOutput("mv", new File(baseDir, name).getAbsolutePath(),
							                        output + "/"));
				}
			} else {
				out.println("Failed.");
				if (!doAgain)
					return Status.CANCEL_STATUS;
			}
			
			// -- Run again if we've determined new dependencies...
			//
			if (doAgain) {
				out.println("repeating");
				initialise();
				return run(monitor);
			}
			
		} catch (IOException e) {
			return Status.CANCEL_STATUS;
		} catch (InterruptedException e) {
			return Status.CANCEL_STATUS;
		} catch (CoreException e) {
			e.printStackTrace();
			return Status.CANCEL_STATUS;
		}
		
		// -- Link the project files together now...
		//
		try {
			String cmd = buildLinkCommand();
			out.println(cmd);
			Process link = runtime.exec(cmd);
			copyToStream(out, link.getInputStream(), link.getErrorStream());
			link.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
			return Status.CANCEL_STATUS;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return Status.CANCEL_STATUS;
		}

		return Status.OK_STATUS;
	}

	
	/**
	 * Find a console suitable for this plugin, or create one if necessary.
	 * 
	 * @param name name of the console
	 * @return
	 */
	private MessageConsole findConsole(String name) {
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		for (int i = 0; i < existing.length; i++)
			if (name.equals(existing[i].getName()))
				return (MessageConsole) existing[i];
		//no console found, so create a new one
		MessageConsole myConsole = new MessageConsole(name, null);
		conMan.addConsoles(new IConsole[] { myConsole });
		return myConsole;
	}

}
