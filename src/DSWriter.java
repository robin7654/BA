public class DSWriter {
	
	
	public int getRating(int i, int j) {
		if(i%4 == j%4) {
			if(i/4 > j/4) return Strategies.range[i/4][j/4];
			else return Strategies.range[j/4][i/4];
		}else {
			if(i/4 < j/4) return Strategies.range[i/4][j/4];
			else return Strategies.range[j/4][i/4];
		}
	}
	
	public int getButton(int playerNum) {	//1-Button	0-Kein Button
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
	public int potSizeAtPreFlopFifth(String bets) {
		return (Integer.parseInt(bets.split(",")[0]) + Integer.parseInt(bets.split(",")[1]) + Integer.parseInt(bets.split(",")[2]));
	}
	public int cardCombination(int[] cards){
		return DetermineWinner.playersHand(cards)[0];
	}
	
	public int getWasRaised() {
		if(GameController.highestBet > GameController.blind) return 1;
		return 0;
	}
	
	public int getWasRaisedBySomeoneElse(int playerNum, String s) {
		if(Integer.parseInt(s.split(",")[0]) == 2 || Integer.parseInt(s.split(",")[1]) == 2 || Integer.parseInt(s.split(",")[2]) == 2) {
			if(Integer.parseInt(s.split(",")[playerNum]) == 2) return 0;
			return 1;
		}
		return 0;
	}
	
	public void writeInDS(String line0, String line1) {
		
		String[] cards = line0.split(" ");
		String[] info = line1.split("\\|");
		
		int[] stacksBeforeHand = new int[3];
		for(int i = 0; i < stacksBeforeHand.length; i++) {
			stacksBeforeHand[i] = Integer.parseInt(info[0].split(",")[i]);
		}
		
		writeInDSForPreFlop(cards, info);
		writeInDSForFlop(cards, info);
	}
	
	public void writeInDSForPreFlop(String[] cards, String[] info) {
		
		//System.out.println(line0);
		//System.out.println(line1);
		
		//String[] cards = line0.split(" ");
		//String[] info = line1.split("\\|");
		

		
		for(int i = 0; i < 3; i++) {
			int value = (Integer.parseInt(info[11].split(",")[i]) - Integer.parseInt(info[0].split(",")[i]));
			int action = Integer.parseInt(info[4].split(",")[i]);
			int playerBB = playerBB(Integer.parseInt(info[0].split(",")[i]),Integer.parseInt(info[2]));
			int cardRating = getRating(Integer.parseInt(cards[5+(2*i)]),Integer.parseInt(cards[6+(2*i)]));
			int wasRaisedBySomeoneElse = getWasRaisedBySomeoneElse(i, info[4]);
			int button = getButton(i, Integer.parseInt(info[1]));
			int potSizeInBB = potSizeAtPreFlopFifth(info[3])/Integer.parseInt(info[2]);
			
			
			
			
			//preFlopStrategy[cardRating][button][playerBB][potSizeAtPreFlop][wasRaisedBySomeoneElse][actionPreFlop] 
			//		+= (GameController.player[i].balance - Integer.parseInt(info[0].split(",")[i]));
			
			GameController.cD.createEntry(value, action, playerBB, cardRating, wasRaisedBySomeoneElse, button, potSizeInBB);
			//reihenfolge  GameController.player[i].action; verändern
			
			System.out.println(value);
			System.out.println(action);
			System.out.println(playerBB);
			System.out.println(cardRating);
			System.out.println(wasRaisedBySomeoneElse);
			System.out.println(button);
			System.out.println(potSizeInBB);
			
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
	public void writeInDSForFlop(String[] cards, String[] info) {
		
		for(int i = 0; i < 3; i++) {
			int value = (Integer.parseInt(info[11].split(",")[i]) - Integer.parseInt(info[0].split(",")[i]));
			int action = Integer.parseInt(info[6].split(",")[i]);
			int playerBB = playerBB(Integer.parseInt(info[0].split(",")[i]),Integer.parseInt(info[2]));
			int cardRating = getRating(Integer.parseInt(cards[5+(2*i)]),Integer.parseInt(cards[6+(2*i)]));
			int wasRaisedBySomeoneElse = getWasRaisedBySomeoneElse(i, info[4]);
			int button = getButton(i, Integer.parseInt(info[1]));
			int potSizeInBB = potSizeAtPreFlopFifth(info[3])/Integer.parseInt(info[2]);
		}
	}
}
