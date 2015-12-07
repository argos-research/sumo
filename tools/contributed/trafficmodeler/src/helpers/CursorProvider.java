package helpers;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

/**
 * Class used to provide cursors.
 * 
 */
public class CursorProvider {
	/**
	 * @author  PapaleonLe01
	 */
	public enum Cursors {
		Hand, HandClosed, ZoomIn, ZoomOut, Select, SelectAdd, SelectRemove
	}

	public static Cursor getCursor(CursorProvider.Cursors cursor) {
		String filename = null;
		Point hotspot = null;
		String cursorName = null;

		switch (cursor) {
		case Hand:
			filename = "/resources/cursors/hand.gif";
			hotspot = new Point(16, 16);
			cursorName = "hand";
			break;
		case HandClosed:
			filename = "/resources/cursors/handclosed.gif";
			hotspot = new Point(16, 16);
			cursorName = "handclosed";
			break;
		case ZoomIn:
			filename = "/resources/cursors/ZoomIn.gif";
			hotspot = new Point(14, 14);
			cursorName = "zoomin";
			break;
		case ZoomOut:
			filename = "/resources/cursors/ZoomOut.gif";
			hotspot = new Point(14, 14);
			cursorName = "zoomin";
			break;
		case Select:
			filename = "/resources/cursors/Select.gif";
			hotspot = new Point(5, 4);
			cursorName = "select";
			break;
		case SelectAdd:
			filename = "/resources/cursors/SelectAdd.gif";
			hotspot = new Point(5, 4);
			cursorName = "selectadd";
			break;
		case SelectRemove:
			filename = "/resources/cursors/SelectRemove.gif";
			hotspot = new Point(5, 4);
			cursorName = "selectremove";
			break;
		}

		Image pointer = (new ImageIcon((new Object()).getClass().getResource(filename))).getImage();

		return Toolkit.getDefaultToolkit().createCustomCursor(pointer, hotspot, cursorName);
	}
}
