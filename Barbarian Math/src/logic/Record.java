package logic;

import java.util.List;


	
public class Record{
	String label;
	HashMap<int, double> map;
	
	public Record(CharacterBuild character){
		label = character.name;
		map = new HashMap<>();
	}
}
	