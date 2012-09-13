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

import valable.model.ValaSource;
import valable.model.ValaType;

/**
 * Provide a tree of content for {@link ValaSource} and its children.
 */
public class ValaContentProvider extends TreeNodeContentProvider {

	/**
	 * @see org.eclipse.jface.viewers.TreeNodeContentProvider#getChildren(java.lang.Object)
	 */
	@Override
	public TreeNode[] getElements(Object parent) {
		List<Object> elements = new ArrayList<Object>();

		if (parent instanceof ValaSource) {
			elements.addAll(((ValaSource) parent).getUses());
			elements.addAll(((ValaSource) parent).getTypes().values());

		} else if (parent instanceof ValaType) {
			elements.addAll(((ValaType) parent).getFields());
			elements.addAll(((ValaType) parent).getMethods());
		}

		TreeNode[] results = new TreeNode[elements.size()];
		for (int i = 0; i < elements.size(); i++) {
			results[i] = new TreeNode(elements.get(i));

			if (results[i].getValue() instanceof ValaType) {
				results[i].setChildren(getElements(results[i].getValue()));
			}
		}

		return results;
	}

}
