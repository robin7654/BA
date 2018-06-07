import java.util.HashMap;
import java.util.Map;


public class TurnCollectedData {
	
	private int action;
	private int cardCombination;
	private int playerBB;
	private int wasRaisedBySomeoneElse;
	private int highestBoardCardIsInHandCombination;
	private int potSize;
	private int flushDraw;
	private int straightDraw;
	

	
	static Map<TurnCollectedData, RewardEntry> map = new HashMap<TurnCollectedData, RewardEntry>();
	
	public int getAction() { return action; }
	public int getcardCombination() { return cardCombination; }
	public int getPlayerBB() { return playerBB; }
	public int getWasRaisedBySomeoneElse() { return wasRaisedBySomeoneElse; }
	public int getHighestBoardCardIsInHandCombination() { return highestBoardCardIsInHandCombination; }
	public int getPotSizeAtTurn() { return potSize; }
	public int getFlushDraw() { return flushDraw; }
	public int getStraightDraw() { return straightDraw; }
	

	
	public TurnCollectedData(int a, int b, int c, int d, int e, int f, int g, int h){
		this.action = a;
		this.cardCombination = b;
		this.playerBB = c;
		this.wasRaisedBySomeoneElse = d;
		this.highestBoardCardIsInHandCombination = e;
		this.potSize = f;
		this.flushDraw = g;
		this.straightDraw = h;
	}
	
	public TurnCollectedData() {
		
	}
	
	public void createEntry(int value, int a, int b, int c, int d, int e, int f, int g, int h){
		try {
			
			RewardEntry rewEnt = map.get(new TurnCollectedData(a,b,c,d,e,f,g,h));
			rewEnt.addEntry(value);
			
			
			map.put(new TurnCollectedData(a,b,c,d,e,f,g,h), rewEnt);
		}catch(Exception z) {
			map.put(new TurnCollectedData(a,b,c,d,e,f,g,h), new RewardEntry(value));
		}
	}
	
	public RewardEntry getEntry(int a, int b, int c, int d, int e, int f, int g, int h){
			RewardEntry mapValue = map.get(new TurnCollectedData(a,b,c,d,e,f,g,h));
			return mapValue;
	}



	@Override
	public boolean equals(Object obj){
		if (obj != null && obj instanceof TurnCollectedData){
			TurnCollectedData dataObj = (TurnCollectedData) obj;
			
			boolean propertyA = action == dataObj.getAction();
			boolean propertyB = cardCombination == dataObj.getcardCombination();
			boolean propertyC = playerBB == dataObj.getPlayerBB();
			boolean propertyD = wasRaisedBySomeoneElse == dataObj.getWasRaisedBySomeoneElse();
			boolean propertyE = highestBoardCardIsInHandCombination == dataObj.getHighestBoardCardIsInHandCombination();
			boolean propertyF =	potSize == dataObj.getPotSizeAtTurn();
			boolean propertyG = flushDraw == dataObj.getFlushDraw();
			boolean propertyH = straightDraw == dataObj.getStraightDraw();

			return propertyA && propertyB && propertyC && propertyD && propertyE && propertyF && propertyG && propertyH ;
		}
		return false ; 
	}
	
	@Override
	public int hashCode(){
		int hash = 17;
		hash = hash * 31 * action;
		hash = hash * 31 * cardCombination;
		hash = hash * 31 * playerBB;
		hash = hash * 31 * wasRaisedBySomeoneElse;
		hash = hash * 31 * highestBoardCardIsInHandCombination;		
		hash = hash * 31 * potSize;
		hash = hash * 31 * flushDraw;
		hash = hash * 31 * straightDraw;
		return hash; 
	}

}
