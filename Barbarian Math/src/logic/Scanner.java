package logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Scanner 
{
	/** 
	 * Creates a ruleset object based off of xml files
	 * @param directory a string containing the directory containing all of the xml files
	 * @return A ruleset containing all of the data xml files
	 */
	public Ruleset LoadRuleset(String directory) throws ParserConfigurationException
	{
		return LoadRuleset(new File(directory));
	}
	/** 
	 * Creates a ruleset object based off of xml files
	 * @param directory a File object of the directory containing all of the xml files
	 * @return A ruleset containing all of the data xml files
	 */
	public Ruleset LoadRuleset(File directory) throws ParserConfigurationException
	{
		String name = "";
		List<Modifier> modifiers = new ArrayList<Modifier>();
		List<Adventurer> adventurers = new ArrayList<Adventurer>();
				
		List<File> AdventurerFiles = new ArrayList<File>();
		List<File> valueFiles = new ArrayList<File>();
		List<File> characterFiles = new ArrayList<File>();
		
		//loop through all of the files
		for (File file : directory.listFiles()) 
		{
			try 
			{
				//load the xml file into memory
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		        Document doc = dBuilder.parse(file);
		        doc.getDocumentElement().normalize();
		        
		        //print the path of the xml file, for debug purposes 
		        System.out.println("Path: " + file.getAbsolutePath());
		        //Get the root element
		        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
				
		        //load all of the .val files
				if (file.getName().endsWith((".val"))) 
				{
					NodeList nList = doc.getElementsByTagName("modifier");
					valueFiles.add(file);
					modifiers = LoadModifiers(nList);
				}
				
				if (file.getName().endsWith((".adv"))) 
				{
					NodeList nList = doc.getElementsByTagName("adventurer");
					AdventurerFiles.add(file);
					adventurers = LoadAdventurers(nList);
					
					//System.out.println(adventurers);
				}
				
				if (file.getName().endsWith((".char"))) 
				{
					characterFiles.add(file);
				}
				
				if (file.getName().endsWith((".ruleset"))) 
				{
					name = doc.getElementsByTagName("name").item(0).getTextContent();
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();	      
			}
		}
		
		return new Ruleset(name, modifiers, adventurers);
	}
	
	public Element FindComponentInXML(String name, String filePath, String nodeType) throws ParserConfigurationException, SAXException, IOException
	{
		return FindComponentInXML(name, new File(filePath), nodeType);
	}
	public Element FindComponentInXML(String name, File file, String componentType) throws ParserConfigurationException, SAXException, IOException
	{
		//load the xml file into memory
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();
        
        //get all of the tags that correspond to the componentType, then loop through them
        NodeList nList = doc.getElementsByTagName(componentType);
        for (int i = 0; i < nList.getLength(); i++) 
        {
        	Node nNode = nList.item(i);

        	if (nNode.getNodeType() == Node.ELEMENT_NODE) 
        	{
        		Element eElement = (Element) nNode;
        		
        		//if the element's name is the entered name, return it
        		if (eElement.getElementsByTagName("name").item(0).getTextContent().equals(name))
        		{
        			return eElement;
        		}
        	}
        }
        
        //if there is no element of the entered type and name, return null
        return null;
	}
	
	public File[] ListRulesets(String directoryPath)
	{
		return ListRulesets(new File(directoryPath));
	}	
	public File[] ListRulesets(File directory)
	{
		return directory.listFiles(File::isDirectory);
	}
	
	public void Write(Modifier modifier, String filePath) throws ParserConfigurationException, SAXException, IOException
	{
		Write(modifier, new File(filePath));
	}	
	public void Write(Modifier modifier, File file) throws ParserConfigurationException, SAXException, IOException
	{
		//Check if the element we want to write already exists, and if so, delete it
		Element existing = FindComponentInXML(modifier.name, file, "modifier");
		if(existing != null)
		{
			existing.getParentNode().removeChild(existing);
		}
		
		
	}
	
	public void Write(Adventurer adventurer, String filePath) throws ParserConfigurationException, SAXException, IOException
	{
		Write(adventurer, new File(filePath));
	}	
	public void Write(Adventurer adventurer, File file) throws ParserConfigurationException, SAXException, IOException
	{
		//Check if the element we want to write already exists, and if so, delete it
		Element existing = FindComponentInXML(adventurer.name, file, "adventurer");
		if(existing != null)
		{
			existing.getParentNode().removeChild(existing);
		}
	}
	
	public void Write(CharacterBuild characterBuild, String filePath) throws ParserConfigurationException, SAXException, IOException
	{
		Write(characterBuild, new File(filePath));
	}
	public void Write(CharacterBuild characterBuild, File file) throws ParserConfigurationException, SAXException, IOException
	{
		//Check if the element we want to write already exists, and if so, delete it
		Element existing = FindComponentInXML(characterBuild.name, file, "characterBuild");
		if(existing != null)
		{
			existing.getParentNode().removeChild(existing);
		}
	}
	
	/** 
	 * Takes a list of adventurer xml elements and turns them into a list of adventurer objects.
	 * @param nList a list of the adventurer xml elements
	 * @return a list of adventurer objects based on the list of adventurer xml elements
	 */
	List<Adventurer> LoadAdventurers(NodeList nList)
	{
		//the list to return
		List<Adventurer> adventurers = new ArrayList<Adventurer> ();
		//System.out.println(nList.getLength());
        //Loop through all of the adventurer elements
        for (int i = 0; i < nList.getLength(); i++) 
        {
        	//get the adventurer we are currently working on
        	Node nNode = nList.item(i);
        	
            //System.out.println("\nCurrent Element :" + nNode.getNodeName());

            //the main class
            Bundle adventurerMainclass = new Bundle("Dummy Class", new ArrayList<String>(), new TreeMap<Integer, String>());
            //all of the subclasses
            List<Bundle> adventurerSubclasses = new ArrayList<Bundle>();
            //all of the modifiers in this adventurer
            List<Modifier> adventurerModifiers = new ArrayList<Modifier>();
            //all of the scalars in this adventurer (mostly spells)
            List<Scalar> adventurerScalars = new ArrayList<Scalar>();
            
        	if (nNode.getNodeType() == Node.ELEMENT_NODE) 
        	{
        		
        		Element eElement = (Element) nNode;
        		
        		//get all of the modifier tags, then loop through them
        		NodeList modifierList = eElement.getElementsByTagName("modifier");
        		for (int n = 0; n < modifierList.getLength(); n++) 
                {
        			//get the current modifier
        			Node modifierNode = modifierList.item(i);
                    //System.out.println("\nCurrent Element: " + modifierNode.getNodeName());
                    
                    //then parse it and add it to the list of modifiers
                	if (modifierNode.getNodeType() == Node.ELEMENT_NODE) 
                	{
                		Element modifierElement = (Element) nNode;
                		adventurerModifiers.add(ParseModifier(modifierElement));
                	}
                }
        		
        		//get all of the bundle tags, then loop through them
        		NodeList bundleList = eElement.getElementsByTagName("bundle");
        		for (int n = 0; n < bundleList.getLength(); n++) 
                {
        			//get the current bundle 
        			Node bundleNode = bundleList.item(n);
                    //System.out.println("\nCurrent Element :" + bundleNode.getNodeName());
                    
                	if (bundleNode.getNodeType() == Node.ELEMENT_NODE) 
                	{
                		Element bundleElement = (Element) bundleNode;
                		
                		//System.out.println("Bundle type: " + bundleElement.getAttribute("type"));
                		
                		//if it is the mainclass, then put it in the main class
                		if(bundleElement.getAttribute("type").equals("mainclass"))
                		{
                			//System.out.println("Mainclass");
                			adventurerMainclass = ParseBundle(bundleElement);
                			
                			//System.out.println(adventurerMainclass);
                		}
                		else if(bundleElement.getAttribute("type").equals("subclass")) //if it is a sublass, then put it in the subclass list
                		{
                			//System.out.println("Subclass");
                			adventurerSubclasses.add(ParseBundle(bundleElement));
                			//System.out.println(adventurerSubclasses.get(n - 1));
                		}
                		else
                		{
                			throw new java.lang.Error("Bundle type unrecognised");
                		}
                	}
                }
        		
        		//get all of the scalar tags, then loop through them
        		NodeList scalarList = eElement.getElementsByTagName("scalar");
        		for (int n = 0; n < scalarList.getLength(); n++) 
                {
        			//get the current bundle 
        			Node scalarNode = scalarList.item(n);
                    //System.out.println("\nCurrent Element :" + scalarNode.getNodeName());
                    
                	if (scalarNode.getNodeType() == Node.ELEMENT_NODE) 
                	{
                		Element scalarElement = (Element) scalarNode;
                		adventurerScalars.add(ParseScalar(scalarElement));
                		//System.out.println(adventurerScalars.get(n));
                		
                	}
                }
        	}        	
        	adventurers.add(new Adventurer(adventurerMainclass.name, adventurerMainclass, adventurerSubclasses, adventurerModifiers, adventurerScalars));
        }
        
        return adventurers;
	}
	
	/** 
	 * Takes a bundle xml element and turns it into a modifier object
	 * @param eElement the bundle xml element
	 * @return A bundle object based on the given xml element
	 */
	Bundle ParseBundle(Element eElement)
	{
		//the name of the bundle
		String bundleName = eElement.getElementsByTagName("name").item(0).getTextContent();
		
		//the tags of the bundle
		List<String> bundleTags =  Arrays.asList(eElement.getElementsByTagName("tags").item(0).getTextContent().split(" "));
		
		//the map of the bundle values
		Map<Integer, String> bundleValues = new TreeMap<Integer, String>();
		
		//the list of the value tags
		NodeList valueList = eElement.getElementsByTagName("value");
		
		for (int i = 0; i < valueList.getLength(); i++) 
        {
			//a temporary array to store the values in the tag
			String[] s = valueList.item(i).getTextContent().split(":");
			
			bundleValues.put(Integer.parseInt(s[0]), s[1]);
        }
		
		return new Bundle(bundleName, bundleTags, bundleValues);
	}
	
	/** 
	 * Takes a scalar xml element and turns it into a modifier object
	 * @param eElement the bundle xml element
	 * @return A scalar object based on the given xml element
	 */
	Scalar ParseScalar(Element eElement)
	{
		//Scalar is a subclass of Bundle, so we can re-use ParseBundle() to get most of what we need
		Bundle bundle = ParseBundle(eElement);
		
		List<String> scalarLimits = new ArrayList<String>();
		if(eElement.getElementsByTagName("limit").getLength() > 0)
		{
			scalarLimits = Arrays.asList(eElement.getElementsByTagName("limit").item(0).getTextContent().split(" "));
		}
		
		return new Scalar(bundle.name, bundle.tags, bundle.values, scalarLimits);
	}
	
	/** 
	 * Takes a list of modifier xml elements and turns them into a list of modifier objects.
	 * @param nList a list of the modifier xml elements
	 * @return a list of modifier objects based on the list of modifier xml elements
	 */
	List<Modifier> LoadModifiers(NodeList nList)
	{
		List<Modifier> modifiers = new ArrayList<Modifier> ();
		
		/*load the xml file into memory
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        
        //print the path of the xml file, for debug purposes 
        System.out.println("Path: " + inputFile.getAbsolutePath());
        //Get the root element
        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        NodeList nList = doc.getElementsByTagName("modifier");*/
        
        //System.out.println(nList.getLength());
		
        //Loop through all of the elements in the xml file
        for (int i = 0; i < nList.getLength(); i++) 
        {
        	Node nNode = nList.item(i);
            //System.out.println("\nCurrent Element :" + nNode.getNodeName());

        	if (nNode.getNodeType() == Node.ELEMENT_NODE) 
        	{
                Element eElement = (Element) nNode;
                
                //add the modifier to the big list
                modifiers.add(ParseModifier(eElement));
                //System.out.println("Name : " + modifiers.get(i).name);
                
                /*for (int n = 0; n < modifiers.get(i).tags.size(); n++) 
    	        {
                	System.out.println("Tag " + n + ": " + modifiers.get(i).tags.get(n));
    	        }
                	                
                for (int n = 0; n < modifiers.get(i).values.size(); n++) 
    	        {
                	System.out.println("Value " + modifiers.get(i).values.get(n).name + ": " + modifiers.get(i).values.get(n).value);
    	        }
                
                for (int n = 0; n < modifiers.get(i).limits.size(); n++) 
    	        {
                	System.out.println("Limit " + n + ": " + modifiers.get(i).limits.get(n));
    	        }
                
                for (int n = 0; n < modifiers.get(i).costs.size(); n++) 
    	        {
                	System.out.println("Costs " + n + ": " + modifiers.get(i).costs.get(n));
    	        }*/
        	}
        }
        
        return modifiers;
	}
	
	/** 
	 * Takes a modifier xml element and turns it into a modifier object
	 * @param eElement the modifier xml element
	 * @return A modifier object based on the given xml element
	 */
	Modifier ParseModifier(Element eElement)
	{
		//The name of the modifier
        String modifierName = eElement.getElementsByTagName("name").item(0).getTextContent();      
        
        //The tags of the modifier
        List<String> modifierTags = Arrays.asList(eElement.getElementsByTagName("tags").item(0).getTextContent().split(" "));
                     
        //The values of the modifier
        List<String> valueStrings = Arrays.asList(eElement.getElementsByTagName("values").item(0).getTextContent().split(" "));
        List<Value> modifierValues = new ArrayList<Value>();
        for (int n = 0; n < valueStrings.size(); n++) 
        {
        	modifierValues.add(ParseValue(valueStrings.get(n)));
        }
       
        //The limits of the modifier
        List<String> modifierLimits = Arrays.asList(eElement.getElementsByTagName("limits").item(0).getTextContent().split(" "));
        
        //The costs of the modifier
        List<String> modifierCosts = Arrays.asList(eElement.getElementsByTagName("cost").item(0).getTextContent().split(" "));
        
        //add the modifier to the big list
        return new Modifier(modifierName, modifierValues, modifierTags, modifierLimits, modifierCosts);
	}
	
	/** 
	 * Takes a value string element and turns it into a value object
	 * @param name the string value
	 * @return A value object based on the given string
	 */
	Value ParseValue(String name)
	{
		//if the string has no digits, then the value is based on a number that is not yet available, such as a charicters strength
		boolean noDigits = true;
		//if it has a colon, than it is a named value
		boolean hasColon = false;
		for(int i = 0; i < name.length(); i++) 
		{
			if(!Character.isLetter(name.charAt(i)))
			{
				noDigits = false;
				
				if(name.charAt(i) == ':')
				{
					hasColon = true;
				}
				break;
			}
		}
		if(noDigits && !hasColon)
		{
			return new Value(name, 0);
		}
		
		String title = name;
		if(hasColon)
		{
			String[] s = name.split(":");
			
			title = s[0];
			name = s[1];
		}
		
		//if it is in the format [Title]:[Value] then separate the value and the title
		
		//if it is only numbers, then it is a number (duh)
		boolean onlyNumbers = true;
		for(int i = 0; i < name.length(); i++) 
		{
			if(!Character.isDigit(name.charAt(i)))
			{
				onlyNumbers = false;
			}
		}
		if(onlyNumbers)
		{
			return new Value(title, Integer.parseInt(name));
		}
		
		//if the first and last chars are numbers and it contains only one d, then it is a dice number
		boolean isDie = false;
		if(Character.isDigit(name.charAt(0)) && Character.isDigit(name.charAt(name.length() - 1)))
		{
			for(int i = 0; i < name.length(); i++) 
			{
				//if the char is a d and the only d
				if(name.charAt(i) == 'd')
				{
					if(isDie == true)
					{
						isDie = false;
						break;
					}
					isDie = true;
				}
			}
		}
		if(isDie)
		{
			String[] diceNumbers = name.split("d");
			
			
			//System.out.println(Integer.parseInt(diceNumbers[0]) + " " + Integer.parseInt(diceNumbers[1]));
			return new DiceValue(title, Integer.parseInt(diceNumbers[0]), Integer.parseInt(diceNumbers[1]));
		}
		
		throw new java.lang.Error("Value format unrecognised");
	}
}
