import java.util.Random;

public class Player {
	int balance;
	int bet;
	boolean activeInGame;
	boolean activeInHand;
	boolean allIn;
	boolean bot;
	public Player(boolean b){
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
	}
	public void fold(){
		activeInHand = false;
	}
	
	public void decideMove() {
		Random randomGenerator = new Random();
		int rand = randomGenerator.nextInt(10);
		if(rand < 0) {
			fold();
			return;
		}
		else if(rand < 5) {
			raise(GameController.blind*2);
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
