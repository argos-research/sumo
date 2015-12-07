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
public class Node {

    private String id;
    private String type;
    private Double[] coord;
    private ArrayList incoming;
    private ArrayList outgoing;
    private HashMap foes;
    private HashMap prohibits;
    private String[] incLanes;
    
    public Node(String id, String type, Double[] coord, String[] incLanes) {
        this.id = id;
        this.type = type;
        this.coord = coord;
        this.incoming = new ArrayList();
        this.outgoing = new ArrayList();
        this.foes = new HashMap();
        this.prohibits = new HashMap();
        this.incLanes = incLanes;
    }

    public String getId() {
        return id;
    }
    
    public void addOutgoing(Edge edge){
        this.outgoing.add(edge);
    }
    
    public ArrayList getOutgoing(){
        return this.outgoing;
    }
    
    public void addIncoming(Edge edge){
        this.incoming.add(edge);
    }
    
    public void setFoes(int index, String foes, String prohibits){
        this.foes.put(index, foes);
        this.prohibits.put(index, prohibits);
    }
    
    public Double[] getCoord() {
        return coord;
    }
    
    public void setCoord(Double[] coord) {
        this.coord = coord;
    }
    
    public String[] getIncLanes() {
        return incLanes;
    }
    
    public void setIncLanes(String[] incLanes) {
        this.incLanes = incLanes;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
          
}
