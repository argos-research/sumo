package ui;

import classes.Layer;
import classes.Project;
import classes.ProjectElement;
import enums.EditingMode;
import enums.SelectionType;
import enums.ToolType;
import helpers.Broadcaster;
import helpers.CursorProvider;
import interfaces.CurrentLayerChangedListener;
import interfaces.ProjectChangedListener;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import roadnetwork.Edge;
import roadnetwork.RoadNetwork;
import roadnetwork.RoadNetworkElement;
import trafficdefinition.Accident;
import trafficdefinition.AreaFlow;
import trafficdefinition.Flow;
import trafficdefinition.Handle;
import trafficdefinition.HotSpot;
import trafficdefinition.School;
import trafficdefinition.TrafficArea;
import trafficdefinition.TrafficDefinitionElement;
import trafficdefinition.TrafficDefinitionLayer;

/**
 * @author  PapaleonLe01
 */
public class Display extends JComponent implements CurrentLayerChangedListener, ProjectChangedListener, MouseListener, MouseMotionListener, MouseWheelListener, AdjustmentListener, KeyListener {

	private static final long serialVersionUID = 7162061244639610972L;

	private JScrollBar scrlVertical = null;

	private JScrollBar scrlHorizontal = null;

	/**
	 * The currently selected traffic layer
	 */
	private TrafficDefinitionLayer currentTrafficLayer;

	/**
	 * The current project's road network
	 */
	private RoadNetwork roadNetwork;

	/**
	 * Flag that states whether a project is open
	 */
	private boolean projectLoaded = false;

	/**
	 * The horizontal map translation
	 */
	private double translateX;

	/**
	 * The vertical map translation
	 */
	private double translateY;

	/**
	 * The minimum and maximum allowe transalation values
	 */
	private double mintranslateX;
	/**
	 * The minimum and maximum allowe transalation values
	 */
	private double maxtranslateX;
	/**
	 * The minimum and maximum allowe transalation values
	 */
	private double mintranslateY;
	/**
	 * The minimum and maximum allowe transalation values
	 */
	private double maxtranslateY;

	/**
	 * Scaling factor (zoom in - out)
	 */
	private double scale;

	private Point previousMouseLocation;

	private boolean areaZoom;

	/**
	 * The current tool
	 */
	private ToolType tool = ToolType.None;

	/**
	 * The current selection type
	 */
	private SelectionType selectionType = SelectionType.New;

	/**
	 * Helper variable to store a new element during its creation process
	 */
	private TrafficDefinitionElement newElement;

	/**
	 * Flag to know whether to draw all traffic layers or just the currently
	 * selected one
	 */
	private boolean drawAllTrafficLayers = false;

	private GeneralPath areaPath;

	private boolean creatingNewElement = false;

	private Handle currentHandle = null;

	private Rectangle marquee;

	private Point marqueeStart;

	private Point marqueeEnd;

	/**
	 * The transformation used while drawing the map and traffic layers
	 */
	private AffineTransform tx = new AffineTransform();

	private EditingMode editingMode;

	private boolean manipulatingHandle = false;

	/**
	 * Default constructor.
	 * 
	 * @param scrlvertical
	 * @param scrlhorizontal
	 */
	public Display(JScrollBar scrlvertical, JScrollBar scrlhorizontal) {

		scrlVertical = scrlvertical;
		scrlHorizontal = scrlhorizontal;

		// Initialize transformation variables
		translateX = 0;
		translateY = 0;
		scale = 1;

		setOpaque(true);
		setDoubleBuffered(true);

		// Register event handlers
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		addKeyListener(this);

		scrlVertical.addAdjustmentListener(this);
		scrlHorizontal.addAdjustmentListener(this);

		// Be notified when the user opens or closes the project
		Broadcaster.addProjectChangedListener(this);
	}

	/**
	 * Event handler for scroll bar events. Sets the translation variables so
	 * that the map moves when the user scrolls
	 */
	public void adjustmentValueChanged(AdjustmentEvent e) {
		// Horizontal scrolling
		if (e.getSource() == scrlHorizontal) {
			translateX = Math.max(maxtranslateX, mintranslateX) - e.getValue() + Math.min(maxtranslateX, mintranslateX);
			repaint();
		}
		// Vertical scrolling
		else if (e.getSource() == scrlVertical) {
			translateY = Math.max(maxtranslateY, mintranslateY) - e.getValue() + Math.min(maxtranslateY, mintranslateY);
			repaint();
		}

	}

