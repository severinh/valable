/**
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.valable.viewsupport;

import static org.junit.Assert.assertEquals;

import org.eclipse.jface.viewers.TreeNode;
import org.gnome.vala.Class;
import org.gnome.vala.Field;
import org.gnome.vala.Method;
import org.gnome.vala.Property;
import org.gnome.vala.Signal;
import org.gnome.vala.SourceFile;
import org.gnome.vala.VoidType;
import org.junit.Test;

import org.valable.AbstractTest;
import org.valable.model.ValaSource;
import org.valable.viewsupport.ValaUILabelProvider;

/**
 * Tests {@link ValaUILabelProvider}.
 */
public class ValaUILabelProviderTest extends AbstractTest {

	@Test
	public void testText() {
		ValaUILabelProvider labelProvider = new ValaUILabelProvider();
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
		SourceFile sourceFile = source.getSourceFile();

		Class nonPrivAccessClass = sourceFile.getClass("NonPrivAccess");
		Class sampleClass = sourceFile.getClass("Sample");
		Property realStructProperty = nonPrivAccessClass
				.getProperty("real_struct");
		Property automaticProperty = sampleClass.getProperty("automatic");
		Property delegProperty = sampleClass.getProperty("deleg");

		ValaUILabelProvider labelProvider = new ValaUILabelProvider();

		assertEquals("real_struct : RealStruct",
				labelProvider.getText(realStructProperty));
		assertEquals("automatic : string",
				labelProvider.getText(automaticProperty));
		assertEquals("deleg : Delegate", labelProvider.getText(delegProperty));
	}

	@Test
	public void testSignals() {
		ValaSource source = parseTestSource("signals.vala");
		SourceFile sourceFile = source.getSourceFile();

		Class fooClass = sourceFile.getClass("Foo");
		Signal activatedSignal = fooClass.getSignal("activated");
		Class returnFooClass = sourceFile.getClass("ReturnFoo");
		Signal intActivatedSignal = returnFooClass.getSignal("int_activated");

		ValaUILabelProvider labelProvider = new ValaUILabelProvider();

		assertEquals("activated(bool) : void",
				labelProvider.getText(activatedSignal));
		assertEquals("int_activated(int) : int",
				labelProvider.getText(intActivatedSignal));
	}

}
