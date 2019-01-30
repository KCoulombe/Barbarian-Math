package logic;

import java.util.List;

public class Modifier 
{
	public String name;
	public List<String> values;
	public List<String> tags;
	public List<String> limits;
	public List<String> cost;

    public Modifier(String name, List<String> values, List<String> tags, List<String> limits, List<String> cost)
    {
        this.name = name;
        this.values = values;
        this.tags = tags;
        this.limits = limits;
        this.cost = cost;
    }

    public Modifier(String name, String path)
    {

    }
}