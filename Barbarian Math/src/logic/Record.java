package logic;

import java.util.List;
import java.util.HashMap;

	
public class Record{
	String label;
	HashMap<Integer, Double> map;
	
	public Record(CharacterBuild character){
		label = character.name;
		map = new HashMap<>();
	}
}
	
