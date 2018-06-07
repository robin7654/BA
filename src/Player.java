import java.util.Random;


public class Player {
	String playerName;
	int playerNum;
	int balance;
	int bet;
	boolean activeInGame;
	boolean activeInHand;
	boolean allIn;
	boolean bot;
	boolean rand;
	boolean acted;
	boolean playsForSidePot = false;
	
	//int balance = 0;
	int bbThird = 0;
	int potSizeInBBThird = 0;
	int action = 0;
	int hasButton = 0;
	int wasRaisedBySomeoneElse = 0;
	int card0, card1;
	int timesF = 0;
	int timesC = 0;
	int timesR = 0;
	int[] flop = new int[3];
	int[] turn = new int[4];
	int[] river = new int[5];	
	int flopCombination, turnCombination, riverCombination;
	int highestFlopCardIsInHandCombination, highestTurnCardIsInHandCombination, highestRiverCardIsInHandCombination;
	int flushDrawOnFlop, flushDrawOnTurn;
	int straightDrawOnFlop, straightDrawOnTurn;
	
	
	public Player(boolean b, boolean r, String pName, int pNum){
		playerName = pName;
		playerNum = pNum;
		bot = b;
		rand = r;
		balance = 500;
		activeInGame = true;
		activeInHand = true;
		bet = 0;
		allIn = false;
		acted = false;
	}
	
	public void raise(int n) {
		if(balance + bet > n) {
			balance += bet;
			bet = n;
			balance -= bet;
			
		}
		else {
			bet += balance;
			balance = 0;
			allIn = true;
			
		}
		
		for(int i = 0; i < GameController.player.length; i++) {
			GameController.player[i].acted = false;
		}
		this.acted = true;
		action = 2;
		GameController.highestBet = bet;
		//System.out.println(playerName + " raised to " + bet);
		
		GameController.changeActivePlayer();
	}
	
	public void call() {
		if(bet < GameController.highestBet && balance + bet > GameController.highestBet) {
			balance += bet;
			bet = GameController.highestBet;
			balance -= bet;
			//System.out.println(playerName + " called " + bet);
			
		}else if(balance + bet <= GameController.highestBet) {
			bet += balance;
			balance = 0;
			allIn = true;
			
		}else {
			//System.out.println(playerName + " checked");
		}
		
		this.acted = true;
		action = 1;
		GameController.changeActivePlayer();
	}
	
	public void fold(){
		activeInHand = false;
		this.acted = true;
		//System.out.println(playerName + " folded");
		int[] bets = new int[3];
		for(int i = 0; i < GameController.player.length; i++) {
			bets[i] = GameController.player[i].bet;
		}
		if(GameController.minWithoutZero(bets) >= bet) {
			boolean b = true;
			for(int i = 0; i < 3; i++) {
				if(GameController.player[i].balance + GameController.player[i].bet < bet && GameController.player[i].activeInGame) {
					b = false;
				}
			}
			if(b) {
				GameController.mainPot += bet;
				bet = 0;
			}
			
		}
		
		GameController.changeActivePlayer();
		action = 0;
	}
	
	public void setBlind(int n) {
		if(balance + bet < n) {
			bet += balance;
			balance = 0;
			allIn = true;
			acted = true;
		}else {
			balance += bet;
			bet = n;
			balance -= bet;
		}
	}
	
	public void decideMove() {
		saveVar();
		if(!rand && GameController.gameState == 0) {
			findBestMovePreFlop();
		}else if(!rand && GameController.gameState == 1) {
			findBestMoveOnFlop();
		}else if(!rand && GameController.gameState == 2) {
			findBestMoveOnTurn();
		}
		else if(!rand && GameController.gameState == 3) {
			findBestMoveOnRiver();
		}
		else {
		
			Random randomGenerator = new Random();
			int rand = randomGenerator.nextInt(10);
			if(rand < 0 && GameController.highestBet > bet) {
				fold();
				return;
			}
			else if(rand < 6) {
				if(balance + bet <= GameController.highestBet) call();
				else if(GameController.highestBet == 0) raise(GameController.blind);
				else {
					raise(GameController.highestBet*2);
					return;
				}
			}
			else {
				call();
			}
		}
	}
	
