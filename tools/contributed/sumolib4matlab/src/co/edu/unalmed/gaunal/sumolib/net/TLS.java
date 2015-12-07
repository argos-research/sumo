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
public class TLS {
    private String id;
    private ArrayList<ArrayList> connections;
    private Integer maxConnectionNumber;
    private HashMap programs;

    public TLS(String id) {
        this.id = id;
        this.connections = new ArrayList();
        this.maxConnectionNumber = new Integer(-1);
        this.programs = new HashMap();
    }

    public String getId() {
        return id;
    }

    public ArrayList<ArrayList> getConnections() {
        return connections;
    }

    public Integer getMaxConnectionNumber() {
        return maxConnectionNumber;
    }

    public HashMap getPrograms() {
        return programs;
    }
    
    public void addConnection(Lane inLane, Lane outLane, Integer linkNo){
        ArrayList connection = new ArrayList();
        connection.add(inLane);
        connection.add(outLane);
        connection.add(linkNo);
        this.connections.add(connection);
    }
    
}
