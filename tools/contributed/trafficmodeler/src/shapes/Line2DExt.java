package shapes;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.Serializable;

/**
 * Extension of the java {@link Line2D.Double} class that provides
 * serialization.
 * 
 */
public class Line2DExt extends Line2D.Double implements Serializable {
	private static final long serialVersionUID = 1L;

	public Line2DExt(Point2D.Double lineStart, Point2D.Double lineEnd) {
		super(lineStart, lineEnd);
	}

	public Line2DExt() {
	}

	public Line2DExt(Line2D.Double source){
		super(source.x1,source.y1,source.x2,source.y2);
	}
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeDouble(x1);
		out.writeDouble(y1);
		out.writeDouble(x2);
		out.writeDouble(y2);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		x1 = in.readDouble();
		y1 = in.readDouble();
		x2 = in.readDouble();
		y2 = in.readDouble();
	}
}
