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
public class Roundabout {
    private String[] nodes;

    public Roundabout(String[] nodes) {
        this.nodes = nodes;
    }

    public String[] getNodes() {
        return nodes;
    }
    
}
