public class Dice{
	private int side;
	
	public Dice(){
		roll();
	}
	
	public int roll(){
		side = (int)((Math.random()* 6) + 1);
		return side;
	}
	
	public int getSide(){
		return side;
	}
	
}