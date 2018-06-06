public class DSWriter {
	
	
	String[] cards;
	String[] info;
	
	int buttonPos, blind;
	
	int[] stacksPreHand = new int[3];
	
	int[] betsPreFlop = new int[3];
	int[] actionsPreFlop = new int[3];
	
	int[] betsOnFlop = new int[3];
	int[] actionsOnFlop = new int[3];
	
	int[] betsOnTurn = new int[3];
	int[] actionsOnTurn = new int[3];
	
	int[] betsOnRiver = new int[3];
	int[] actionsOnRiver = new int[3];
	
	int[] stacksPostHand = new int[3];
	
	int[] holeCards = new int[6];
	
	
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
		if(buttonPos == playerNum) {
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
	public int potSizeAtPreFlopInBB(int[] bets) {
		return (bets[0] + bets[1] + bets[2])/blind;
	}
	public int cardCombination(int[] cards){
		return DetermineWinner.playersHand(cards)[0];
	}
	
	public int getWasRaised() {
		if(GameController.highestBet > GameController.blind) return 1;
		return 0;
	}
	
	public int getWasRaisedBySomeoneElse(int playerNum, int[] i) {
		if(i[0] == 2 || i[1] == 2 || i[2] == 2) {
			if(i[playerNum] == 2) return 0;
			return 1;
		}
		return 0;
	}
	
	public void writeInDS(String line0, String line1) {
		
		cards = line0.split(" ");
		info = line1.split("\\|");
		
		setVar();
		
		
		
		writeInDSForPreFlop();
		writeInDSForFlop();
		writeInDSForTurn();
		writeInDSForRiver();
	}
	
	public void setVar() {

		
		buttonPos = Integer.parseInt(info[1]);
		blind = Integer.parseInt(info[2]);
		
		for(int i = 0; i < 3; i++) {
			stacksPreHand[i] = Integer.parseInt(info[0].split(",")[i]);
		}
		
		for(int i = 0; i < 3; i++) {
			stacksPostHand[i] = Integer.parseInt(info[11].split(",")[i]);
		}
		
		for(int i = 0; i < 3; i++) {
			betsPreFlop[i] = Integer.parseInt(info[3].split(",")[i]);
		}
		
		for(int i = 0; i < 3; i++) {
			actionsPreFlop[i] = Integer.parseInt(info[4].split(",")[i]);
		}
		for(int i = 0; i < 3; i++) {
			betsOnFlop[i] = Integer.parseInt(info[5].split(",")[i]);
		}
		
		for(int i = 0; i < 3; i++) {
			actionsOnFlop[i] = Integer.parseInt(info[6].split(",")[i]);
		}
		for(int i = 0; i < 3; i++) {
			betsOnTurn[i] = Integer.parseInt(info[7].split(",")[i]);
		}
		
		for(int i = 0; i < 3; i++) {
			actionsOnTurn[i] = Integer.parseInt(info[8].split(",")[i]);
		}
		for(int i = 0; i < 3; i++) {
			betsOnRiver[i] = Integer.parseInt(info[9].split(",")[i]);
		}
		
		for(int i = 0; i < 3; i++) {
			actionsOnRiver[i] = Integer.parseInt(info[10].split(",")[i]);
		}
		
		holeCards[0] = Integer.parseInt(cards[5]);
		holeCards[1] = Integer.parseInt(cards[6]);
		holeCards[2] = Integer.parseInt(cards[7]);
		holeCards[3] = Integer.parseInt(cards[8]);
		holeCards[4] = Integer.parseInt(cards[9]);
		holeCards[5] = Integer.parseInt(cards[10]);
	}
	
	public void writeInDSForPreFlop() {
		
		for(int i = 0; i < 3; i++) {
//			int value = (Integer.parseInt(info[11].split(",")[i]) - Integer.parseInt(info[0].split(",")[i]));
//			int action = Integer.parseInt(info[4].split(",")[i]);
//			int playerBB = playerBB(Integer.parseInt(info[0].split(",")[i]),Integer.parseInt(info[2]));
//			int cardRating = getRating(Integer.parseInt(cards[5+(2*i)]),Integer.parseInt(cards[6+(2*i)]));
//			int wasRaisedBySomeoneElse = getWasRaisedBySomeoneElse(i, info[4]);
//			int button = getButton(i, Integer.parseInt(info[1]));
//			int potSizeInBB = potSizeAtPreFlopFifth(info[3])/Integer.parseInt(info[2]);
			
			int value = stacksPostHand[i] - stacksPreHand[i];
			int action = actionsPreFlop[i];
			int playerBB = playerBB(stacksPreHand[i], blind);
			int cardRating = getRating(holeCards[2*i], holeCards[1+(2*i)]);
			int wasRaisedBySomeoneElse = getWasRaisedBySomeoneElse(i, actionsPreFlop);
			int button = getButton(i);
			int potSizeInBB = potSizeAtPreFlopInBB(betsPreFlop);
			
			
			GameController.cD.createEntry(value, action, playerBB, cardRating, wasRaisedBySomeoneElse, button, potSizeInBB);
			
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
	public void writeInDSForFlop() {
		//TODO
	}
	public void writeInDSForTurn() {
		//TODO
	}
	public void writeInDSForRiver() {
		//TODO
	}

}
