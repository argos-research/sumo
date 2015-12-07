/*
 * Copyright 2015 Universidad Nacional de Colombia,
 * Politecnico Jaime Isaza Cadavid.
 * Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
 * $Id$
 */

package co.edu.unalmed.gaunal.sumolib.net;

/**
 *
 * @author GaunalJD
 */
public class Connection {
    private Edge from;
    private Edge to;
    private Lane fromLane;
    private Lane toLane;
    private String tls;
    private int tllink;

    public Connection(Edge fromEdge, Edge toEdge, Lane fromLane, Lane toLane,
            String direction, String tls, int tllink) {
        this.from = fromEdge;
        this.to = toEdge;
        this.fromLane = fromLane;
        this.toLane = toLane;
        this.tls = tls;
        this.tllink = tllink;
    }

    public Edge getFrom() {
        return from;
    }

    public Edge getTo() {
        return to;
    }
    
}
