package com.stablesort.challenge.potsofgold;

import com.stablesort.challenge.potsofgold.PotsOfGoldGame.Player;

/**
 * Simple strategy - just add the all the even coins at the start of the game. Compare that to adding up all of the odd coins.
 * Whichever is greater, keep on picking either the odd or even positioned coins. This only works if the number of coins is
 * even and you are the first player to go.
 * 
 * @author Andre
 */
public class PotsOfGoldOddEvenStrategy implements Player {
	
	private final String name;
	private final boolean alwaysUseEvens;
	
	public PotsOfGoldOddEvenStrategy(String name, int[] ar) {
		this.name = name;
		this.alwaysUseEvens = this.useEvenStrategy(ar, 0, ar.length-1);
	}
	
	/**
	 * recalculate if you'd like to adjust strategy mid flight
	 * 
	 * @param coins
	 * @param l - inclusive
	 * @param r - inclusive
	 * @return
	 */
	boolean useEvenStrategy(int[] coins, int l, int r) {
		int totalEven = 0;
		int totalOdd = 0;		
		boolean even = true;
		for (int i = l; i <= r; i++) {
			if (even) {
				totalEven += coins[i];
			} else {
				totalOdd += coins[i];
			}
			even = !even;
		}
		
		System.out.println(name + ": useEvenStrategy: totalEven="+totalEven + ", totalOdd=" + totalOdd + ", use evens=" + (totalEven > totalOdd));
		return totalEven > totalOdd;
	}
	
	/**
	 * If the strategy is to alwaysTakeEven, then 
	 * @param coins
	 * @param l - inclusive index to the array left bound, starting at zero
	 * @param r - inclusive index to the array right bound
	 * @return
	 */
	@Override
	public boolean takeLeft(int[] coins, int l, int r) {	
				
		if (alwaysUseEvens) {
			if ((l & 1) == 0) { // left index is even
				return true; // take left coin
			} else if ((r & 1) == 0) {
				return false; // take right coin
			} else {
				return coins[l] > coins[r]; // both indexes are odd, revert to simple greedy rule
			}
		} else { // always pick the odd indices
			if ((l & 1) == 1) {
				return true;
			} else if ((r & 1) == 1) {
				return false;
			} else {
				return coins[l] > coins[r];
			}
		}
	}

	@Override
	public String getName() {
		return name;
	}
	
	public static void main(String[] args) {
		int[] coins = {1, 1, 9, 2};
//		int[] coins = {20, 30, 2, 3, 4, 10, 234, 345, 12, 24, 45, 34, 1, 3, 6, 87, 26, 45, 89, 89, 23, 52,63,87, 80, 43, 22, 12, 45, 12, 1, 3};		
		
		Player a = new PotsOfGoldOddEvenStrategy("Alice", coins);
		Player b = new PotsOfGoldOddEvenStrategy("Bob", coins);

		PotsOfGoldGame.play(coins, a, b);
	}
}
