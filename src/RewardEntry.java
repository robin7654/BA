import java.util.ArrayList;


public class RewardEntry {
	
	int rewardCount;
	int rewardValue;
	double rewardAverage;
	double rewardVariance;
	ArrayList<Integer> individualRewards;
	
	public RewardEntry(int v){
		this.rewardCount = 1;
		this.rewardValue = v;
		this.rewardVariance = 0;
		this.individualRewards = new ArrayList<>();
	}
	public void addEntry(int value){
		rewardCount =  rewardCount +1;
		rewardValue = rewardValue + value;
		rewardVariance = 0;
		int sumOfEntryValues = 0;
		rewardAverage = 0;
		individualRewards.add(value);
		for (int valueInList: individualRewards){
			sumOfEntryValues += valueInList;
		}
		rewardAverage = sumOfEntryValues / rewardCount;
		for (int valueInList: individualRewards){
			rewardVariance += Math.pow(valueInList - rewardAverage, 2);
		}
		rewardVariance = rewardVariance/rewardCount;
	}
	public int getRewardAverage(){
		return (int) rewardAverage;
	}
	public double getRewardVariance(){
		return rewardVariance;
	}
	public int getRewardValue(){
		return rewardValue;
	}

}
