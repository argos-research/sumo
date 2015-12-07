package shapes;

import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.io.Serializable;

/**
 * Extension of the java {@link Ellipse2D.Float} class that provides serialization.
 *
 */
public class Ellipse2DExt extends Ellipse2D.Float implements Serializable {

	private static final long serialVersionUID = 1L;

	public Ellipse2DExt(Ellipse2D.Float source){
		super(source.x,source.y,source.width,source.height);
	}
	
	public Ellipse2DExt(){
		super();
	}
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeFloat(this.x);
		out.writeFloat(this.y);
		out.writeFloat(this.width);
		out.writeFloat(this.height);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		x = in.readFloat();
		y = in.readFloat();
		width = in.readFloat();
		height = in.readFloat();
	}
}