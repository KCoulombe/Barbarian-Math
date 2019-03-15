public Record fakeMath( CharacterBuild LaughingBarbarian){
	Record r = new Record(LaughingBarbarian);
	for(int i = 1; i=<20; i++){
		r.addSet(i , i * i );
	}
	return r;
}