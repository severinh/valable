/**
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.outline;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.TreeNode;
import org.gnome.vala.Class;
import org.gnome.vala.Enum;
import org.gnome.vala.SourceFile;
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
		SourceFile sourceFile = source.getSourceFile();

		Class sampleClass = source.getClass("Simple");
		Class fooClass = source.getClass("Foo");
		TreeNode sampleTreeNode = new TreeNode(sampleClass);
		sampleTreeNode.setChildren(new TreeNode[] {
				new TreeNode(sampleClass.getMethod(".new")),
				new TreeNode(sampleClass.getField("age")),
				new TreeNode(sampleClass.getField("name")),
				new TreeNode(sampleClass.getField("count")),
				new TreeNode(sampleClass.getMethod("main")),
				new TreeNode(sampleClass.getMethod("doThing")),
				new TreeNode(sampleClass.getMethod("getParent")) });
		TreeNode fooTreeNode = new TreeNode(fooClass);
		fooTreeNode.setChildren(new TreeNode[] {
				new TreeNode(fooClass.getMethod(".new")),
				new TreeNode(fooClass.getMethod("getParent")),
				new TreeNode(fooClass.getMethod("removeParent")) });

		TreeNode[] expectedTreeNodes = { sampleTreeNode, fooTreeNode };

		ValaContentProvider contentProvider = new ValaContentProvider();
		TreeNode[] treeNodes = contentProvider.getElements(sourceFile);

		assertTreeNodes(expectedTreeNodes, treeNodes);
	}

	@Test
	public void testProperties() {
		ValaSource source = parseTestSource("properties.vala");
		SourceFile sourceFile = source.getSourceFile();

		Class nonPrivAccessClass = source.getClass("NonPrivAccess");
		TreeNode nonPrivAccessClassNode = new TreeNode(nonPrivAccessClass);
		nonPrivAccessClassNode.setChildren(new TreeNode[] { new TreeNode(
				nonPrivAccessClass.getMethod(".new")) });

		Class sampleClass = source.getClass("Sample");
		TreeNode sampleClassNode = new TreeNode(sampleClass);
		sampleClassNode.setChildren(new TreeNode[] {
				new TreeNode(sampleClass.getField("_name")),
				new TreeNode(sampleClass.getField("_read_only")),
				new TreeNode(sampleClass.getMethod(".new")),
				new TreeNode(sampleClass.getMethod("run")),
				new TreeNode(sampleClass.getMethod("main")) });

		Class fooClass = source.getClass("Foo");
		TreeNode fooClassNode = new TreeNode(fooClass);
		fooClassNode.setChildren(new TreeNode[] {
				new TreeNode(fooClass.getMethod(".new")),
				new TreeNode(fooClass.getField("_public_base_property")) });

		Class barClass = source.getClass("Bar");
		TreeNode barClassNode = new TreeNode(barClass);
		barClassNode.setChildren(new TreeNode[] {
				new TreeNode(barClass.getMethod(".new")),
				new TreeNode(barClass.getMethod("do_action")),
				new TreeNode(barClass.getMethod("run")) });

		Class bazClass = source.getClass("Baz");
		TreeNode bazClassNode = new TreeNode(bazClass);
		bazClassNode.setChildren(new TreeNode[] { new TreeNode(bazClass
				.getMethod(".new")) });

		TreeNode[] expectedTreeNodes = { nonPrivAccessClassNode,
				sampleClassNode, fooClassNode, barClassNode, bazClassNode };
		ValaContentProvider contentProvider = new ValaContentProvider();
		TreeNode[] treeNodes = contentProvider.getElements(sourceFile);

		assertTreeNodes(expectedTreeNodes, treeNodes);
	}

	@Test
	public void testEnums() {
		ValaSource source = parseTestSource("enums.vala");
		SourceFile sourceFile = source.getSourceFile();

		Enum fooEnum = sourceFile.getEnum("Foo");
		TreeNode fooEnumNode = new TreeNode(fooEnum);
		fooEnumNode.setChildren(new TreeNode[] {
				new TreeNode(fooEnum.getValues().get(0)),
				new TreeNode(fooEnum.getValues().get(1)),
				new TreeNode(fooEnum.getValues().get(2)) });

		Enum fooishEnum = sourceFile.getEnum("Fooish");
		TreeNode fooishEnumNode = new TreeNode(fooishEnum);
		fooishEnumNode.setChildren(new TreeNode[] {
				new TreeNode(fooishEnum.getValues().get(0)),
				new TreeNode(fooishEnum.getValues().get(1)) });

		Class barClass = source.getClass("Bar");
		TreeNode barClassNode = new TreeNode(barClass);
		barClassNode
				.setChildren(new TreeNode[] {
						new TreeNode(barClass.getMethod(".new")),
						new TreeNode(barClass.getMethod("run")),
						new TreeNode(barClass
								.getMethod("test_enums_0_conversion")),
						new TreeNode(barClass
								.getMethod("test_enum_methods_constants")),
						new TreeNode(barClass.getMethod("main")) });

		TreeNode[] expectedTreeNodes = { fooEnumNode, fooishEnumNode,
				barClassNode };
		ValaContentProvider contentProvider = new ValaContentProvider();
		TreeNode[] treeNodes = contentProvider.getElements(sourceFile);

		assertTreeNodes(expectedTreeNodes, treeNodes);
	}

	/**
	 * Asserts that two tree node arrays are equal, including their children.
	 * 
	 * @param expectedTreeNodes
	 *            the expected array of tree nodes
	 * @param treeNodes
	 *            the actual array of tree nodes
	 */
	public void assertTreeNodes(TreeNode[] expectedTreeNodes,
			TreeNode[] treeNodes) {
		if (expectedTreeNodes == null && treeNodes == null) {
			return;
		}
		assertEquals(expectedTreeNodes.length, treeNodes.length);

		for (int index = 0; index < treeNodes.length; index++) {
			TreeNode expectedTreeNode = expectedTreeNodes[index];
			TreeNode treeNode = treeNodes[index];
			assertEquals(expectedTreeNode.getValue(), treeNode.getValue());
			assertTreeNodes(expectedTreeNode.getChildren(),
					treeNode.getChildren());
		}
	}

}
