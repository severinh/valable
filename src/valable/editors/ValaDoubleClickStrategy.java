package valable.editors;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextViewer;

import valable.editors.vala.ValaWordDetector;

public class ValaDoubleClickStrategy implements ITextDoubleClickStrategy {
	protected ITextViewer fText;

	public void doubleClicked(ITextViewer part) {
		int pos = part.getSelectedRange().x;

		if (pos < 0)
			return;
		fText = part;
			selectWord(pos);
	}

	protected boolean selectWord(int caretPos) {

		IDocument doc = fText.getDocument();
		int startPos = 0;
		int endPos = 0;
		ValaWordDetector detector = new ValaWordDetector();

		try {

			int pos = caretPos;
			char c;

			while (pos >= 0) {
				c = doc.getChar(pos);
				if (!Character.isJavaIdentifierPart(c) && !detector.isWordPart(c))
					break;
				--pos;
			}

			if (doc.getChar(pos) == '@' && !Character.isJavaIdentifierPart(doc.getChar(pos-1)))
				pos--;

			startPos = pos;

			pos = caretPos;
			int length = doc.getLength();

			while (pos < length) {
				c = doc.getChar(pos);
				if (!Character.isJavaIdentifierPart(c) && !detector.isWordPart(c))
					break;
				++pos;
			}

			endPos = pos;
			selectRange(startPos, endPos);
			return true;

		} catch (BadLocationException x) {
		}

		return false;
	}

	private void selectRange(int startPos, int stopPos) {
		int offset = startPos + 1;
		int length = stopPos - offset;
		fText.setSelectedRange(offset, length);
	}
}