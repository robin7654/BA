

public class MonteCarloSimulation {

	public static GameContoller gc = new GameContoller();
	
	public int winProbabilityOnRiver(int cards[], int playerNumber, int opponent){
		int[] winners = gc.evaluate();
		if (winners[playerNumber] > winners[opponent]){
			return 1;
		}
		if (winners[playerNumber] == winners[opponent]){
			return 2;
		}
		return 0;
	}
	public int winProbabilityOnRiver(int cards[], int playerNumber, int opponent1, int opponent2){
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
	public static int winProbabilityOnTurn(){
		double winProb = 0
	}
	
	
	
}
