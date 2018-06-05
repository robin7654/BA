import java.util.HashMap;
import java.util.Map;


public class CollectedData {
	private int cardRating;
	private int button;
	private int playerBB;
	private int potSizeAtPreFlop;
	private int wasRaisedBySomeoneElse;
	private int actionPreFlop;
	
	static Map<CollectedData, RewardEntry> map = new HashMap<CollectedData, RewardEntry>();
	
	public int getCardRating() { return cardRating; }
	public int getButton() { return button; }
	public int getPlayerBB() { return playerBB; }
	public int getPotSizeAtPreFlop() { return potSizeAtPreFlop; }
	public int getWasRaisedBySomeoneElse() { return wasRaisedBySomeoneElse; }
	public int getActionPreFlop() { return actionPreFlop; }
	
	
	public CollectedData(int a, int b, int c, int d, int e, int f){
		this.cardRating = a;
		this.button = b;
		this.playerBB = c;
		this.potSizeAtPreFlop = d;
		this.wasRaisedBySomeoneElse = e;
		this.actionPreFlop = f;
	}
	
	public CollectedData() {
		
	}

	public void createEntry(int value, int a, int b, int c, int d, int e, int f){
		//CollectedData collectedData = new CollectedData(cardRating, button, playerBB, potSizeAtPreFlop, wasRaisedBySomeoneElse, actionPreFlop);
		try {
			RewardEntry rewEnt = this.getEntry(a,b,c,d,e,f).addEntry(value);
			map.put(new CollectedData(a,b,c,d,e,f), rewEnt);
		}catch(Exception z) {
			RewardEntry rewEnt = new RewardEntry(value);
			map.put(new CollectedData(a,b,c,d,e,f), rewEnt);
		}
	}
	
	public RewardEntry getEntry(int a, int b, int c, int d, int e, int f){
			RewardEntry mapValue = map.get(new CollectedData(a,b,c,d,e,f));
			return mapValue;
	}


	@Override
	public boolean equals(Object obj){
		if (obj != null && obj instanceof CollectedData){
			CollectedData dataObj = (CollectedData) obj;
			
			boolean propertyA = cardRating == dataObj.getCardRating();
			boolean propertyB = button == dataObj.getButton();
			boolean propertyC = playerBB == dataObj.getPlayerBB();
			boolean propertyD =	potSizeAtPreFlop == dataObj.getPotSizeAtPreFlop();
			boolean propertyE = wasRaisedBySomeoneElse == dataObj.getWasRaisedBySomeoneElse();
 			boolean propertyF = actionPreFlop == dataObj.getActionPreFlop();

			return propertyA && propertyB && propertyC && propertyD && propertyE && propertyF;
		}
		return false ; 
	}
	
	@Override
	public int hashCode(){
		int hash = 17;
		hash = hash * 31 * cardRating;
		hash = hash * 31 * button;
		hash = hash * 31 * playerBB;
		hash = hash * 31 * potSizeAtPreFlop;
		hash = hash * 31 * wasRaisedBySomeoneElse;			
		hash = hash * 31 * actionPreFlop;
		return hash; 
	}

	
	
	
}
