/* GTKDocCompletionProcessor.java
 *
 * Copyright (C) 2008  Johann Prieur <johann.prieur@gmail.com>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package valable.editors.doc;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

public class GTKDocCompletionProcessor implements IContentAssistProcessor, IGTKDocLanguageWords {

	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
			int offset) {
		int nb = section.length + function.length + 2 * docbook.length;
		ICompletionProposal[] proposals = new ICompletionProposal[nb];
		
		int i = 0;
		for(String s : section) {
			String proposal = String.format("%s", s);
			proposals[i++] = new CompletionProposal(proposal, offset, 0, proposal.length());
		}
		for(String f : function) {
			proposals[i++] = new CompletionProposal(f, offset, 0, f.length());
		}
		for(String d : docbook) {
			String proposal = String.format("%s>", d);
			proposals[i++] = new CompletionProposal(proposal, offset, 0, proposal.length());
			proposal = String.format("/%s>", d);
			proposals[i++] = new CompletionProposal(proposal, offset, 0, proposal.length());
		}
		
		return proposals;
	}

	@Override
	public IContextInformation[] computeContextInformation(ITextViewer viewer,
			int offset) {
		return null;
	}

	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		return new char[] { '@', '<' };
	}

	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		return null;
	}

	@Override
	public IContextInformationValidator getContextInformationValidator() {
		return null;
	}

	@Override
	public String getErrorMessage() {
		return null;
	}

}
