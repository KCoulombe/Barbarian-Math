package logic;

import java.util.ArrayList;
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
	public List<Modifier> modifiers;
	public List<String> dataFiles;
	
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
	 * @param directory The directory where the xml files that contains the Ruleset data are in
	 * @throws ParserConfigurationException 
	 */
	public Ruleset(String directory) throws ParserConfigurationException
	{
		File dir = new File(directory);
		
		List<File> classFiles = new ArrayList<File>();
		List<File> valueFiles = new ArrayList<File>();
		List<File> characterFiles = new ArrayList<File>();
		
		for (File file : dir.listFiles()) 
		{
			if (file.getName().endsWith((".class"))) 
			{
				classFiles.add(file);
			}
			
			if (file.getName().endsWith((".val"))) 
			{
				valueFiles.add(file);
				LoadModifiers(file);
			}
			
			if (file.getName().endsWith((".char"))) 
			{
				characterFiles.add(file);
			}
		}
		
		/*try 
		{
			//load the xml file into memory
			File inputFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(inputFile);
	        doc.getDocumentElement().normalize();
	        
	        //print the path of the xml file, for debug purposes 
	        System.out.println("Path: " + inputFile.getAbsolutePath());
	        //Get the root element
	        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	        NodeList nList = doc.getElementsByTagName("component");
	        System.out.println("----------------------------");
	        
	        System.out.println(nList.getLength());
	        //Loop through all of the elements in the xml file
	        for (int i = 0; i < nList.getLength(); i++) 
	        {
	        	Node nNode = nList.item(i);
	            System.out.println("\nCurrent Element :" + nNode.getNodeName());
	            
	            Element eElement = (Element) nNode;
	            System.out.println("File Path: " + eElement.getAttribute("path"));
	            
	            LoadModifiers(eElement.getAttribute("path"));
	        }

		} 
		catch (Exception e) 
		{
			e.printStackTrace();	      
		}*/
	}
	
	//Load all of the modifiers in the file into memory
	void LoadModifiers(File inputFile) throws ParserConfigurationException
	{
		try 
		{
			//load the xml file into memory
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(inputFile);
	        doc.getDocumentElement().normalize();
	        
	        //print the path of the xml file, for debug purposes 
	        System.out.println("Path: " + inputFile.getAbsolutePath());
	        //Get the root element
	        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	        NodeList nList = doc.getElementsByTagName("modifier");
	        
	        System.out.println(nList.getLength());
	        //Loop through all of the elements in the xml file
	        for (int i = 0; i < nList.getLength(); i++) 
	        {
	        	Node nNode = nList.item(i);
	            System.out.println("\nCurrent Element :" + nNode.getNodeName());

	        	if (nNode.getNodeType() == Node.ELEMENT_NODE) 
	        	{
	                Element eElement = (Element) nNode;
	                
	                //The name of the modifier
	                String modifierName = eElement.getElementsByTagName("name").item(0).getTextContent();
	                System.out.println("Name : " + modifierName);
	                
	                //The tags of the modifier
	                String[] modifierTags = eElement.getElementsByTagName("tags").item(0).getTextContent().split(" ");
	                for (int n = 0; n < modifierTags.length; n++) 
	    	        {
	                	System.out.println("Tag " + n + ": " + modifierTags[n]);
	    	        }
	                
	                //The values of the modifier
	                String[] valueStrings = eElement.getElementsByTagName("values").item(0).getTextContent().split(" ");
	                List<Value> modifierValues = new ArrayList<Value>();
	                for (int n = 0; n < valueStrings.length; n++) 
	    	        {
	                	System.out.println("Value " + n + ": " + valueStrings[n]);
	                	modifierValues.add(ParseValue(valueStrings[n]));
	    	        }
	                
	                
	                String[] modifierLimits = eElement.getElementsByTagName("limits").item(0).getTextContent().split(" ");
	                for (int n = 0; n < modifierLimits.length; n++) 
	    	        {
	                	System.out.println("Limit " + n + ": " + modifierLimits[n]);
	    	        }
	                
	                String modifierCost = eElement.getElementsByTagName("cost").item(0).getTextContent();
	                System.out.println("Cost: " + modifierCost);
	        	}
	        }
		} 
		catch (Exception e) 
		{
			e.printStackTrace();	      
		}
	}
	Value ParseValue(String name)
	{
		return new Value("DummyValue", 0);
	}
	/** 
	 * Constructor for reading data from xml.
	 * @param type The type of the thing to be loaded, "adv" for adventurer, "val" for modifier/bundle, and "build" for a charicter build
	 * @throws ParserConfigurationException 
	 */
	public void Load(String name, String path, String type)
	{
		
	}
}



























