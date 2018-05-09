import java.util.Random;

public class Player {
	String playerName;
	int balance;
	int bet;
	boolean activeInGame;
	boolean activeInHand;
	boolean allIn;
	boolean bot;
	public Player(boolean b, String pName){
		playerName = pName;
		bot = b;
		balance = 500;
		activeInGame = true;
		activeInHand = true;
		bet = 0;
		allIn = false;
	}
	
	public void raise(int n) {
		if(balance + bet > n) {
			balance = balance - (n - bet);
			bet = n;
		}
		else {
			bet += balance;
			balance = 0;
			allIn = true;
		}
		
		if(GameController.highestBet < bet) {
			GameController.highestBet = bet;
			GameController.activePlayerC = 1;
			System.out.println(playerName + ": raise to " + bet);
		}else {
			GameController.activePlayerC++;
			System.out.println(playerName + ": called " + bet);
		}
		
		GameController.changeActivePlayer();
		
		//System.out.println("aPC: " + GameController.activePlayerC);
		//System.out.println("aPS: " + GameController.activePlayers);
	}
	public void fold(){
		activeInHand = false;
		GameController.changeActivePlayer();
		System.out.println(playerName + ": fold");
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
		else if(rand < 5) {
			if(GameController.highestBet == 0) raise(GameController.blind);
			else {
				raise(GameController.highestBet*2);
				return;
			}
		}
		else {
			raise(GameController.highestBet);
		}
	}
	
	public void setBet(int n) {
		bet = n;
	}
}
