/* PreferenceInitializer.java
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

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import valable.ValaPlugin;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = ValaPlugin.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_VALAC_EXE, "/usr/local/bin/valac");
		store.setDefault(PreferenceConstants.P_VAPI_PATH, "/usr/local/share/vala/vapi/");
		store.setDefault(PreferenceConstants.P_OUTPUT_FOLDER, "/output/");
	}

}
