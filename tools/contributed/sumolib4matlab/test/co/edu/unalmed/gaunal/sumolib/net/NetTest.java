/*
 * Copyright 2015 Universidad Nacional de Colombia,
 * Politecnico Jaime Isaza Cadavid.
 * Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
 * $Id$
 */

package co.edu.unalmed.gaunal.sumolib.net;

import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author GaunalJD
 */
public class NetTest {
    
    public NetTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of setLocation method, of class Net.
     */
    /* @Test
    public void testSetLocation() {
    System.out.println("setLocation");
    Double[] netOffset = null;
    Double[] convBoundary = null;
    Double[] origBoundary = null;
    char projParameter = ' ';
    Net instance = new Net();
    instance.setLocation(netOffset, convBoundary, origBoundary, projParameter);
    
    }*/

    /**
     * Test of addNode method, of class Net.
     */
    @Test
    public void testAddNode_String() {
        System.out.println("addNode");
        String id = "myNode";
        Net instance = new Net();
        Node expResult = new Node(id, null, null, null);
        Node result = instance.addNode(id);
        assertEquals(expResult.getId(), result.getId());
        Assert.assertArrayEquals(expResult.getCoord(), result.getCoord());
        Assert.assertArrayEquals(expResult.getIncLanes(), result.getIncLanes());
        assertEquals(expResult.getType(), result.getType());
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of addNode method, of class Net.
     */
    @Test
    public void testAddNode_String_String() {
        System.out.println("addNode");
        String id = "myNode";
        String type = "priority";
        Net instance = new Net();
        Node expResult = new Node(id, type, null, null);
        Node result = instance.addNode(id, type);
        assertEquals(expResult.getId(), result.getId());
        Assert.assertArrayEquals(expResult.getCoord(), result.getCoord());
        Assert.assertArrayEquals(expResult.getIncLanes(), result.getIncLanes());
        assertEquals(expResult.getType(), result.getType());
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of addNode method, of class Net.
     */
    @Test
    public void testAddNode_3args() {
        System.out.println("addNode");
        String id = "myNode";
        String type = "priority";
        Double[] coord = new Double[] {1.9,2.0};
        Net instance = new Net();
        Node expResult = new Node(id, type, coord, null);
        Node result = instance.addNode(id, type, coord);
        assertEquals(expResult.getId(), result.getId());
        Assert.assertArrayEquals(expResult.getCoord(), result.getCoord());
        Assert.assertArrayEquals(expResult.getIncLanes(), result.getIncLanes());
        assertEquals(expResult.getType(), result.getType());
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of addNode method, of class Net.
     */
    @Test
    public void testAddNode_4args() {
        System.out.println("addNode");
        String id = "myNode";
        String type = "priority";
        Double[] coord = new Double[] {1.9,2.0};
        String[] incLanes = new String[] {"2","3"};
        Net instance = new Net();
        Node expResult = new Node(id, type, coord, incLanes);
        Node result = instance.addNode(id, type, coord, incLanes);
        assertEquals(expResult.getId(), result.getId());
        Assert.assertArrayEquals(expResult.getCoord(), result.getCoord());
        Assert.assertArrayEquals(expResult.getIncLanes(), result.getIncLanes());
        assertEquals(expResult.getType(), result.getType());
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of setAdditionalNodeInfo method, of class Net.
     */
    @Test
    public void testSetAdditionalNodeInfo() {
        System.out.println("setAdditionalNodeInfo");
        String id = "myNode";
        String type = "priority";
        Double[] coord = new Double[] {1.9,2.0};
        String[] incLanes = new String[] {"2","3"};
        Node node = new Node(id, type, coord, incLanes);
        Net instance = new Net();
        instance.setAdditionalNodeInfo(node, type, coord, incLanes);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of addEdge method, of class Net.
     */
    @Test
    public void testAddEdge() {
        System.out.println("addEdge");
        String id = "myEdge";
        String fromID = "1";
        String toID = "2";
        Integer prio = 1;
        String function = "internal";
        String name = "";
        Net instance = new Net();
        Edge expResult = new Edge(id, instance.addNode(fromID),
                instance.addNode(toID), prio, function, name);
        Edge result = instance.addEdge(id, fromID, toID, prio, function, name);
        assertEquals(expResult.getId(), result.getId());
        assertEquals(expResult.getLanes(), result.getLanes());
        assertEquals(expResult.getName(), result.getName());
        assertEquals(expResult.getShape(), result.getShape());
        assertEquals(expResult.getTLS(), result.getTLS());
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of addLane method, of class Net.
     */
    @Test
    public void testAddLane() {
        System.out.println("addLane");
        String id = "myEdge";
        String fromID = "1";
        String toID = "2";
        Integer prio = 1;
        String function = "internal";
        String name = "";
        Double speed = 60.0;
        Double length = 120.0;
        Net instance = new Net();
        Edge edge1 = new Edge(id, instance.addNode(fromID),
                instance.addNode(toID), prio, function, name);
        Edge edge2 = new Edge(id, instance.addNode(fromID),
                instance.addNode(toID), prio, function, name);
        Lane expResult = new Lane(edge1, speed, length);
        Lane result = instance.addLane(edge2, speed, length);
        String expResultID = expResult.getID();
        String resultID = result.getID();
        assertEquals(expResultID, resultID);
        assertEquals(expResult.getLength(), result.getLength());
        assertEquals(expResult.getParams(), result.getParams());
        assertEquals(expResult.getShape(), result.getShape());
        assertEquals(expResult.getSpeed(), result.getSpeed());
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of addRoundabout method, of class Net.
     */
    @Test
    public void testAddRoundabout() {
        System.out.println("addRoundabout");
        String[] nodes = new String[] {"1","2","3","4"};
        Net instance = new Net();
        Roundabout expResult = new Roundabout(nodes);
        Roundabout result = instance.addRoundabout(nodes);
        Assert.assertArrayEquals(expResult.getNodes(), result.getNodes());
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of addConnection method, of class Net.
     */
    @Test
    public void testAddConnection() {
        System.out.println("addConnection");
        String id1 = "myEdge1";
        String fromID1 = "1";
        String toID1 = "2";
        Integer prio1 = 1;
        String function1 = "internal";
        String name1 = "";

        String id2 = "myEdge2";
        String fromID2 = "3";
        String toID2 = "4";
        Integer prio2 = 5;
        String function2 = "internal";
        String name2 = "";

        Double speed = 60.0;
        Double length = 120.0;
        
        String direction = "";
        String tls = "";
        int tllink = 0;
        
        Net instance = new Net();
        Edge fromEdge = new Edge(id1, instance.addNode(fromID1),
                instance.addNode(toID1), prio1, function1, name1);
        Edge toEdge = new Edge(id2, instance.addNode(fromID2),
                instance.addNode(toID2), prio2, function2, name2);
        
        Lane fromlane = new Lane(fromEdge, speed, length);
        Lane tolane = new Lane(toEdge, speed, length);
        
        instance.addConnection(fromEdge, toEdge, fromlane, tolane, direction, tls, tllink);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getRoundabouts method, of class Net.
     */
    @Test
    public void testGetRoundabouts() {
        System.out.println("getRoundabouts");
        Net instance = new Net();
        String[] myNodes1 = new String[] {"1","2"};
        String[] myNodes2 = new String[] {"3","4"};
        Roundabout roundabout1 = new Roundabout(myNodes1);
        Roundabout roundabout2 = new Roundabout(myNodes2);
        ArrayList expResult = new ArrayList();
        expResult.add(roundabout1);
        expResult.add(roundabout2);
        instance.addRoundabout(myNodes1);
        instance.addRoundabout(myNodes2);
        ArrayList result = instance.getRoundabouts();
        Assert.assertArrayEquals(((Roundabout)expResult.get(0)).getNodes(),
                ((Roundabout)result.get(0)).getNodes());
        Assert.assertArrayEquals(((Roundabout)expResult.get(1)).getNodes(),
                ((Roundabout)result.get(1)).getNodes());
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of hasEdge method, of class Net.
     */
    @Test
    public void testHasEdge() {
        System.out.println("hasEdge");
        String id = "myEdge";
        String fromID = "1";
        String toID = "2";
        Integer prio = 1;
        String function = "internal";
        String name = "";
        Net instance = new Net();
        instance.addEdge(id, fromID, toID, prio, function, name);
        Boolean expResult = true;
        Boolean result = instance.hasEdge(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getEdge method, of class Net.
     */
    @Test
    public void testGetEdge() {
        System.out.println("getEdge");
        String id = "myEdge";
        String fromID = "1";
        String toID = "2";
        Integer prio = 1;
        String function = "internal";
        String name = "";
        Net instance = new Net();
        instance.addEdge(id, fromID, toID, prio, function, name);
        Edge expResult = new Edge(id, instance.addNode(fromID),
                instance.addNode(toID), prio, function, name);
        Edge result = instance.getEdge(id);
        assertEquals(expResult.getId(), result.getId());
        assertEquals(expResult.getLanes(), result.getLanes());
        assertEquals(expResult.getShape(), result.getShape());
        assertEquals(expResult.getTLS(), result.getTLS());
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of hasNode method, of class Net.
     */
    @Test
    public void testHasNode() {
        System.out.println("hasNode");
        String id = "myNode";
        String type = "priority";
        Double[] coord = new Double[] {1.9,2.0};
        String[] incLanes = new String[] {"2","3"};
        Net instance = new Net();
        instance.addNode(id, type, coord, incLanes);
        Boolean expResult = true;
        Boolean result = instance.hasNode(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getNode method, of class Net.
     */
    @Test
    public void testGetNode() {
        System.out.println("getNode");
        String id = "myNode";
        String type = "priority";
        Double[] coord = new Double[] {1.9,2.0};
        String[] incLanes = new String[] {"2","3"};
        Net instance = new Net();
        instance.addNode(id, type, coord, incLanes);
        Node expResult = new Node(id, type, coord, incLanes);
        Node result = instance.getNode(id);
        Assert.assertArrayEquals(expResult.getCoord(), result.getCoord());
        assertEquals(expResult.getId(), result.getId());
        Assert.assertArrayEquals(expResult.getIncLanes(), result.getIncLanes());
        assertEquals(expResult.getOutgoing(), result.getOutgoing());
        Assert.assertArrayEquals(expResult.getIncLanes(), result.getIncLanes());
        assertEquals(expResult.getType(), result.getType());
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getNodes method, of class Net.
     */
    @Test
    public void testGetNodes() {
        System.out.println("getNodes");
        String id1 = "myNode1";
        String type1 = "priority";
        Double[] coord1 = new Double[] {1.9,2.0};
        String[] incLanes1 = new String[] {"2","3"};
        
        String id2 = "myNode2";
        String type2 = "priority";
        Double[] coord2 = new Double[] {5.5,-3.0};
        String[] incLanes2 = new String[] {"0","2"};
        
        Node myNode1 = new Node(id1, type1, coord1, incLanes1);
        Node myNode2 = new Node(id2, type2, coord2, incLanes2);
        
        Net instance = new Net();
        instance.addNode(id1, type1, coord1, incLanes1);
        instance.addNode(id2, type2, coord2, incLanes2);
        
        ArrayList expResult = new ArrayList();
        expResult.add(myNode1);
        expResult.add(myNode2);
        
        ArrayList result = instance.getNodes();
        Node result1 = (Node)expResult.get(0);
        Node result2 = (Node)expResult.get(1);
        
        Assert.assertArrayEquals(result1.getCoord(), myNode1.getCoord());
        assertEquals(result1.getId(), myNode1.getId());
        Assert.assertArrayEquals(result1.getIncLanes(), myNode1.getIncLanes());
        assertEquals(result1.getOutgoing(), myNode1.getOutgoing());
        Assert.assertArrayEquals(result1.getIncLanes(), myNode1.getIncLanes());
        assertEquals(result1.getType(), myNode1.getType());
        
        Assert.assertArrayEquals(result2.getCoord(), myNode2.getCoord());
        assertEquals(result2.getId(), myNode2.getId());
        Assert.assertArrayEquals(result2.getIncLanes(), myNode2.getIncLanes());
        assertEquals(result2.getOutgoing(), myNode2.getOutgoing());
        Assert.assertArrayEquals(result2.getIncLanes(), myNode2.getIncLanes());
        assertEquals(result2.getType(), myNode2.getType());
        
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getTLSSecure method, of class Net.
     */
    @Test
    public void testGetTLSSecure() {
        System.out.println("getTLSSecure");
        
        String id1 = "myEdge1";
        String fromID1 = "1";
        String toID1 = "2";
        Integer prio1 = 1;
        String function1 = "internal";
        String name1 = "";
        
        String id2 = "myEdge2";
        String fromID2 = "3";
        String toID2 = "4";
        Integer prio2 = 5;
        String function2 = "internal";
        String name2 = "";
        
        Double speed = 11.0;
        Double length = 50.0;
        
        String tlid = "myTLS";
        
        Net instance = new Net();
        Edge myEdge1 = new Edge(id1, instance.addNode(fromID1), instance.addNode(toID1),
                prio1, function1, name1);
        
        Edge myEdge2 = new Edge(id2, instance.addNode(fromID2), instance.addNode(toID2),
                prio2, function2, name2);
        
        Lane inLane = new Lane(myEdge1, speed, length);
        Lane outLane = new Lane(myEdge2, speed, length);
        
        instance.addTLS(tlid, inLane, outLane, 1);
        
        TLS expResult = new TLS(tlid);
        expResult.addConnection(inLane, outLane, 1);
        
        TLS result = instance.getTLSSecure(tlid);
        assertEquals(expResult.getConnections(), result.getConnections());
        assertEquals(expResult.getId(), result.getId());
        assertEquals(expResult.getMaxConnectionNumber(), 
                result.getMaxConnectionNumber());
        assertEquals(expResult.getPrograms(), result.getPrograms());
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of addTLS method, of class Net.
     */
    @Test
    public void testAddTLS() {
        System.out.println("addTLS");
        String id1 = "myEdge1";
        String fromID1 = "1";
        String toID1 = "2";
        Integer prio1 = 1;
        String function1 = "internal";
        String name1 = "";
        
        String id2 = "myEdge2";
        String fromID2 = "3";
        String toID2 = "4";
        Integer prio2 = 5;
        String function2 = "internal";
        String name2 = "";
        
        Double speed = 11.0;
        Double length = 50.0;
        
        String tlid = "myTLS";
        
        Net instance = new Net();
        
        Edge myEdge1 = new Edge(id1, instance.addNode(fromID1), instance.addNode(toID1),
                prio1, function1, name1);
        
        Edge myEdge2 = new Edge(id2, instance.addNode(fromID2), instance.addNode(toID2),
                prio2, function2, name2);
        
        Lane inLane = new Lane(myEdge1, speed, length);
        Lane outLane = new Lane(myEdge2, speed, length);
        
        TLS expResult = new TLS(tlid);
        expResult.addConnection(inLane, outLane, 1);
        
        TLS result = instance.addTLS(tlid, inLane, outLane, 1);
        assertEquals(expResult.getConnections(), result.getConnections());
        assertEquals(expResult.getId(), result.getId());
        assertEquals(expResult.getMaxConnectionNumber(),
                result.getMaxConnectionNumber());
        assertEquals(expResult.getPrograms(), result.getPrograms());
        // TODO review the generated test code and remove the default call to fail.
    }
    
}
