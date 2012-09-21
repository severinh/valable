/**
 * Copyright (C) 2008  Andrew Flegg <andrew@bleb.org>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IPathVariableManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.jobs.ISchedulingRule;

/**
 * A simple implementation of {@link IFile}, backed by another {@link File}.
 */
public class LocalFile implements IFile {

	private final File file;
	private boolean hidden = false;

	/**
	 * Create a new local file from the given path.
	 */
	public LocalFile(File file) {
		super();
		this.file = file;
	}

	@Override
	public void appendContents(InputStream source, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void appendContents(InputStream source, boolean force,
			boolean keepHistory, IProgressMonitor monitor) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void create(InputStream source, boolean force,
			IProgressMonitor monitor) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void create(InputStream source, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void createLink(IPath localLocation, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void createLink(URI location, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void delete(boolean force, boolean keepHistory,
			IProgressMonitor monitor) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public String getCharset() throws CoreException {
		return "utf-8";
	}

	@Override
	public String getCharset(boolean checkImplicit) throws CoreException {
		return getCharset();
	}

	@Override
	public String getCharsetFor(Reader reader) throws CoreException {
		return getCharset();
	}

	@Override
	public IContentDescription getContentDescription() throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public InputStream getContents() throws CoreException {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new CoreException(Status.CANCEL_STATUS);
		}
	}

	@Override
	public InputStream getContents(boolean force) throws CoreException {
		return getContents();
	}

	@Deprecated
	@Override
	public int getEncoding() throws CoreException {
		return IFile.ENCODING_UTF_8;
	}

	@Override
	public IPath getFullPath() {
		return new Path(file.getAbsolutePath());
	}

	@Override
	public IFileState[] getHistory(IProgressMonitor monitor)
			throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public String getName() {
		return file.getName();
	}

	@Override
	public boolean isReadOnly() {
		return false;
	}

	@Override
	public void move(IPath destination, boolean force, boolean keepHistory,
			IProgressMonitor monitor) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void setCharset(String newCharset) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void setCharset(String newCharset, IProgressMonitor monitor)
			throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void setContents(InputStream source, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void setContents(IFileState source, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void setContents(InputStream source, boolean force,
			boolean keepHistory, IProgressMonitor monitor) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void setContents(IFileState source, boolean force,
			boolean keepHistory, IProgressMonitor monitor) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void accept(IResourceVisitor visitor) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void accept(IResourceProxyVisitor visitor, int memberFlags)
			throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void accept(IResourceVisitor visitor, int depth,
			boolean includePhantoms) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void accept(IResourceVisitor visitor, int depth, int memberFlags)
			throws CoreException {
		throw new IllegalStateException("not implemented");

	}

	@Override
	public void clearHistory(IProgressMonitor monitor) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void copy(IPath destination, boolean force, IProgressMonitor monitor)
			throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void copy(IPath destination, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void copy(IProjectDescription description, boolean force,
			IProgressMonitor monitor) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void copy(IProjectDescription description, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public IMarker createMarker(String type) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public IResourceProxy createProxy() {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void delete(boolean force, IProgressMonitor monitor)
			throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void delete(int updateFlags, IProgressMonitor monitor)
			throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void deleteMarkers(String type, boolean includeSubtypes, int depth)
			throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public boolean exists() {
		return true;
	}

	@Override
	public IMarker findMarker(long id) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public IMarker[] findMarkers(String type, boolean includeSubtypes, int depth)
			throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public String getFileExtension() {
		String[] parts = file.getName().split("\\.");
		return parts[parts.length - 1];
	}

	@Override
	public long getLocalTimeStamp() {
		return 0;
	}

	@Override
	public IPath getLocation() {
		return new Path(file.getAbsolutePath());
	}

	@Override
	public URI getLocationURI() {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public IMarker getMarker(long id) {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public long getModificationStamp() {
		return 0;
	}

	@Override
	public IContainer getParent() {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public String getPersistentProperty(QualifiedName key) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public IProject getProject() {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public IPath getProjectRelativePath() {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public IPath getRawLocation() {
		return new Path(file.getAbsolutePath());
	}

	@Override
	public URI getRawLocationURI() {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public ResourceAttributes getResourceAttributes() {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public Object getSessionProperty(QualifiedName key) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public int getType() {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public IWorkspace getWorkspace() {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public boolean isAccessible() {
		return true;
	}

	@Override
	public boolean isDerived() {
		return false;
	}

	@Override
	public boolean isLinked() {
		return false;
	}

	@Override
	public boolean isLinked(int options) {
		return false;
	}

	@Override
	public boolean isLocal(int depth) {
		return true;
	}

	@Override
	public boolean isPhantom() {
		return false;
	}

	@Override
	public boolean isSynchronized(int depth) {
		return false;
	}

	@Override
	public boolean isTeamPrivateMember() {
		return false;
	}

	@Override
	public void move(IPath destination, boolean force, IProgressMonitor monitor)
			throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void move(IPath destination, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void move(IProjectDescription description, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void move(IProjectDescription description, boolean force,
			boolean keepHistory, IProgressMonitor monitor) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void refreshLocal(int depth, IProgressMonitor monitor)
			throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void revertModificationStamp(long value) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void setDerived(boolean isDerived) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void setLocal(boolean flag, int depth, IProgressMonitor monitor)
			throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public long setLocalTimeStamp(long value) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void setPersistentProperty(QualifiedName key, String value)
			throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void setResourceAttributes(ResourceAttributes attributes)
			throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void setSessionProperty(QualifiedName key, Object value)
			throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void setTeamPrivateMember(boolean isTeamPrivate)
			throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void touch(IProgressMonitor monitor) throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public boolean contains(ISchedulingRule rule) {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public boolean isConflicting(ISchedulingRule rule) {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public int findMaxProblemSeverity(String arg0, boolean arg1, int arg2)
			throws CoreException {
		return 0;
	}

	@Override
	public Map<QualifiedName, String> getPersistentProperties()
			throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public Map<QualifiedName, Object> getSessionProperties()
			throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public boolean isDerived(int arg0) {
		return false;
	}

	@Override
	public boolean isHidden() {
		return hidden;
	}

	@Override
	public void setHidden(boolean hidden) throws CoreException {
		this.hidden = hidden;
	}

	@Override
	public IPathVariableManager getPathVariableManager() {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public boolean isHidden(int arg0) {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public boolean isTeamPrivateMember(int arg0) {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public boolean isVirtual() {
		return false;
	}

	@Override
	public void setDerived(boolean arg0, IProgressMonitor arg1)
			throws CoreException {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public void accept(IResourceProxyVisitor visitor, int depth, int memberFlags)
			throws CoreException {
		throw new IllegalStateException("not implemented");
	}

}