	/**
	 * Handler for the event of the current traffic layer changed.
	 */
	public void CurrentLayerChanged(Layer<? extends ProjectElement> newCurrentLayer) {
		// Deselect all elements before changing the layer
		if (currentTrafficLayer != null) {
			currentTrafficLayer.SelectNoneElements();
		}

		if (roadNetwork != null) {
			roadNetwork.SelectNoneElements();
		}

		// If the user has selected the map layer then hide any previously
		// selected traffic layer
		if (newCurrentLayer instanceof RoadNetwork) {
			setEditingMode(EditingMode.Map);

			currentTrafficLayer = null;
		} else if (newCurrentLayer instanceof TrafficDefinitionLayer) {
			currentTrafficLayer = (TrafficDefinitionLayer) newCurrentLayer;

			setEditingMode(EditingMode.GenericTraffic);
		}

		repaint();
	}

	/**
	 * Deletes the selected elements of the currently editing map or traffic
	 * layer
	 * 
	 */
	public void DeleteSelectedObjects() {
		switch (editingMode) {
		case Map:
			roadNetwork.DeleteSelectedElements();
			break;
		case GenericTraffic:
			currentTrafficLayer.DeleteSelectedElements();
		}

		repaint();
	}

	/**
	 * Updates the mouse cursor when a key is pressed, or deletes selected
	 * elements when the delete key is pressed,or cancels the creation of a new
	 * object if the Esc key was pressed
	 */
	public void keyPressed(KeyEvent e) {
		if (!projectLoaded) {
			return;
		}

		// Delete the selected elements if the Delete key was pressed
		if (e.getKeyCode() == KeyEvent.VK_DELETE) {
			DeleteSelectedObjects();

			return;
		}
		// Cancel the creation of a new element if the Escape key was pressed
		else if ((e.getKeyCode() == KeyEvent.VK_ESCAPE) && (creatingNewElement)) {
			creatingNewElement = false;
			newElement = null;
			repaint();

			return;
		}

		if ((tool == ToolType.Select) && (e.getKeyCode() == KeyEvent.VK_CONTROL)) {
			setCursor(CursorProvider.getCursor(CursorProvider.Cursors.SelectAdd));

			return;
		}

		if ((tool == ToolType.Select) && (e.getKeyCode() == KeyEvent.VK_SHIFT)) {
			setCursor(CursorProvider.getCursor(CursorProvider.Cursors.SelectRemove));

			return;
		}

		if ((tool == ToolType.Zoom) && (e.getKeyCode() == KeyEvent.VK_CONTROL)) {
			setCursor(CursorProvider.getCursor(CursorProvider.Cursors.ZoomOut));

			return;
		}
	}

