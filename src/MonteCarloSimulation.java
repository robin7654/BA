import java.util.stream.IntStream;



public class MonteCarloSimulation {

	public static GameContoller gc = new GameContoller();
	
	public static int winProbabilityOnRiver(int cards[], int playerNumber, int opponent){
		int[] winners = gc.evaluate();
		if (winners[playerNumber] > winners[opponent]){
			return 1;
		}
		if (winners[playerNumber] == winners[opponent]){
			return 2;
		}
		return 0;
	}
	
	public static int winProbabilityOnRiver(int cards[], int playerNumber, int opponent1, int opponent2){
		int[] winners = gc.evaluate();
		//verlierer
		if (winners[playerNumber] < winners[opponent1] | winners[playerNumber] < winners[opponent2]){
			return 0;
		}
		//gewinner
		if (winners[playerNumber] > winners[opponent1] & winners[playerNumber] > winners[opponent2]){
			return 1;
		}
		//split 3
		if (winners[playerNumber] == winners[opponent1] & winners[opponent1] == winners[opponent2]){
			return 3;
		}
		//split 2
		if (winners[playerNumber] > winners[opponent1] | winners[playerNumber] > winners[opponent2]){
			return 2;
		}
		return -1; 
	}
	public static int winProbabilityOnTurn(int cards[], int playerNumber, int opponent1, int opponent2){
		int [] cardsWithoutRiver = cards;
		cardsWithoutRiver[4] = -1;
		double wins = 0; 
		for (int i = 0; i < 52; i++){
			int a = i;
			if (IntStream.of(cardsWithoutRiver).anyMatch(x -> x == a) == false){
				cardsWithoutRiver[4]= i;
				wins = wins + 1/winProbabilityOnRiver(cards, playerNumber, opponent1, opponent2);
			}
		}
		return (int)  (100*wins/42) ;
	}
	public static int winProbabilityOnTurn(int cards[], int playerNumber, int opponent){
		int [] cardsWithoutRiver = cards;
		cardsWithoutRiver[4] = -1;
		double wins = 0; 
		for (int i = 0; i < 52; i++){
			int a = i;
			if (IntStream.of(cardsWithoutRiver).anyMatch(x -> x == a) == false){
				cardsWithoutRiver[4]= i;
				wins = wins + 1/winProbabilityOnRiver(cards, playerNumber, opponent);
			}
		}
		return (int)  (100*wins/42) ;
	}
	public int winProbabilityOnFlop(int cards[], int playerNumber, int opponent1, int opponent2){
		int [] cardsWithoutTurn = cards;
		cardsWithoutTurn[4] = -1;
		cardsWithoutTurn[3] = -1;
		double wins = 0; 
		for (int i = 0; i < 52; i++){
			int a = i;
			if (IntStream.of(cards).anyMatch(x -> x == a) == false){
				cards[3]= i;
				wins = wins + 1/winProbabilityOnTurn(cards, playerNumber, opponent1, opponent2);
			}
		}
		return (int)  (100*wins/43) ;
	}
	public int winProbabilityOnFlop(int cards[], int playerNumber, int opponent){
		int [] cardsWithoutTurn = cards;
		cardsWithoutTurn[4] = -1;
		cardsWithoutTurn[3] = -1;
		double wins = 0; 
		for (int i = 0; i < 52; i++){
			int a = i;
			if (IntStream.of(cards).anyMatch(x -> x == a) == false){
				cards[3]= i;
				wins = wins + 1/winProbabilityOnTurn(cards, playerNumber, opponent);
			}
		} 
		return (int)  (100*wins/43) ;
	}
	
	
	
	
	
}
