/* ValaCompletionProcessor.java
 *
 * Copyright (C) 2008  Andrew Flegg <andrew@bleb.org>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package valable.editors.vala;

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

import valable.ValaPlugin;
import valable.ValaPlugin.ImageType;
import valable.model.ValaEntity;
import valable.model.ValaField;
import valable.model.ValaMethod;
import valable.model.ValaProject;
import valable.model.ValaSource;
import valable.model.ValaSymbolAccessibility;
import valable.model.ValaType;

/**
 * Handle code complete of possible types.
 * 
 * <p>This is currently very basic and has little to no contextual information.</p>
 */
public class ValaCompletionProcessor implements IContentAssistProcessor {
	private static final IContextInformation[] NO_CONTEXTS      = new IContextInformation[0];
    private static final char[]                ACTIVATION_CHARS = new char[] { '.' };
    
    public static final Pattern               LAST_IDENTIFIER  = Pattern.compile("(?s).*?(" + ValaEntity.IDENTIFIER.pattern() + ")$");


	/**
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#computeCompletionProposals(org.eclipse.jface.text.ITextViewer, int)
	 */
	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		IFile currentFile   = ValaPlugin.getCurrentFile();
		ValaProject project = ValaProject.getProject(currentFile);
		ValaSource  source  = project.getSource(currentFile);
		
		List<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
		
		String  text          = viewer.getDocument().get().substring(0, offset);
		Matcher lastIdMatcher = LAST_IDENTIFIER.matcher(text);
		String  filter        = ""; // Only include values which match this
		if (lastIdMatcher.matches())
			filter = lastIdMatcher.group(1);
		
		// -- Add fields and methods in the current source file...
		//
		List<ICompletionProposal> localVars = new ArrayList<ICompletionProposal>();
		for (ValaType type : source.getTypes().values()) {
			for (ValaField field : type.getFields()) {
				addProposal(proposals, ImageType.FIELD, field.getAccessibility(), field.getName(), field.getType() + " - " + type.getName(), filter, offset, 0);
			}
			for (ValaMethod method : type.getMethods()) {
				// Add all local variables from all methods at head of list
				for (ValaField var : method.getLocalVariables())
					addProposal(localVars, ImageType.VARIABLE,  ValaSymbolAccessibility.INTERNAL, var.getName(), var.getType(), filter, offset, 0);
				
				// TODO Signature
				addProposal(proposals, ImageType.METHOD, method.getAccessibility(), method.getName() + "()", method.getType() + " - " + type.getName(), filter, offset, -1);
			}
		}
		
		// -- Add types in the project...
		//
		for (ValaType type : project.getTypes()) {
			addProposal(proposals, ImageType.CLASS, ValaSymbolAccessibility.INTERNAL, type.getName(), "", filter, offset, 0);
		}
		
		proposals.addAll(0, localVars); // Local vars at the beginning
		return proposals.toArray(new ICompletionProposal[proposals.size()]);
	}
	
	
	/**
	 * Add a proposal to the completion list, if it matches <var>filter</var>.
	 * If selected, the cursor will be positioned <var>endOffset</var> characters
	 * from the end of <var>value</var>.
	 * 
	 * @param proposals
	 * @param value
	 * @param filter
	 * @param endOffset
	 */
	private void addProposal(List<ICompletionProposal> proposals, ImageType type, ValaSymbolAccessibility accessibility, String value, String description, String filter, int start, int endOffset) {
		if (value.substring(0, filter.length()).toLowerCase().equals(filter)) {
			Image image = ValaPlugin.getDefault()
					.findImage(type, accessibility);
			int replacementOffset = start - filter.length();
			int replacementLength = filter.length();
			int cursorPosition = value.length() + endOffset;
			String displayString = value + "    " + description;
			proposals.add(new CompletionProposal(value, replacementOffset,
					replacementLength, cursorPosition, image, displayString,
					null, ""));
		}
	}
	
	

	/**
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#computeContextInformation(org.eclipse.jface.text.ITextViewer, int)
	 */
	@Override
	public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {
		return NO_CONTEXTS;
	}

	
	/**
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getCompletionProposalAutoActivationCharacters()
	 */
	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		return ACTIVATION_CHARS;
	}
	

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getContextInformationAutoActivationCharacters()
	 */
	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getContextInformationValidator()
	 */
	@Override
	public IContextInformationValidator getContextInformationValidator() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getErrorMessage()
	 */
	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}
}
