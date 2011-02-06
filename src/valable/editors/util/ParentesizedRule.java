package valable.editors.util;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;

public class ParentesizedRule extends SingleLineRule {

	public ParentesizedRule(String prefix, IToken token) {
		super(prefix+"(", ")", token);
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner, boolean resume) {
		if (resume) {
			if (endSequenceDetected(scanner))
				return fToken;
		} else {
			int c= scanner.read();
			if (c == fStartSequence[0]) {
				if (sequenceDetected(scanner, fStartSequence, false)) {
					if (endSequenceDetected(scanner))
						return fToken;

					return fToken;
				}
			}
		}

		scanner.unread();
		return Token.UNDEFINED;
	}
	

	@Override
	protected boolean endSequenceDetected(ICharacterScanner scanner) {
		int readCount= 1;
		int parentesis = 1;
		int c;
		while ((c= scanner.read()) != ICharacterScanner.EOF) {
			if (c == '(') {
				parentesis++;
			} else if (c == ')') {
				if (--parentesis == 0)
					return true;
			}
			readCount++;
		}

		for (; readCount > 0; readCount--)
			scanner.unread();

		return false;
	}
}
