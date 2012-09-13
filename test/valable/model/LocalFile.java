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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IFile#appendContents(java.io.InputStream,
	 * int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void appendContents(InputStream source, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IFile#appendContents(java.io.InputStream,
	 * boolean, boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void appendContents(InputStream source, boolean force,
			boolean keepHistory, IProgressMonitor monitor) throws CoreException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IFile#create(java.io.InputStream,
	 * boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void create(InputStream source, boolean force,
			IProgressMonitor monitor) throws CoreException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IFile#create(java.io.InputStream, int,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void create(InputStream source, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IFile#createLink(org.eclipse.core.runtime.
	 * IPath, int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void createLink(IPath localLocation, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IFile#createLink(java.net.URI, int,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void createLink(URI location, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IFile#delete(boolean, boolean,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void delete(boolean force, boolean keepHistory,
			IProgressMonitor monitor) throws CoreException {
	}

	/**
	 * @see org.eclipse.core.resources.IFile#getCharset()
	 */
	@Override
	public String getCharset() throws CoreException {
		return "utf-8";
	}

	/**
	 * @see org.eclipse.core.resources.IFile#getCharset(boolean)
	 */
	@Override
	public String getCharset(boolean checkImplicit) throws CoreException {
		return getCharset();
	}

	/**
	 * @see org.eclipse.core.resources.IFile#getCharsetFor(java.io.Reader)
	 */
	@Override
	public String getCharsetFor(Reader reader) throws CoreException {
		return getCharset();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IFile#getContentDescription()
	 */
	@Override
	public IContentDescription getContentDescription() throws CoreException {
		return null;
	}

	/**
	 * @see org.eclipse.core.resources.IFile#getContents()
	 */
	@Override
	public InputStream getContents() throws CoreException {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new CoreException(Status.CANCEL_STATUS);
		}
	}

	/**
	 * @see org.eclipse.core.resources.IFile#getContents(boolean)
	 */
	@Override
	public InputStream getContents(boolean force) throws CoreException {
		return getContents();
	}

	/**
	 * @see org.eclipse.core.resources.IFile#getEncoding()
	 * @deprecated
	 */
	@Deprecated
	@Override
	public int getEncoding() throws CoreException {
		return IFile.ENCODING_UTF_8;
	}

	/**
	 * @see org.eclipse.core.resources.IFile#getFullPath()
	 */
	@Override
	public IPath getFullPath() {
		return new Path(file.getAbsolutePath());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IFile#getHistory(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */
	@Override
	public IFileState[] getHistory(IProgressMonitor monitor)
			throws CoreException {
		return null;
	}

	/**
	 * @see org.eclipse.core.resources.IFile#getName()
	 */
	@Override
	public String getName() {
		return file.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IFile#isReadOnly()
	 */
	@Override
	public boolean isReadOnly() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IFile#move(org.eclipse.core.runtime.IPath,
	 * boolean, boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void move(IPath destination, boolean force, boolean keepHistory,
			IProgressMonitor monitor) throws CoreException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IFile#setCharset(java.lang.String)
	 */
	@Override
	public void setCharset(String newCharset) throws CoreException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IFile#setCharset(java.lang.String,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void setCharset(String newCharset, IProgressMonitor monitor)
			throws CoreException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IFile#setContents(java.io.InputStream,
	 * int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void setContents(InputStream source, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IFile#setContents(org.eclipse.core.resources
	 * .IFileState, int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void setContents(IFileState source, int updateFlags,
			IProgressMonitor monitor) throws CoreException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IFile#setContents(java.io.InputStream,
	 * boolean, boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void setContents(InputStream source, boolean force,
			boolean keepHistory, IProgressMonitor monitor) throws CoreException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IFile#setContents(org.eclipse.core.resources
	 * .IFileState, boolean, boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void setContents(IFileState source, boolean force,
			boolean keepHistory, IProgressMonitor monitor) throws CoreException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IResource#accept(org.eclipse.core.resources
	 * .IResourceVisitor)
	 */
	@Override
	public void accept(IResourceVisitor visitor) throws CoreException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IResource#accept(org.eclipse.core.resources
	 * .IResourceProxyVisitor, int)
	 */
	@Override
	public void accept(IResourceProxyVisitor visitor, int memberFlags)
			throws CoreException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IResource#accept(org.eclipse.core.resources
	 * .IResourceVisitor, int, boolean)
	 */
	@Override
	public void accept(IResourceVisitor visitor, int depth,
			boolean includePhantoms) throws CoreException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IResource#accept(org.eclipse.core.resources
	 * .IResourceVisitor, int, int)
	 */
	@Override
	public void accept(IResourceVisitor visitor, int depth, int memberFlags)
			throws CoreException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IResource#clearHistory(org.eclipse.core.runtime
	 * .IProgressMonitor)
	 */
	@Override
	public void clearHistory(IProgressMonitor monitor) throws CoreException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IResource#copy(org.eclipse.core.runtime.IPath,
	 * boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void copy(IPath destination, boolean force, IProgressMonitor monitor)
			throws CoreException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IResource#copy(org.eclipse.core.runtime.IPath,
	 * int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void copy(IPath destination, int updateFlags,
			IProgressMonitor monitor) throws CoreException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IResource#copy(org.eclipse.core.resources.
	 * IProjectDescription, boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void copy(IProjectDescription description, boolean force,
			IProgressMonitor monitor) throws CoreException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IResource#copy(org.eclipse.core.resources.
	 * IProjectDescription, int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void copy(IProjectDescription description, int updateFlags,
			IProgressMonitor monitor) throws CoreException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#createMarker(java.lang.String)
	 */
	@Override
	public IMarker createMarker(String type) throws CoreException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#createProxy()
	 */
	@Override
	public IResourceProxy createProxy() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#delete(boolean,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void delete(boolean force, IProgressMonitor monitor)
			throws CoreException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#delete(int,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void delete(int updateFlags, IProgressMonitor monitor)
			throws CoreException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#deleteMarkers(java.lang.String,
	 * boolean, int)
	 */
	@Override
	public void deleteMarkers(String type, boolean includeSubtypes, int depth)
			throws CoreException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#exists()
	 */
	@Override
	public boolean exists() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#findMarker(long)
	 */
	@Override
	public IMarker findMarker(long id) throws CoreException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#findMarkers(java.lang.String,
	 * boolean, int)
	 */
	@Override
	public IMarker[] findMarkers(String type, boolean includeSubtypes, int depth)
			throws CoreException {
		return null;
	}

	/**
	 * @see org.eclipse.core.resources.IResource#getFileExtension()
	 */
	@Override
	public String getFileExtension() {
		String[] parts = file.getName().split("\\.");
		return parts[parts.length - 1];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#getLocalTimeStamp()
	 */
	@Override
	public long getLocalTimeStamp() {
		return 0;
	}

	/**
	 * @see org.eclipse.core.resources.IResource#getLocation()
	 */
	@Override
	public IPath getLocation() {
		return new Path(file.getAbsolutePath());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#getLocationURI()
	 */
	@Override
	public URI getLocationURI() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#getMarker(long)
	 */
	@Override
	public IMarker getMarker(long id) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#getModificationStamp()
	 */
	@Override
	public long getModificationStamp() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#getParent()
	 */
	@Override
	public IContainer getParent() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IResource#getPersistentProperty(org.eclipse
	 * .core.runtime.QualifiedName)
	 */
	@Override
	public String getPersistentProperty(QualifiedName key) throws CoreException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#getProject()
	 */
	@Override
	public IProject getProject() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#getProjectRelativePath()
	 */
	@Override
	public IPath getProjectRelativePath() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#getRawLocation()
	 */
	@Override
	public IPath getRawLocation() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#getRawLocationURI()
	 */
	@Override
	public URI getRawLocationURI() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#getResourceAttributes()
	 */
	@Override
	public ResourceAttributes getResourceAttributes() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IResource#getSessionProperty(org.eclipse.core
	 * .runtime.QualifiedName)
	 */
	@Override
	public Object getSessionProperty(QualifiedName key) throws CoreException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#getType()
	 */
	@Override
	public int getType() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#getWorkspace()
	 */
	@Override
	public IWorkspace getWorkspace() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#isAccessible()
	 */
	@Override
	public boolean isAccessible() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#isDerived()
	 */
	@Override
	public boolean isDerived() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#isLinked()
	 */
	@Override
	public boolean isLinked() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#isLinked(int)
	 */
	@Override
	public boolean isLinked(int options) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#isLocal(int)
	 */
	@Override
	public boolean isLocal(int depth) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#isPhantom()
	 */
	@Override
	public boolean isPhantom() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#isSynchronized(int)
	 */
	@Override
	public boolean isSynchronized(int depth) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#isTeamPrivateMember()
	 */
	@Override
	public boolean isTeamPrivateMember() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IResource#move(org.eclipse.core.runtime.IPath,
	 * boolean, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void move(IPath destination, boolean force, IProgressMonitor monitor)
			throws CoreException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IResource#move(org.eclipse.core.runtime.IPath,
	 * int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void move(IPath destination, int updateFlags,
			IProgressMonitor monitor) throws CoreException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IResource#move(org.eclipse.core.resources.
	 * IProjectDescription, int, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void move(IProjectDescription description, int updateFlags,
			IProgressMonitor monitor) throws CoreException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IResource#move(org.eclipse.core.resources.
	 * IProjectDescription, boolean, boolean,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void move(IProjectDescription description, boolean force,
			boolean keepHistory, IProgressMonitor monitor) throws CoreException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#refreshLocal(int,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void refreshLocal(int depth, IProgressMonitor monitor)
			throws CoreException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#revertModificationStamp(long)
	 */
	@Override
	public void revertModificationStamp(long value) throws CoreException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#setDerived(boolean)
	 */
	@Override
	public void setDerived(boolean isDerived) throws CoreException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#setLocal(boolean, int,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void setLocal(boolean flag, int depth, IProgressMonitor monitor)
			throws CoreException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#setLocalTimeStamp(long)
	 */
	@Override
	public long setLocalTimeStamp(long value) throws CoreException {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IResource#setPersistentProperty(org.eclipse
	 * .core.runtime.QualifiedName, java.lang.String)
	 */
	@Override
	public void setPersistentProperty(QualifiedName key, String value)
			throws CoreException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#setReadOnly(boolean)
	 */
	@Override
	public void setReadOnly(boolean readOnly) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IResource#setResourceAttributes(org.eclipse
	 * .core.resources.ResourceAttributes)
	 */
	@Override
	public void setResourceAttributes(ResourceAttributes attributes)
			throws CoreException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IResource#setSessionProperty(org.eclipse.core
	 * .runtime.QualifiedName, java.lang.Object)
	 */
	@Override
	public void setSessionProperty(QualifiedName key, Object value)
			throws CoreException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#setTeamPrivateMember(boolean)
	 */
	@Override
	public void setTeamPrivateMember(boolean isTeamPrivate)
			throws CoreException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#touch(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */
	@Override
	public void touch(IProgressMonitor monitor) throws CoreException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.jobs.ISchedulingRule#contains(org.eclipse.core
	 * .runtime.jobs.ISchedulingRule)
	 */
	@Override
	public boolean contains(ISchedulingRule rule) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.jobs.ISchedulingRule#isConflicting(org.eclipse
	 * .core.runtime.jobs.ISchedulingRule)
	 */
	@Override
	public boolean isConflicting(ISchedulingRule rule) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IResource#findMaxProblemSeverity(java.lang
	 * .String, boolean, int)
	 */
	@Override
	public int findMaxProblemSeverity(String arg0, boolean arg1, int arg2)
			throws CoreException {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#getPersistentProperties()
	 */
	@Override
	public Map<QualifiedName, String> getPersistentProperties()
			throws CoreException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#getSessionProperties()
	 */
	@Override
	public Map<QualifiedName, Object> getSessionProperties()
			throws CoreException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#isDerived(int)
	 */
	@Override
	public boolean isDerived(int arg0) {
		return false;
	}

	/**
	 * @see org.eclipse.core.resources.IResource#isHidden()
	 */
	@Override
	public boolean isHidden() {
		return hidden;
	}

	/**
	 * @see org.eclipse.core.resources.IResource#setHidden(boolean)
	 */
	@Override
	public void setHidden(boolean hidden) throws CoreException {
		this.hidden = hidden;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#getPathVariableManager()
	 */
	@Override
	public IPathVariableManager getPathVariableManager() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#isHidden(int)
	 */
	@Override
	public boolean isHidden(int arg0) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#isTeamPrivateMember(int)
	 */
	@Override
	public boolean isTeamPrivateMember(int arg0) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#isVirtual()
	 */
	@Override
	public boolean isVirtual() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IResource#setDerived(boolean,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void setDerived(boolean arg0, IProgressMonitor arg1)
			throws CoreException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IResource#accept(org.eclipse.core.resources
	 * .IResourceProxyVisitor, int, int)
	 */
	@Override
	public void accept(IResourceProxyVisitor visitor, int depth, int memberFlags)
			throws CoreException {

	}

}
