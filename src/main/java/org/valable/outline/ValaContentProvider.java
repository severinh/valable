/**
 * Copyright (C) 2008  Andrew Flegg <andrew@bleb.org>
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.valable.outline;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.gnome.vala.Class;
import org.gnome.vala.CodeNode;
import org.gnome.vala.Constant;
import org.gnome.vala.Enum;
import org.gnome.vala.EnumValue;
import org.gnome.vala.Field;
import org.gnome.vala.Interface;
import org.gnome.vala.Method;
import org.gnome.vala.NopCodeVisitor;
import org.gnome.vala.Property;
import org.gnome.vala.Signal;
import org.gnome.vala.SourceFile;
import org.gnome.vala.Symbol;

import org.valable.model.ValaSource;

/**
 * Provide a tree of content for {@link ValaSource} and its children.
 */
public class ValaContentProvider extends TreeNodeContentProvider {

	@Override
	public TreeNode[] getElements(Object parent) {
		if (parent instanceof SourceFile) {
			SourceFile sourceFile = (SourceFile) parent;
			TreeNode[] result = sourceFile.accept(ValaContentProviderImpl
					.getInstance());
			return result;
		} else {
			throw new IllegalStateException(
					"expected parent of type SourceFile, got "
							+ parent.getClass().getSimpleName());
		}
	}

	private static class ValaContentProviderImpl extends
			NopCodeVisitor<TreeNode[]> {

		private static final ValaContentProviderImpl instance;
		private static final TreeNode[] emptyTreeNodeArray;

		static {
			instance = new ValaContentProviderImpl();
			emptyTreeNodeArray = new TreeNode[] {};
		}

		public static ValaContentProviderImpl getInstance() {
			return instance;
		}

		public static TreeNode[] getEmptyTreeNodeArray() {
			return emptyTreeNodeArray;
		}

		@Override
		public TreeNode[] visitSourceFile(SourceFile sourceFile) {
			TreeNode[] result = visitNodes(sourceFile.getNodes());
			return result;
		}

		@Override
		public TreeNode[] visitClass(Class cls) {
			TreeNode[] result = visitNodes(cls.getNodes());
			return result;
		}

		@Override
		public TreeNode[] visitInterface(Interface iface) {
			TreeNode[] result = visitNodes(iface.getNodes());
			return result;
		}

		@Override
		public TreeNode[] visitEnum(Enum enm) {
			TreeNode[] result = visitNodes(enm.getNodes());
			return result;
		}

		@Override
		public TreeNode[] visitEnumValue(EnumValue enumValue) {
			return visitDisplayableSymbol(enumValue);
		}

		@Override
		public TreeNode[] visitMethod(Method method) {
			return visitDisplayableSymbol(method);
		}

		@Override
		public TreeNode[] visitField(Field field) {
			return visitDisplayableSymbol(field);
		}

		@Override
		public TreeNode[] visitProperty(Property property) {
			return visitDisplayableSymbol(property);
		}

		@Override
		public TreeNode[] visitSignal(Signal signal) {
			return visitDisplayableSymbol(signal);
		}

		@Override
		public TreeNode[] visitConstant(Constant constant) {
			return visitDisplayableSymbol(constant);
		}

		private TreeNode[] visitNodes(List<CodeNode> nodes) {
			List<TreeNode> result = new ArrayList<TreeNode>();
			for (CodeNode node : nodes) {
				TreeNode[] children = node.accept(this);
				if (children != null) {
					TreeNode treeNode = new TreeNode(node);
					treeNode.setChildren(children);
					result.add(treeNode);
				}
			}
			TreeNode[] resultArray = new TreeNode[result.size()];
			result.toArray(resultArray);
			return resultArray;
		}

		public TreeNode[] visitDisplayableSymbol(Symbol symbol) {
			if (symbol.hasNameSourceReference()) {
				return getEmptyTreeNodeArray();
			} else {
				return null;
			}
		}

	}

}
