

import java.util.Random;

//import java.io.IOException;
//import java.io.Writer;
//import java.io.BufferedWriter;
//import java.io.FileWriter;

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
	
	int balancePreFlop = 0;
	int bbPreFlopFifth = 0;
	int potSizeInBBPreFlopFifth = 0;
	int actionPreFlop = 0;
	int hasButton = 0;
	int wasActionPreFlop = 0;
	int card1, card2;
	int timesF = 0;
	int timesC = 0;
	int timesR = 0;
	
	
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
		if(GameController.gameState == 0) actionPreFlop = 2;
		GameController.highestBet = bet;
		System.out.println(playerName + " raised to " + bet);
		
		GameController.changeActivePlayer();
	}
	
	public void call() {
		if(bet < GameController.highestBet && balance + bet > GameController.highestBet) {
			balance += bet;
			//System.out.println("HB:" + GameController.highestBet);
			bet = GameController.highestBet;
			balance -= bet;
			System.out.println(playerName + " called " + bet);
			
		}else if(balance + bet <= GameController.highestBet) {
			bet += balance;
			balance = 0;
			allIn = true;
			
		}else {
			System.out.println(playerName + " checked");
		}
		
		this.acted = true;
		if(GameController.gameState == 0) actionPreFlop = 1;
		GameController.changeActivePlayer();
	}
	
	public void fold(){
		activeInHand = false;
		this.acted = true;
		System.out.println(playerName + " folded");
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
		if(GameController.gameState == 0) actionPreFlop = 0;
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
		
		if(activeInGame) saveSituation();
		
		if(!rand && GameController.gameState == 0) {
			findBestMovePreFlop();
		}else {
		
			Random randomGenerator = new Random();
			int rand = randomGenerator.nextInt(10);
			//System.out.println(rand);
			if(rand < 0 && GameController.highestBet > bet) {
				fold();
				return;
			}
			else if(rand < 6) {
				//System.out.println("Raise " + (balance +bet) + " " + GameController.highestBet);
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
			n = GameController.str.preFlopStrategy[GameController.str.getRating(card1, card2)][hasButton][bbPreFlopFifth][potSizeInBBPreFlopFifth][wasActionPreFlop][i];
			//System.out.println(n);
			if(n > max) {
				max = n;
				j = i;
			}
		}
		
		//if(max > 0) {
			if(j == 0) {
				if(GameController.highestBet > bet) call();
				else call();
			}
			else if(j == 1) {
				call();
			}
			else {
				raise(GameController.highestBet*2);
			}
		/*}
		else {
			Random randomGenerator = new Random();
			int rand = randomGenerator.nextInt(10);
			
			if(rand < 1 && GameController.highestBet > bet) {
				fold();
				return;
			}
			else if(rand < 6) {
				//System.out.println("Raise " + (balance +bet) + " " + GameController.highestBet);
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
		}*/
	}
	
	public void saveSituation() {
		if(GameController.gameState == 0) {
			if(playerNum == 0) {
				card1 = GameController.cardDeck[5];
				card2 = GameController.cardDeck[6];
			}
			else if(playerNum == 1) {
				card1 = GameController.cardDeck[7];
				card2 = GameController.cardDeck[8];
			}
			else {
				card1 = GameController.cardDeck[9];
				card2 = GameController.cardDeck[10];
			}
			
			if(GameController.button == playerNum) hasButton = 1;
			else hasButton = 0;
			
			balancePreFlop = balance;
			bbPreFlopFifth = (balancePreFlop/GameController.blind)/5;
			
			for(int i = 0; i < GameController.player.length; i++) {
				potSizeInBBPreFlopFifth += GameController.player[i].bet;
			}
			
			potSizeInBBPreFlopFifth = (potSizeInBBPreFlopFifth/GameController.blind)/5;
			
			if(GameController.highestBet > GameController.blind) wasActionPreFlop = 1;
			else wasActionPreFlop = 0;
			
		}
	}
	
	public void writeSituation() {
		//System.out.println(playerNum + " " + card1 + " " + card2 + " " + hasButton + " " + bbPreFlopFifth + " " + potSizeInBBPreFlopFifth + " " + wasActionPreFlop + " " + actionPreFlop + ":"  + (balance - balancePreFlop));
		//GameController.str.preFlopStrategy[GameController.str.getRating(card1, card2)][hasButton][bbPreFlopFifth][potSizeInBBPreFlopFifth][wasActionPreFlop][actionPreFlop] += balance - balancePreFlop;
	}
	
	
	
	
}