	public void findBestMovePreFlop() {
		int max = 0;
		int n = 0;
		int j = 0;
		for(int i = 0; i < 3; i++) {
			n = getSimilarSituationAveragePreFlop(
					i,
					bbThird,
					GameController.dsW.getRating(card0, card1),
					wasRaisedBySomeoneElse,
					hasButton,
					potSizeInBBThird);
			System.out.println(n);
			if(n > max) {
				max = n;
				j = i;
			}
		}
		if(j == 0) {
			if(GameController.highestBet > bet) fold();
			else call();
		}
		else if(j == 1) {
			call();
		}
		else {
			raise(GameController.highestBet*2);
		}
	}
	
	public void findBestMoveOnFlop() {
		int max = 0;
		int n = 0;
		int j = 0;
		for(int i = 0; i < 3; i++) {
			n = getSimilarSituationAverageOnFlop(
					i,
					flopCombination,
					bbThird,
					wasRaisedBySomeoneElse,
					highestFlopCardIsInHandCombination,
					potSizeInBBThird,
					flushDrawOnFlop,
					straightDrawOnFlop);
			if(n > max) {
				max = n;
				j = i;
			}
		}
		if(j == 0) {
			if(GameController.highestBet > bet) fold();
			else call();
		}
		else if(j == 1) {
			call();
		}
		else {
			raise(GameController.highestBet*2);
		}
	}
	
	public void findBestMoveOnTurn() {
		int max = 0;
		int n = 0;
		int j = 0;
		for(int i = 0; i < 3; i++) {
			n = getSimilarSituationAverageOnTurn(
					i,
					turnCombination,
					bbThird,
					wasRaisedBySomeoneElse,
					highestTurnCardIsInHandCombination,
					potSizeInBBThird,
					flushDrawOnTurn,
					straightDrawOnTurn);
			if(n > max) {
				max = n;
				j = i;
			}
		}
		if(j == 0) {
			if(GameController.highestBet > bet) fold();
			else call();
		}
		else if(j == 1) {
			call();
		}
		else {
			raise(GameController.highestBet*2);
		}
	}
	
	public void findBestMoveOnRiver() {
		int max = 0;
		int n = 0;
		int j = 0;
		for(int i = 0; i < 3; i++) {
			n = getSimilarSituationAverageOnRiver(
					i,
					riverCombination,
					bbThird,
					wasRaisedBySomeoneElse,
					highestRiverCardIsInHandCombination,
					potSizeInBBThird);
			if(n > max) {
				max = n;
				j = i;
			}
		}
		if(j == 0) {
			if(GameController.highestBet > bet) fold();
			else call();
		}
		else if(j == 1) {
			call();
		}
		else {
			raise(GameController.highestBet*2);
		}
	}
	
	public int getSimilarSituationAveragePreFlop(int a, int b, int c, int d, int e, int f) {
		return getSimilarButtonSituationAveragePreFlop(a, b, c, d, e, f);
	}
	
