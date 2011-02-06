package valable.editors.vala;

import org.eclipse.jface.text.rules.IWordDetector;

public class ValaWordDetector implements IWordDetector, IValaLanguageWords {

	@Override
	public boolean isWordPart(char c) {
		return Character.isJavaIdentifierPart(c);
	}

	@Override
	public boolean isWordStart(char c) {
		// In Vala you can use any keyword when using '@' as prefix.
		return Character.isJavaIdentifierStart(c) || c == '@' || c == '#';
	}
}