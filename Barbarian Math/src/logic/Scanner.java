package logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class Scanner 
{
	/** 
	 * Creates a ruleset object based off of xml files
	 * @param directory a string containing the directory containing all of the xml files
	 * @return 
	 * @return A ruleset containing all of the data xml files
	 */
	
	private Scanner()
	{
		
	}
	
	static public Ruleset LoadRuleset(String directory) throws ParserConfigurationException
	{
		return LoadRuleset(new File(directory));
	}
	/** 
	 * Creates a ruleset object based off of xml files
	 * @param directory a File object of the directory containing all of the xml files
	 * @return A ruleset containing all of the data xml files
	 */
	
	static public Ruleset LoadRuleset(File directory) throws ParserConfigurationException
	{
		Ruleset ruleset = new Ruleset("", new ArrayList<Modifier>(), new ArrayList<Scalar>(), new ArrayList<Adventurer>(), new ArrayList<CharacterBuild>());
		
		/*String name = "";
		List<Modifier> modifiers = new ArrayList<Modifier>();
		List<Scalar> scalars = new ArrayList<Scalar>();
		List<Adventurer> adventurers = new ArrayList<Adventurer>();
		List<CharacterBuild> characters = new ArrayList<CharacterBuild>();*/
				
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
					valueFiles.add(file);
					
					/*NodeList modifierNodes = doc.getElementsByTagName("modifier");
					modifiers.addAll(LoadModifiers(modifierNodes));*/
					
					ruleset.modifiers.addAll(GetModifiersFromFile(file));
				}
				
				if (file.getName().endsWith((".sca"))) 
				{
					//NodeList scalarNodes = doc.getElementsByTagName("scalar");
					//scalars.addAll(LoadScalars(scalarNodes));
					
					ruleset.scalars.addAll(GetScalarsFromFile(file));
				}
				
				if (file.getName().endsWith((".adv"))) 
				{
					//NodeList nList = doc.getElementsByTagName("adventurer");
					AdventurerFiles.add(file);
					//adventurers.addAll(LoadAdventurers(nList));
					
					ruleset.adventurers.addAll(GetAdventurersFromFile(file));
					
					//System.out.println(adventurers);
				}
				
				if (file.getName().endsWith((".char"))) 
				{
					characterFiles.add(file);
					//NodeList characterNodes = doc.getElementsByTagName("character");
					//characters.addAll(LoadCharacters(characterNodes));
					
					ruleset.characters.addAll(GetCharacterBuildsFromFile(file));
					
					
				}
				
				if (file.getName().endsWith((".ruleset"))) 
				{
					ruleset.name = doc.getElementsByTagName("name").item(0).getTextContent();
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();	      
			}
		}
		
		//link all of the character to their values
		ruleset.LinkCharactersToValues();
		
		/*System.out.println("\n" + ruleset.characters.get(0).name + "\n" );
		
		System.out.println("\n" + ruleset.characters.get(0).classes.get(0).main_class + "\n" );
		System.out.println("\n" + ruleset.characters.get(0).classes.get(0).level + "\n" );
		System.out.println("\n" + ruleset.characters.get(0).classes.get(0).GetSubclass() + "\n" );
		
		System.out.println("\n" + ruleset.characters.get(0).classes.get(1).main_class + "\n" );
		System.out.println("\n" + ruleset.characters.get(0).classes.get(1).level + "\n" );
		System.out.println("\n" + ruleset.characters.get(0).classes.get(1).GetSubclass() + "\n" );
		
		System.out.println("\n" + ruleset.characters.get(0).modifiers + "\n" );
		System.out.println("\n" + ruleset.characters.get(0).classModifiers + "\n" );
		System.out.println("\n" + ruleset.characters.get(0).scalars + "\n" );*/
		
		return ruleset;
	}

    /** 
	 * Lists all of the rulesets in the specified directory.
	 * @param directoryPath the filepath of the directory as a string
	 * @return A array of File objects for each ruleset in the specified directory
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	static public  List<String> ListRulesetNames(String directoryPath) throws ParserConfigurationException, SAXException, IOException
	{
		return ListRulesetNames(new File(directoryPath));
	}
	
	/** 
	 * Lists all of the rulesets in the specified directory.
	 * @param directory the directory as a File object
	 * @return A array of File objects for each ruleset in the specified directory
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	static public  List<String> ListRulesetNames(File directory) throws ParserConfigurationException, SAXException, IOException
	{
		List<String> rulesetNames = new ArrayList<String>();
		
		for(File dir : directory.listFiles(File::isDirectory))
		{
			for (File file : dir.listFiles()) 
			{
				if (file.getName().endsWith((".ruleset"))) 
				{
					//load the xml file into memory
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			        Document doc = dBuilder.parse(file);
			        doc.getDocumentElement().normalize();
					//return directory.listFiles(File::isDirectory);
			        rulesetNames.add(doc.getElementsByTagName("name").item(0).getTextContent());
		        }
			}
		}
		
		return rulesetNames;
	}
	
	static public  Element FindComponentInXML(String name, String filePath, String nodeType) throws ParserConfigurationException, SAXException, IOException
	{
		return FindComponentInXML(name, new File(filePath), nodeType);
	}
	static public  Element FindComponentInXML(String name, File file, String componentType) throws ParserConfigurationException, SAXException, IOException
	{
		//load the xml file into memory
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();
        
        return FindComponentInXML(name, doc, componentType);
	}
	static public  Element FindComponentInXML(String name, Document doc, String componentType) throws ParserConfigurationException, SAXException, IOException
	{
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
        			System.out.println("A " + componentType + " named '" + eElement.getElementsByTagName("name").item(0).getTextContent() +  "' was found");
        			return eElement;
        		}
        	}
        }
        
        //if there is no element of the entered type and name, return null
        return null;
	}
	
	/** 
	 * Gets all modifiers in an xml file
	 * @param filePath the filepath of the file as a string
	 * @return A list of Modifier objects in the specified file
	 */
	public static List<Modifier> GetModifiersFromFile(String filePath) throws SAXException, IOException, ParserConfigurationException
	{
		return GetModifiersFromFile(new File(filePath));
	}
	/** 
	 * Gets all modifiers in an xml file
	 * @param file the file
	 * @return A list of Modifier objects in the specified file
	 */
	public static List<Modifier> GetModifiersFromFile(File file) throws SAXException, IOException, ParserConfigurationException
	{
		//load the xml file into memory
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();
        		
		NodeList modifierNodes = doc.getElementsByTagName("modifier");
		
		return (LoadModifiers(modifierNodes));
	}
	
	/** 
	 * Gets all Scalars in an xml file
	 * @param filePath the filepath of the file as a string
	 * @return A list of Scalar objects in the specified file
	 */
	public static List<Scalar> GetScalarsFromFile(String filePath) throws SAXException, IOException, ParserConfigurationException
	{
		return GetScalarsFromFile(new File(filePath));
	}
	/** 
	 * Gets all Scalars in an xml file
	 * @param file the file
	 * @return A list of Scalar objects in the specified file
	 */
	public static List<Scalar> GetScalarsFromFile(File file) throws SAXException, IOException, ParserConfigurationException
	{
		//load the xml file into memory
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();
        		
		NodeList scalarNodes = doc.getElementsByTagName("scalar");
		
		return (LoadScalars(scalarNodes));
	}
	
	/** 
	 * Gets all Adventurers in an xml file
	 * @param filePath the filepath of the file as a string
	 * @return A list of Adventurer objects in the specified file
	 */
	public static List<Adventurer> GetAdventurersFromFile(String filePath) throws SAXException, IOException, ParserConfigurationException
	{
		return GetAdventurersFromFile(new File(filePath));
	}
	/** 
	 * Gets all Adventurers in an xml file
	 * @param file the file
	 * @return A list of Adventurer objects in the specified file
	 */
	public static List<Adventurer> GetAdventurersFromFile(File file) throws SAXException, IOException, ParserConfigurationException
	{
		//load the xml file into memory
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();
        		
		NodeList adventurerNodes = doc.getElementsByTagName("adventurer");
		
		return (LoadAdventurers(adventurerNodes));
	}
	
	/** 
	 * Gets all Adventurers in an xml file
	 * @param filePath the filepath of the file as a string
	 * @return A list of Adventurer objects in the specified file
	 */
	public static List<CharacterBuild> GetCharacterBuildsFromFile(String filePath) throws SAXException, IOException, ParserConfigurationException
	{
		return GetCharacterBuildsFromFile(new File(filePath));
	}
	/** 
	 * Gets all characters in an xml file
	 * @param file the file
	 * @return A list of Adventurer objects in the specified file
	 */
	public static List<CharacterBuild> GetCharacterBuildsFromFile(File file) throws SAXException, IOException, ParserConfigurationException
	{
		//load the xml file into memory
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();
        		
		NodeList characterNodes = doc.getElementsByTagName("character");
		
		return (LoadCharacters(characterNodes));
	}
	
	/** 
	 * Lists all of the rulesets in the specified directory.
	 * @param directoryPath the filepath of the directory as a string
	 * @return A array of File objects for each ruleset in the specified directory
	 */
	static public  File[] ListRulesets(String directoryPath)
	{
		return ListRulesets(new File(directoryPath));
	}
	
	/** 
	 * Lists all of the rulesets in the specified directory.
	 * @param directory the directory as a File object
	 * @return A array of File objects for each ruleset in the specified directory
	 */
	static public  File[] ListRulesets(File directory)
	{
		return directory.listFiles(File::isDirectory);
	}
	
	/** 
	 * Writes a modifier object to the specified file.
	 * @param modifier the modifier to be written.  if a modifier with the same name exists, it will be overwritten.
	 * @param filePath the path to the file that will be written to.
	 */
	static public  void Write(Modifier modifier, String filePath) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{
		Write(modifier, new File(filePath));
	}
	/** 
	 * Writes a modifier object to the specified file.
	 * @param modifier the modifier to be written.  if a modifier with the same name exists, it will be overwritten.
	 * @param file the file that will be written to.
	 */
	static public  void Write(Modifier modifier, File file) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{
		//load the xml file into memory
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();
		
		//Check if the element we want to write already exists, and if so, delete it
		Element existing = FindComponentInXML(modifier.name, doc, "modifier");
		if(existing != null)
		{
			existing.getParentNode().removeChild(existing);
		}
		
        //Create the new Element
        Element newModifier = doc.createElement("modifier");
        
        //create the name element
        //Text modifierName = doc.createTextNode("name");
        Element modifierName = doc.createElement("name");
        modifierName.appendChild(doc.createTextNode(modifier.name));
        //modifierName.setTextContent(modifier.name);
        newModifier.appendChild(modifierName);
        
        //create the tags element    
        Element modifierTags = doc.createElement("tags");
        String tagsText = "";
        if(modifier.tags.size() > 0)
        {
	        for(int i = 0; i < modifier.tags.size(); i++)
	        {
	        	tagsText += modifier.tags.get(i);
	        	if(i != modifier.tags.size() - 1)
	        	{
	        		tagsText +=  " ";
	        	}
	        }
        }
        //tagsText += "\b";
        modifierTags.appendChild(doc.createTextNode(tagsText));
        newModifier.appendChild(modifierTags);
        
        //create the values element
        Element modifierValues = doc.createElement("values");
        String valuesText = "";
        for(int i = 0; i < modifier.values.size(); i++)
        {
        	valuesText += modifier.values.get(i).name;
        	if(i != modifier.values.size() - 1)
        	{
        		valuesText +=  " ";
        	}
        }
        //valuesText += "\b";
        modifierValues.appendChild(doc.createTextNode(valuesText));
        newModifier.appendChild(modifierValues);
        
        //create the limits element
        Element modifierLimits = doc.createElement("limits");
        String limitsText = "";
        for(int i = 0; i < modifier.limits.size(); i++)
        {
        	limitsText += modifier.limits.get(i);
        	if(i != modifier.limits.size() - 1)
        	{
        		limitsText +=  " ";
        	}
        }
        //limitsText += "\b";
        modifierLimits.appendChild(doc.createTextNode(limitsText));
        newModifier.appendChild(modifierLimits);
        
        //create the costs element
        Element modifierCosts = doc.createElement("costs");
        String costsText = "";
        for(int i = 0; i < modifier.costs.size(); i++)
        {
        	costsText += modifier.costs.get(i);
        	if(i != modifier.costs.size() - 1)
        	{
        		costsText +=  " ";
        	}
        }
        //costsText += "\b";
        modifierCosts.appendChild(doc.createTextNode(costsText));
        newModifier.appendChild(modifierCosts);
        
        //add the new modifier to the document object
        Node root = doc.getFirstChild();
        root.appendChild(newModifier);
        
        //write the new modifier to the xml file
        DOMSource source = new DOMSource(doc);
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        StreamResult result = new StreamResult(file);
        transformer.transform(source, result);
	}
	
	static public  void Write(Scalar scalar, String filePath) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{
		Write(scalar, new File(filePath));
	}	
	static public  void Write(Scalar scalar, File file) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{
		//load the xml file into memory
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();
		
		//Check if the element we want to write already exists, and if so, delete it
		Element existing = FindComponentInXML(scalar.name, doc, "scalar");
		if(existing != null)
		{
			existing.getParentNode().removeChild(existing);
		}
		
        //Create a new scalar 
        Node root = doc.getFirstChild();
        root.appendChild(ScalarToXML(scalar, doc));
        
        //write the new modifier to the xml file
        DOMSource source = new DOMSource(doc);
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        StreamResult result = new StreamResult(file);
        transformer.transform(source, result);
	}
	
	/** 
	 * Takes a Scaler object and turns it into an XML element, Basically the inverse of @ParseScalar()
	 * @param scalar the Scalar object to be turned into XML.
	 * @param doc the document that the XML will be using
	 * @return An XML element based off of the Scalar Object
	 * @see Scanner#ParseScalar(Element eElement)
	 */
	static private  Element ScalarToXML(Scalar scalar, Document doc)
	{
		//A scalar is a subtype of bundle, so we can save some code by creating a bundle and using BundleToXML()
		//then adding a limits element to the resulting xml
		Bundle bundle = new Bundle(scalar.name, scalar.tags, null);
		 //Create the new Element
        Element newScalar = BundleToXML(bundle, doc);
        
        //create the value elements
        if(scalar.values != null)
        {
	        //get a list of all of the keys
	        List<Integer> keys = new ArrayList<Integer>(scalar.values.keySet());
	        for(int i = 0; i < keys.size(); i++)
	        {
	        	//get key i
	        	int key = keys.get(i);
	        	
	        	//turn the array of values into a string
	        	String valuesText = "";
	        	for(int n = 0; n < scalar.values.get(key).size(); n++)
	 	        {
	        		valuesText += scalar.values.get(key).get(n);
	        		if(n != scalar.values.get(key).size() - n)
	            	{
	            		valuesText +=  " ";
	            	}
	 	        }
	        	
	        	//create a new element based on the keys
	        	Element newValue = doc.createElement("value");
	        	newValue.appendChild(doc.createTextNode(key + ":" + valuesText));
	        	newScalar.appendChild(newValue);
	        }
        }
             
        //create the limits element
        Element scalarLimits = doc.createElement("limits");
        String limitsText = "";
        for(int i = 0; i < scalar.limits.size(); i++)
        {
        	limitsText += scalar.limits.get(i);
        	if(i != scalar.limits.size() - 1)
        	{
        		limitsText +=  " ";
        	}
        }
        scalarLimits.appendChild(doc.createTextNode(limitsText));
        newScalar.appendChild(scalarLimits);
        
        return newScalar;
	}
	
	/** 
	 * Takes a Bundle object and turns it into an XML element, Basically the inverse of ParseBundle()
	 * @param bundle the Bundle object to be turned into XML.
	 * @param doc the document that the XML will be using
	 * @return An XML element based off of the Bundle Object
	 * @see Scanner#ParseBundle(Element eElement)
	 */
	static private  Element BundleToXML(Bundle bundle, Document doc)
	{
		//Create the new Element
        Element newBundle = doc.createElement("scalar");
		
		//create the name element
        Element scalarName = doc.createElement("name");
        scalarName.appendChild(doc.createTextNode(bundle.name));
        newBundle.appendChild(scalarName);
        
        //create the tags element   
        if(bundle.tags != null)
        {
	        Element bundleTags = doc.createElement("tags");
	        String tagsText = "";
	        for(int i = 0; i < bundle.tags.size(); i++)
	        {
	        	tagsText += bundle.tags.get(i);
	        	if(i != bundle.tags.size() - 1)
	        	{
	        		tagsText +=  " ";
	        	}
	        }
	        bundleTags.appendChild(doc.createTextNode(tagsText));
	        newBundle.appendChild(bundleTags);
        }
        
        //create the value elements
        if(bundle.values != null)
        {
	        //get a list of all of the keys
	        List<Integer> keys = new ArrayList<Integer>(bundle.values.keySet());
	        for(int i = 0; i < keys.size(); i++)
	        {
	        	//get key i
	        	int key = keys.get(i);
	        	
	        	//create a new element based on the keys
	        	Element newValue = doc.createElement("value");
	        	newValue.appendChild(doc.createTextNode(key + ":" + bundle.values.get(key)));
	        	newBundle.appendChild(newValue);
	        }
        }
        
        return newBundle;
	}
	
	static public  void Write(Adventurer adventurer, String filePath) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{
		Write(adventurer, new File(filePath));
	}	
	static public  void Write(Adventurer adventurer, File file) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{
		//load the xml file into memory
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();
		
		//Check if the element we want to write already exists, and if so, delete it
		Element existing = FindComponentInXML(adventurer.name, doc, "adventurer");
		if(existing != null)
		{
			existing.getParentNode().removeChild(existing);
		}
		
		//Create a new adventurer 
        Node root = doc.getFirstChild();
        root.appendChild(AdventurerToXML(adventurer, doc));
        
        //write the new modifier to the xml file
        DOMSource source = new DOMSource(doc);
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        StreamResult result = new StreamResult(file);
        transformer.transform(source, result);
	}
	
	static private  Element AdventurerToXML(Adventurer adventurer, Document doc)
	{
		//Create the new Element
        Element newAdventurer = doc.createElement("adventurer");
		
		//create the main class element
        Element mainClass = BundleToXML(adventurer.main_class, doc);
        mainClass.setAttribute("type", "mainclass");
        newAdventurer.appendChild(mainClass);
        
        //create all of the subclasses
        for(int i = 0; i < adventurer.sub_classes.size(); i++)
        {
        	//create the main class element
            Element subClass = BundleToXML(adventurer.sub_classes.get(i), doc);
            subClass.setAttribute("type", "subclass");
            newAdventurer.appendChild(subClass);
        }
        
        //create all of the modifiers
        for(int i = 0; i < adventurer.sub_classes.size(); i++)
        {
        	
        }
        
        //create all of the scalars
        for(int i = 0; i < adventurer.scalars.size(); i++)
        {
        	//create the main class element
            Element newScalar = ScalarToXML(adventurer.scalars.get(i), doc);
            newAdventurer.appendChild(newScalar);
        }
        
      //create all of the scalars
        for(int i = 0; i < adventurer.modifiers.size(); i++)
        {
        	//create the main class element
            Element newScalar = ModifierToXML(adventurer.modifiers.get(i), doc);
            newAdventurer.appendChild(newScalar);
        }
		
		return newAdventurer;
	}
	
	static private  Element ModifierToXML(Modifier modifier, Document doc)
	{
		//Create the new Element
        Element newModifier = doc.createElement("modifier");
		
		//create the name element
        Element modifierName = doc.createElement("name");
        modifierName.appendChild(doc.createTextNode(modifier.name));
        newModifier.appendChild(modifierName);
        
        //create the tags element    
        Element modifierTags = doc.createElement("tags");
        String tagsText = "";
        for(int i = 0; i < modifier.tags.size(); i++)
        {
        	tagsText += modifier.tags.get(i);
        	if(i != modifier.tags.size() - 1)
        	{
        		tagsText +=  " ";
        	}
        }
        modifierTags.appendChild(doc.createTextNode(tagsText));
        newModifier.appendChild(modifierTags);
        
        //create the values element    
        Element modifierValues = doc.createElement("values");
        String valuesText = "";
        for(int i = 0; i < modifier.values.size(); i++)
        {
        	valuesText += modifier.values.get(i).name;
        	if(i != modifier.values.size() - 1)
        	{
        		valuesText +=  " ";
        	}
        }
        modifierValues.appendChild(doc.createTextNode(valuesText));
        newModifier.appendChild(modifierValues);
        
        //create the limits element    
        Element modifierLimits = doc.createElement("limits");
        String limitsText = "";
        for(int i = 0; i < modifier.limits.size(); i++)
        {
        	limitsText += modifier.limits.get(i);
        	if(i != modifier.limits.size() - 1)
        	{
        		limitsText +=  " ";
        	}
        }
        modifierLimits.appendChild(doc.createTextNode(limitsText));
        newModifier.appendChild(modifierLimits);
        
        //create the cost element    
        Element modifierCost = doc.createElement("tcostags");
        String costText = "";
        for(int i = 0; i < modifier.costs.size(); i++)
        {
        	costText += modifier.costs.get(i);
        	if(i != modifier.costs.size() - 1)
        	{
        		costText +=  " ";
        	}
        }
        modifierCost.appendChild(doc.createTextNode(costText));
        newModifier.appendChild(modifierCost);
		
		return newModifier;
	}
	
	static public  void Write(CharacterBuild characterBuild, String filePath) throws ParserConfigurationException, SAXException, IOException
	{
		Write(characterBuild, new File(filePath));
	}
	static public  void Write(CharacterBuild characterBuild, File file) throws ParserConfigurationException, SAXException, IOException
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
	static private  List<Adventurer> LoadAdventurers(NodeList nList)
	{
		//the list to return
		List<Adventurer> adventurers = new ArrayList<Adventurer>();
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
	
	static private  List<CharacterBuild> LoadCharacters(NodeList nList)
	{
		List<CharacterBuild> characters = new ArrayList<CharacterBuild>();
		
		//Loop through all of the character elements
        for (int i = 0; i < nList.getLength(); i++) 
        {
        	//get the character we are currently working on
        	Node nNode = nList.item(i);	
			
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) 
	    	{
				
	    		
	    		Element eElement = (Element) nNode;
	    		
	    		//the name
	    		String name = eElement.getElementsByTagName("name").item(0).getTextContent().trim();
	    		
	    		//all of the class data
	    		List<String> classNames = new ArrayList<String>();
				List<Integer> classLevels = new ArrayList<Integer>();
				List<String> classSubclasses = new ArrayList<String>();
				
				//get all of the modifiers
	    		List<String> modifierNames = Arrays.asList(eElement.getElementsByTagName("modifiers").item(0).getTextContent().trim().split(" "));
				List<String> scalarNames = Arrays.asList(eElement.getElementsByTagName("scalars").item(0).getTextContent().trim().split(" "));
				
				//all of the attributes
				Map<String, Value> attributes = new HashMap<String, Value>();
				
	    		//get all of the class tags, then loop through them
	    		NodeList classList = eElement.getElementsByTagName("class");
	    		for (int n = 0; n < classList.getLength(); n++) 
	            {
	    			//get the current modifier
        			Node classNode = classList.item(n);
                    //System.out.println("\nCurrent Element: " + modifierNode.getNodeName());
                    
                    //then parse it and add it to the list of modifiers
                	if (classNode.getNodeType() == Node.ELEMENT_NODE) 
                	{
                		Element classElement = (Element) classNode;
                		
                		//The name of the class
                		classNames.add(classElement.getElementsByTagName("name").item(0).getTextContent());
                		classLevels.add(Integer.parseInt(classElement.getElementsByTagName("level").item(0).getTextContent()));
                		classSubclasses.add(classElement.getElementsByTagName("subclass").item(0).getTextContent());
                	}
	            }
				
				Node attributesNode = eElement.getElementsByTagName("attributes").item(0);
				//get all of the item tags, and loop through them
				NodeList itemList = ((Element) attributesNode).getElementsByTagName("item");
				
	    		for (int n = 0; n < itemList.getLength(); n++) 
	            {
	    			//get the current modifier
        			Node itemNode = itemList.item(n);
                    
                    //then parse it and add it to the list of modifiers
                	if (itemNode.getNodeType() == Node.ELEMENT_NODE) 
                	{
                		Element itemElement = (Element) itemNode;
                		String[] itemStrings = itemElement.getTextContent().split(":");
                		attributes.put(itemStrings[0], ParseValue(itemStrings[1]));
                	}
	            }
	    		
	    		//System.out.println("--------------" + classNames + "--------------" );
	    		
	    		characters.add(new CharacterBuild(name, modifierNames, scalarNames, classNames, classLevels, classSubclasses, attributes));
	    	}
        }
		
		return characters;
	}
	
	/** 
	 * Takes a bundle xml element and turns it into a modifier object
	 * @param eElement the bundle xml element
	 * @return A bundle object based on the given xml element
	 */
	static private  Bundle ParseBundle(Element eElement)
	{
		//the name of the bundle
		String bundleName = eElement.getElementsByTagName("name").item(0).getTextContent().trim();
		
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
	
	/** 
	 * Takes a list of modifier xml elements and turns them into a list of modifier objects.
	 * @param nList a list of the modifier xml elements
	 * @return a list of modifier objects based on the list of modifier xml elements
	 */
	static private  List<Modifier> LoadModifiers(NodeList nList)
	{
		List<Modifier> modifiers = new ArrayList<Modifier> ();
		
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
        	}
        } 
        return modifiers;
	}
	
	/** 
	 * Takes a modifier xml element and turns it into a modifier object
	 * @param eElement the modifier xml element
	 * @return A modifier object based on the given xml element
	 */
	static private  Modifier ParseModifier(Element eElement)
	{
		//The name of the modifier
        String modifierName = eElement.getElementsByTagName("name").item(0).getTextContent().trim();      
        
        //The tags of the modifier
        List<String> modifierTags;
        if(eElement.getElementsByTagName("tags").getLength() > 0)
    	{
        	modifierTags = Arrays.asList(eElement.getElementsByTagName("tags").item(0).getTextContent().split(" "));
    	}
        else
        {
        	modifierTags = new ArrayList<String>();
        }
                     
        //The values of the modifier
        List<Value> modifierValues = new ArrayList<Value>();
        if(eElement.getElementsByTagName("values").getLength() > 0)
    	{
	        List<String> valueStrings = Arrays.asList(eElement.getElementsByTagName("values").item(0).getTextContent().split(" "));
	        
	        for (int n = 0; n < valueStrings.size(); n++) 
	        {
	        	modifierValues.add(ParseValue(valueStrings.get(n)));
	        }
    	}
       
        //The limits of the modifier
        List<String> modifierLimits;
        if(eElement.getElementsByTagName("limits").getLength() > 0)
    	{
        	modifierLimits = Arrays.asList(eElement.getElementsByTagName("limits").item(0).getTextContent().split(" "));
    	}
        else
        {
        	modifierLimits = new ArrayList<String>();
        }
        
        //The costs of the modifier
        List<String> modifierCosts;
        if(eElement.getElementsByTagName("cost").getLength() > 0)
    	{
    		modifierCosts = Arrays.asList(eElement.getElementsByTagName("cost").item(0).getTextContent().split(" "));
    	}
        else
        {
        	modifierCosts = new ArrayList<String>();
        }
        
        //add the modifier to the big list
        return new Modifier(modifierName, modifierValues, modifierTags, modifierLimits, modifierCosts);
	}

	static private  List<Scalar> LoadScalars(NodeList nList)
	{
		List<Scalar> scalars = new ArrayList<Scalar> ();
		
        //Loop through all of the elements in the xml file
        for (int i = 0; i < nList.getLength(); i++) 
        {
        	Node nNode = nList.item(i);
            //System.out.println("\nCurrent Element :" + nNode.getNodeName());

        	if (nNode.getNodeType() == Node.ELEMENT_NODE) 
        	{
                Element eElement = (Element) nNode;
                
                //add the modifier to the big list
                scalars.add(ParseScalar(eElement));
        	}
        } 
        return scalars;
	}
	
	static private  Scalar ParseScalar(Element eElement)
	{
		//Scalar is a subclass of Bundle, so we can re-use ParseBundle() to get some of what we need
		Bundle bundle = ParseBundle(eElement);
		
		Map<Integer, List<Value>> valueMap = new TreeMap<Integer, List<Value>>();
		
		
		List<String> scalarLimits = new ArrayList<String>();
		if(eElement.getElementsByTagName("limit").getLength() > 0)
		{
			scalarLimits = Arrays.asList(eElement.getElementsByTagName("limit").item(0).getTextContent().split(" "));
		}
		
		//the list of the value tags
		NodeList valueNodes = eElement.getElementsByTagName("value");
		
		for (int i = 0; i < valueNodes.getLength(); i++) 
        {
			//a temporary list to store the values in the tag
			List<Value> valueList = new ArrayList<Value>();
			
			//isolate the names and values
			String[] s = valueNodes.item(i).getTextContent().split(":", 2); //we only split at the first colon
			String[] v = s[1].split(" ");
			
			for (int n = 0; n < v.length; n++) 
	        {
				valueList.add(ParseValue(v[n]));
	        }
			
			valueMap.put(Integer.parseInt(s[0]), valueList);
        }
		
		return new Scalar(bundle.name, bundle.tags, valueMap, scalarLimits);
	}
	
	
	/** 
	 * Takes a value string element and turns it into a value object.  
	 * 
	 * @param string the string value. If it has a name, then the name cannot contain ':', '+', or any digits. It must be in one of the following forms:<br>
	 * [name] ex "strMod" <br>
	 * [value] ex "8"<br>
	 * [name]:[value] ex "coldDmg:8"<br>
	 * [dice]d[sides] ex "3d6"<br>
	 * [name]:[dice]d[sides] ex "coldDmg:3d6"<br>
	 * [dice]d[sides]+[value] ex "3d6+8"<br>
	 * [name]:[dice]d[sides]+[value] ex "coldDmg:3d6+8"<br>
	 * 
	 * @return A value object based on the given string
	 */
	static public Value ParseValue(String string)
	{	
		//if the string has a colon, than it is a named value
		boolean isNamed = false;
		//if the string contains a number, then it has a value
		boolean isValue = false;
		//if the string has a 'd' in it then it is a die value
		boolean isDie = false;
		//if the string has a plus, than it is a hybrid values
		boolean isHybrid = false;
		
		//loop through the string to tell if it has a name
		for(int i = 0; i < string.length(); i++) 
		{
			if(string.charAt(i) == ':')
			{
				isNamed = true;
				break;
			}
		}
		
		//the name of the value, which is by default, the value
		String name = string;
		
		//if it has a name, then separate it from the value
		if(isNamed)
		{
			String[] s = string.split(":");
			
			name = s[0];
			string = s[1];
		}
		
		//loop through the string to tell what type of value it is
		for(int i = 0; i < string.length(); i++) 
		{
			if(Character.isDigit(string.charAt(i)))
			{
				isValue = true;
			}
			else if(string.charAt(i) == 'd')
			{
				isDie = true;
			}
			else if(string.charAt(i) == '+')
			{
				isHybrid = true;
			}
		}
		
		//if it is not a value then it is something that will be added in later, such as a dexMod
		if(!isValue)
		{
			return new Value(string, 0);
		}
		
		//if it is written as a hybrid, such as "3d6+5", then parse it as such
		if(isHybrid)
		{
			//split "3d6+5" into "3d6" and "5"
			String[] hybridNumbers = string.split("\\+");
			//split "3d6" into "3" and "6"
			String[] dieNumbers = hybridNumbers[0].split("d");
	
			//System.out.println(string + " = " + dieNumbers[0] + "d" + dieNumbers[1] + "+" + hybridNumbers[1]);
			
			return new HybridValue(name, Integer.parseInt(dieNumbers[0]), Integer.parseInt(dieNumbers[1]), Integer.parseInt(hybridNumbers[1]));
		}
		else if(isDie) //if it is written as a die, such as "3d6", then parse it as such
		{
			//split "3d6" into "3" and "6"
			String[] dieNumbers = string.split("d");
			
			return new DiceValue(name, Integer.parseInt(dieNumbers[0]), Integer.parseInt(dieNumbers[1]));
		}
		else //if it has a number, then it is a value
		{
			return new Value(name, Integer.parseInt(string));
		}
		
		/*if(noDigits && !isNamed)
		{
			return new Value(string, 0);
		}
		
		String title = string;
		if(isNamed)
		{
			String[] s = string.split(":");
			
			title = s[0];
			string = s[1];
		}
		
		//if it is in the format [Title]:[Value] then separate the value and the title
		
		//if it is only numbers, then it is a number (duh)
		boolean onlyNumbers = true;
		for(int i = 0; i < string.length(); i++) 
		{
			if(!Character.isDigit(string.charAt(i)))
			{
				onlyNumbers = false;
			}
		}
		if(onlyNumbers)
		{
			return new Value(title, Integer.parseInt(string));
		}
		
		//if the first and last chars are numbers and it contains only one d, then it is a dice number
		boolean isDie = false;
		if(Character.isDigit(string.charAt(0)) && Character.isDigit(string.charAt(string.length() - 1)))
		{
			for(int i = 0; i < string.length(); i++) 
			{
				//if the char is a d and the only d
				if(string.charAt(i) == 'd')
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
			String[] diceNumbers = string.split("d");
			
			
			//System.out.println(Integer.parseInt(diceNumbers[0]) + " " + Integer.parseInt(diceNumbers[1]));
			return new DiceValue(title, Integer.parseInt(diceNumbers[0]), Integer.parseInt(diceNumbers[1]));
		}*/
		
		//throw new java.lang.Error("Value format unrecognised");
	}
}