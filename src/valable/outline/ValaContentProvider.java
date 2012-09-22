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
import org.gnome.vala.Field;
import org.gnome.vala.Method;
import org.gnome.vala.Symbol;

import valable.model.SymbolLocationComparator;
import valable.model.ValaSource;

/**
 * Provide a tree of content for {@link ValaSource} and its children.
 */
public class ValaContentProvider extends TreeNodeContentProvider {

	@Override
	public TreeNode[] getElements(Object parent) {
		List<Object> elements = new ArrayList<Object>();

		if (parent instanceof ValaSource) {
			ValaSource source = (ValaSource) parent;
			List<Class> classes = new ArrayList<Class>(source.getClasses());
			Collections.sort(classes, SymbolLocationComparator.getInstance());
			elements.addAll(classes);
		} else if (parent instanceof Class) {
			Class cls = (Class) parent;
			List<Field> fields = cls.getFields();
			List<Method> methods = cls.getMethods();
			int symbolCount = fields.size() + methods.size();
			List<Symbol> symbols = new ArrayList<Symbol>(symbolCount);
			symbols.addAll(fields);
			symbols.addAll(methods);
			Collections.sort(symbols, SymbolLocationComparator.getInstance());

			for (Symbol symbol : symbols) {
				if (symbol.hasNameSourceReference()) {
					elements.add(symbol);
				}
			}
		}

		TreeNode[] results = new TreeNode[elements.size()];
		for (int i = 0; i < elements.size(); i++) {
			results[i] = new TreeNode(elements.get(i));

			if (results[i].getValue() instanceof Class) {
				results[i].setChildren(getElements(results[i].getValue()));
			}
		}

		return results;
	}

}
