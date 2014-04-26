/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.badlogic.gdx.utils.XmlWriter;
import entities.Player;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author alvarez
 */
public class GameLoaderUtil  {
public Player player;
private String path;
public int healthToLoad;
private String configFile;

public GameLoaderUtil(String filePath){
    path = filePath;
}
   
public void load() {
       
        XmlReader xml = new XmlReader();
       
    try{
      Element root = new XmlReader().parse(Gdx.files.local("C:/Users/alvarez/Documents/NetBeansProjects/Platformer/src/platformer/" + path));
      Element health = root.getChildByName("health");
      healthToLoad = root.getInt("health");
    }catch(IOException e){
        e.printStackTrace();
    }
    }


  

  public void setFile(String configFile) {
    this.configFile = configFile;
    
  }

  public void save(String healthStatus) throws Exception {
    // create an XMLOutputFactory
    String hS = healthStatus;
    configFile = "C:/Users/alvarez/Documents/NetBeansProjects/Platformer/src/platformer/XMLTEST.xml";
    XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
    // create XMLEventWriter
    XMLEventWriter eventWriter = outputFactory
        .createXMLEventWriter(new FileOutputStream(configFile));
    // create an EventFactory
    XMLEventFactory eventFactory = XMLEventFactory.newInstance();
    XMLEvent end = eventFactory.createDTD("\n");
    // create and write Start Tag
    StartDocument startDocument = eventFactory.createStartDocument();
    eventWriter.add(startDocument);

    // create config open tag
    StartElement configStartElement = eventFactory.createStartElement("",
        "", "root");
    eventWriter.add(configStartElement);
    eventWriter.add(end);
    // Write the different nodes
    createNode(eventWriter, "health", hS);
    eventWriter.add(eventFactory.createEndElement("", "", "config"));
    eventWriter.add(end);
    eventWriter.add(eventFactory.createEndDocument());
    eventWriter.close();
  }

  private void createNode(XMLEventWriter eventWriter, String name,
      String value) throws XMLStreamException {

    XMLEventFactory eventFactory = XMLEventFactory.newInstance();
    XMLEvent end = eventFactory.createDTD("\n");
    XMLEvent tab = eventFactory.createDTD("\t");
    // create Start node
    StartElement sElement = eventFactory.createStartElement("", "", name);
    eventWriter.add(tab);
    eventWriter.add(sElement);
    // create Content
    Characters characters = eventFactory.createCharacters(value);
    eventWriter.add(characters);
    // create End node
    EndElement eElement = eventFactory.createEndElement("", "", name);
    eventWriter.add(eElement);
    eventWriter.add(end);
  }

    public int returnHealth(){
        return healthToLoad;
    }

    
    
}
