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

import java.util.List;

import org.eclipse.jface.viewers.TreeNode;
import org.gnome.vala.Class;
import org.gnome.vala.Field;
import org.gnome.vala.Method;
import org.gnome.vala.SymbolAccessibility;
import org.gnome.vala.VoidType;
import org.junit.Test;

import valable.AbstractTest;
import valable.ValaPluginConstants;
import valable.model.ValaPackage;
import valable.model.ValaSource;

/**
 * Tests {@link ValaLabelProvider}.
 */
public class ValaLabelProviderTest extends AbstractTest {

	@Test
	public void testImageKey() {
		ValaSource source = parseTestSource("simple.vala");

		ValaLabelProvider labelProvider = new ValaLabelProvider();
		ValaPackage valaPackage = new ValaPackage("package");
		Field field = new Field("field", new VoidType());
		field.setAccessibility(SymbolAccessibility.INTERNAL);

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
		Field field = new Field("field", new VoidType());
		TreeNode fieldTreeNode = new TreeNode(field);
		String expectedFieldText = "field : void";

		assertEquals(expectedFieldText, labelProvider.getText(field));
		assertEquals(expectedFieldText, labelProvider.getText(fieldTreeNode));

		Method method = new Method("method", new VoidType());
		assertEquals("method() : void", labelProvider.getText(method));
	}

	@Test
	public void testProperties() {
		ValaSource source = parseTestSource("properties.vala");
		Class nonPrivAccessClass = source.getClass("NonPrivAccess");
		Class sampleClass = source.getClass("Sample");
		List<Field> sampleFields = sampleClass.getFields();
		Field realStructField = nonPrivAccessClass.getField("_real_struct");
		Field automaticField = sampleClass.getField("_automatic");
		Field delegField = sampleClass.getField("_deleg");

		ValaLabelProvider labelProvider = new ValaLabelProvider();

		assertEquals("_real_struct : RealStruct",
				labelProvider.getText(realStructField));
		assertEquals("_automatic : string",
				labelProvider.getText(automaticField));
		assertEquals("_deleg : Delegate", labelProvider.getText(delegField));
	}

}
