import java.util.ArrayList;


public class RewardEntry {
	
	int rewardCount;
	int rewardValue;
	double rewardVariance;
	ArrayList<Integer> individualRewards;
	
	public RewardEntry(){
		this.rewardCount = 0;
		this.rewardValue = 0;
		this.rewardVariance = 0;
		this.individualRewards = new ArrayList<>();
	}
	public void addEntry(int value){
		rewardCount =  rewardCount +1;
		rewardValue = rewardValue + value;
		rewardVariance = 0;
		int sumOfEntryValues = 0;
		double averageOfEntryValues = 0;
		individualRewards.add(value);
		for (int valueInList: individualRewards){
			sumOfEntryValues += valueInList;
		}
		averageOfEntryValues = sumOfEntryValues / rewardCount;
		for (int valueInList: individualRewards){
			rewardVariance += Math.pow(valueInList - averageOfEntryValues, 2);
		}
		rewardVariance = rewardVariance/rewardCount;
	}
	public double getRewardVariance(){
		return rewardVariance;
	}
	public int getRewardValue(){
		return rewardValue;
	}
}
