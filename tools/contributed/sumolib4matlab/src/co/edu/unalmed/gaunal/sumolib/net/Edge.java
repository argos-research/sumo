/*
 * Copyright 2015 Universidad Nacional de Colombia,
 * Politecnico Jaime Isaza Cadavid.
 * Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
 * $Id$
 */

package co.edu.unalmed.gaunal.sumolib.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author GaunalJD
 */
class Edge{
    
    private String id;
    private Node from;
    private Node to;
    private Integer prio;
    private ArrayList lanes;
    private Double speed;
    private Object length;
    private HashMap<Edge,ArrayList> incoming;
    private HashMap<Edge,ArrayList> outgoing;
    private Object shape;
    private Object cachedShapeWithJunctions;
    private String function;
    private TLS tls;
    private String name;
    
    public Edge(String id, Node fromN, Node toN, Integer prio,
            String function, String name) {
        this.id = id;
        this.from = fromN;
        this.to = toN;
        this.prio = prio;
        fromN.addOutgoing(this);
        toN.addIncoming(this);
        this.lanes = new ArrayList();
        this.speed = null;
        this.length = null;
        this.incoming = new HashMap();
        this.outgoing = new HashMap();
        this.shape = null;
        this.cachedShapeWithJunctions = null;
        this.function = function;
        this.tls = null;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public ArrayList getLanes() {
        return lanes;
    }
    
    public String getName(){
        return this.name;
    }
    
    public Object getTLS(){
        return this.tls;
    }
    
    public void addLane(Lane lane){
        this.lanes.add(lane);
        this.speed = lane.getSpeed();
        this.length = lane.getLength();
    }
    
    public void addOutgoing(Connection conn){
        if (!this.outgoing.containsKey(conn.getTo())){
            this.outgoing.put(conn.getTo(), new ArrayList());   
        }
        this.outgoing.get(conn.getTo()).add(conn);
    }
    
    public void addIncoming(Connection conn){
        if (!this.incoming.containsKey(conn.getFrom())){
            this.incoming.put(conn.getFrom(), new ArrayList());   
        }
        this.incoming.get(conn.getFrom()).add(conn);
    }
    
    public void setShape(ArrayList shape){
        this.shape = shape;
        this.cachedShapeWithJunctions = null;
    }
    
    public Lane getLane(int idx){
        return (Lane) this.lanes.get(idx);
    }
    
    public void rebuildShape(){
        int noShapes = this.lanes.size();
        if (noShapes%2 == 1){
            Lane theLane = (Lane) this.lanes.get((int) noShapes/2);
            this.setShape(theLane.getShape());
        } else{
            ArrayList shape = new ArrayList();
            int minLen = -1;
            for (Iterator it = this.lanes.iterator(); it.hasNext();) {
                Lane l = (Lane) it.next();
                if (minLen==-1 || minLen > l.getShape().size()){
                    minLen = l.getShape().size();
                }
            }
            for (int i=0; i<minLen; i++){
                Double x = 0.0;
                Double y = 0.0;
                for (int j=0; j<this.lanes.size(); j++){
                    Lane theLane = (Lane) this.lanes.get(j);
                    Double[] theNode = (Double[]) theLane.getShape().get(i);
                    x = x + theNode[0];
                    y = y + theNode[1];
                }
                x = x / this.lanes.size();
                y = y / this.lanes.size();
                shape.add(new Double[] {x, y});
            }
            this.setShape(shape);
        }
    }

    public void setTls(TLS tls) {
        this.tls = tls;
    }

    public Object getShape() {
        return shape;
    }
    
}
