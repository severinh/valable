/**
 * Copyright (C) 2008  Johann Prieur <johann.prieur@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package valable.wizard.file;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ValaCreationTemplateFactory {

	public static InputStream generateValaClassTemplate(String[] usings, 
			String namespace, String[] modifiers, String name, String base,
			String[] interfaces, boolean generateMain, boolean generateInherited) {
		
		String generalTemplate = "%s\n%sclass %s : %s {\n%s\n}";
		
		String importsSource = "";
		for(String using : usings){
			importsSource += String.format("using %s;\n", using);
		}
		
		String modifiersSource = "";
		for(String modifier : modifiers){
			modifiersSource += String.format("%s ", modifier);
		}
		
		String baseAndInterfacesSource = base;
		for(String interfac : interfaces){
			baseAndInterfacesSource += String.format(", %s", interfac);
		}
		
		String mainSource = "";
		if(generateMain){ 
			mainSource = "public static void main(string[] args) {\n}"; 
		}
		
		// TODO : add interfaces methods to implement
		// TODO : add inherited methods from abstract base
		
		String source = String.format(generalTemplate, importsSource, modifiersSource, 
				name, baseAndInterfacesSource, mainSource);
		return new ByteArrayInputStream(source.getBytes());
	}
	
	public static void main(String[] args) {
		
	}
	
}
