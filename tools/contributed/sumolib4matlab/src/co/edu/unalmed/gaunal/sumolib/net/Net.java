/*
 * Copyright 2015 Universidad Nacional de Colombia,
 * Politecnico Jaime Isaza Cadavid.
 * Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
 * $Id$
 */

package co.edu.unalmed.gaunal.sumolib.net;

import java.util.ArrayList;
import java.util.HashMap;
import com.infomatiq.jsi.rtree.RTree;

/**
 *
 * @author GaunalJD
 */
public class Net {

    private HashMap location;
    private HashMap id2node;
    private HashMap id2edge;
    private HashMap id2tls;
    private ArrayList nodes;
    private ArrayList edges;
    private ArrayList tlss;
    private Double[][] ranges = {{10000.0, -10000.0},{10000.0, -10000.0}}; 
    private ArrayList roundabouts;
    private RTree rtree;
    
    public Net() {
        this.location = new HashMap();
        this.id2node = new HashMap();
        this.id2edge = new HashMap();
        this.id2tls = new HashMap();
        this.nodes = new ArrayList();
        this.edges = new ArrayList();
        this.tlss = new ArrayList();
        this.roundabouts = new ArrayList();
        this.rtree = new RTree();
    }

    public void setLocation(String netOffset,
            String convBoundary,
            String origBoundary,
            String projParameter) {
        this.location.put("netOffset", netOffset);
        this.location.put("convBoundary", convBoundary);
        this.location.put("origBoundary", origBoundary);
        this.location.put("projParameter", projParameter);
    }
    
    public Node addNode(String id){
        String type = null;
        Double[] coord = null;
        String[] incLanes = null;
        if (!this.id2node.containsKey(id)){
            Node n = new Node(id, type, coord, incLanes);
            this.nodes.add(n);
            this.id2node.put(id, n);
        }
        this.setAdditionalNodeInfo((Node)this.id2node.get(id),
                type, coord, incLanes);
        return (Node)this.id2node.get(id);
    }
    
    public Node addNode(String id, String type){
        Double[] coord = null;
        String[] incLanes = null;
        if (!this.id2node.containsKey(id)){
            Node n = new Node(id, type, coord, incLanes);
            this.nodes.add(n);
            this.id2node.put(id, n);
        }
        this.setAdditionalNodeInfo((Node)this.id2node.get(id),
                type, coord, incLanes);
        return (Node)this.id2node.get(id);
    }
    
    public Node addNode(String id, String type, Double[] coord){
        String[] incLanes = null;
        if (!this.id2node.containsKey(id)){
            Node n = new Node(id, type, coord, incLanes);
            this.nodes.add(n);
            this.id2node.put(id, n);
        }
        this.setAdditionalNodeInfo((Node)this.id2node.get(id),
                type, coord, incLanes);
        return (Node)this.id2node.get(id);
    }
    
    public Node addNode(String id, String type, Double[] coord,
                String[] incLanes){
        if (!this.id2node.containsKey(id)){
            Node n = new Node(id, type, coord, incLanes);
            this.nodes.add(n);
            this.id2node.put(id, n);
        }
        this.setAdditionalNodeInfo((Node)this.id2node.get(id),
                type, coord, incLanes);
        return (Node)this.id2node.get(id);
    }
    
    public void setAdditionalNodeInfo(Node node,
            String type,
            Double[] coord,
            String[] incLanes){
        if (coord!=null && node.getCoord() == null){
            node.setCoord(coord);
            this.ranges[0][0] = Math.min(this.ranges[0][0], coord[0]);
            this.ranges[0][1] = Math.max(this.ranges[0][1], coord[0]);
            this.ranges[1][0] = Math.min(this.ranges[1][0], coord[1]);
            this.ranges[1][1] = Math.min(this.ranges[1][1], coord[1]);
        }
        if (incLanes!=null && node.getIncLanes() == null){
            node.setIncLanes(incLanes);
        }
        if (type != null && node.getType()== null){
            node.setType(type);
        }
    }
    
    /**
     *
     * @param id
     * @param fromID
     * @param toID
     * @param prio
     * @param function
     * @param name
     * @return
     */
    public Edge addEdge(String id, String fromID, String toID, Integer prio,
            String function, String name){
        if (!this.id2edge.containsKey(id)){
            Node fromN = this.addNode(fromID);
            Node toN = this.addNode(toID);
            Edge e = new Edge(id, fromN, toN, prio, function, name);
            this.edges.add(e);
            this.id2edge.put(id, e);
        }
        return (Edge)this.id2edge.get(id);
    }
    
    public Lane addLane(Edge edge, Double speed, Double length){
        Lane lane = new Lane(edge, speed, length);
        return lane;
    }
    
    public Roundabout addRoundabout(String[] nodes){
        Roundabout r = new Roundabout(nodes);
        this.roundabouts.add(r);
        return r;
    }
    
    public void addConnection(Edge fromEdge, Edge toEdge, Lane fromlane,
            Lane tolane, String direction, String tls, int tllink){
        Connection conn = new Connection(fromEdge, toEdge, fromlane, tolane,
        direction, tls, tllink);
        fromEdge.addOutgoing(conn);
        toEdge.addIncoming(conn);
    }
    
    public ArrayList getEdges(){
        return this.edges;
    }
    
    public ArrayList getRoundabouts(){
        return this.roundabouts;
    }
    
    public Boolean hasEdge(String id){
        return this.id2edge.containsKey(id);
    }
    
    public Edge getEdge(String id){
        return (Edge)this.id2edge.get(id);
    }
    
    /* initRtree missing */
    
    /* public ArrayList getNeighboringEdges(x, y){
        Double r = 0.1;
        ArrayList edges = new ArrayList();
        
    } */
    
    public Boolean hasNode(String id){
        return this.id2node.containsKey(id);
    }
    
    public Node getNode(String id){
        return (Node)this.id2node.get(id);
    }

    public ArrayList getNodes() {
        return nodes;
    }
    
    public TLS getTLSSecure(String tlid){
        TLS tls;
        if (this.id2tls.containsKey(tlid)){
            tls = (TLS)this.id2tls.get(tlid);
            return tls;
        }else{
            tls = new TLS(tlid);
            this.id2tls.put(tlid, tls);
            this.tlss.add(tls);

        }
        return tls;
    }
    
    public TLS addTLS(String tlid, Lane inLane, Lane outLane, Integer linkNo){
        TLS tls = this.getTLSSecure(tlid);
        tls.addConnection(inLane, outLane, linkNo);
        return tls;
    }
    
    /* public TLSProgram addTLSProgram(String tlid, String programID,
            Double offset, Double Time){
        
    } */
    
}
