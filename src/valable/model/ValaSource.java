/* ValaSource.java
 *
 * Copyright (C) 2008  Andrew Flegg <andrew@bleb.org>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package valable.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;

/**
 * Encapsulate information about a Vala source file. A source file can
 * consist of a number of types.
 */
public class ValaSource {
	private static final Pattern USING = Pattern.compile("^\\s*using (\\S+)\\s*;\\s*$");
	
	private final ValaProject     project;
	private final IFile           source;
	private List<ValaPackage>     uses  = new ArrayList<ValaPackage>();
	private Map<String, ValaType> types = new HashMap<String, ValaType>();
	
	
	/**
	 * Create a new instance for the given source file within a 
	 * project.
	 * 
	 * @param source
	 */
	public ValaSource(ValaProject project, IFile source) {
		super();
		
		if (!source.getFileExtension().equals("vala"))
			throw new IllegalArgumentException("Only .vala files can be represented");
		
		this.project = project;
		this.source  = source;
		this.project.getSources().add(this);
	}
	
	
	/**
	 * Parse the source file to build up a tree of types, uses etc.
	 * 
	 * <p>Current implementation is a mix of file parsing and {@code ctags}:
	 * in future this should use an <acronym title="Abstract Source Tree">AST</a>.
	 * </p>
	 * 
	 * @throws CoreException if the file is unparseable 
	 */
	public void parse() throws CoreException {
		uses.clear();
		types.clear();
		
		// -- Find usings...
		//
		Scanner scanner = new Scanner(source.getContents());
		while (scanner.hasNextLine()) {
			Matcher matcher = USING.matcher(scanner.nextLine());
			if (matcher.matches()) {
				Set<ValaPackage> pkgs = ValaProject.getAvailablePackages().get(matcher.group(1));
				if (pkgs != null)
					uses.addAll(pkgs);
			}
		}
		scanner.close();
		
		// -- Parse types...
		//
		Process output = null;
		try {
			output = Runtime.getRuntime().exec(new String[] { "ctags",
				"--language-force=C#",
				"-f", "-",
				"-n",
				"--fields=akiSt",
				source.getLocation().toOSString()});
		} catch (IOException e) {
			e.printStackTrace();
			throw new CoreException(Status.CANCEL_STATUS);
		}
		
		scanner = new Scanner(output.getInputStream());
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] cols = line.split("\\t");
			System.out.println(line);
			
			// -- Parse the ctags output...
			//
			String name = cols[0];
			//String file = cols[1];
			int    lineNumber = Integer.parseInt(cols[2].replaceAll("\\D", ""));
			char   type = cols[3].charAt(0);
			
			// Use a map for extra data in the form 'key:value'
			Map<String, String> extraData = new HashMap<String, String>();
			for (int i = 4; i < cols.length; i++) {
				String[] keyValue = cols[i].split(":", 2);
				extraData.put(keyValue[0], keyValue[1]);
			}
			
			// -- Build up the model...
			//
			SourceReference sourceRef = new SourceReference(lineNumber, 0);
			if (type == 'c') {
				// TODO What about private classes?
				ValaType typeDefn = project.getType(name);
				typeDefn.setSourceReference(sourceRef);
				typeDefn.reset();
				
				if (extraData.containsKey("inherits"))
					for (String superType : extraData.get("inherits").split(",\\s*"))
						typeDefn.getInherits().add(project.getType(superType));
				
				types.put(name, typeDefn);
				
			} else if (type == 'f') {
				ValaType  typeDefn  = findTypeForLine(lineNumber);
				ValaField fieldDefn = new ValaField(name);
				fieldDefn.setSourceReference(sourceRef);
				fieldDefn.getModifiers().addAll(Arrays.asList(extraData.get("access").split(",\\s*")));
				
				typeDefn.getFields().add(fieldDefn);
				
			} else if (type == 'm') {
				ValaType   typeDefn   = findTypeForLine(lineNumber);
				ValaMethod methodDefn = new ValaMethod(name);
				methodDefn.setSourceReference(sourceRef);
				methodDefn.getModifiers().addAll(Arrays.asList(extraData.get("access").split(",\\s*")));
				// TODO Signature
				
				typeDefn.getMethods().add(methodDefn);
			}
		}
		scanner.close();
	}


	/**
	 * Find the type which is being created for the given line number.
	 * Loops over all {@link #types} and finds the one where
	 * <var>lineNumber</var> > {@link SourceReference#getLine()}
	 * and <var>lineNumber</var> < {@link SourceReference#getLine()} for
	 * the next class.
	 * 
	 * @param lineNumber
	 * @return ValaType enclosing the given line number.
	 */
	private ValaType findTypeForLine(int lineNumber) {
		SortedSet<ValaType> sortedTypes = new TreeSet<ValaType>(ValaEntity.SOURCE_ORDER);
		sortedTypes.addAll(types.values());

		ValaType lastType = null;
		for (ValaType type : sortedTypes) {
			if (lastType != null && lineNumber >= lastType.getSourceReference().getLine() &&
					lineNumber < type.getSourceReference().getLine())
				return lastType;
			
			lastType = type;
		}
		
		return lastType;
	}


	/**
	 * @return the source
	 */
	public IFile getSource() {
		return source;
	}


	/**
	 * @return the types
	 */
	public Map<String, ValaType> getTypes() {
		return types;
	}


	/**
	 * @return the uses
	 */
	public List<ValaPackage> getUses() {
		return uses;
	}
	
	
	/**
	 * Encode information on an entities source location.
	 */
	public class SourceReference {
		private final int line;
		private final int column;
		
		/**
		 * Create a new reference to the given source position
		 * in this {@link ValaSource}.
		 * 
		 * @param line Line number, starts at 1.
		 * @param column Column number, starts at 1.
		 */
		public SourceReference(int line, int column) {
			super();
			this.line = line;
			this.column = column;
		}

		/**
		 * @return the column
		 */
		public int getColumn() {
			return column;
		}

		/**
		 * @return the line
		 */
		public int getLine() {
			return line;
		}
		
		
		/**
		 * @return the source file containing this entity.
		 */
		public ValaSource getSource() {
			return ValaSource.this;
		}
	}

}
