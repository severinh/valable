/**
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.model;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.TreeNode;
import org.junit.Test;

import valable.ValaPluginConstants;
import valable.outline.ValaLabelProvider;

/**
 * Tests {@link ValaLabelProvider}.
 */
public class ValaLabelProviderTest {

	@Test
	public void testImageKey() {
		ValaLabelProvider labelProvider = new ValaLabelProvider();
		IFile file = new LocalFile(new File("test/simple.vala"));
		ValaProject project = new ValaProject("project");
		ValaSource source = new ValaSource(project, file);
		ValaPackage valaPackage = new ValaPackage("package");
		ValaField field = new ValaField("field");

		assertEquals(ValaPluginConstants.IMG_OBJECT_VALA,
				labelProvider.getImageKey(source));
		assertEquals(ValaPluginConstants.IMG_OBJECT_PACKAGE,
				labelProvider.getImageKey(valaPackage));
		assertEquals(ValaPluginConstants.IMG_OBJECT_UNKNOWN,
				labelProvider.getImageKey(new Object()));

		assertEquals(ValaPluginConstants.IMG_OBJECT_FIELD_DEFAULT,
				labelProvider.getImageKey(field));
		TreeNode fieldTreeNode = new TreeNode(field);
		assertEquals(ValaPluginConstants.IMG_OBJECT_FIELD_DEFAULT,
				labelProvider.getImageKey(fieldTreeNode));
	}

	@Test
	public void testText() {
		ValaLabelProvider labelProvider = new ValaLabelProvider();
		ValaField field = new ValaField("field");
		TreeNode fieldTreeNode = new TreeNode(field);
		field.setType("Type");
		String expectedFieldText = "field : Type";

		assertEquals(expectedFieldText, labelProvider.getText(field));
		assertEquals(expectedFieldText, labelProvider.getText(fieldTreeNode));

		ValaMethod method = new ValaMethod("method");
		method.setType("Type");
		assertEquals("method() : Type", labelProvider.getText(method));
	}

}
