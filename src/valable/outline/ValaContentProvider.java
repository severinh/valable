/**
 * Copyright (C) 2008  Andrew Flegg <andrew@bleb.org>
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.outline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.gnome.vala.Class;
import org.gnome.vala.CodeNode;
import org.gnome.vala.Enum;
import org.gnome.vala.Field;
import org.gnome.vala.Method;
import org.gnome.vala.SourceFile;
import org.gnome.vala.Symbol;

import valable.model.SymbolLocationComparator;
import valable.model.ValaSource;

/**
 * Provide a tree of content for {@link ValaSource} and its children.
 */
public class ValaContentProvider extends TreeNodeContentProvider {

	@Override
	public TreeNode[] getElements(Object parent) {
		if (parent instanceof SourceFile) {
			SourceFile sourceFile = (SourceFile) parent;
			return getElements(sourceFile);
		} else if (parent instanceof Class) {
			Class cls = (Class) parent;
			return getElements(cls);
		}
		return new TreeNode[] {};
	}

	/**
	 * Returns the elements inside of a source file, including classes,
	 * functions, etc.
	 * 
	 * @param sourceFile
	 *            the source file to create tree nodes for
	 * @return the resulting tree nodes
	 */
	private TreeNode[] getElements(SourceFile sourceFile) {
		List<TreeNode> result = new ArrayList<TreeNode>();
		for (CodeNode node : sourceFile.getNodes()) {
			TreeNode treeNode = new TreeNode(node);
			TreeNode[] children = null;
			if (node instanceof Class) {
				Class cls = (Class) node;
				children = getElements(cls);
			} else if (node instanceof Enum) {
				Enum enm = (Enum) node;
				children = getElements(enm);
			} else {
				continue;
			}
			treeNode.setChildren(children);
			result.add(treeNode);
		}
		TreeNode[] resultArray = makeResultArray(result);
		return resultArray;
	}

	/**
	 * Returns the elements inside of a class, including fields and methods.
	 * 
	 * @param cls
	 *            the class to create tree nodes for
	 * @return the resulting tree nodes
	 */
	private TreeNode[] getElements(Class cls) {
		List<TreeNode> result = new ArrayList<TreeNode>();
		List<Field> fields = cls.getFields();
		List<Method> methods = cls.getMethods();
		int symbolCount = fields.size() + methods.size();
		List<Symbol> symbols = new ArrayList<Symbol>(symbolCount);
		symbols.addAll(fields);
		symbols.addAll(methods);
		Collections.sort(symbols, SymbolLocationComparator.getInstance());

		for (Symbol symbol : symbols) {
			if (symbol.hasNameSourceReference()) {
				result.add(new TreeNode(symbol));
			}
		}
		TreeNode[] resultArray = makeResultArray(result);
		return resultArray;
	}

	/**
	 * Returns the elements inside of an enum.
	 * 
	 * @param enm
	 *            the enum to create tree nodes for
	 * @return the resulting tree nodes
	 */
	private TreeNode[] getElements(Enum enm) {
		return new TreeNode[] {};
	}

	private static TreeNode[] makeResultArray(List<TreeNode> result) {
		TreeNode[] resultArray = new TreeNode[result.size()];
		result.toArray(resultArray);
		return resultArray;
	}

}
