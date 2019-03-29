package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


	
public class Record{
	String label;
	HashMap<Integer, Double> map;
	/**
	 * For general use
	 * @param character uses CharacterBuild name for label
	 */
	public Record(CharacterBuild character){
		label = character.name;
		map = new HashMap<>();
	}
	/**
	 * Used to generate headers for tables
	 * @param n number of levels, will default to 1 for numbers below 1
	 */
	public Record(int n)
	{
		int nlevels = n;
		if(nlevels < 1)
		{
			nlevels = 1;
		}
		label = Constants.LABEL_TABLE_NAME;
		map = new HashMap<>();
		for(int i = 1; i <=nlevels; i++)
		{
			map.put(i,(double)i);
		}
	}
	public ArrayList<String> toRow()
	{
		ArrayList<String> row = new ArrayList<>();
		row.add(label);
		for(Double d : map.values())
		{
			row.add(d.toString());
		}
		return row;
	}
}
	