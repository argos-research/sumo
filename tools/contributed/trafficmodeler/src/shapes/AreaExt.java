package shapes;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.io.IOException;
import java.io.Serializable;

/**
 * Extension of the java {@link Area} class that provides serialization.
 * 
 */
public class AreaExt extends Area implements Serializable {

	private static final long serialVersionUID = 3801278214712144623L;

	/**
	 * Default constructor
	 * 
	 * @param s
	 */
	public AreaExt(Shape s) {
		super(s);
	}

	/**
	 * Writes the area to the specified output stream
	 * 
	 * @param out
	 * @throws IOException
	 */
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		final PathIterator pi = this.getPathIterator(new AffineTransform());
		final float[] args = new float[6];
		out.writeBoolean(pi.isDone());
		while (!pi.isDone()) {
			final int type = pi.currentSegment(args);
			out.writeInt(type);
			// TODO: could write this to only stream the values
			// required for the segment type
			for (int i = 0; i < 6; i++) {
				out.writeFloat(args[i]);
			}
			out.writeInt(pi.getWindingRule());
			pi.next();
			out.writeBoolean(pi.isDone());
		}
	}

	/**
	 * Reads the area from the specified input stream
	 * 
	 * @param in
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		final GeneralPath gp = new GeneralPath();
		final float[] args = new float[6];
		boolean hasNext = in.readBoolean();
		while (!hasNext) {
			final int type = in.readInt();
			for (int i = 0; i < 6; i++) {
				args[i] = in.readFloat();
			}
			switch (type) {
			case PathIterator.SEG_MOVETO:
				gp.moveTo(args[0], args[1]);
				break;
			case PathIterator.SEG_LINETO:
				gp.lineTo(args[0], args[1]);
				break;
			case PathIterator.SEG_CUBICTO:
				gp.curveTo(args[0], args[1], args[2], args[3], args[4], args[5]);
				break;
			case PathIterator.SEG_QUADTO:
				gp.quadTo(args[0], args[1], args[2], args[3]);
				break;
			case PathIterator.SEG_CLOSE:
				// result = gp;
				break;
			default:
				throw new RuntimeException("JFreeChart - No path exists");
			}
			gp.setWindingRule(in.readInt());
			hasNext = in.readBoolean();
		}

		super.add(new Area(gp));
	}
}
