package logic;

import java.util.ArrayList;

/**
 * Does the math, all the math. 
 * @author Tim
 *
 */
public class Mathemagics {

	public Mathemagics() {
	}
	/**
	 * Outward facing method. should be only visible method
	 * @param builds The CharacterBuilds to be parsed
	 * @return
	 */
	public static Record makeRecords(ArrayList<CharacterBuild> builds)
	{
		return null;
	}
	public Record MathLogic( CharacterBuild player, String mode) {
		Record r = new Record(player); // create the record
		double A = 0.0; // A is dmg done via features
		double B = 0.0;// B is number of hits
		double C = 0.0; // once per turn effects
		double damage=0.0;
		
		//cycles through all of the modifiers in the characterbuild
		for(Modifier m: player.modifiers) {
			String result = ABorC(m);	
				if(result=="A" && mode=="AVG") {
					for(int i =0; i< m.values.size(); i++) {
						A += m.values.get(i).value;
					}
				}
				if(result=="A" && mode.contains("MAX")) {
					for(int i =0; i< m.values.size(); i++) {
						A += m.values.get(i).max;
					}
				}
				if(result=="A" && mode.contains("MIN")) {
					for(int i =0; i< m.values.size(); i++) {
						A += m.values.get(i).min;
					}
				}
				if(result=="B" && mode.contains("AVG")) {
					for(int i =0; i< m.values.size(); i++) {
						B += m.values.get(i).value;
					}
				}if(result=="B" && mode.contains("MAX")) {
					for(int i =0; i< m.values.size(); i++) {
						B += m.values.get(i).max;
					}
				}if(result=="B" && mode.contains("MIN")) {
					for(int i =0; i< m.values.size(); i++) {
						B += m.values.get(i).min;
					}
				}if(result=="C" && mode.contains("AVG")) {
					for(int i =0; i< m.values.size(); i++) {
						C += m.values.get(i).value;
					}
				}if(result=="C" && mode.contains("MAX")) {
					for(int i =0; i< m.values.size(); i++) {
						C += m.values.get(i).max;
					}
				}if(result=="C" && mode.contains("MIN")) {
					for(int i =0; i< m.values.size(); i++) {
						C += m.values.get(i).min;
					}
				}
		}
		damage = smashingGoodFun(A,B,C);
		r.map.put(player.level,damage);
		return r;	//return the record
	}
	public String ABorC (Modifier m) {
		String Albatross = "";
		if(m.tags.contains("oncePerTurn")) {
			Albatross = "C";
		}
		else if(m.tags.contains("extraAttack")){
			Albatross = "B";
		}
		else {
			Albatross = "A";
		}
		return Albatross;
	}
	public static double smashingGoodFun(double A, double B, double C) {
		return (A*B)+C;
	}

}
