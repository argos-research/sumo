package ui.filefilters;

import java.io.File;

/**
 * File filter used in the new project dialog. Accepts only .net.xml files that
 * are accompanied by .edg.xml and .nod.xml files with the same name in the same
 * folder
 * 
 */
public class SUMOFileFilter extends javax.swing.filechooser.FileFilter {

	@Override
	public boolean accept(File f) {

		if (f.isDirectory()) {
			return true;
		}

		// Check to see if nodes and edges files exist
		if ((f.getName().endsWith(".net.xml")) && (new File(f.getAbsolutePath().replaceFirst(".net.", ".edg.")).exists()) && (new File(f.getAbsolutePath().replaceFirst(".net.", ".nod.")).exists())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getDescription() {
		return "SUMO network files with associated nodes and edges files";
	}
}