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

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

import valable.ValaPlugin;
import valable.model.ValaField;
import valable.model.ValaMethod;
import valable.model.ValaProject;
import valable.model.ValaSource;
import valable.model.ValaType;

/**
 * Handle code complete of possible types.
 * 
 * <p>This is currently very basic and has little to no contextual information.</p>
 */
public class ValaCompletionProcessor implements IContentAssistProcessor {
	private static final IContextInformation[] NO_CONTEXTS      = new IContextInformation[0];
    private static final char[]                ACTIVATION_CHARS = new char[] { '.' };


	/**
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#computeCompletionProposals(org.eclipse.jface.text.ITextViewer, int)
	 */
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		IFile currentFile   = ValaPlugin.getCurrentFile();
		ValaProject project = ValaProject.getProject(currentFile);
		ValaSource  source  = project.getSource(currentFile);
		
		List<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
		
		// -- Add fields and methods in the current source file...
		//
		for (ValaType type : source.getTypes().values()) {
			for (ValaField field : type.getFields()) {
				proposals.add(new CompletionProposal(field.getName(), offset, 0, field.getName().length()));
			}
			for (ValaMethod method : type.getMethods()) {
				// TODO Signature
				proposals.add(new CompletionProposal(method.getName() + "()", offset, 0, method.getName().length() + 1));
			}
		}
		
		// -- Add types in the project...
		//
		for (ValaType type : project.getTypes()) {
			proposals.add(new CompletionProposal(type.getName(), offset, 0, type.getName().length()));
		}
		
		return proposals.toArray(new ICompletionProposal[proposals.size()]);
	}

	/**
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#computeContextInformation(org.eclipse.jface.text.ITextViewer, int)
	 */
	public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {
		return NO_CONTEXTS;
	}

	
	/**
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getCompletionProposalAutoActivationCharacters()
	 */
	public char[] getCompletionProposalAutoActivationCharacters() {
		return ACTIVATION_CHARS;
	}
	

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getContextInformationAutoActivationCharacters()
	 */
	public char[] getContextInformationAutoActivationCharacters() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getContextInformationValidator()
	 */
	public IContextInformationValidator getContextInformationValidator() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getErrorMessage()
	 */
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}
}