	/**
	 * Updates the mouse cursor when a key is released
	 */
	public void keyReleased(KeyEvent e) {
		if (!projectLoaded) {
			return;
		}

		if ((tool == ToolType.Select) && ((e.getKeyCode() == KeyEvent.VK_CONTROL) || (e.getKeyCode() == KeyEvent.VK_SHIFT))) {
			setCursor(CursorProvider.getCursor(CursorProvider.Cursors.Select));

			return;
		}

		if ((tool == ToolType.Zoom) && (e.getKeyCode() == KeyEvent.VK_CONTROL)) {
			setCursor(CursorProvider.getCursor(CursorProvider.Cursors.ZoomIn));

			return;
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
		if (!projectLoaded) {
			return;
		}

		// Transform the point to map coordinates
		Point2D.Double transformedPoint = TransformPoint(e.getPoint());

		if (transformedPoint == null) {
			return;
		}

		switch (tool) {
		// Create school
		case School:
			if (e.getButton() == MouseEvent.BUTTON1) {
				currentTrafficLayer.AddTrafficElement(new School(transformedPoint));
			}

			repaint();
			break;
		case Hotspot:
			// If we are in the middle of creating a new hotspot and the user
			// right clicks then cancel the creation
			if ((creatingNewElement) && (e.getButton() == MouseEvent.BUTTON3)) {
				creatingNewElement = false;
				newElement = null;
				repaint();

				return;
			}

			// If this is the second click (we were creating a hot spot)
			if (creatingNewElement) {
				// Finalize
				((HotSpot) newElement).createHandles();

				// Add the hotspot to the layer
				currentTrafficLayer.AddTrafficElement(newElement);

				// Reset helper variables
				newElement = null;
				creatingNewElement = false;

				repaint();
			}
			// If this is the first click
			else {
				// Create a temporary hotspot and set its center
				newElement = new HotSpot(transformedPoint);

				creatingNewElement = true;
			}

			return;
		case AreaFlow:
			// If we are in the middle of creating a new area flow and the user
			// right clicks then cancel the creation
			if ((creatingNewElement) && (e.getButton() == MouseEvent.BUTTON3)) {
				creatingNewElement = false;
				newElement = null;
				repaint();

				return;
			}

			// If we are in the middle of creating an area flow
			if (creatingNewElement) {
				AreaFlow newFlow = (AreaFlow) newElement;

				newFlow.constructionPhase++;

				// If the construction has finished (4 clicks needed)
				if (newFlow.constructionPhase == 3) {
					newFlow.createHandles();

					currentTrafficLayer.AddTrafficElement(newElement);

					// Reset helper variables
					creatingNewElement = false;
					newElement = null;

					repaint();
				}
			}
			// First click - construction beginning
			else {
				// Create temporary area flow and set its starting location
				newElement = new AreaFlow();
				((AreaFlow) newElement).setStartAreaCenter(transformedPoint);

				((AreaFlow) newElement).constructionPhase = 0;

				creatingNewElement = true;
			}

			return;
		case Flow:
			// Get the map element below the mouse
			RoadNetworkElement elementBelowMouse = roadNetwork.PointHitTest(transformedPoint);

			// If we are in the middle of creating a new flow and the user right
			// clicks or clicks somewhere that is not an edge cancel the
			// operation
			if ((creatingNewElement) && ((e.getButton() == MouseEvent.BUTTON3) || (elementBelowMouse == null) || !(elementBelowMouse instanceof Edge))) {
				creatingNewElement = false;
				newElement = null;
				repaint();

				return;
			}

			if ((elementBelowMouse != null) && (elementBelowMouse instanceof Edge)) {
				// Set the flow's ending point and add it to the traffic layer
				if (creatingNewElement) {
					((Flow) newElement).setTo(new Point2D.Double(elementBelowMouse.getBounds().getCenterX(), elementBelowMouse.getBounds().getCenterY()));

					((Flow) newElement).setEnd((Edge) elementBelowMouse);

					((Flow) newElement).SetDependencies();

					currentTrafficLayer.AddTrafficElement(newElement);

					creatingNewElement = false;
					newElement = null;

					repaint();
				}
				// Start creating the flow
				else {
					newElement = new Flow();
					((Flow) newElement).setStart((Edge) elementBelowMouse);
					((Flow) newElement).setFrom(new Point2D.Float((float) elementBelowMouse.getBounds().getCenterX(), (float) elementBelowMouse.getBounds().getCenterY()));

					creatingNewElement = true;
				}
			}

			return;
		case Accident:
			// Get the map element below the mouse
			RoadNetworkElement elementBelowMouse2 = roadNetwork.PointHitTest(transformedPoint);

			if ((elementBelowMouse2 != null) && (elementBelowMouse2 instanceof Edge) && (e.getButton() == MouseEvent.BUTTON1)) {
				currentTrafficLayer.AddTrafficElement(new Accident((Edge) elementBelowMouse2, transformedPoint));
			}

			repaint();

			break;
		case Select:
			switch (editingMode) {
			case Map:
				if (e.isControlDown()) {
					roadNetwork.AlterSelection(new Rectangle((int) Math.round(transformedPoint.x), (int) Math.round(transformedPoint.y), 1, 1), SelectionType.Add);
				} else if (e.isShiftDown()) {
					roadNetwork.AlterSelection(new Rectangle((int) Math.round(transformedPoint.x), (int) Math.round(transformedPoint.y), 1, 1), SelectionType.Remove);
				} else {
					roadNetwork.AlterSelection(new Rectangle((int) Math.round(transformedPoint.x), (int) Math.round(transformedPoint.y), 1, 1), selectionType);
				}

				repaint();

				break;
			case GenericTraffic:
				if (e.isControlDown()) {
					currentTrafficLayer.AlterSelection(new Rectangle((int) Math.round(transformedPoint.x), (int) Math.round(transformedPoint.y), 1, 1), SelectionType.Add);
				} else if (e.isShiftDown()) {
					currentTrafficLayer.AlterSelection(new Rectangle((int) Math.round(transformedPoint.x), (int) Math.round(transformedPoint.y), 1, 1), SelectionType.Remove);
				} else {
					currentTrafficLayer.AlterSelection(new Rectangle((int) Math.round(transformedPoint.x), (int) Math.round(transformedPoint.y), 1, 1), selectionType);
				}

				repaint();

				break;
			}
			break;
		case Zoom:
			// Zoom out
			if (e.isControlDown()) {
				// If the map fits in the display then disable further zoom out
				if ((getWidth() > roadNetwork.getBounds().width * scale) && (getHeight() > roadNetwork.getBounds().height * scale)) {
					return;
				} else {
					scale -= 0.5;
				}
			}
			// Zoom in
			else {
				scale += 0.5;
			}

			// Move the previous center of the map to the center of the display
			MoveMapPointToDisplayCenter(transformedPoint);

			UpdateScrollBars();

			repaint();

			break;
		}
	}

	public void mouseDragged(MouseEvent e) {
		if (!projectLoaded) {
			return;
		}

		// Transform from device coordinates to map coordinates
		Point2D.Double transformedPoint = TransformPoint(e.getPoint());

		if (transformedPoint == null) {
			return;
		}

		switch (tool) {
		case Pan:
			// Update the translation variables
			translateX += e.getX() - previousMouseLocation.getX();
			translateY += e.getY() - previousMouseLocation.getY();

			// Save the new mouse position
			previousMouseLocation = e.getPoint();

			UpdateScrollBars();

			repaint();

			break;
		case Zoom:
			// Marquee zooming
			areaZoom = true;

			// Update the marquee
			marqueeEnd = e.getPoint();

			marquee.setFrameFromDiagonal(marqueeStart, marqueeEnd);

			repaint();

			break;
		case Select:
			// If we are moving a handle then change the handle's location
			if (manipulatingHandle) {
				currentHandle.setLocationAndNotify(transformedPoint);
			}
			// If we are creating a marquee
			else {
				areaZoom = true;

				// Update the marquee
				marqueeEnd = e.getPoint();

				marquee.setFrameFromDiagonal(marqueeStart, marqueeEnd);
			}
			repaint();

			break;
		case Area:
			// If we are creating a traffic area then update its path
			areaPath.lineTo((float) transformedPoint.x, (float) transformedPoint.y);

			repaint();

			break;
		}

	}

	/**
	 * Set the focus on the display so that we can catch key events
	 */
	public void mouseEntered(MouseEvent e) {
		setFocusable(true);
		requestFocus(true);
	}

	public void mouseExited(MouseEvent e) {
		manipulatingHandle = false;
	}

	public void mouseMoved(MouseEvent e) {
		if (!projectLoaded) {
			return;
		}

		// Transform the mouse's location to map coordinates
		Point2D.Double transformedPoint = TransformPoint(e.getPoint());

		if (transformedPoint == null) {
			return;
		}

		// Find if an element is below the mouse location to show its tooltip
		TrafficDefinitionElement trafficElementBelowMouse = null;
		RoadNetworkElement roadElementBelowMouse = roadNetwork.PointHitTest(transformedPoint);

		if (currentTrafficLayer != null) {
			trafficElementBelowMouse = currentTrafficLayer.PointHitTest(transformedPoint);
		}

		// Set the tooltip of the element below the mouse
		if (trafficElementBelowMouse != null) {
			setToolTipText(trafficElementBelowMouse.getToolTip());
		} else if (roadElementBelowMouse != null) {
			setToolTipText(roadElementBelowMouse.getToolTip());
		} else {
			setToolTipText(null);
		}

		switch (tool) {
		case Select:
			// Check to see if the mouse is over a selected element's handle
			currentHandle = null;

			boolean overHandle = false;

			if (currentTrafficLayer != null) {
				List<TrafficDefinitionElement> selectedTrafficElements = currentTrafficLayer.getSelectedElements();

				for (TrafficDefinitionElement t : selectedTrafficElements) {
					currentHandle = t.handleHit(e.getPoint(), tx);

					if (currentHandle != null) {
						overHandle = true;
						break;
					}
				}
			}

			// If it is, update its cursor
			if (overHandle) {
				this.setCursor(CursorProvider.getCursor(CursorProvider.Cursors.Hand));
			} else {
				this.setCursor(CursorProvider.getCursor(CursorProvider.Cursors.Select));
			}
			break;
		case Hotspot:
			// Set the hotspot's radius
			if (creatingNewElement) {
				HotSpot newHotSpot = (HotSpot) newElement;

				newHotSpot.setRadius(transformedPoint);

				repaint();
			}

			break;
		case AreaFlow:
			// Set the flow's properties based on the construction phase
			if (creatingNewElement) {
				AreaFlow newFlow = (AreaFlow) newElement;

				switch (newFlow.constructionPhase) {
				case 0:
					newFlow.setStartAreaRadius(transformedPoint);
					break;
				case 1:
					newFlow.setEndAreaCenter(transformedPoint);
					break;
				case 2:
					newFlow.setEndAreaRadius(transformedPoint);
					break;
				}

				repaint();
			}

			break;
		case Flow:
			// Update the cursor if the mouse is over an edge
			if ((roadElementBelowMouse != null) && (roadElementBelowMouse instanceof Edge)) {
				setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			} else {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}

			if (!creatingNewElement) {
				return;
			}

			// Show flow while creating it
			((Flow) newElement).setTo(transformedPoint);

			repaint();

			break;
		case Accident:
			// Update the cursor if the mouse is over an edge
			if ((roadElementBelowMouse != null) && (roadElementBelowMouse instanceof Edge)) {
				setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			} else {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}

			break;
		}
	}

	public void mousePressed(MouseEvent e) {
		if (!projectLoaded) {
			return;
		}

		// Capture starting point
		previousMouseLocation = e.getPoint();

		// Transform the point to map coordinates
		Point2D.Double transformedPoint = TransformPoint(e.getPoint());

		if (transformedPoint == null) {
			return;
		}

		switch (tool) {
		case Select:

			// If the mouse was over a handle then start manipulating it
			if (currentHandle != null) {
				this.setCursor(CursorProvider.getCursor(CursorProvider.Cursors.HandClosed));

				manipulatingHandle = true;
			} else {
				// If the user is making a new selection first deselect all
				// previous elements
				if ((selectionType == SelectionType.New) && (!e.isShiftDown()) && (!e.isControlDown())) {
					switch (editingMode) {
					case Map:
						roadNetwork.SelectNoneElements();
						break;
					case GenericTraffic:
						currentTrafficLayer.SelectNoneElements();
						break;
					}
				}

				areaZoom = false;

				// Create the marquee rectangle
				marquee = new Rectangle();
				marqueeStart = e.getPoint();

				manipulatingHandle = false;
			}
			break;
		case Zoom:
			areaZoom = false;

			// Create the marquee rectangle
			marquee = new Rectangle();
			marqueeStart = e.getPoint();

			break;
		case Pan:
			// Update cursor
			setCursor(CursorProvider.getCursor(CursorProvider.Cursors.HandClosed));

			break;
		case Area:
			areaPath = new GeneralPath();

			areaPath.moveTo((float) transformedPoint.x, (float) transformedPoint.y);

			break;
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (!projectLoaded) {
			return;
		}

		switch (tool) {
		case Pan:
			// Restore cursor
			setCursor(CursorProvider.getCursor(CursorProvider.Cursors.Hand));
			break;
		case Zoom:
			if ((!areaZoom) || (marquee.height * marquee.width < 10)) {
				return;
			}

			// Transform the marquee to map coordinates
			Point2D.Double transformedMarqueeStart = TransformPoint(marqueeStart);
			Point2D.Double transformedMarqueeEnd = TransformPoint(marqueeEnd);

			if ((transformedMarqueeStart == null) || (transformedMarqueeEnd == null)) {
				return;
			}

			Rectangle transformedRectangle = new Rectangle();
			transformedRectangle.setFrameFromDiagonal(transformedMarqueeStart, transformedMarqueeEnd);

			// Zoom the map to the rectangle specified by the marquee
			ZoomToRectangleAndCenter(transformedRectangle);

			UpdateScrollBars();

			marquee = null;

			repaint();

			break;
		case Select:
			// Release the handle if we were manipulating it
			if (manipulatingHandle) {
				manipulatingHandle = false;
			} else {
				if ((!areaZoom) || (marquee.height * marquee.width < 10)) {
					return;
				}

				// Transform the marquee to map coordinates
				Point2D.Double transformedMarqueeStart1 = TransformPoint(marqueeStart);
				Point2D.Double transformedMarqueeEnd1 = TransformPoint(marqueeEnd);

				if ((transformedMarqueeStart1 == null) || (transformedMarqueeEnd1 == null)) {
					return;
				}

				Rectangle transformedRectangle1 = new Rectangle();
				transformedRectangle1.setFrameFromDiagonal(transformedMarqueeStart1, transformedMarqueeEnd1);

				// Select the current layer's elements based on the selection
				// type and the marquee
				switch (editingMode) {
				case Map:
					if (e.isControlDown()) {
						roadNetwork.AlterSelection(transformedRectangle1, SelectionType.Add);
					} else if (e.isShiftDown()) {
						roadNetwork.AlterSelection(transformedRectangle1, SelectionType.Remove);
					} else {
						roadNetwork.AlterSelection(transformedRectangle1, selectionType);
					}

					repaint();

					break;
				case GenericTraffic:
					if (e.isControlDown()) {
						currentTrafficLayer.AlterSelection(transformedRectangle1, SelectionType.Add);
					} else if (e.isShiftDown()) {
						currentTrafficLayer.AlterSelection(transformedRectangle1, SelectionType.Remove);
					} else {
						currentTrafficLayer.AlterSelection(transformedRectangle1, selectionType);
					}

					repaint();

					break;
				}

				marquee = null;

				repaint();
			}

			break;
		case Area:
			// Finalize the area's shape
			areaPath.closePath();

			currentTrafficLayer.AddTrafficElement(new TrafficArea(areaPath));

			areaPath = null;

			repaint();
			break;
		}

	}

	/**
	 * Zooms in or out when the user scrolls the mouse wheel
	 */
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {

			// If we are zooming out
			if (e.getWheelRotation() < 0) {
				// If the map fits in the display then disable further zoom out
				if ((getWidth() > roadNetwork.getBounds().width * scale) && (getHeight() > roadNetwork.getBounds().height * scale)) {
					return;
				}
			}

			Point2D.Double transformedPoint = TransformPoint(e.getPoint());

			if (transformedPoint == null) {
				return;
			}

			// Make it a reasonable amount of zoom
			// 0.01 gives a nice slow transition
			scale += 0.01 * e.getWheelRotation();

			MoveMapPointToDisplayPoint(transformedPoint, e.getPoint());

			UpdateScrollBars();

			repaint();
		}
	}

	/**
	 * Moves the map point (expressed in map coordinates) to the specified
	 * display point (expressed in device coordinates)
	 * 
	 * @param mapPoint
	 * @param displayPoint
	 */
	private void MoveMapPointToDisplayPoint(Point2D.Double mapPoint, Point displayPoint) {
		translateX = displayPoint.x - (mapPoint.x * scale);
		translateY = displayPoint.y + (mapPoint.y * scale);
	}

	/**
	 * Translates the map so that the specified point is at the center of the
	 * display
	 * 
	 * @param mapPoint
	 */
	public void MoveMapPointToDisplayCenter(Point2D.Double mapPoint) {
		MoveMapPointToDisplayPoint(mapPoint, new Point(getWidth() / 2, getHeight() / 2));
	}

	/**
	 * Responsible for the drawing of the component. Draws the map and traffic
	 * layers.
	 */
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D graphics2D = (Graphics2D) g;

		// Get the clip bounds
		Rectangle clip = graphics2D.getClipBounds();

		// If only part of the component needs repainting then repaint the whole
		// display anyway.
		// No mechanism yet for painting only part of the component.
		if ((clip.x != 0) || (clip.y != 0)) {
			repaint();
			return;
		}

		// Draw gray background
		graphics2D.setColor(Color.GRAY);
		graphics2D.fillRect(0, 0, getWidth(), getHeight());

		// If no project is loaded don't continue
		if (!projectLoaded) {
			return;
		}

		// Set transformation matrix (for pan and zoom)
		tx.setToIdentity();
		tx.translate(translateX, translateY);

		// Minus is for y-axis reversal
		tx.scale(scale, -scale);

		graphics2D.setTransform(tx);

		// Set rendering hints
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Draw white page
		graphics2D.setColor(Color.WHITE);
		graphics2D.fill(roadNetwork.getBounds());

		// Draw page border
		graphics2D.setColor(Color.BLACK);
		graphics2D.draw(roadNetwork.getBounds());

		// Draw the road network
		roadNetwork.Draw(graphics2D, scale);

		if(drawAllTrafficLayers){
			// Draw all the project traffic definition layers
			for (TrafficDefinitionLayer tdl : Project.getCurrentlyLoadedProject().getTrafficLayers()) {
				tdl.Draw(graphics2D, scale);
			}
		}
		else{
			 //Draw the current traffic definition layer
			 if (currentTrafficLayer != null) {
				 currentTrafficLayer.Draw(graphics2D, scale);
			 }
		}
		
		// Draw the currently creating new element
		if (newElement != null) {
			graphics2D.setColor(Color.RED);
			newElement.Draw(graphics2D, scale, false, false);
		}

		// Draw area
		if (areaPath != null) {
			graphics2D.setColor(new Color(120, 65, 87, 125));
			graphics2D.fill(areaPath);
		}

		// Draw marquee
		if (marquee != null) {
			graphics2D.setTransform(new AffineTransform());
			graphics2D.setColor(Color.RED);
			graphics2D.draw(marquee);
		}
	}

