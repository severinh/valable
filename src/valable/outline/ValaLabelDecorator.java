package valable.outline;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.jface.viewers.TreeNode;
import org.gnome.vala.Class;
import org.gnome.vala.CreationMethod;
import org.gnome.vala.EnumValue;
import org.gnome.vala.Field;
import org.gnome.vala.MemberBinding;
import org.gnome.vala.Method;
import org.gnome.vala.NopCodeVisitor;
import org.gnome.vala.Property;
import org.gnome.vala.Symbol;

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
		if (element instanceof TreeNode) {
			element = ((TreeNode) element).getValue();
		}
		if (element instanceof Symbol) {
			Symbol symbol = (Symbol) element;
			ValaLabelDecoratorImpl labelDecorator = new ValaLabelDecoratorImpl(
					decoration);
			symbol.accept(labelDecorator);
		}
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

	private class ValaLabelDecoratorImpl extends NopCodeVisitor<Void> {

		private final IDecoration decoration;

		public ValaLabelDecoratorImpl(IDecoration decoration) {
			super();
			this.decoration = decoration;
		}

		public IDecoration getDecoration() {
			return decoration;
		}

		/**
		 * Adds decorations to the given class where appropriate.
		 * 
		 * @param cls
		 *            the class to be decorated
		 */
		@Override
		public Void visitClass(Class cls) {
			if (cls.isAbstract()) {
				addOverlay(ValaPluginConstants.IMG_OVERLAY_ABSTRACT);
			}
			return null;
		}

		/**
		 * Adds decorations to the given enum value where appropriate.
		 * 
		 * @param enumValue
		 *            the enum value to be decorated
		 */
		@Override
		public Void visitEnumValue(EnumValue enumValue) {
			addOverlay(ValaPluginConstants.IMG_OVERLAY_STATIC);
			return null;
		}

		/**
		 * Adds decorations to the given method where appropriate.
		 * 
		 * @param method
		 *            the method to be decorated
		 */
		@Override
		public Void visitMethod(Method method) {
			if (method instanceof CreationMethod) {
				addOverlay(ValaPluginConstants.IMG_OVERLAY_CONSTRUCTOR);
			}
			if (method.isAbstract()) {
				addOverlay(ValaPluginConstants.IMG_OVERLAY_ABSTRACT);
			}
			visitMemberBinding(method.getBinding());
			return null;
		}

		/**
		 * Adds decorations to the given field where appropriate.
		 * 
		 * @param field
		 *            the field to be decorated
		 */
		@Override
		public Void visitField(Field field) {
			visitMemberBinding(field.getBinding());
			return null;
		}

		/**
		 * Adds decorations to the given property where appropriate.
		 * 
		 * @param property
		 *            the property to be decorated
		 */
		@Override
		public Void visitProperty(Property property) {
			if (property.isAbstract()) {
				addOverlay(ValaPluginConstants.IMG_OVERLAY_ABSTRACT);
			}
			visitMemberBinding(property.getBinding());
			return null;
		}

		/**
		 * Adds decorations depending on the given member binding.
		 * 
		 * @param binding
		 *            the member binding
		 */
		public Void visitMemberBinding(MemberBinding binding) {
			if (binding.equals(MemberBinding.STATIC)) {
				addOverlay(ValaPluginConstants.IMG_OVERLAY_STATIC);
			}
			return null;
		}

		private void addOverlay(String overlayKey) {
			ImageDescriptor overlay = imageRegistry.getDescriptor(overlayKey);
			getDecoration().addOverlay(overlay, IDecoration.TOP_RIGHT);
		}

	}

}
