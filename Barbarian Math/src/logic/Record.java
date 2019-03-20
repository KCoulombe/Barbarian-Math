package logic;

import java.util.List;
import java.util.Map;

	
public class Record{
	String label;
	HashMap<int, double> map;
	
	public Record(CharacterBuild character){
		label = character.name;
		map = new HashMap<>();
	}
}
	
