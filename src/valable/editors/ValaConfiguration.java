/* ValaConfiguration.java
 *
 * Copyright (C) 2007  Johann Prieur <johann.prieur@gmail.com>
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
package valable.editors;

import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

import valable.editors.doc.GTKDocScanner;
import valable.editors.util.IColorConstants;
import valable.editors.util.ColorManager;
import valable.editors.vala.ValaAutoIndentStrategy;
import valable.editors.vala.ValaCodeScanner;
import valable.editors.vala.ValaPartitionScanner;

public class ValaConfiguration extends SourceViewerConfiguration {
	
	private ValaCodeScanner codeScanner;
	private GTKDocScanner docScanner;
	private ColorManager colorManager;

	public ValaConfiguration(ColorManager colorManager) {
		this.colorManager = colorManager;
	}
	
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] {
			IDocument.DEFAULT_CONTENT_TYPE,
			ValaPartitionScanner.VALA_MULTILINE_COMMENT };
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

	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
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
					colorManager.getColor(IColorConstants.COMMENT))));
		dr = new DefaultDamagerRepairer(multilineScanner);
		reconciler.setDamager(dr, ValaPartitionScanner.VALA_MULTILINE_COMMENT);
		reconciler.setRepairer(dr, ValaPartitionScanner.VALA_MULTILINE_COMMENT);
		
		return reconciler;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getAutoEditStrategies(org.eclipse.jface.text.source.ISourceViewer, java.lang.String)
	 */
	@Override
	public IAutoEditStrategy[] getAutoEditStrategies(
			ISourceViewer sourceViewer, String contentType) {
		IAutoEditStrategy[] indent = { new ValaAutoIndentStrategy() };
		return indent;
	}

	
	
}