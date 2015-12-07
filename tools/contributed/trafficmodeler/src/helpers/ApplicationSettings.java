package helpers;

import java.io.File;
import java.util.prefs.Preferences;

import javax.swing.JOptionPane;

/**
 * Class used to provide and store global application settings such as the paths
 * to the SUMO executables
 * 
 */
public class ApplicationSettings {
	public static String getDUARouterPath() {
		return Preferences.userRoot().get("DUARouterPath", "");
	}

	public static void setDUARouterPath(String path) {
		Preferences.userRoot().put("DUARouterPath", path);
	}

	public static String getNetConvertPath() {
		return Preferences.userRoot().get("NetConvertPath", "");
	}

	public static void setNetConvertPath(String path) {
		Preferences.userRoot().put("NetConvertPath", path);
	}


	/**
	 * Validates the paths that they are not empty and that they exist
	 * @return true if the paths are valid, false otherwise
	 */
	public static boolean Validate() {
		File dua = new File(getDUARouterPath());
		File netconvert = new File(getNetConvertPath());

		if (!dua.exists()) {
			JOptionPane.showMessageDialog(null, "DUA router path invalid or not specified");
			return false;
		}

		if (!netconvert.exists()) {
			JOptionPane.showMessageDialog(null, "NetConvert path invalid or not specified");
			return false;
		}
		
		return true;
	}
}
