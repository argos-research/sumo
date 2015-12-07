package ui.filefilters;

import java.io.File;

/**
 * Filter used in the open / save project dialogs
 *
 */
public class ProjectFileFilter extends javax.swing.filechooser.FileFilter{

	@Override
	public boolean accept(File f) {

		if((f.isDirectory())||(f.getName().endsWith(".sumo.prj"))){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public String getDescription() {
		return "SUMO traffic project (*.sumo.prj)";
	}
}