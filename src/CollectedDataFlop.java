import java.util.HashMap;
import java.util.Map;


public class CollectedDataFlop {

	private int cardRating;
	private int playerBB;
	private int highestBoardCard;
	private int wasRaisedBySomeoneElse;
	private int highestBoardCardIsInHandCombination;
	private int potSizeAtFlop;
	private int flushDraw;
	private int straightDraw;
	
	
	
	static Map<CollectedData, RewardEntry> map = new HashMap<CollectedData, RewardEntry>();
	
	public int getActionPreFlop() { return actionPreFlop; }
	public int getPlayerBB() { return playerBB; }
	public int getCardRating() { return cardRating; }
	public int getWasRaisedBySomeoneElse() { return wasRaisedBySomeoneElse; }
	public int getButton() { return button; }
	public int getPotSizeAtPreFlop() { return potSizeAtPreFlop; }
	

	
	public CollectedData(int a, int b, int c, int d, int e, int f){
		this.actionPreFlop = a;
		this.playerBB = b;
		this.cardRating = c;
		this.wasRaisedBySomeoneElse = d;
		this.button = e;
		this.potSizeAtPreFlop = f;
	}
	
	public CollectedData() {
		
	}
	
	public void createEntry(int value, int a, int b, int c, int d, int e, int f){
		//CollectedData collectedData = new CollectedData(cardRating, button, playerBB, potSizeAtPreFlop, wasRaisedBySomeoneElse, actionPreFlop);
		try {
			
			RewardEntry rewEnt = map.get(new CollectedData(a,b,c,d,e,f));
			rewEnt.addEntry(value);
			
			
			map.put(new CollectedData(a,b,c,d,e,f), rewEnt);
		}catch(Exception z) {
			map.put(new CollectedData(a,b,c,d,e,f), new RewardEntry(value));
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
			
			
			boolean propertyA = actionPreFlop == dataObj.getActionPreFlop();
			boolean propertyB = playerBB == dataObj.getPlayerBB();
			boolean propertyC = cardRating == dataObj.getCardRating();
			boolean propertyD = wasRaisedBySomeoneElse == dataObj.getWasRaisedBySomeoneElse();
			boolean propertyE = button == dataObj.getButton();
			boolean propertyF =	potSizeAtPreFlop == dataObj.getPotSizeAtPreFlop();
			
 			

			return propertyA && propertyB && propertyC && propertyD && propertyE && propertyF;
		}
		return false ; 
	}
	
	@Override
	public int hashCode(){
		int hash = 17;
		hash = hash * 31 * actionPreFlop;
		hash = hash * 31 * playerBB;
		hash = hash * 31 * cardRating;
		hash = hash * 31 * wasRaisedBySomeoneElse;
		hash = hash * 31 * button;			
		hash = hash * 31 * potSizeAtPreFlop;
		return hash; 
	}

}