	public int getSimilarButtonSituationAveragePreFlop(int a, int b, int c, int d, int e, int f) {
		if(getSimilarPotSituationAveragePreFlop(a,b,c,d,e,f) > Integer.MIN_VALUE) {
			return getSimilarPotSituationAveragePreFlop(a,b,c,d,e,f);
		}else if(getSimilarPotSituationAveragePreFlop(a,b,c,d,1 - e,f) > Integer.MIN_VALUE){
			return getSimilarPotSituationAveragePreFlop(a,b,c,d,1 - e,f);
		}
		return Integer.MIN_VALUE;
	}
	public int getSimilarPotSituationAveragePreFlop(int a, int b, int c, int d, int e, int f) {
		for(int i = 0; i < 3; i++) {
				if(GameController.cD.getEntry(a,b,c,d,e,f+i) != null)
					return GameController.cD.getEntry(a,b,c,d,e,f+i).getRewardAverage();
					
				if(GameController.cD.getEntry(a,b,c,d,e,f-i) != null)
					return GameController.cD.getEntry(a,b,c,d,e,f-i).getRewardAverage();
		}
		return Integer.MIN_VALUE;
	}
	
	
	public int getSimilarSituationAverageOnFlop(int a, int b, int c, int d, int e, int f, int g, int h) {
		return getSimilarPotSituationAverageOnFlop(a,b,c,d,e,f,g,h);
	}
	
	public int getSimilarPotSituationAverageOnFlop(int a, int b, int c, int d, int e, int f, int g, int h) {
		for(int i = 0; i < 3; i++) {
			if(getSimilarFlushDrawSituationOnFlop(a,b,c,d,e,f+i,g,h) > Integer.MIN_VALUE) return getSimilarFlushDrawSituationOnFlop(a,b,c,d,e,f,g+i,h);
			if(getSimilarFlushDrawSituationOnFlop(a,b,c,d,e,f-i,g,h) > Integer.MIN_VALUE) return getSimilarFlushDrawSituationOnFlop(a,b,c,d,e,f,g-i,h);
		}
		return Integer.MIN_VALUE;
	}
	public int getSimilarFlushDrawSituationOnFlop(int a, int b, int c, int d, int e, int f, int g, int h) {
		if(getSimilarStraightDrawSituationOnFlop(a,b,c,d,e,f,g,h) > Integer.MIN_VALUE) return getSimilarStraightDrawSituationOnFlop(a,b,c,d,e,f,g,h);
		else if(getSimilarStraightDrawSituationOnFlop(a,b,c,d,e,f,1-g,h) > Integer.MIN_VALUE) return getSimilarStraightDrawSituationOnFlop(a,b,c,d,e,f,1-g,1-h);
		return Integer.MIN_VALUE;
	}
	public int getSimilarStraightDrawSituationOnFlop(int a, int b, int c, int d, int e, int f, int g, int h) {
		if(GameController.cDF.getEntry(a, b, c, d, e, f, g, h) != null) return GameController.cDF.getEntry(a, b, c, d, e, f, g, h).getRewardAverage();
		else if(GameController.cDF.getEntry(a, b, c, d, e, f, g,1- h) != null) return GameController.cDF.getEntry(a, b, c, d, e, f, g,1- h).getRewardAverage();
		return Integer.MIN_VALUE;
	}
	
	
	public int getSimilarSituationAverageOnTurn(int a, int b, int c, int d, int e, int f, int g, int h) {
		return getSimilarPotSituationAverageOnTurn(a,b,c,d,e,f,g,h);
	}
	
	public int getSimilarPotSituationAverageOnTurn(int a, int b, int c, int d, int e, int f, int g, int h) {
		for(int i = 0; i < 3; i++) {
			if(getSimilarFlushDrawSituationOnTurn(a,b,c,d,e,f+i,g,h) > Integer.MIN_VALUE) return getSimilarFlushDrawSituationOnTurn(a,b,c,d,e,f,g+i,h);
			if(getSimilarFlushDrawSituationOnTurn(a,b,c,d,e,f-i,g,h) > Integer.MIN_VALUE) return getSimilarFlushDrawSituationOnTurn(a,b,c,d,e,f,g-i,h);
		}
		return Integer.MIN_VALUE;
	}
	public int getSimilarFlushDrawSituationOnTurn(int a, int b, int c, int d, int e, int f, int g, int h) {
		if(getSimilarStraightDrawSituationOnTurn(a,b,c,d,e,f,g,h) > Integer.MIN_VALUE) return getSimilarStraightDrawSituationOnTurn(a,b,c,d,e,f,g,h);
		else if(getSimilarStraightDrawSituationOnTurn(a,b,c,d,e,f,1-g,h) > Integer.MIN_VALUE) return getSimilarStraightDrawSituationOnTurn(a,b,c,d,e,f,1-g,1-h);
		return Integer.MIN_VALUE;
	}
	public int getSimilarStraightDrawSituationOnTurn(int a, int b, int c, int d, int e, int f, int g, int h) {
		if(GameController.cDT.getEntry(a, b, c, d, e, f, g, h) != null) return GameController.cDT.getEntry(a, b, c, d, e, f, g, h).getRewardAverage();
		else if(GameController.cDT.getEntry(a, b, c, d, e, f, g,1- h) != null) return GameController.cDT.getEntry(a, b, c, d, e, f, g,1- h).getRewardAverage();
		return Integer.MIN_VALUE;
	}
	