	/**
	 * Handler for the event of the project change.
	 * 
	 * @param project
	 *            the newly loaded project or null if the project was closed
	 */
	public void ProjectChanged(Project project) {
		// If the new project is null it means that the project was closed.
		if (project == null) {
			projectLoaded = false;

			roadNetwork = null;
			currentTrafficLayer = null;

			repaint();
		}
		// Else it means the project was opened or created
		else {
			projectLoaded = true;

			roadNetwork = project.getRoadNetwork();

			ZoomToFitAndCenter();

			UpdateScrollBars();

			setEditingMode(EditingMode.Map);
		}
	}

	/**
	 * Repaints the display
	 * 
	 */

	// Refreshes the display
	public void Refresh() {
		repaint();
	}

	/**
	 * Selects all elements on the currently editing map or traffic layer
	 * 
	 */
	public void SelectAll() {
		switch (editingMode) {
		case Map:
			roadNetwork.SelectAllElements();
			break;
		case GenericTraffic:
			currentTrafficLayer.SelectAllElements();
		}

		repaint();
	}

	/**
	 * Inverts the selection on the currently editing map or traffic layer
	 * 
	 */
	public void SelectInverse() {
		switch (editingMode) {
		case Map:
			roadNetwork.SelectInverseElements();
			break;
		case GenericTraffic:
			currentTrafficLayer.SelectInverseElements();
			break;
		}

		repaint();
	}

