/* ValaCreationTemplateFactory.java
 *
 * Copyright (C) 2008  Johann Prieur <johann.prieur@gmail.com>
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
