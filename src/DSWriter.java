import java.util.Arrays;

public class DSWriter {
	
	
	String[] cards;
	String[] info;
	
	int buttonPos, blind;
	
	int[] stacksPreHand = new int[3];
	
	int[] betsPreFlop = new int[3];
	int[] actionsPreFlop = new int[3];
	
	int[] betsOnFlop = new int[3];
	int[] actionsOnFlop = new int[3];
	int[] stacksOnFlop = new int[3];
	
	int[] betsOnTurn = new int[3];
	int[] actionsOnTurn = new int[3];
	int[] stacksOnTurn = new int[3];
	
	int[] betsOnRiver = new int[3];
	int[] actionsOnRiver = new int[3];
	int[] stacksOnRiver = new int[3];
	
	int[] stacksPostHand = new int[3];
	
	int[] holeCards = new int[6];
	
	
	
	
	int[] boardCards = new int[5];
	int [] flop = new int[3];
	int [] turn = new int[4];
	int [] river = new int[5];
	
	public static int cardCombination(int[] boardCards, int card1, int card2){
		int[] cardsToCheck = new int [boardCards.length + 2];
		for (int i = 0; i < boardCards.length; i++){
			cardsToCheck[i] = boardCards[i];
		}
		cardsToCheck[cardsToCheck.length -1] = card2;
		cardsToCheck[cardsToCheck.length -2] = card1;
		return DetermineWinner.playersHand(cardsToCheck)[0];
	}
	public int highestBoardCard(int[] cards){
		//takes array of board cards
		Arrays.sort(cards);
		return cards[0]/4;	
	}
	public static int highestCardInHandCombination(int[] boardCards, int card1, int card2){
		int[] cardsToCheck = new int [boardCards.length + 2];
		for (int i = 0; i < boardCards.length; i++){
			cardsToCheck[i] = boardCards[i];
		}
		cardsToCheck[cardsToCheck.length -1] = card2;
		cardsToCheck[cardsToCheck.length -2] = card1;
		return DetermineWinner.playersHand(cardsToCheck)[1]/4;
	}
	
