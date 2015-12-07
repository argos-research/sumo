/*
 * Copyright 2015 Universidad Nacional de Colombia,
 * Politecnico Jaime Isaza Cadavid.
 * Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
 * $Id$
 */

package co.edu.unalmed.gaunal.sumolib.net;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
/**
 *
 * @author GaunalJD
 */
public class NetReader extends DefaultHandler{

    /**
     *
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    
    private Net net;
    private Edge currentEdge;
    private Node currentNode;
    private Lane currentLane;
    private String currentShape;
    private TLSProgram currentProgram; 
    private Boolean withPhases;
    private Boolean withConnections;
    private Boolean withFoes;
    
    public NetReader() throws ParserConfigurationException, SAXException {
        this.net = new Net();
        this.currentEdge = null;
        this.currentNode = null;
        this.currentLane = null;
        this.currentShape = "";
        this.currentProgram = null;
        this.withPhases = false;
        this.withConnections = true;
        this.withFoes = true;
    }

    @Override
    public void startElement(String namespaceURI,
            String localName,
            String qName,
            Attributes atts){
        
        if (localName.equals("location")){
            this.net.setLocation(atts.getValue("netOffset"),
                    atts.getValue("convBoundary"),
                    atts.getValue("origBoundary"),
                    atts.getValue("projParameter"));
        }
        
        if (localName.equals("edge")){
            
            // Process non-internal edges
            if (atts.getValue("function")==null ||
                    !atts.getValue("function").equals("internal")){
                int prio;
                prio = -1;
                if (atts.getValue("priority")!=null){
                    prio = Integer.valueOf(atts.getValue("priority"));
                }
                String function;
                function = "";
                if (atts.getValue("function")!=null){
                    function = atts.getValue("function");
                }
                String name;
                name = "";
                if (atts.getValue("name")!=null){
                    name = atts.getValue("name");
                }
                this.currentEdge = this.net.addEdge(atts.getValue("id"),
                        atts.getValue("from"),atts.getValue("to"),
                        prio, function, name);
                if (atts.getValue("shape")!=null){
                    try {
                        this.processShape(this.currentEdge,atts.getValue("shape"));
                    } catch (Exception ex) {
                        Logger.getLogger(NetReader.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else{
               this.currentEdge = null; 
            } 
        }
        
        if (localName.equals("lane") && this.currentEdge != null){
            Double speed = Double.valueOf(atts.getValue("speed"));
            Double length = Double.valueOf(atts.getValue("length"));
            this.currentLane = this.net.addLane(this.currentEdge,speed,length);
            if (atts.getValue("shape")!=null){
                this.currentShape = atts.getValue("shape");                
            } else{
                this.currentShape = "";
            }
        }
        
        if (localName.equals("junction")){
            if (!String.valueOf(atts.getValue("id").charAt(0)).equals(":")){
                Double x = Double.valueOf(atts.getValue("x"));
                Double y = Double.valueOf(atts.getValue("y"));
                Double[] coord = new Double[]{x, y};
                String[] incLanes = atts.getValue("incLanes").split(" ");
                this.currentNode = this.net.addNode(atts.getValue("id"),
                        atts.getValue("type"), coord, incLanes);
            }
        }
        
        if (localName.equals("connection") && this.withConnections && 
                !String.valueOf(atts.getValue("from").charAt(0)).equals(":")){
            Edge fromEdge = this.net.getEdge(atts.getValue("from"));
            Edge toEdge = this.net.getEdge(atts.getValue("to"));
            Lane fromLane = fromEdge.getLane(Integer.valueOf(atts.getValue("fromLane")));
            Lane toLane = toEdge.getLane(Integer.valueOf(atts.getValue("toLane")));
            String tl;
            int tllink;
            if (atts.getValue("tl")!=null && !atts.getValue("tl").equals("")){
                tl = atts.getValue("tl");
                tllink = Integer.valueOf(atts.getValue("linkIndex"));
                TLS tls = this.net.addTLS(tl, fromLane, toLane, tllink);
                fromEdge.setTls(tls);
            } else{
                tl = "";
                tllink = -1;
            }
            this.net.addConnection(fromEdge, toEdge, fromLane, toLane,
                    atts.getValue("dir"), tl, tllink);
        }
        
        if (localName.equals("request") && this.withFoes){
            this.currentNode.setFoes(Integer.valueOf(atts.getValue("index")),
                    atts.getValue("foes"), atts.getValue("response"));
        }
        
        if (localName.equals("phases") && this.withPhases){
            this.currentProgram.addPhase(atts.getValue("state"),
                    Integer.valueOf(atts.getValue("duration")));
        }
        
        if (localName.equals("roundabout")){
            this.net.addRoundabout(atts.getValue("nodes").split(" "));
        }
        
        if (localName.equals("param")){
            if (this.currentLane!=null){
                this.currentLane.getParams().put(atts.getValue("key"),
                        atts.getValue("value"));
            }
        }
    }
    
    @Override
    public void characters(char[] ch, int start, int length){
        if (this.currentLane != null){
            this.currentShape = this.currentShape + ch;
        }
    }
    
    
    @Override
    public void endElement(String uri, String localName, String qName){
        if (localName.equals("lane")){
            if (this.currentLane!=null){
                try {
                    this.processShape(this.currentLane, this.currentShape);
                } catch (Exception ex) {
                    Logger.getLogger(NetReader.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.currentShape = "";
            }
            this.currentLane = null;
        }
        if (localName.equals("edge")){
            if (this.currentEdge!=null && this.currentEdge.getShape()==null){
                this.currentEdge.rebuildShape();
            }
            this.currentEdge = null;
        }
    }
    
    public void processShape(Object object,
            String shapeString) throws Exception{
        ArrayList cshape = new ArrayList();
        String[] es = shapeString.trim().split(" ");
        for (String e : es) {
            String[] p = e.split(",");
            cshape.add(new Double[]{Double.valueOf(p[0]),Double.valueOf(p[1])});
            object.getClass().getMethod("setShape", 
                    new Class<?>[]{ArrayList.class}).invoke(object, cshape);
        }
    }

    public Net getNet() {
        return net;
    }
    
    /*public Double[] parseGeoData(String data){
    String[] splitData = data.split(",");
    Double[] parsedGeoData = new Double[4];
    for (int i=1; i<parsedGeoData.length; i++){
    parsedGeoData[i] = Double.parseDouble(splitData[i]);
    }
    return parsedGeoData;
    }*/
    
    public static Net readNet(String filename) throws ParserConfigurationException, SAXException{
        NetReader netReader = new NetReader();
        try{
            File f = new File(filename);
            if (!f.exists() || f.isDirectory()){
                System.out.format("Network file '%s' not found",filename);
                System.exit(1);
            }
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            SAXParser saxParser = spf.newSAXParser();
            
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(netReader);
            xmlReader.parse(filename); 
        } catch (ParserConfigurationException /*| SAXException | IOException*/ e){
            System.out.println("Please mind that the network format has changed "
                    + "in 0.13.0, you may need to update your network!");
            System.exit(1);
        } catch (IOException ex) {
            Logger.getLogger(NetReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return netReader.getNet();
    }
    
}