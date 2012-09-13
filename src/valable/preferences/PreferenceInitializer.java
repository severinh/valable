/**
 * Copyright (C) 2007  Johann Prieur <johann.prieur@gmail.com>
 * Copyright (C) 2012  Severin Heiniger <severinheiniger@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import valable.ValaPlugin;
import valable.model.ValaBuildContext;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		ValaBuildContext context = ValaBuildContext.getDefault();

		IPreferenceStore store = ValaPlugin.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_VALAC_EXE, context
				.getValacExecutable().getAbsolutePath());
		store.setDefault(PreferenceConstants.P_VAPI_PATH, context
				.getVapiDirectories().get(0).getAbsolutePath());
		store.setDefault(PreferenceConstants.P_OUTPUT_FOLDER, "/output/");
	}

}
