/**
 * Copyright (C) 2007-2008  Johann Prieur <johann.prieur@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.editors;

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

import valable.editors.gtkdoc.GTKDocScanner;
import valable.editors.util.ColorManager;
import valable.editors.util.IColorConstants;
import valable.editors.vala.ValaAutoIndentStrategy;
import valable.editors.vala.ValaCodeScanner;
import valable.editors.vala.ValaCompletionProcessor;
import valable.editors.vala.ValaPartitionScanner;
import valable.editors.vala.ValaStringTemplateScanner;

public class ValaConfiguration extends SourceViewerConfiguration {

	private final ColorManager colorManager;

	private ValaCodeScanner codeScanner;
	private GTKDocScanner docScanner;
	private ValaStringTemplateScanner templateScanner;
	private ValaDoubleClickStrategy doubleClickStrategy;

	public ValaConfiguration(ColorManager colorManager) {
		this.colorManager = colorManager;
	}

	@Override
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] { IDocument.DEFAULT_CONTENT_TYPE,
				ValaPartitionScanner.VALA_MULTILINE_COMMENT,
				ValaPartitionScanner.VALA_MULTILINE_STRING,
				ValaPartitionScanner.VALA_VERBATIM_STRING,
				ValaPartitionScanner.VALA_STRING_TEMPLATES };
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

	// TODO: @"string" scanner
	@Override
	public IPresentationReconciler getPresentationReconciler(
			ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		// Rule for code
		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(getValaScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		// Rule for gtk-doc
		dr = new DefaultDamagerRepairer(getGTKDocScanner());
		reconciler.setDamager(dr, ValaPartitionScanner.GTKDOC_COMMENT);
		reconciler.setRepairer(dr, ValaPartitionScanner.GTKDOC_COMMENT);

		// Rule for multi line comments
		RuleBasedScanner multilineScanner = new RuleBasedScanner();
		multilineScanner.setDefaultReturnToken(new Token(new TextAttribute(
				colorManager.getColor(IColorConstants.COMMENT_COLOR))));
		dr = new DefaultDamagerRepairer(multilineScanner);
		reconciler.setDamager(dr, ValaPartitionScanner.VALA_MULTILINE_COMMENT);
		reconciler.setRepairer(dr, ValaPartitionScanner.VALA_MULTILINE_COMMENT);

		// Rule for multiline strings
		RuleBasedScanner stringScanner = new RuleBasedScanner();
		stringScanner.setDefaultReturnToken(new Token(new TextAttribute(
				colorManager.getColor(IColorConstants.STRING_COLOR))));
		dr = new DefaultDamagerRepairer(stringScanner);
		reconciler.setDamager(dr, ValaPartitionScanner.VALA_MULTILINE_STRING);
		reconciler.setRepairer(dr, ValaPartitionScanner.VALA_MULTILINE_STRING);

		// Rule for verbatim strings
		RuleBasedScanner verbatimScanner = new RuleBasedScanner();
		verbatimScanner.setDefaultReturnToken(new Token(new TextAttribute(
				colorManager.getColor(IColorConstants.STRING_COLOR))));
		dr = new DefaultDamagerRepairer(verbatimScanner);
		reconciler.setDamager(dr, ValaPartitionScanner.VALA_VERBATIM_STRING);
		reconciler.setRepairer(dr, ValaPartitionScanner.VALA_VERBATIM_STRING);

		// Rule for string templates
		dr = new DefaultDamagerRepairer(getStringTemplateScanner());
		reconciler.setDamager(dr, ValaPartitionScanner.VALA_STRING_TEMPLATES);
		reconciler.setRepairer(dr, ValaPartitionScanner.VALA_STRING_TEMPLATES);

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

}