package org.valable.job;

import org.eclipse.core.resources.IMarker;
import org.gnome.vala.SourceLocation;

/**
 * Represents a compilation warning or error from the Vala compiler.
 */
public class ValaReportElement {

	/**
	 * Model representing compilation failures and warnings.
	 */
	public enum Severity {
		ERROR(IMarker.SEVERITY_ERROR), WARNING(IMarker.SEVERITY_WARNING);

		private final int iMarkerSeverity;

		/**
		 * Construct a new message type.
		 * 
		 * @param severity
		 *            Severity of the problem for the {@link IMarker}.
		 */
		private Severity(int severity) {
			this.iMarkerSeverity = severity;
		}

		public int getiMarkerSeverity() {
			return iMarkerSeverity;
		}

	}

	private final String filePath;
	private final String message;
	private final Severity severity;
	private final SourceLocation startLocation;
	private final SourceLocation endLocation;

	public ValaReportElement(String filePath, String message,
			Severity severity, SourceLocation startLocation,
			SourceLocation endLocation) {
		super();
		if (filePath == null) {
			throw new IllegalArgumentException("file path must not be null");
		}
		if (message == null) {
			throw new IllegalArgumentException("message must not be null");
		}
		if (severity == null) {
			throw new IllegalArgumentException("severity must not be null");
		}
		if (startLocation == null) {
			throw new IllegalArgumentException(
					"start location must not be null");
		}
		if (endLocation == null) {
			throw new IllegalArgumentException("end location must not be null");
		}
		this.filePath = filePath;
		this.message = message;
		this.severity = severity;
		this.startLocation = startLocation;
		this.endLocation = endLocation;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getMessage() {
		return message;
	}

	public Severity getSeverity() {
		return severity;
	}

	public SourceLocation getStartLocation() {
		return startLocation;
	}

	public SourceLocation getEndLocation() {
		return endLocation;
	}

}
