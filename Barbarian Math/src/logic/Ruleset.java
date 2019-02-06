package logic;

import java.util.List;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class Ruleset 
{
	public String name;
	public List<Component> components;
	
	public Ruleset(String name, List<Component> components)
	{
		this.name = name;
		this.components = components;
	}
	
	public Ruleset(String name, String path)
	{
		
	}
}
