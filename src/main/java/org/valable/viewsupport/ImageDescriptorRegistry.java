/*******************************************************************************
 * Copyright (c) 2000, 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.valable.viewsupport;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import org.eclipse.core.runtime.Assert;

import org.eclipse.jface.resource.ImageDescriptor;

import org.eclipse.ui.PlatformUI;

/**
 * A registry that maps <code>ImageDescriptors</code> to <code>Image</code>.
 */
public class ImageDescriptorRegistry {

	private HashMap<ImageDescriptor, Image> registry = new HashMap<ImageDescriptor, Image>(10);
	private Display display;

	/**
	 * Creates a new image descriptor registry for the given display. All images
	 * managed by this registry will be disposed when the display gets disposed.
	 */
	public ImageDescriptorRegistry() {
		display = PlatformUI.getWorkbench().getDisplay();
		Assert.isNotNull(display);
		hookDisplay();
	}

	/**
	 * Returns the image associated with the given image descriptor.
	 * 
	 * @param descriptor the image descriptor for which the registry manages an
	 *            image, or <code>null</code> for a missing image descriptor
	 * @return the image associated with the image descriptor or
	 *         <code>null</code> if the image descriptor can't create the
	 *         requested image.
	 */
	public Image get(ImageDescriptor descriptor) {
		if (descriptor == null) {
			descriptor = ImageDescriptor.getMissingImageDescriptor();
		}

		Image result = registry.get(descriptor);
		if (result != null) {
			return result;
		}

		result = descriptor.createImage();
		if (result != null) {
			registry.put(descriptor, result);
		}
		return result;
	}

	/**
	 * Disposes all images managed by this registry.
	 */
	public void dispose() {
		for (Iterator<Image> iter = registry.values().iterator(); iter.hasNext();) {
			Image image = iter.next();
			image.dispose();
		}
		registry.clear();
	}

	private void hookDisplay() {
		display.disposeExec(new Runnable() {
			public void run() {
				dispose();
			}
		});
	}
}