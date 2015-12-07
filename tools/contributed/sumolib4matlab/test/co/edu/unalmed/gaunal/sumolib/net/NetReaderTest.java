/*
 * Copyright 2015 Universidad Nacional de Colombia,
 * Politecnico Jaime Isaza Cadavid.
 * Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
 * $Id$
 */

package co.edu.unalmed.gaunal.sumolib.net;

import com.sun.org.apache.xml.internal.utils.AttList;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.Attributes2;
import org.xml.sax.helpers.AttributesImpl;

/**
 *
 * @author GaunalJD
 */
public class NetReaderTest {
    
    public NetReaderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of startElement method, of class NetReader.
     */
    @Test
    public void testStartElement() {
        System.out.println("startElement");
        String namespaceURI = "";
        String localNameLocation = "location";
        String localNameEdge = "edge";
        String localNameLane = "lane";
        String localNameJunction = "junction";
        String localNameConnection = "connection";
        String localNameRequest = "request";
        String localNamePhase = "phase";
        String localNameRoundabout = "roundabout";

        String qName = "";

        AttributesImpl attsLocation = new AttributesImpl();
        attsLocation.addAttribute(null, null, "netOffset", null,
        "452.00,452.00");
        attsLocation.addAttribute(null, null, "convBoundary", null,
        "0.00,0.00,904.00,904.00");
        attsLocation.addAttribute(null, null, "origBoundary", null,
        "-452.00,-452.00,452.00,452.00");
        attsLocation.addAttribute(null, null, "projParameter", null,
        "!");

        AttributesImpl attsEdgeInternal = new AttributesImpl();
        attsEdgeInternal.addAttribute(null, null, "id", null,
        ":a_0");
        attsEdgeInternal.addAttribute(null, null, "function", null,
        "internal");
        
        AttributesImpl attsLaneInternal = new AttributesImpl();
        attsLaneInternal.addAttribute(null, null, "id", null,
        ":a_0_0");
        attsLaneInternal.addAttribute(null, null, "index", null,
        "0");
        attsLaneInternal.addAttribute(null, null, "speed", null,
        "50.00");
        attsLaneInternal.addAttribute(null, null, "length", null,
        "5.00");
        attsLaneInternal.addAttribute(null, null, "shape", null,
        "443.75,463.35 443.56,461.99 442.98,461.02 442.01,460.44 440.65,460.25");

        AttributesImpl attsEdgeFrom = new AttributesImpl();
        attsEdgeFrom.addAttribute(null, null, "id", null,
        "O1a");
        attsEdgeFrom.addAttribute(null, null, "from", null,
        "O1");
        attsEdgeFrom.addAttribute(null, null, "to", null,
        "a");
        attsEdgeFrom.addAttribute(null, null, "priority", null,
        "1");
        attsEdgeFrom.addAttribute(null, null, "type", null,
        "calleTipo1");
        
        AttributesImpl attsLaneFrom = new AttributesImpl();
        attsLaneFrom.addAttribute(null, null, "id", null,
        "O1a_0");
        attsLaneFrom.addAttribute(null, null, "index", null,
        "0");
        attsLaneFrom.addAttribute(null, null, "speed", null,
        "50.00");
        attsLaneFrom.addAttribute(null, null, "length", null,
        "440.65");
        attsLaneFrom.addAttribute(null, null, "shape", null,
        "443.75,904.00 443.75,463.35");
        
        AttributesImpl attsEdgeTo = new AttributesImpl();
        attsEdgeTo.addAttribute(null, null, "id", null,
        "aO2");
        attsEdgeTo.addAttribute(null, null, "from", null,
        "a");
        attsEdgeTo.addAttribute(null, null, "to", null,
        "O2");
        attsEdgeTo.addAttribute(null, null, "priority", null,
        "1");
        attsEdgeTo.addAttribute(null, null, "type", null,
        "calleTipo1");    
        
        AttributesImpl attsLaneTo = new AttributesImpl();
        attsLaneTo.addAttribute(null, null, "id", null,
        "aO2_0");
        attsLaneTo.addAttribute(null, null, "index", null,
        "0");
        attsLaneTo.addAttribute(null, null, "speed", null,
        "50.00");
        attsLaneTo.addAttribute(null, null, "length", null,
        "440.65");
        attsLaneTo.addAttribute(null, null, "shape", null,
        "440.65,460.25 0.00,460.25");

        AttributesImpl attsJunction = new AttributesImpl();
        attsJunction.addAttribute(null, null, "id", null,
        "O1");
        attsJunction.addAttribute(null, null, "type", null,
        "dead_end");
        attsJunction.addAttribute(null, null, "x", null,
        "452.00");
        attsJunction.addAttribute(null, null, "y", null,
        "904.00");
        attsJunction.addAttribute(null, null, "incLanes", null,
        "aO1_0 aO1_1 aO1_2");
        attsJunction.addAttribute(null, null, "intLanes", null,
        "");
        attsJunction.addAttribute(null, null, "shape", null,
        "451.95,904.00 442.15,904.00 461.85,904.00 452.05,904.00");

        AttributesImpl attsRequest = new AttributesImpl();
        attsRequest.addAttribute(null, null, "index", null,
        "0");
        attsRequest.addAttribute(null, null, "response", null,
        "000000000000");
        attsRequest.addAttribute(null, null, "foes", null,
        "000000000000");
        attsRequest.addAttribute(null, null, "cont", null,
        "0");

        AttributesImpl attsConnection = new AttributesImpl();
        attsConnection.addAttribute(null, null, "from", null,
        "O1a");
        attsConnection.addAttribute(null, null, "to", null,
        "aO2");
        attsConnection.addAttribute(null, null, "fromLane", null,
        "0");
        attsConnection.addAttribute(null, null, "toLane", null,
        "0");
        attsConnection.addAttribute(null, null, "via", null,
        ":a_0_0");
        attsConnection.addAttribute(null, null, "tl", null,
        "a");
        attsConnection.addAttribute(null, null, "linkIndex", null,
        "0");
        attsConnection.addAttribute(null, null, "dir", null,
        "r");
        attsConnection.addAttribute(null, null, "state", null,
        "o");

        AttributesImpl attsPhase = new AttributesImpl();
        attsPhase.addAttribute(null, null, "duration", null,
        "31");
        attsPhase.addAttribute(null, null, "state", null,
        "GGgGrrGGgGrr");

        AttributesImpl attsRoundabout = new AttributesImpl();
        attsRoundabout.addAttribute(null, null, "nodes", null,
        "322001698 1305076200 322002002 322001698");

        try {
            NetReader instance = new NetReader();
            instance.startElement(null, localNameLocation, null, attsLocation);
            instance.startElement(null, localNameEdge, null, attsEdgeInternal);
            instance.startElement(null,localNameLane, null, attsLaneInternal);
            instance.startElement(null, localNameEdge, null, attsEdgeFrom);
            instance.startElement(null,localNameLane, null, attsLaneFrom);
            instance.startElement(null, localNameEdge, null, attsEdgeTo);
            instance.startElement(null,localNameLane, null, attsLaneTo);
            instance.startElement(null,localNameJunction, null, attsJunction);
            instance.startElement(null,localNameConnection, null, attsConnection);
            instance.startElement(null,localNamePhase, null, attsPhase);
            instance.startElement(null,localNameRoundabout, null, attsRoundabout);
        } catch (ParserConfigurationException /*| SAXException*/ ex) {
            System.exit(1);
        } catch (SAXException ex) {
            Logger.getLogger(NetReaderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // TODO review the generated test code and remove the default call to fail.

    
    /**
     * Test of endElement method, of class NetReader.
     */
    /*    @Test
    public void testEndElement() {
    System.out.println("endElement");
    String uri = "";
    String localName = "";
    String qName = "";
    NetReader instance = new NetReader();
    instance.endElement(uri, localName, qName);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
    }*/

    /**
     * Test of processShape method, of class NetReader.
     */
    /*   @Test
    public void testProcessShape() throws Exception {
    System.out.println("processShape");
    Object object = null;
    String shapeString = "";
    NetReader instance = new NetReader();
    instance.processShape(object, shapeString);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
    }*/

    /**
     * Test of getNet method, of class NetReader.
     */
    /*    @Test
    public void testGetNet() {
    System.out.println("getNet");
    NetReader instance = new NetReader();
    Net expResult = null;
    Net result = instance.getNet();
    assertEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
    }*/

    /**
     * Test of parseGeoData method, of class NetReader.
     */
    /*    @Test
    public void testParseGeoData() {
    System.out.println("parseGeoData");
    String data = "";
    NetReader instance = new NetReader();
    Double[] expResult = null;
    Double[] result = instance.parseGeoData(data);
    assertArrayEquals(expResult, result);
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
    }*/

    /**
     * Test of readNet method, of class NetReader.
     * @throws java.lang.Exception
     */
    @Test
    public void testReadNet() throws Exception {
        // System.out.println("readNet");
        String filename = "C:\\sumo-0.20.0\\docs\\tutorial\\traci_tls\\data\\cross.net.xml";
        /*Net expResult = null;*/
        Net result = NetReader.readNet(filename);
        ArrayList nodes;
        ArrayList edges;
        /*assertEquals(expResult, result);*/
        // TODO review the generated test code and remove the default call to fail.
        nodes = result.getNodes();
        edges = result.getEdges();
        System.out.println("The IDs of the edges in the SUMO network:");
        for(int i=0;i<edges.size();i++){
            Edge currentEdge = (Edge) edges.get(i);
            System.out.print(currentEdge.getId() + ", ");
        }
        System.out.println("\nThe IDs of the nodes in the SUMO network:");
        for(int i=0;i<nodes.size();i++){
            Node currentNode = (Node) nodes.get(i);
            System.out.print(currentNode.getId() + ", ");
        }
    }
    
}
