
public class Strategies {
	
	CollectedData cD = new CollectedData();
	
	int[][] range = {
//	     A K Q J T  9 8 7 6 5  4 3 2
		{1,2,2,2,2, 3,3,3,3,3, 3,3,3},	//A
		{2,1,2,2,2, 3,3,3,3,3, 3,3,6},	//K
		{2,2,1,2,2, 3,3,3,3,6, 6,6,6},	//Q
		{2,2,2,1,2, 3,3,3,6,6, 6,8,8},	//J
		{2,2,2,2,1, 3,3,3,6,6, 8,8,8},	//T
			
		{3,3,3,3,7, 4,7,8,8,8, 8,8,9},	//9
		{3,3,3,3,6, 7,4,7,8,8, 8,9,9},	//8
		{3,3,6,6,6, 8,7,4,8,9, 9,9,9},	//7
		{3,6,6,6,6, 8,8,8,4,8, 9,9,9},	//6
		{6,6,6,6,3, 9,9,9,8,4, 8,9,9},	//5
		
		{6,6,3,3,3, 9,9,9,9,8, 5,8,9},	//4
		{6,3,3,3,9, 9,9,9,9,9, 8,5,8},	//3
		{6,3,3,9,9, 9,9,9,9,9, 9,8,5},	//2

		
	};
	
	public int getRating(int i, int j) {
		if(i%4 == j%4) {
			if(i/4 > j/4) return range[i/4][j/4];
			else return range[j/4][i/4];
		}else {
			if(i/4 < j/4) return range[i/4][j/4];
			else return range[j/4][i/4];
		}
	}
	
	public int getButton(int playerNum) {	//0-Button	1-Kein Button
		if(GameController.button == playerNum) {
			return 1;
		} return 0;
	}
	
	public int getButton(int playerNum, int buttonPos) {
		if(buttonPos == playerNum) return 1;
		return 0;
	}
	
	public int getPlayerBB(int playerNum) {
		return ((GameController.player[playerNum].balance + GameController.player[playerNum].bet) / GameController.blind)/5;
	}
	public int playerBB(int balance0, int bb){
		return balance0/(bb*5); 
	}

	public int getPotSizeInBB() {
		int pot = 0;
		for(int i = 0; i < GameController.player.length; i++) {
			pot += GameController.player[i].bet;
		}
		return ((GameController.mainPot + pot)/GameController.blind)/5;
	}
	public int potSizeAtPreFlop(String bets) {
		return Integer.parseInt(bets.split(",")[0]) + Integer.parseInt(bets.split(",")[1]) + Integer.parseInt(bets.split(",")[2]);
	}
	public int cardCombination(int[] cards){
		return DetermineWinner.playersHand(cards)[0];
	}
	
	public int getWasRaised() {
		if(GameController.highestBet > GameController.blind) return 1;
		return 0;
	}
	
	public int getWasRaisedBySomeoneElse(int playerNum) {
		if(GameController.player[GameController.getNextPlayerNum(playerNum)].action == 2 || GameController.player[GameController.getNextPlayerNum(GameController.getNextPlayerNum(playerNum))].action == 2)
			return 1;
		return 0;
	}
	
	int[][][][][][] preFlopStrategy = new int[10][2][16][16][2][3];
	//0 - Card Rating
	//1 - Button Position 	-
	//2 - BB before Hand / 5
	//3 - Pot Size in BB    - 
	//4 - Was Raised		- 
	//5 - Fold - Call - Raise
	int[][][][][][] flopStrategy = new int[8][3][3][10][2][3];
	//kartenbild
	//board pair
	//karten gleicher farbe
	//höchste karte
	//wurde geraist
	//fold call raise
	

	
	public void writeInArray(String line0, String line1) {
		
		//System.out.println(line0);
		//System.out.println(line1);
		
		String[] cards = line0.split(" ");
		String[] info = line1.split("\\|");
		
		for(int i = 0; i < 3; i++) {
			int cardRating = getRating(Integer.parseInt(cards[5+(2*i)]),Integer.parseInt(cards[6+(2*i)]));
			int button = getButton(i, Integer.parseInt(info[1]));
			int playerBB = playerBB(Integer.parseInt(info[0].split(",")[i]),Integer.parseInt(info[2]));
			int potSizeAtPreFlop = potSizeAtPreFlop(info[3])/Integer.parseInt(info[2]);
			int wasRaisedBySomeoneElse = getWasRaisedBySomeoneElse(i);
			int actionPreFlop = GameController.player[i].action;
			int value = (GameController.player[i].balance - Integer.parseInt(info[0].split(",")[i]));
			
			//preFlopStrategy[cardRating][button][playerBB][potSizeAtPreFlop][wasRaisedBySomeoneElse][actionPreFlop] 
			//		+= (GameController.player[i].balance - Integer.parseInt(info[0].split(",")[i]));
			
			cD.createEntry(value, cardRating, button, playerBB, potSizeAtPreFlop, wasRaisedBySomeoneElse, actionPreFlop);
			
			
			
			System.out.println(cD.getEntry(cardRating, button, playerBB, potSizeAtPreFlop, wasRaisedBySomeoneElse, actionPreFlop));
			
//			System.out.println(getRating(Integer.parseInt(cards[5+(2*i)]),Integer.parseInt(cards[6+(2*i)])));
//			System.out.println(getButton(i, Integer.parseInt(info[1])));
//			System.out.println(playerBB(Integer.parseInt(info[0].split(",")[i]),Integer.parseInt(info[2])));
//			System.out.println(potSizeAtPreFlop(info[3])/Integer.parseInt(info[2]));
//			System.out.println(getWasRaisedBySomeoneElse(i));
//			System.out.println(GameController.player[i].actionPreFlop);
//			System.out.println(GameController.player[i].balance - Integer.parseInt(info[0].split(",")[i]));
//			System.out.println(preFlopStrategy[cardRating][button][playerBB][potSizeAtPreFlop][wasRaisedBySomeoneElse][actionPreFlop] );
		}

		/*System.out.println(cards.length);
		for(int i = 0; i < cards.length; i++) {
			System.out.print(cards[i]);
		}
		System.out.println();
		
		System.out.println(info.length);
		for(int i = 0; i < info.length; i++) {
			System.out.println(info[i]);
		}*/

	}
	
}
