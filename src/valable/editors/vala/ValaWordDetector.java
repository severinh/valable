package valable.editors.vala;

import org.eclipse.jface.text.rules.IWordDetector;

public class ValaWordDetector implements IWordDetector, IValaLanguageWords {

	@Override
	public boolean isWordPart(char c) {
		for (String kw : keywords)
			if (kw.indexOf(c) != -1)
				return true;

		return false;
	}

	@Override
	public boolean isWordStart(char c) {
		// In Vala you can use any keyword when using '@' as prefix.
		if (c == '@')
			return true;

		for (String kw : keywords)
			if (kw.charAt(0) == c)
				return true;

		return false;
	}
}