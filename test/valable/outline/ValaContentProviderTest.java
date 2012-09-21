/**
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.outline;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.TreeNode;
import org.gnome.vala.Class;
import org.junit.Assert;
import org.junit.Test;

import valable.AbstractTest;
import valable.model.LocalFile;
import valable.model.ValaProject;
import valable.model.ValaSource;

/**
 * Tests {@link ValaContentProvider}.
 */
public class ValaContentProviderTest extends AbstractTest {

	@Test
	public void testParse() throws CoreException, IOException {
		IFile file = new LocalFile(new File("test/simple.vala"));
		ValaSource source = new ValaSource(new ValaProject("Test"), file);
		source.parse();

		Class sampleClass = source.getClasses().get("Simple");
		Class fooClass = source.getClasses().get("Foo");
		TreeNode geeUseTreeNode = new TreeNode(source.getUse("Gee"));
		TreeNode sampleTreeNode = new TreeNode(sampleClass);
		sampleTreeNode.setChildren(new TreeNode[] {
				new TreeNode(sampleClass.getField("age")),
				new TreeNode(sampleClass.getField("name")),
				new TreeNode(sampleClass.getField("count")),
				new TreeNode(sampleClass.getMethod("main")),
				new TreeNode(sampleClass.getMethod("doThing")),
				new TreeNode(sampleClass.getMethod("getParent")) });
		TreeNode fooTreeNode = new TreeNode(fooClass);
		fooTreeNode.setChildren(new TreeNode[] {
				new TreeNode(fooClass.getMethod("getParent")),
				new TreeNode(fooClass.getMethod("removeParent")) });

		TreeNode[] expectedTreeNodes = { geeUseTreeNode, sampleTreeNode,
				fooTreeNode };

		ValaContentProvider contentProvider = new ValaContentProvider();
		TreeNode[] treeNodes = contentProvider.getElements(source);

		Assert.assertArrayEquals(expectedTreeNodes, treeNodes);
	}

}