	public int getSimilarSituationAverageOnRiver(int a, int b, int c, int d, int e, int f) {
		return getSimilarPotSituationAverageOnRiver(a,b,c,d,e,f);
	}
	
	public int getSimilarPotSituationAverageOnRiver(int a, int b, int c, int d, int e, int f) {
		for(int i = 0; i < 3; i++) {
			if(GameController.cDR.getEntry(a, b, c, d, e, f+i) != null) return GameController.cDR.getEntry(a, b, c, d, e, f).getRewardAverage();
			if(GameController.cDR.getEntry(a, b, c, d, e, f-i) != null) GameController.cDR.getEntry(a, b, c, d, e, f).getRewardAverage();
		}
		return Integer.MIN_VALUE;
	}
	
	public void saveVar() {
			if(playerNum == 0) {
				card0 = GameController.cardDeck[5];
				card1 = GameController.cardDeck[6];
			}
			else if(playerNum == 1) {
				card0 = GameController.cardDeck[7];
				card1 = GameController.cardDeck[8];
			}
			else {
				card0 = GameController.cardDeck[9];
				card1 = GameController.cardDeck[10];
			}
			for(int i = 0; i < 3; i++) {
				flop[i] = GameController.cardDeck[i];
				turn[i] = GameController.cardDeck[i];
				river[i] = GameController.cardDeck[i];
			}
			turn[3] = GameController.cardDeck[3];
			river[3] = GameController.cardDeck[3];
			river[4] = GameController.cardDeck[4];
			
			flopCombination = GameController.dsW.cardCombination(flop, card0, card1);
			turnCombination = GameController.dsW.cardCombination(turn, card0, card1);
			riverCombination = GameController.dsW.cardCombination(river, card0, card1);
			
			highestFlopCardIsInHandCombination = GameController.dsW.highestBoardCardIsInHandCombination(flop, card0, card1);
			highestTurnCardIsInHandCombination = GameController.dsW.highestBoardCardIsInHandCombination(turn, card0, card1);
			highestRiverCardIsInHandCombination = GameController.dsW.highestBoardCardIsInHandCombination(river, card0, card1);
			
			flushDrawOnFlop = DetermineWinner.isFlushDraw(card0, card1, flop);
			flushDrawOnTurn = DetermineWinner.isFlushDraw(card0, card1, turn);
			straightDrawOnFlop = DetermineWinner.isStraightDraw(card0, card1, flop);
			straightDrawOnTurn = DetermineWinner.isStraightDraw(card0, card1, turn);
			
			
			if(GameController.button == playerNum) hasButton = 1;
			else hasButton = 0;
			
			bbThird = (balance/GameController.blind)/3;
			
			potSizeInBBThird = GameController.mainPot + GameController.sidePot;
			for(int i = 0; i < 3; i++) {
				potSizeInBBThird += GameController.player[i].bet;
			}
			
			if(GameController.highestBet > bet) wasRaisedBySomeoneElse = 1;
			else wasRaisedBySomeoneElse = 0;
			
	}
	
}
