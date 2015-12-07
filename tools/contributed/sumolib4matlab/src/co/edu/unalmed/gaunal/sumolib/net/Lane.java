/*
 * Copyright 2015 Universidad Nacional de Colombia,
 * Politecnico Jaime Isaza Cadavid.
 * Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
 * $Id$
 */

package co.edu.unalmed.gaunal.sumolib.net;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author GaunalJD
 */
class Lane extends Object{
    private Edge edge;
    private Double speed;
    private Double length;
    private ArrayList shape;
    private ArrayList outgoing;
    private HashMap params;

    public Lane(Edge edge, Double speed, Double length) {
        this.edge = edge;
        this.speed = speed;
        this.length = length;
        this.shape = new ArrayList();
        this.outgoing = new ArrayList();
        this.params = new HashMap();
        edge.addLane(this);
    }

    public Double getSpeed() {
        return speed;
    }

    public Double getLength() {
        return length;
    }

    public ArrayList getShape() {
        return shape;
    }

    public void setShape(ArrayList shape) {
        this.shape = shape;
    }
    
    public String getID(){
        String id = this.edge.getId() + "_" +
                String.valueOf(this.edge.getLanes().indexOf(this));
        return id;
    }

    public HashMap getParams() {
        return params;
    }
    
}
