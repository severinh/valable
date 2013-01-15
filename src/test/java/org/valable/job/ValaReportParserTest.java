package org.valable.job;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.gnome.vala.SourceLocation;
import org.junit.Test;
import org.valable.AbstractTest;
import org.valable.job.ValaReportElement.Severity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests that {@link ValaReportParser} works correctly.
 */
public class ValaReportParserTest extends AbstractTest {

	private static final String LINE_1;
	private static final String LINE_2;
	private static final ValaReportElement EXPECTED_ELEMENT_1;
	private static final ValaReportElement EXPECTED_ELEMENT_2;

	static {
		LINE_1 = "/home/me/Test.vala:7.2-7.19: warning: method `Test.test' never used";
		LINE_2 = "/home/me/Test.vala:11.2-11.2: error: syntax error, expected identifier";
		EXPECTED_ELEMENT_1 = new ValaReportElement("/home/me/Test.vala",
				"method `Test.test' never used", Severity.WARNING,
				new SourceLocation(7, 2), new SourceLocation(7, 19));
		EXPECTED_ELEMENT_2 = new ValaReportElement("/home/me/Test.vala",
				"syntax error, expected identifier", Severity.ERROR,
				new SourceLocation(11, 2), new SourceLocation(11, 2));
	}

	@Test
	public void testParseLine() {
		ValaReportParser parser = new ValaReportParser();

		ValaReportElement element_1 = parser.parseLine(LINE_1);
		assertReportElement(EXPECTED_ELEMENT_1, element_1);
		ValaReportElement element_2 = parser.parseLine(LINE_2);
		assertReportElement(EXPECTED_ELEMENT_2, element_2);

		assertNull(parser.parseLine("bla"));
	}

	@Test
	public void testParse() {
		List<ValaReportElement> expectedElements = Arrays.asList(
				EXPECTED_ELEMENT_1, EXPECTED_ELEMENT_2);
		String lines = LINE_1 + "\nbla\n" + LINE_2;
		InputStream errorStream = IOUtils.toInputStream(lines);
		ValaReportParser parser = new ValaReportParser();

		List<ValaReportElement> elements = parser.parse(errorStream);
		assertEquals(expectedElements.size(), elements.size());
		for (int i = 0; i < expectedElements.size(); i++) {
			assertReportElement(expectedElements.get(i), elements.get(i));
		}
	}

	public void assertReportElement(ValaReportElement expectedElement,
			ValaReportElement element) {
		assertEquals(expectedElement.getFilePath(), element.getFilePath());
		assertEquals(expectedElement.getMessage(), element.getMessage());
		assertEquals(expectedElement.getSeverity(), element.getSeverity());
		assertEquals(expectedElement.getStartLocation(),
				element.getStartLocation());
		assertEquals(expectedElement.getEndLocation(), element.getEndLocation());
	}

}
