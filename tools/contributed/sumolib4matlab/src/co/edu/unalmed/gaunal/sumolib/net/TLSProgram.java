/*
 * Copyright 2015 Universidad Nacional de Colombia,
 * Politecnico Jaime Isaza Cadavid.
 * Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
 * $Id$
 */

package co.edu.unalmed.gaunal.sumolib.net;

import java.util.ArrayList;

/**
 *
 * @author GaunalJD
 */
public class TLSProgram {
    private String id;
    private Double offset;
    private String type;
    private ArrayList<ArrayList> phases;

    public TLSProgram(String id, Double offset, String type) {
        this.id = id;
        this.offset = offset;
        this.type = type;
        this.phases = new ArrayList();
    }
    
    public void addPhase(String state, int duration){
        ArrayList phase = new ArrayList();
        phase.add(state);
        phase.add(duration);
        this.phases.add(phase);
    }
    
    /* public String toXML(tlsID){
        String ret = "\t "; 
    } */
    
}
