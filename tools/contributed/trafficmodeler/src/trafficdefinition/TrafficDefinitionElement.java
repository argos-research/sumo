package trafficdefinition;

import classes.ProjectElement;
import interfaces.DrawableElement;
import interfaces.HandleListener;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

/**
 * Abstract class decribing an element used to define a traffic pattern.
 */
public abstract class TrafficDefinitionElement extends ProjectElement implements Serializable, HandleListener, DrawableElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8283034182313177579L;

	/**
	 * Helper variable used to give new names to new layers
	 */
	protected static int increment = 1;

	/**
	 * Graphical UI handles that can be used by the user to change the element's
	 * location or shape
	 */
	protected Hashtable<String, Handle> handles;

	/**
	 * The element's name. This name is used in the output files.
	 */
	protected String name = null;

	/**
	 * Flag to define whether the polygon of this element will be exported to an
	 * additional file to be loaded in SUMO
	 */
	protected boolean exportPolygon = false;

	/**
	 * Flag to define whether the traffic element will be exported
	 */
	protected boolean enabled = true;

	/**
	 * The traffic definition layer that this element is placed on
	 */
	protected TrafficDefinitionLayer layer = null;
	
	/**
	 * Class constructor
	 */
	public TrafficDefinitionElement() {
		handles = new Hashtable<String, Handle>();
	}

	/**
	 * Creates a new {@link TrafficDefinitionElement} that is a copy of the
	 * specified element
	 * 
	 * @param source
	 *            the element to copy
	 */
	protected TrafficDefinitionElement(TrafficDefinitionElement source) {
		// Copy the handle's name
		name = source.name;

		// Create the handles collection
		handles = new Hashtable<String, Handle>();

		// Copy the handles
		for (Entry<String, Handle> e : source.handles.entrySet()) {
			Handle h = new Handle(e.getValue());
			h.setHandleListener(this);

			handles.put(e.getKey(), h);
		}

		// Copy other properties
		exportPolygon = source.exportPolygon;
	}

	/**
	 * Returns an exact deep copy of this element
	 * 
	 * @return
	 */
	public abstract TrafficDefinitionElement elementClone();

	/* (non-Javadoc)
	 * @see trafficdefinition.DrawableElement#Draw(java.awt.Graphics2D, double, boolean, boolean)
	 */
	@Override
	public void Draw(Graphics2D g, double zoomFactor, boolean isDeleted, boolean isSelected) {
		// If the element is selected draw its handles
		if (isSelected) {
			for (Handle handle : handles.values()) {
				handle.Draw(g);
			}
		}
	}

	/**
	 * @return  the name
	 * @uml.property  name="name"
	 */
	public String getName() {
		return name;
	}

	/**
	 * Checks to see if the mouse is over one of the handles
	 * 
	 * @param p
	 * @param tx
	 * @return The handle hit or null
	 */
	public Handle handleHit(Point p, AffineTransform tx) {
		for (Handle handle : handles.values()) {
			if (handle.hit(p, tx)) {
				return handle;
			}
		}

		return null;
	}

	/**
	 * @param name  the name to set
	 * @uml.property  name="name"
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * Adds the specified handles to the element's handle list and registers the
	 * element as a listener to be notified when the handle's location has
	 * changed
	 * 
	 * @param handles
	 */
	protected void addHandles(List<Handle> handles) {
		for (Handle h : handles) {
			this.handles.put(h.getName(), h);
			h.setHandleListener(this);
		}
	}

	/**
	 * @return  the exportPolygon
	 * @uml.property  name="exportPolygon"
	 */
	public boolean getExportPolygon() {
		return exportPolygon;
	}

	/**
	 * @param exportPolygon  the exportPolygon to set
	 * @uml.property  name="exportPolygon"
	 */
	public void setExportPolygon(boolean e) {
		exportPolygon = e;
	}

	/**
	 * Returns an XML description of the element's geometry. The description has
	 * to be in the format supported by SUMO (poly/poi).
	 * 
	 * @return
	 */
	public String getGeometryXML() {
		return "";
	}

	/**
	 * If the element is enabled then traffic will be generated during the export of the simulation
	 * @uml.property  name="enabled"
	 */
	public boolean isEnabled() {
		return enabled;
	}


	/**
	 * @param enabled  the enabled to set
	 * @uml.property  name="enabled"
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Returns an xml representation of the element
	 * @return
	 */
	public abstract String toXML();

	/**
	 * @return  the layer on which the element is placed
	 * @uml.property  name="layer"
	 */
	public TrafficDefinitionLayer getLayer() {
		return layer;
	}

	/**
	 * @param layer  the layer on which the element is placed
	 * @uml.property  name="layer"
	 */
	public void setLayer(TrafficDefinitionLayer layer) {
		this.layer = layer;
	} 
}
