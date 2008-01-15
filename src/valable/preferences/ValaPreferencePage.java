/* ValaPreferencePage.java
 *
 * Copyright (C) 2007  Johann Prieur <johann.prieur@gmail.com>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */
package valable.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import valable.ValaPlugin;

public class ValaPreferencePage extends FieldEditorPreferencePage 
	implements IWorkbenchPreferencePage {

	private String _(String key) {
		return ValaPlugin.getResourceBundle().getString(key);
	}
	
	public ValaPreferencePage() {
		super(GRID);
		setPreferenceStore(ValaPlugin.getDefault().getPreferenceStore());
		setDescription(_("preferences.general.description"));
	}
	
	public void createFieldEditors() {
		addField(new FileFieldEditor(PreferenceConstants.P_VALAC_EXE, 
				_("preferences.general.valac.executable"), getFieldEditorParent()));
		addField(new DirectoryFieldEditor(PreferenceConstants.P_VAPI_PATH, 
				_("preferences.general.vapi.path"), getFieldEditorParent()));	
		addField(new DirectoryFieldEditor(PreferenceConstants.P_OUTPUT_FOLDER, 
				_("preferences.general.output.directory"), getFieldEditorParent()));	
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}