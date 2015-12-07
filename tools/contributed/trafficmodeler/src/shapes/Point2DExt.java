package shapes;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.Serializable;

/**
 * Extension of the java {@link Point2D.Double} class that provides
 * serialization.
 * 
 */
public class Point2DExt extends Point2D.Double implements Serializable {

	private static final long serialVersionUID = 1L;

	public Point2DExt(double x, double y) {
		super(x, y);
	}

	public Point2DExt(Point2D.Double p) {
		super(p.x, p.y);
	}
	
	public Point2DExt() {
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeDouble(x);
		out.writeDouble(y);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		x = in.readDouble();
		y = in.readDouble();
	}
}
