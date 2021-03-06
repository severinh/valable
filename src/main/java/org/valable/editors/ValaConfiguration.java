/**
 * Copyright (C) 2007-2008  Johann Prieur <johann.prieur@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.valable.editors;

import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

import org.valable.editors.gtkdoc.GTKDocScanner;
import org.valable.editors.util.ColorManager;
import org.valable.editors.util.IValaColorConstants;
import org.valable.editors.vala.IValaPartitions;
import org.valable.editors.vala.ValaAutoIndentStrategy;
import org.valable.editors.vala.ValaCodeScanner;
import org.valable.editors.vala.ValaCompletionProcessor;
import org.valable.editors.vala.ValaStringTemplateScanner;

public class ValaConfiguration extends SourceViewerConfiguration {

	private final ColorManager colorManager;
	
	/**
	 * The document partitioning.
	 */
	private final String documentPartitioning;
	
	private ValaCodeScanner codeScanner;
	private GTKDocScanner docScanner;
	private ValaStringTemplateScanner templateScanner;
	private ValaDoubleClickStrategy doubleClickStrategy;

	public ValaConfiguration(ColorManager colorManager, String documentPartitioning) {
		this.colorManager = colorManager;
		this.documentPartitioning = documentPartitioning;
	}

	@Override
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] {
				IDocument.DEFAULT_CONTENT_TYPE,
				IValaPartitions.GTKDOC_COMMENT,
				IValaPartitions.VALA_MULTILINE_COMMENT,
				IValaPartitions.VALA_MULTILINE_STRING,
				IValaPartitions.VALA_VERBATIM_STRING,
				IValaPartitions.VALA_STRING_TEMPLATES,
				IValaPartitions.VALA_CHARACTER};
	}

	protected ValaCodeScanner getValaScanner() {
		if (codeScanner == null) {
			codeScanner = new ValaCodeScanner(colorManager);
		}
		return codeScanner;
	}

	protected GTKDocScanner getGTKDocScanner() {
		if (docScanner == null) {
			docScanner = new GTKDocScanner(colorManager);
		}
		return docScanner;
	}

	protected ValaStringTemplateScanner getStringTemplateScanner() {
		if (templateScanner == null) {
			templateScanner = new ValaStringTemplateScanner(colorManager);
		}
		return templateScanner;
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(
			ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();
		reconciler.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));

		// Rule for code
		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(getValaScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		// Rule for gtk-doc
		dr = new DefaultDamagerRepairer(getGTKDocScanner());
		reconciler.setDamager(dr, IValaPartitions.GTKDOC_COMMENT);
		reconciler.setRepairer(dr, IValaPartitions.GTKDOC_COMMENT);

		// Rule for multi line comments
		RuleBasedScanner multilineScanner = new RuleBasedScanner();
		multilineScanner.setDefaultReturnToken(new Token(new TextAttribute(
				colorManager.getColor(IValaColorConstants.VALA_COMMENT_COLOR))));
		dr = new DefaultDamagerRepairer(multilineScanner);
		reconciler.setDamager(dr, IValaPartitions.VALA_MULTILINE_COMMENT);
		reconciler.setRepairer(dr, IValaPartitions.VALA_MULTILINE_COMMENT);

		// Rule for multiline strings
		RuleBasedScanner stringScanner = new RuleBasedScanner();
		stringScanner.setDefaultReturnToken(new Token(new TextAttribute(
				colorManager.getColor(IValaColorConstants.VALA_STRING_COLOR))));
		dr = new DefaultDamagerRepairer(stringScanner);
		reconciler.setDamager(dr, IValaPartitions.VALA_MULTILINE_STRING);
		reconciler.setRepairer(dr, IValaPartitions.VALA_MULTILINE_STRING);

		// Rule for verbatim strings
		dr = new DefaultDamagerRepairer(stringScanner);
		reconciler.setDamager(dr, IValaPartitions.VALA_VERBATIM_STRING);
		reconciler.setRepairer(dr, IValaPartitions.VALA_VERBATIM_STRING);

		// Rule for string templates
		dr = new DefaultDamagerRepairer(getStringTemplateScanner());
		reconciler.setDamager(dr, IValaPartitions.VALA_STRING_TEMPLATES);
		reconciler.setRepairer(dr, IValaPartitions.VALA_STRING_TEMPLATES);

		// Rule for character constants
		dr = new DefaultDamagerRepairer(stringScanner);
		reconciler.setDamager(dr, IValaPartitions.VALA_CHARACTER);
		reconciler.setRepairer(dr, IValaPartitions.VALA_CHARACTER);
		
		return reconciler;
	}

	@Override
	public IAutoEditStrategy[] getAutoEditStrategies(
			ISourceViewer sourceViewer, String contentType) {
		IAutoEditStrategy[] indent = { new ValaAutoIndentStrategy() };
		return indent;
	}

	@Override
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		ContentAssistant ca = new ContentAssistant();
		IContentAssistProcessor cap = new ValaCompletionProcessor();
		ca.setContentAssistProcessor(cap, IDocument.DEFAULT_CONTENT_TYPE);
		ca.setInformationControlCreator(getInformationControlCreator(sourceViewer));
		return ca;
	}

	@Override
	public ITextDoubleClickStrategy getDoubleClickStrategy(
			ISourceViewer sourceViewer, String contentType) {
		if (doubleClickStrategy == null) {
			doubleClickStrategy = new ValaDoubleClickStrategy();
		}
		return doubleClickStrategy;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getConfiguredDocumentPartitioning(org.eclipse.jface.text.source.ISourceViewer)
	 */
	@Override
	public String getConfiguredDocumentPartitioning(ISourceViewer sourceViewer) {
		if (documentPartitioning != null) {
			return documentPartitioning;
		}
		return super.getConfiguredDocumentPartitioning(sourceViewer);
	}

}