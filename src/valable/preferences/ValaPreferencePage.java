/* ValaPreferencePage.java
 *
 * Copyright (C) 2007  Johann Prieur <johann.prieur@gmail.com>
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
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
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
}