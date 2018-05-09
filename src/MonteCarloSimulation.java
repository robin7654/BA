import java.util.stream.IntStream;



public class MonteCarloSimulation {
	
	public int winProbabilityOnRiver(int cards[], int playerNumber, int opponent){
		int[] winners = GameController.evaluate(cards);
		if (winners[playerNumber] > winners[opponent]){
			return 1;
		}
		if (winners[playerNumber] == winners[opponent]){
			return 2;
		}
		return 0;
	}
	
	public int winProbabilityOnRiver(int cards[], int playerNumber, int opponent1, int opponent2){
		int[] winners = GameController.evaluate(cards);
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
	public int winProbabilityOnTurn(int cards[], int playerNumber, int opponent1, int opponent2){
		int [] cardsWithoutRiver = cards.clone();
		cardsWithoutRiver[4] = -1;
		double wins = 0;
		for (int i = 0; i < 52; i++){
			int a = i;
			if (IntStream.of(cardsWithoutRiver).anyMatch(x -> x == a) == false){
				cardsWithoutRiver[4]= i;
				int winProbabilityOnRiver = winProbabilityOnRiver(cardsWithoutRiver, playerNumber, opponent1, opponent2);
				if (winProbabilityOnRiver != 0)wins = wins + 1/winProbabilityOnRiver;
				
			}
		}
		return (int)  (100*wins/42) ;
	}
	public int winProbabilityOnTurn(int cards[], int playerNumber, int opponent){
		int [] cardsWithoutRiver = cards.clone();
		cardsWithoutRiver[4] = -1;
		double wins = 0; 
		for (int i = 0; i < 52; i++){
			int a = i;
			if (IntStream.of(cardsWithoutRiver).anyMatch(x -> x == a) == false){
				cardsWithoutRiver[4]= i;
				int winProbabilityOnRiver = winProbabilityOnRiver(cardsWithoutRiver, playerNumber, opponent);
				if (winProbabilityOnRiver != 0)wins = wins + 1/winProbabilityOnRiver;
			}
		}
		return (int)  (100*wins/42) ;
	}
	public int winProbabilityOnFlop(int cards[], int playerNumber, int opponent1, int opponent2){
		int [] cardsWithoutTurn = cards.clone();
		cardsWithoutTurn[4] = -1;
		cardsWithoutTurn[3] = -1;
		double wins = 0; 
		for (int i = 0; i < 52; i++){
			int a = i;
			if (IntStream.of(cardsWithoutTurn).anyMatch(x -> x == a) == false){
				cardsWithoutTurn[3]= i;
				int winProbabilityOnTurn = winProbabilityOnTurn(cardsWithoutTurn, playerNumber, opponent1, opponent2);
				if (winProbabilityOnTurn != 0)wins = wins + winProbabilityOnTurn;
				
			}
		}
		return (int)  (wins/43) ;
	}
	public int winProbabilityOnFlop(int cards[], int playerNumber, int opponent){
		int [] cardsWithoutTurn = cards.clone();
		cardsWithoutTurn[4] = -1;
		cardsWithoutTurn[3] = -1;
		double wins = 0; 
		for (int i = 0; i < 52; i++){
			int a = i;
			if (IntStream.of(cardsWithoutTurn).anyMatch(x -> x == a) == false){
				cardsWithoutTurn[3]= i;
				int winProbabilityOnTurn = winProbabilityOnTurn(cardsWithoutTurn, playerNumber, opponent);
				if (winProbabilityOnTurn != 0)wins = wins + winProbabilityOnTurn;
			}
		}
		return (int)  (wins/43) ;
	}
	
	
	
	
	
}
