/**
 * Copyright (C) 2008  Andrew Flegg <andrew@bleb.org>
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.gnome.vala.CodeContext;
import org.gnome.vala.Parser;
import org.gnome.vala.SourceFile;

/**
 * Encapsulates information about a Vala source file. A source file can consist
 * of a number of types.
 */
public class ValaSource {

	private static final Pattern USING = Pattern
			.compile("^\\s*using (\\S+)\\s*;\\s*$");

	private final IFile source;
	private final Set<ValaPackage> uses = new LinkedHashSet<ValaPackage>();

	private SourceFile sourceFile;

	/**
	 * Creates a new instance for the given source file within a project.
	 */
	public ValaSource(ValaProject project, IFile source) {
		super();

		if (!(source.getFileExtension().equals("vala") || source
				.getFileExtension().equals("vapi")))
			throw new IllegalArgumentException(
					"Only .vala / .vapi files can be represented");

		this.source = source;
	}

	public IFile getSource() {
		return source;
	}

	public SourceFile getSourceFile() {
		return sourceFile;
	}

	public Set<ValaPackage> getUses() {
		return uses;
	}

	public ValaPackage getUse(String name) {
		for (ValaPackage use : getUses()) {
			if (use.getName().equals(name)) {
				return use;
			}
		}
		return null;
	}

	/**
	 * Parse the source file to build up a tree of types, uses etc.
	 * 
	 * @throws CoreException
	 *             if the file is unparseable
	 */
	public List<String> parse() {
		uses.clear();

		// Find usings
		InputStream contents;
		try {
			contents = source.getContents();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		Scanner scanner = new Scanner(contents);
		List<String> lines = new ArrayList<String>();
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			Matcher matcher = USING.matcher(line);

			lines.add(line);
			if (matcher.matches()) {
				Set<ValaPackage> pkgs = ValaProject.getAvailablePackages().get(
						matcher.group(1));
				if (pkgs != null)
					uses.addAll(pkgs);
			}
		}
		scanner.close();

		String sourceFilename = getSource().getRawLocation().toOSString();

		CodeContext codeContext = new CodeContext();
		CodeContext.push(codeContext);
		codeContext.addExternalPackage("glib-2.0");
		codeContext.addExternalPackage("gobject-2.0");
		codeContext.addExternalPackage("gee-1.0");
		codeContext.addSourceFilename(sourceFilename, false, false);

		Parser parser = new Parser();
		parser.parse(codeContext);
		codeContext.check();

		for (SourceFile someSourceFile : codeContext.getSourceFiles()) {
			if (someSourceFile.getFilename().equals(sourceFilename)) {
				sourceFile = someSourceFile;
				break;
			}
		}

		return lines;
	}

}
