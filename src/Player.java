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
			allIn = true;
		}
		if(bet == n) GameController.activePlayerC++;
		else if(n == GameController.highestBet) GameController.activePlayerC++;
		else GameController.activePlayerC = 0;
		
		GameController.changeActivePlayer();
		System.out.println(playerName + ": raise to " + bet);
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
		if(rand < 0) {
			fold();
			return;
		}
		else if(rand < 5) {
			raise(GameController.highestBet*2);
			return;
		}
		else {
			raise(GameController.highestBet);
		}
	}
	
	public void setBet(int n) {
		bet = n;
	}
}
