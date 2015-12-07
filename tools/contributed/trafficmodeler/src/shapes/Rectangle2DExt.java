package shapes;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.Serializable;

/**
 * Extension of the java {@link Rectangle2D.Double} class that provides
 * serialization.
 * 
 */
public class Rectangle2DExt extends Rectangle2D.Double implements Serializable {

	private static final long serialVersionUID = 1L;

	public Rectangle2DExt(Rectangle2D.Double rect) {
		super(rect.x, rect.y, rect.width, rect.height);
	}

	public Rectangle2DExt() {
	}

	public Rectangle2DExt(double centerX, double centerY, double width, double height) {
		super(centerX,centerY,width,height);
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeDouble(x);
		out.writeDouble(y);
		out.writeDouble(width);
		out.writeDouble(height);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		x = in.readDouble();
		y = in.readDouble();
		width = in.readDouble();
		height = in.readDouble();
	}

}
