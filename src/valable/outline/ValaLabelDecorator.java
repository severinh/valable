package valable.outline;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.TreeNode;
import org.gnome.vala.Class;
import org.gnome.vala.CreationMethod;
import org.gnome.vala.Method;

import valable.ValaPlugin;
import valable.ValaPluginConstants;

/**
 * Decorates Vala symbols with overlay images.
 * 
 * For instance, it adds an 'A' overlay image to abstract Vala classes.
 */
public class ValaLabelDecorator implements ILightweightLabelDecorator {

	private final ImageRegistry imageRegistry = ValaPlugin.getDefault()
			.getImageRegistry();

	@Override
	public void decorate(Object element, IDecoration decoration) {
		element = maybeGetTreeNodeValue(element);
		if (element instanceof Class) {
			Class cls = (Class) element;
			decorateClass(cls, decoration);
		} else if (element instanceof Method) {
			Method method = (Method) element;
			decorateMethod(method, decoration);
		}
	}

	/**
	 * Adds decorations to Vala classes where appropriate.
	 * 
	 * @param cls
	 *            the class to be decorated
	 * @param decoration
	 */
	public void decorateClass(Class cls, IDecoration decoration) {
		if (cls.isAbstract()) {
			String overlayKey = ValaPluginConstants.IMG_OVERLAY_ABSTRACT;
			ImageDescriptor overlay = imageRegistry.getDescriptor(overlayKey);
			decoration.addOverlay(overlay, IDecoration.TOP_RIGHT);
		}
	}

	/**
	 * Adds decorations to Vala methods where appropriate.
	 * 
	 * @param method
	 *            the method to be decorated
	 * @param decoration
	 */
	public void decorateMethod(Method method, IDecoration decoration) {
		if (method instanceof CreationMethod) {
			String overlayKey = ValaPluginConstants.IMG_OVERLAY_CONSTRUCTOR;
			ImageDescriptor overlay = imageRegistry.getDescriptor(overlayKey);
			decoration.addOverlay(overlay, IDecoration.TOP_RIGHT);
		}
	}

	/**
	 * Returns its value if the given element is a {@link TreeNode} and the
	 * element itself otherwise.
	 */
	private Object maybeGetTreeNodeValue(Object element) {
		if (element instanceof TreeNode) {
			element = ((TreeNode) element).getValue();
		}
		return element;
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
	}

}
