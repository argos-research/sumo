package helpers;

import java.awt.Graphics2D;

import shapes.Polygon2D;

/**
 * Class providing methods used for drawing.
 * 
 */
public class DrawingHelper {
	/**
	 * Draws an arrow head.
	 * @param g2d The graphics on which to draw the arrow head.
	 * @param startx The x coordinate of the arrow line's starting point
	 * @param starty The y coordinate of the arrow line's starting point
	 * @param endx The x coordinate of the arrow line's ending point
	 * @param endy The y coordinate of the arrow line's ending point
	 * @param stroke The stroke of the arrow head
	 */
	public static void drawArrowHead(Graphics2D g2d, double startx, double starty, double endx, double endy, float stroke) {
		//Find the arrow line's direction
		double aDir = Math.atan2(startx - endx, starty - endy);

		int i1 = 12 + Math.round(stroke * 2);
		int i2 = 6 + Math.round(stroke); 

		//Create the polygon representing the arrow head
		Polygon2D.Double tmpPoly = new Polygon2D.Double();
		tmpPoly.moveTo(endx, endy); // arrow tip
		tmpPoly.lineTo(endx + xCor(i1, aDir + .5), endy + yCor(i1, aDir + .5));
		tmpPoly.lineTo(endx + xCor(i2, aDir), endy + yCor(i2, aDir));
		tmpPoly.lineTo(endx + xCor(i1, aDir - .5), endy + yCor(i1, aDir - .5));
		tmpPoly.lineTo(endx, endy); // arrow tip
		tmpPoly.closePath();

		//Draw the arrow head
		g2d.fill(tmpPoly);
	}

	private static int yCor(int len, double dir) {
		return (int) Math.round(len * Math.cos(dir));
	}

	private static int xCor(int len, double dir) {
		return (int) Math.round(len * Math.sin(dir));
	}
}