	/**
	 * Deselects all elements on the currently editing map or traffic layer
	 * 
	 */
	public void SelectNone() {
		switch (editingMode) {
		case Map:
			roadNetwork.SelectNoneElements();
			break;
		case GenericTraffic:
			currentTrafficLayer.SelectNoneElements();
			break;
		}

		repaint();

	}

	/**
	 * Sets the editing mode (Map or Traffic)
	 * @param  newMode
	 * @uml.property  name="editingMode"
	 */
	public void setEditingMode(EditingMode newMode) {
		editingMode = newMode;
	}

	/**
	 * Sets the type of selection (New, Add or Remove)
	 * @param  selectionType
	 * @uml.property  name="selectionType"
	 */
	public void setSelectionType(SelectionType selectionType) {
		this.selectionType = selectionType;
	}

	/**
	 * Shows or hides the map's deleted elements
	 * 
	 * @param b
	 */
	public void setShowDeleted(boolean b) {
		if (!projectLoaded) {
			return;
		}

		roadNetwork.setShowDeleted(b);

		repaint();
	}

	/**
	 * Sets the drawAllTrafficLayers flag
	 * @param  b
	 * @uml.property  name="drawAllTrafficLayers"
	 */
	public void setDrawAllTrafficLayers(boolean b){
		drawAllTrafficLayers = b;
	}
	
