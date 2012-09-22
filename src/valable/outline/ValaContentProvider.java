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
import java.util.List;

import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.gnome.vala.Class;
import org.gnome.vala.Method;
import org.gnome.vala.SourceReference;

import valable.model.ValaSource;

/**
 * Provide a tree of content for {@link ValaSource} and its children.
 */
public class ValaContentProvider extends TreeNodeContentProvider {

	@Override
	public TreeNode[] getElements(Object parent) {
		List<Object> elements = new ArrayList<Object>();

		if (parent instanceof ValaSource) {
			elements.addAll(((ValaSource) parent).getUses());
			elements.addAll(((ValaSource) parent).getClasses().values());

		} else if (parent instanceof Class) {
			Class cls = (Class) parent;
			elements.addAll(cls.getFields());
			for (Method method : cls.getMethods()) {
				// Only display a method if its SourceReference can be
				// determined.
				SourceReference reference = method.getNameSourceReference();
				if (reference != null) {
					elements.add(method);
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
