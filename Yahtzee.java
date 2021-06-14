import java.util.ArrayList;
import java.util.Scanner;

public class Yahtzee{
	/*
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 *
	 */
	 
	 private ArrayList<Dice> diceList;
	 private YahtzeeScoreboard s1;
   private YahtzeeScoreboard s2;
	 private boolean [] isHeld;
	 private int turn;
   private int rollTurn;
   private boolean gameOn;
   private Scanner scan;
   private int willHold;
	 
	 public Yahtzee(){
     scan = new Scanner (System.in);
     willHold = 0;
     gameOn = true;
     rollTurn = 0;
	 	diceList = new ArrayList<Dice>();
	 	turn = 0;
	 	isHeld = new boolean[]{false,false,false,false,false,false};
	 	for(int i = 0; i < 5; i++){
	 		diceList.add(new Dice());
	 	}
	 	s1 = new YahtzeeScoreboard(diceList);
    s2 = new YahtzeeScoreboard(diceList);
	 }
	 
	 public void playGame(){
	 		
	 	System.out.print("What is player ones name? ");
	 	String p1 = scan.next();
	 	
	 	System.out.print("What is player two's name? ");
	 	String p2 = scan.next();
	 	
    int result = 0;
    while(gameOn){
      String name;
      boolean isFirst;
      turn++;
      if(turn % 2 == 0){
        name = p2;
        isFirst = false;
      }else{
        name = p1;
        isFirst = true;
      }
      System.out.println("\n" + name + " it is you turn.");
      for(int i = 0; i < isHeld.length; i ++){
        isHeld[i] = false;
      }

      roll();
      
      if(isFirst){
        if(willHold == 1){
          scoreboard(1);
        }else{
          hold(1);
        }	
      }else{
        if(willHold == 1){
          scoreboard(2);
        }else{
          hold(2);
        }	
      }
      rollTurn = 0;

      if(turn == 26){
        gameOn = false;
        result = checkWinner();
      }else{
        for(int i = 0; i < isHeld.length; i ++){
          isHeld[i] = false;
        }
      }

    }

    if(result == 1){
      System.out.println("\n\nCongrats " + p1 + "! You won!");
    }
    if(result == 2){
      System.out.println("\n\nCongrats " + p2 + "! You won!");
    }
    if(result == 3){
      System.out.println("\n\nWow! A tie!");
    }
  }

  public int checkWinner(){
    int s1sum = s1.getScore();
    int s2sum = s2.getScore();

    if(s1sum > s2sum){
      return 1;
    }
    if(s2sum > s1sum){
      return 2;
    }
    return 3;
  }
    
    public void roll(){
      
      System.out.println("Here are your next dice: ");
      rollTurn++;
      for(int i = 0; i < 5; i++){
        if(!isHeld[i]){
          diceList.get(i).roll();
        }
        System.out.print(diceList.get(i).getSide() + " ");
      }
      System.out.println("\n");

      if(rollTurn < 3){
        willHold = check("Do you want to pick a spot on the scoreboard or roll again? (1 = scoreboard, 2 = hold)" , 1 , 2);
      }
      

    }

    private int check(String question, int lowBound, int highBound){
      int result;
      
      do{
        System.out.print(question + " ");
        result = scan.nextInt();
      }while(result < lowBound || result > highBound);

      return result;
    }

    private int check(String question, int lowBound, int highBound , int bad){
      int result;
      
      do{
        System.out.print(question + " ");
        result = scan.nextInt();
      }while(result < lowBound || (result > highBound || result == bad));

      return result;
    }  
	 
	 public void hold(int player){
	 	if(rollTurn == 3 || willHold == 1){
       scoreboard(player);
    }
    else{
      int numDiceHeld = check("How many dice would you like to hold?" , 1 , 5);
      
      for(int i = 0; i < isHeld.length; i ++){
        isHeld[i] = false;
      }

	 	  if(numDiceHeld != 0){
		 	  System.out.println("Name which dice or die you want to hold:");
		 	  for(int i = 0; i < numDiceHeld; i++){
		 		  String ques = "Held die #" + (i+1) + ") ";
		 		  int diceVal = check(ques , 0 , 6);
          isHeld[diceVal - 1] = true;
		 	  }
	 	  }
	 	  roll();
	 	  hold(player);
    }
	}

   public void scoreboard(int player){
     if(player == 1)
      s1.printBoard();
     else
      s2.printBoard();
    
    int val = 0;


    do{
    int choice = check("Choose which spot you want to fill, from 1 - 14 (Not 7):" , 1 , 14 , 7);
    
    if(player == 1 && !s1.checkIsFilled(choice - 1)){
      s1.changeVal(choice - 1);
      val = 1;
    }
    else if(player == 2 && !s2.checkIsFilled(choice - 1)){
      s2.changeVal(choice - 1);
      val = 1;
    }
    else{System.out.println("Please choose something that is not already filled: ");}

    }while(val == 0);
      
   }
}