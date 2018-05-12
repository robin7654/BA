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
		GameController.highestBet = bet;
		System.out.println(playerName + " raised to " + bet);
		
		GameController.changeActivePlayer();
		
		//System.out.println("aPC: " + GameController.activePlayerC);
		//System.out.println("aPS: " + GameController.activePlayers);
	}
	
	public void call() {
		if(bet < GameController.highestBet) {
			balance += bet;
			bet = GameController.highestBet;
			balance -= bet;
			GameController.pki.addToLog(playerName + " called " + bet);
		}else {
			GameController.pki.addToLog(playerName + " checked");
		}
		this.acted = true;
		GameController.changeActivePlayer();
	}
	
	public void fold(){
		activeInHand = false;
		this.acted = true;
		GameController.pki.addToLog(playerName + " folded");
		GameController.changeActivePlayer();
		System.out.println(playerName + " folded");
	}
	
	public void setBlind(int n) {
		bet = n;
		balance -= n;
	}
	
	public void decideMove() {
		Random randomGenerator = new Random();
		int rand = randomGenerator.nextInt(10);
		System.out.println(rand);
		if(rand < 0) {
			fold();
			return;
		}
		else if(rand < 0) {
			if(GameController.highestBet == 0) raise(GameController.blind);
			else {
				raise(GameController.highestBet*2);
				return;
			}
		}
		else {
			call();
		}
	}
	
	public void setBet(int n) {
		bet = n;
	}
}
