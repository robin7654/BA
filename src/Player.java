import java.util.Random;

public class Player {
	String playerName;
	int balance;
	int bet;
	boolean activeInGame;
	boolean activeInHand;
	boolean allIn;
	boolean bot;
	boolean acted;
	boolean playsForSidePot = false;
	int bbBefore;
	int balanceBefore;
	int actionPreFlop;
	public Player(boolean b, String pName){
		playerName = pName;
		bot = b;
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
		//GameController.pki.addToLog(playerName + " raised to " + bet);
		System.out.println(playerName + " raised to " + bet);
		
		GameController.changeActivePlayer();
		
		//System.out.println("aPC: " + GameController.activePlayerC);
		//System.out.println("aPS: " + GameController.activePlayers);
	}
	
	public void call() {
		if(bet < GameController.highestBet && balance + bet > GameController.highestBet) {
			balance += bet;
			bet = GameController.highestBet;
			balance -= bet;
			//GameController.pki.addToLog(playerName + " called " + bet);
			System.out.println(playerName + " called " + bet);
		}else if(balance + bet <= GameController.highestBet) {
			bet += balance;
			balance = 0;
			allIn = true;
		}else {
			System.out.println(playerName + " checked");
			//GameController.pki.addToLog(playerName + " checked");
		}
		this.acted = true;
		if(GameController.gameState == 0) actionPreFlop = 1;
		GameController.changeActivePlayer();
	}
	
	public void fold(){
		activeInHand = false;
		this.acted = true;
		//GameController.pki.addToLog(playerName + " folded");
		GameController.changeActivePlayer();
		//System.out.println(playerName + " folded");
		if(GameController.gameState == 0) actionPreFlop = 0;
	}
	
	public void setBlind(int n) {
		bet = n;
		balance -= n;
	}
	
	public void decideMove() {
		Random randomGenerator = new Random();
		int rand = randomGenerator.nextInt(10);
		//System.out.println(rand);
		if(rand < 0) {
			fold();
			return;
		}
		else if(rand < 2) {
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