	/**
	 * Sets the current tool
	 * @param  tool
	 * @uml.property  name="tool"
	 */
	public void setTool(ToolType tool) {
		this.tool = tool;

		// Set the cursor based on the tool
		switch (tool) {
		case Pan:
			setCursor(CursorProvider.getCursor(CursorProvider.Cursors.Hand));
			break;
		case Zoom:
			setCursor(CursorProvider.getCursor(CursorProvider.Cursors.ZoomIn));
			break;
		case Select:
			setCursor(CursorProvider.getCursor(CursorProvider.Cursors.Select));
			break;
		default:
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			break;
		}
	}

	/**
	 * Undeletes the selected deleted elements of the map
	 * 
	 */
	public void UndeleteSelectedObjects() {
		roadNetwork.UndeleteSelectedMapElements();

		repaint();
	}

	/**
	 * Centers the map on the display and zooms so that the whole map fits in
	 * the display.
	 * 
	 */
	public void ZoomToFitAndCenter() {
		if (!projectLoaded) {
			return;
		}

		ZoomToRectangleAndCenter(roadNetwork.getBounds());

		UpdateScrollBars();

		repaint();
	}

	/**
	 * Transforms the specified point from device coordinates to map coordinates
	 * 
	 * @param p
	 *            the point to transform
	 * @return the transformed point
	 */
	private Point2D.Double TransformPoint(Point p) {
		try {
			return (Point2D.Double) tx.inverseTransform(new Point2D.Double(p.getX(), p.getY()), null);
		} catch (NoninvertibleTransformException e) {
			return null;
		}
	}

