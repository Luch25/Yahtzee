import java.util.ArrayList;

public class ScoreboardInfo{
	private int value;
	private boolean isFilled;
	private String name;
	
	public ScoreboardInfo(String name, int val, boolean fill){
		this.name = name;
		value = val;
		isFilled = fill;
	}
	
	public String getName(){
		return name;
	}
	public int getValue(){
		return value;
	}

  public void changeVal(int val){
    value = val;
  }

  public void changeFilled(boolean fill){
    isFilled = fill;
  }
	public boolean getFilled(){
		return isFilled;
	}
}