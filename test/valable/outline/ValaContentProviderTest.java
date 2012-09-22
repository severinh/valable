/**
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.outline;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.TreeNode;
import org.gnome.vala.Class;
import org.junit.Assert;
import org.junit.Test;

import valable.AbstractTest;
import valable.model.ValaSource;

/**
 * Tests {@link ValaContentProvider}.
 */
public class ValaContentProviderTest extends AbstractTest {

	@Test
	public void testParse() throws CoreException, IOException {
		ValaSource source = parseTestSource("simple.vala");

		Class sampleClass = source.getClass("Simple");
		Class fooClass = source.getClass("Foo");
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

		TreeNode[] expectedTreeNodes = { sampleTreeNode, fooTreeNode };

		ValaContentProvider contentProvider = new ValaContentProvider();
		TreeNode[] treeNodes = contentProvider.getElements(source);

		Assert.assertArrayEquals(expectedTreeNodes, treeNodes);
	}

	@Test
	public void testProperties() {
		ValaSource source = parseTestSource("properties.vala");

		Class nonPrivAccessClass = source.getClass("NonPrivAccess");
		Class sampleClass = source.getClass("Sample");
		TreeNode nonPrivAccessClassNode = new TreeNode(nonPrivAccessClass);
		nonPrivAccessClassNode.setChildren(new TreeNode[] { new TreeNode(
				nonPrivAccessClass.getField("real_struct")) });
		TreeNode sampleClassNode = new TreeNode(sampleClass);
		sampleClassNode.setChildren(new TreeNode[] { new TreeNode(sampleClass
				.getField("_automatic")) });

		TreeNode[] expectedTreeNodes = { nonPrivAccessClassNode,
				sampleClassNode };
		ValaContentProvider contentProvider = new ValaContentProvider();
		TreeNode[] treeNodes = contentProvider.getElements(source);

		Assert.assertArrayEquals(expectedTreeNodes, treeNodes);
	}

}
