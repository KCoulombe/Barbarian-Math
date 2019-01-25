package logic;

import java.util.List;

public class Modifier 
{
    String name;
    List<String> values;
    List<String> tags;
    List<String> limits;
    List<String> cost;

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