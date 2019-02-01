package logic;

import java.util.List;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

/**
 * The rules for a tabletop game.  Contains all the components needed to calculate a character's values
 * @author Kyle Coulombe
 * @see Component
 */
public class Ruleset 
{
	public String name;
	public List<Component> components;
	
	/** 
	 * Constructor for receiving data directly from whatever created this object
	 */
	public Ruleset(String name, List<Component> components)
	{
		this.name = name;
		this.components = components;
	}
	
	/** 
	 * Constructor for reading data from xml.
	 * @param name The name of the ruleset
	 * @param The path to the xml file that contains the Ruleset data
	 * @throws ParserConfigurationException 
	 */
	public Ruleset(String name, String path) throws ParserConfigurationException
	{
		try 
		{
			File inputFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(inputFile);
	        doc.getDocumentElement().normalize();
	        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	        NodeList nList = doc.getElementsByTagName("student");
	        System.out.println("----------------------------");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();	      
		}
	}
}



























