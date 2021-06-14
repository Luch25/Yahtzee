
import java.util.ArrayList;

public class YahtzeeScoreboard{
	
	ArrayList<Dice> diceList;
	
	ArrayList<ScoreboardInfo> boardInfo;
	
	
	public YahtzeeScoreboard(ArrayList<Dice> d1){
		diceList = d1;
		
		setBoard();
		
	}

  public boolean checkIsFilled(int i){
    return boardInfo.get(i).getFilled();
  }

  public int getScore(){
    int counter = 0;
    for(ScoreboardInfo s1 : boardInfo){
      if(s1.getFilled()){
        counter += s1.getValue();
      }
    }
    return counter;
  }

  public void changeVal(int i){
    sort();
    boardInfo.get(i).changeVal(getPosValueInt(i));
    boardInfo.get(i).changeFilled(true);
  }
	
	private void setBoard(){
		boardInfo = new ArrayList<ScoreboardInfo>();
		
		boardInfo.add(new ScoreboardInfo("Ones",0,false));
		boardInfo.add(new ScoreboardInfo("Twos",0,false));
		boardInfo.add(new ScoreboardInfo("Threes",0,false));
		boardInfo.add(new ScoreboardInfo("Fours",0,false));
		boardInfo.add(new ScoreboardInfo("Fives",0,false));
		boardInfo.add(new ScoreboardInfo("Sixes",0,false));
		boardInfo.add(new ScoreboardInfo("Bonus",35,false));
		boardInfo.add(new ScoreboardInfo("Three Of Kind",0,false));
		boardInfo.add(new ScoreboardInfo("Four Of Kind",0,false));
		boardInfo.add(new ScoreboardInfo("Full House",25,false));
		boardInfo.add(new ScoreboardInfo("Small Straight",30,false));
		boardInfo.add(new ScoreboardInfo("Large Straight",40,false));
		boardInfo.add(new ScoreboardInfo("Yahzee",50,false));
		boardInfo.add(new ScoreboardInfo("Chance",0,false));
	}

  public void printBoard(){
    sort();
    System.out.println();
    for(int i = 0; i < 14; i++){
      String row = "";
      row += boardInfo.get(i).getName() + " ";
      if(boardInfo.get(i).getFilled()){
        row += "Filled: " + boardInfo.get(i).getValue();
      }else{
        row += getPosValue(i);
      }
      System.out.println((i+1) + ") " + row + "\n---------------------------------");
    }

    System.out.println("Score: " + getScore() + "\n");
  }

  private String getPosValue( int i){
    if(i == 0)return "" + ones();
    if(i == 1)return "" + twos();
    if(i == 2)return "" + threes();
    if(i == 3)return "" + fours();
    if(i == 4)return "" + fives();
    if(i == 5)return "" + sixes();
    if(i == 6)return bonus();
    if(i == 7)return "" + thrOfKind();
    if(i == 8)return "" + fourOfKind();
    if(i == 9)return "" + fullHouse();
    if(i == 10)return "" + smallStraight();
    if(i == 11)return "" + largeStraight();
    if(i == 12)return "" + yahtzee();
    return "" + sum();
  }

  private String bonus(){
    int val = 63 - checkBonus();

    if(val <= 0) return "" + 35;
    return val + " Left"; 
  }

  private int intBonus(){
    int val = 63 - checkBonus();

    if(val <= 0) return 35;
    return 0; 
  }

  private int checkBonus(){
    int counter = 0;
    for(int i = 0; i < 6; i++){
       if(boardInfo.get(i).getFilled()){
         counter += boardInfo.get(i).getValue();
       }
    }
    return counter;
  }

  private int getPosValueInt(int i){
    if(i == 0)return ones();
    if(i == 1)return twos();
    if(i == 2)return threes();
    if(i == 3)return fours();
    if(i == 4)return fives();
    if(i == 5)return sixes();
    if(i == 6)return intBonus();
    if(i == 7)return thrOfKind();
    if(i == 8)return fourOfKind();
    if(i == 9)return fullHouse();
    if(i == 10)return smallStraight();
    if(i == 11)return largeStraight();
    if(i == 12)return yahtzee();
    return sum();
  }

  private void sort(){
    for(int j = 0; j < diceList.size(); j++){
      for(int i = 0; i < diceList.size() - 1; i ++ ){
        if(diceList.get(i + 1).getSide() < diceList.get(i).getSide()){
          diceList.add(i + 1, diceList.remove(i));
        }
      }
    }
  }

  private int yahtzee(){
    if(ones() >= 5 || (twos() >= 10 || (threes() >= 15 || (fours() >= 20 || (fives() >= 25 || sixes() >= 30))))){
      return 50;
    }
    return 0;
  }

  private int smallStraight(){
    
    int counter = 0;
    for(int i = 0;i < diceList.size() - 1; i ++){
      int dist = diceList.get(i+1).getSide() - diceList.get(i).getSide();
      if(dist > 1 && (i != 0 && i != 3)){
        return 0;
      }
      if(!(dist > 1 && (i == 0 || i == 3))){
        counter += dist;
      }
    }
    if(counter > 2)return 30;
    
    return 0;
  }
  
  private int largeStraight(){
    int counter = 0;
    for(int i = 0;i < diceList.size() - 1; i ++){
      int dist = diceList.get(i+1).getSide() - diceList.get(i).getSide();
      if(dist > 1)return 0;
      counter += dist;
    }
    if(counter == 4)return 40;
    return 0;
  }

  private int thrOfKind(){
    if(ones() >= 3 || (twos() >= 6 || (threes() >= 9 || (fours() >= 12 || (fives() >= 15 || sixes() >= 18))))){
      return sum();
    }
    return 0;
  }

  private int fourOfKind(){
    if(ones() >= 4 || (twos() >= 8 || (threes() >= 12 || (fours() >= 16 || (fives() >= 20 || sixes() >= 24))))){
      return sum();
    }
    return 0;
  }

  private int fullHouse(){
    if(thrOfKind() != 0 && ((diceList.get(0).getSide()) == diceList.get(1).getSide()) && (diceList.get(3).getSide() == diceList.get(4).getSide()))
      return 25;
    return 0;
  }

  private int sum(){
    int counter = 0;
    for(Dice d : diceList){
      counter += d.getSide();
    }
    return counter;
  }

  private int ones(){
    int counter = 0;
    for(Dice d : diceList){
      if(d.getSide() == 1){
        counter ++;
      }
    }
    return counter;
  }
  private int twos(){
    int counter = 0;
    for(Dice d : diceList){
      if(d.getSide() == 2){
        counter ++;
      }
    }
    return counter * 2;
  }
  private int threes(){
    int counter = 0;
    for(Dice d : diceList){
      if(d.getSide() == 3){
        counter ++;
      }
    }
    return counter * 3;
  }
  private int fours(){
    int counter = 0;
    for(Dice d : diceList){
      if(d.getSide() == 4){
        counter ++;
      }
    }
    return counter * 4;
  }
  private int fives(){
    int counter = 0;
    for(Dice d : diceList){
      if(d.getSide() == 5){
        counter ++;
      }
    }
    return counter * 5;
  }
  private int sixes(){
    int counter = 0;
    for(Dice d : diceList){
      if(d.getSide() == 6){
        counter ++;
      }
    }
    return counter * 6;
  }
	
}