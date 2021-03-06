/**
 * Copyright (C) 2008  Andrew Flegg <andrew@bleb.org>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.valable.editors.vala;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.swt.graphics.Image;
import org.gnome.vala.Class;
import org.gnome.vala.Field;
import org.gnome.vala.Method;
import org.gnome.vala.TypeSymbol;

import org.valable.ValaPlugin;
import org.valable.model.ValaProject;
import org.valable.model.ValaSource;
import org.valable.viewsupport.ValaSymbolImageProvider;

/**
 * Handle code complete of possible types.
 * 
 * @todo This is currently very basic and has little to no contextual
 *       information.
 */
public class ValaCompletionProcessor implements IContentAssistProcessor {

	private static final IContextInformation[] NO_CONTEXTS = new IContextInformation[0];
	private static final char[] ACTIVATION_CHARS = new char[] { '.' };

	public static final Pattern IDENTIFIER = Pattern
			.compile("[A-Za-z_]([A-Za-z0-9_])*");
	public static final Pattern LAST_IDENTIFIER = Pattern.compile("(?s).*?("
			+ IDENTIFIER.pattern() + ")$");

	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
			int offset) {
		IFile currentFile = ValaPlugin.getCurrentFile();
		ValaProject project = ValaProject.getProject(currentFile);
		ValaSource source = project.getSource(currentFile);

		List<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();

		String text = viewer.getDocument().get().substring(0, offset);
		Matcher lastIdMatcher = LAST_IDENTIFIER.matcher(text);
		String filter = ""; // Only include values which match this
		if (lastIdMatcher.matches()) {
			filter = lastIdMatcher.group(1);
		}

		// Add fields and methods in the current source file
		List<ICompletionProposal> localVars = new ArrayList<ICompletionProposal>();
		for (Class type : source.getSourceFile().getClasses()) {
			for (Field field : type.getFields()) {
				addProposal(proposals, field.getVariableType().getDataType(),
						field.getVariableType() + " - " + type.getName(),
						filter, offset, 0);
			}
			for (Method method : type.getMethods()) {
				// TODO: Add all local variables from all methods at head of
				// list

				// TODO Signature
				addProposal(proposals, method.getReturnType().getDataType(),
						method.getReturnType() + " - " + type.getName(),
						filter, offset, -1);
			}
		}

		// Add types in the project...
		for (TypeSymbol type : project.getClasses()) {
			addProposal(proposals, type, "", filter, offset, 0);
		}

		proposals.addAll(0, localVars); // Local vars at the beginning
		return proposals.toArray(new ICompletionProposal[proposals.size()]);
	}

	/**
	 * Add a proposal to the completion list, if it matches <var>filter</var>.
	 * If selected, the cursor will be positioned <var>endOffset</var>
	 * characters from the end of <var>value</var>.
	 * 
	 * @param proposals
	 * @param filter
	 * @param endOffset
	 */
	private void addProposal(List<ICompletionProposal> proposals,
			TypeSymbol typeSymbol, String description, String filter,
			int start, int endOffset) {
		String name = typeSymbol.getName();
		if (name.substring(0, filter.length()).toLowerCase().equals(filter)) {
			ValaPlugin valaPlugin = ValaPlugin.getDefault();
			Image image = ValaSymbolImageProvider.getImage(valaPlugin,
					typeSymbol);
			int replacementOffset = start - filter.length();
			int replacementLength = filter.length();
			int cursorPosition = name.length() + endOffset;
			String displayString = name + "    " + description;
			proposals.add(new CompletionProposal(name, replacementOffset,
					replacementLength, cursorPosition, image, displayString,
					null, ""));
		}
	}

	@Override
	public IContextInformation[] computeContextInformation(ITextViewer viewer,
			int offset) {
		return NO_CONTEXTS;
	}

	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		return ACTIVATION_CHARS;
	}

	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IContextInformationValidator getContextInformationValidator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
