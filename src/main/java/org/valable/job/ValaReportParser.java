/**
 * Copyright (C) 2008  Johann Prieur <johann.prieur@gmail.com>
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.valable.job;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gnome.vala.SourceLocation;
import org.valable.job.ValaReportElement.Severity;

public class ValaReportParser {

	private static final Pattern REPORT_LINE_PATTERN = Pattern
			.compile("(.*?):(\\d+).(\\d+)-(\\d+).(\\d+): (error|warning): (.*)");

	public List<ValaReportElement> parse(InputStream errorStream) {
		List<ValaReportElement> result = new ArrayList<ValaReportElement>();
		Scanner scanner = new Scanner(errorStream);
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			ValaReportElement reportElement = parseLine(line);
			if (reportElement != null) {
				result.add(reportElement);
			}
		}
		return result;
	}

	public ValaReportElement parseLine(String line) {
		Matcher lineMatcher = REPORT_LINE_PATTERN.matcher(line);
		if (!lineMatcher.matches()) {
			return null;
		}

		String filePath = lineMatcher.group(1);
		int startLine = Integer.parseInt(lineMatcher.group(2));
		int startColumn = Integer.parseInt(lineMatcher.group(3));
		int endLine = Integer.parseInt(lineMatcher.group(4));
		int endColumn = Integer.parseInt(lineMatcher.group(5));
		Severity severity = Severity
				.valueOf(lineMatcher.group(6).toUpperCase());
		String message = lineMatcher.group(7);

		SourceLocation startLocation = new SourceLocation(startLine,
				startColumn);
		SourceLocation endLocation = new SourceLocation(endLine, endColumn);
		ValaReportElement element = new ValaReportElement(filePath, message,
				severity, startLocation, endLocation);
		return element;
	}

}