	public int getRating(int i, int j) {
		if(i%4 == j%4) {
			if(i/4 > j/4) return Strategies.range[i/4][j/4];
			else return Strategies.range[j/4][i/4];
		}else {
			if(i/4 < j/4) return Strategies.range[i/4][j/4];
			else return Strategies.range[j/4][i/4];
		}
	}
	public int getButton(int playerNum) {
		if(buttonPos == playerNum) {
			return 1;
		} return 0;
	}
	public int potSize(int[] bets) {
		return (bets[0] + bets[1] + bets[2]);
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
	
	public void writeInDSForPreFlop() {
		
		for(int i = 0; i < 3; i++) {			
			int value = stacksPostHand[i] - stacksPreHand[i];
			int action = actionsPreFlop[i];
			int playerBBThird = stacksPreHand[i]/blind/3;
			int cardRating = getRating(holeCards[2*i], holeCards[1+(2*i)]);
			int wasRaisedBySomeoneElse = getWasRaisedBySomeoneElse(i, actionsPreFlop);
			int button = getButton(i);
			int potSizeInBBThird = potSize(betsPreFlop)/(blind*3);
			
			
			GameController.cD.createEntry(value, action, playerBBThird, cardRating, wasRaisedBySomeoneElse, button, potSizeInBBThird);
			
			System.out.println(value);
			System.out.println(action);
			System.out.println(playerBBThird);
			System.out.println(cardRating);
			System.out.println(wasRaisedBySomeoneElse);
			System.out.println(button);
			System.out.println(potSizeInBBThird);
			
		}

	}
	public void writeInDSForFlop() {
		for(int i = 0; i < 3; i++) {
			int value = stacksPostHand[i] - stacksPreHand[i];
			int action = actionsOnFlop[i];
			int cardCombination = cardCombination(flop, holeCards[i*2], holeCards[i*2 +1]); 
			int playerBB = stacksOnFlop[i];
			int wasRaisedBySomeoneElse = getWasRaisedBySomeoneElse(i, actionsOnFlop);
			int highestBoardCardIsInHandCombination = highestCardInHandCombination(flop, holeCards[i*2], holeCards[i*2 +1]);
			int potSize = (potSize(betsPreFlop) + potSize(betsOnFlop))/(blind*3);
			int flushDraw = DetermineWinner.isFlushDraw(holeCards[i*2], holeCards[i*2+1], flop);
			int straightDraw = DetermineWinner.isStraightDraw(holeCards[i*2], holeCards[i*2+1], flop);
			
			GameController.cDF.createEntry(value, action, cardCombination, playerBB, wasRaisedBySomeoneElse, highestBoardCardIsInHandCombination, potSize, flushDraw, straightDraw);
			
			System.out.println(value);
			System.out.println(action);
			System.out.println(cardCombination);
			System.out.println(playerBB);
			System.out.println(wasRaisedBySomeoneElse);
			System.out.println(highestBoardCardIsInHandCombination);

			System.out.println(potSize);
			System.out.println(flushDraw);
			System.out.println(straightDraw);
			
		}
		//TODO
	}
	public void writeInDSForTurn() {
		for(int i = 0; i < 3; i++) {
			int value = stacksPostHand[i] - stacksPreHand[i];
			int action = actionsOnTurn[i];
			int cardCombination = cardCombination(turn, holeCards[i*2], holeCards[i*2 +1]); 
			int playerBB = stacksOnTurn[i];
			int wasRaisedBySomeoneElse = getWasRaisedBySomeoneElse(i, actionsOnTurn);
			int highestBoardCardIsInHandCombination = highestCardInHandCombination(turn, holeCards[i*2], holeCards[i*2 +1]);
			int potSize = (potSize(betsPreFlop) + potSize(betsOnFlop) + potSize(betsOnTurn))/(blind*3);
			int flushDraw = DetermineWinner.isFlushDraw(holeCards[i*2], holeCards[i*2+1], turn);
			int straightDraw = DetermineWinner.isStraightDraw(holeCards[i*2], holeCards[i*2+1], turn);
			
			GameController.cDT.createEntry(value, action, cardCombination, playerBB, wasRaisedBySomeoneElse, highestBoardCardIsInHandCombination, potSize, flushDraw, straightDraw);
			
			System.out.println(value);
			System.out.println(action);
			System.out.println(cardCombination);
			System.out.println(playerBB);
			System.out.println(wasRaisedBySomeoneElse);
			System.out.println(highestBoardCardIsInHandCombination);
			System.out.println(potSize);
			System.out.println(flushDraw);
			System.out.println(straightDraw);
			
		}
		
		//TODO
	}
	public void writeInDSForRiver() {
		for(int i = 0; i < 3; i++) {
			int value = stacksPostHand[i] - stacksPreHand[i];
			int action = actionsOnRiver[i];
			int cardCombination = cardCombination(river, holeCards[i*2], holeCards[i*2 +1]); 
			int playerBB = stacksOnRiver[i];
			int wasRaisedBySomeoneElse = getWasRaisedBySomeoneElse(i, actionsOnRiver);
			int highestBoardCardIsInHandCombination = highestCardInHandCombination(river, holeCards[i*2], holeCards[i*2 +1]);
			int potSize = (potSize(betsPreFlop) + potSize(betsOnFlop) + potSize(betsOnTurn) + potSize(betsOnRiver))/(blind*3);
			
			GameController.cDR.createEntry(value, action, cardCombination, playerBB, wasRaisedBySomeoneElse, highestBoardCardIsInHandCombination, potSize);
			
			
			System.out.println(value);
			System.out.println(action);
			System.out.println(cardCombination);
			System.out.println(playerBB);
			System.out.println(wasRaisedBySomeoneElse);
			System.out.println(highestBoardCardIsInHandCombination);
			System.out.println(potSize);
			
		}
		
		
		//TODO
	}

	public void setVar() {

		
		buttonPos = Integer.parseInt(info[1]);
		blind = Integer.parseInt(info[2]);
		
		for(int i = 0; i < 3; i++) {
			stacksPreHand[i] = Integer.parseInt(info[0].split(",")[i]);
			stacksPostHand[i] = Integer.parseInt(info[11].split(",")[i]);
			
			betsPreFlop[i] = Integer.parseInt(info[3].split(",")[i]);
			actionsPreFlop[i] = Integer.parseInt(info[4].split(",")[i]);
			
			betsOnFlop[i] = Integer.parseInt(info[5].split(",")[i]);
			actionsOnFlop[i] = Integer.parseInt(info[6].split(",")[i]);
			stacksOnFlop[i] = stacksPreHand[i] - betsPreFlop[i];
			
			betsOnTurn[i] = Integer.parseInt(info[7].split(",")[i]);
			actionsOnTurn[i] = Integer.parseInt(info[8].split(",")[i]);
			stacksOnTurn[i] = stacksOnFlop[i] - betsOnFlop[i];
			
			betsOnRiver[i] = Integer.parseInt(info[9].split(",")[i]);
			actionsOnRiver[i] = Integer.parseInt(info[10].split(",")[i]);
			stacksOnRiver[i] = stacksOnTurn[i] - betsOnTurn[i];
		}
		
		holeCards[0] = Integer.parseInt(cards[5]);
		holeCards[1] = Integer.parseInt(cards[6]);
		holeCards[2] = Integer.parseInt(cards[7]);
		holeCards[3] = Integer.parseInt(cards[8]);
		holeCards[4] = Integer.parseInt(cards[9]);
		holeCards[5] = Integer.parseInt(cards[10]);
		
		for(int i = 0; i < 5; i++) {
			boardCards[i] = Integer.parseInt(cards[i]);
		}
		
		for(int i = 0; i < 3; i++) {
			flop[i] = boardCards[i];
			turn[i] = boardCards[i];
			river[i] = boardCards[i];
		}
		turn[3] = boardCards[3];
		river[3] = boardCards[3];
		river[4] = boardCards[4];
		
		
	}
}