	/**
	 * Updates the scrollbars based on the scaling and translation variables
	 * 
	 */
	private void UpdateScrollBars() {
		mintranslateX = (getWidth() / 2) - (roadNetwork.getBounds().getMinX() * scale);
		maxtranslateX = (getWidth() / 2) - (roadNetwork.getBounds().getMaxX() * scale);

		mintranslateY = (getHeight() / 2) + (roadNetwork.getBounds().getMinY() * scale);
		maxtranslateY = (getHeight() / 2) + (roadNetwork.getBounds().getMaxY() * scale);

		scrlVertical.removeAdjustmentListener(this);

		scrlVertical.setMinimum((int) Math.min(mintranslateY, maxtranslateY));
		scrlVertical.setMaximum((int) Math.max(mintranslateY, maxtranslateY));

		scrlVertical.setValue((int) -translateY + (int) Math.max(maxtranslateY, mintranslateY) + (int) Math.min(maxtranslateY, mintranslateY));

		scrlVertical.addAdjustmentListener(this);

		scrlHorizontal.removeAdjustmentListener(this);

		scrlHorizontal.setMinimum((int) Math.min(mintranslateX, maxtranslateX));
		scrlHorizontal.setMaximum((int) Math.max(mintranslateX, maxtranslateX));

		scrlHorizontal.setValue((int) -translateX + (int) Math.max(maxtranslateX, mintranslateX) + (int) Math.min(maxtranslateX, mintranslateX));

		scrlHorizontal.addAdjustmentListener(this);
	}

