package logic;
public class fakeMath{
	public static Record doFakeMath( CharacterBuild LaughingBarbarian){
		Record r = new Record(LaughingBarbarian);
		for(int i = 1; i <= 20; i++){
			r.map.put(i, (double)i*i);
		}
		return r;
	}
	
}
