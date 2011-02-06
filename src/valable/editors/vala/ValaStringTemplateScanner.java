package valable.editors.vala;

import java.util.ArrayList;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordPatternRule;
import org.eclipse.swt.SWT;

import valable.editors.util.ColorManager;
import valable.editors.util.ParentesizedRule;
import valable.editors.util.WhitespaceDetector;

public class ValaStringTemplateScanner extends RuleBasedScanner {

	public ValaStringTemplateScanner(ColorManager manager) {
		IToken def = new Token(new TextAttribute(manager.getColor(ColorManager.STRING)));
		IToken template = new Token(new TextAttribute(manager.getColor(ColorManager.STRING_TEMPLATE), null, SWT.BOLD));

		setDefaultReturnToken(def);
		
		ArrayList<IRule> rules = new ArrayList<IRule>();

		// Rule for whitespaces
		rules.add(new WhitespaceRule(new WhitespaceDetector()));
		
		rules.add(new ParentesizedRule("$", template));
		rules.add(new WordPatternRule(new ValaWordDetector(), "$", "", template));
		
		IRule[] r = new IRule[rules.size()];
		rules.toArray(r);
		setRules(r);
	}
}