	/**
	 * Zooms the map so that it fits in the specified rectangle and centers it
	 * on the display
	 * 
	 * @param rectangle
	 */
	private void ZoomToRectangleAndCenter(RectangularShape rectangle) {
		// Fit in height
		if (rectangle.getHeight() > rectangle.getWidth()) {
			scale = (getHeight()) / rectangle.getHeight();
		}
		// Fit in width
		else if (rectangle.getHeight() < rectangle.getWidth()) {
			scale = (getWidth()) / rectangle.getWidth();
		}
		// If the map is square then fit it based on the display's minimum
		// dimension
		else {
			int minDimension = Math.min(getWidth(), getHeight());

			scale = (minDimension) / rectangle.getWidth();
		}

		// Move map to the center of the display
		MoveMapPointToDisplayCenter(new Point2D.Double(rectangle.getCenterX(), rectangle.getCenterY()));
	}

	/**
	 * Zooms and centers on the specified element. This method is called when
	 * the user selects an element from the property panel combo box
	 * 
	 * @param element
	 */
	public void CenterAndZoomOnElement(ProjectElement element) {
		// Move map to the center of the display
		MoveMapPointToDisplayCenter(new Point2D.Double(element.getBounds().getCenterX(), element.getBounds().getCenterY()));

		// TODO: Zooming correctly
		// ZoomToRectangleAndCenter(element.getBounds());

		UpdateScrollBars();

		repaint();
	}
}
