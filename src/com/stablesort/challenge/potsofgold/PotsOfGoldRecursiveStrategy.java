package com.stablesort.challenge.potsofgold;

import java.util.HashMap;
import java.util.Map;

import com.stablesort.challenge.potsofgold.PotsOfGoldGame.Player;

/**
 * Pots of gold game: Two players A & B. There are pots of gold arranged in a line, each containing some gold coins 
 * (the players can see how many coins are there in each gold pot - perfect information). They get alternating turns in which the 
 * player can pick a pot from one of the ends of the line. The winner is the player which has a higher number of coins at the end. 
 * The objective is to "maximize" the number of coins collected by A, assuming B also plays optimally. A starts the game.
 * 
 * The idea is to find an optimal strategy that makes A win knowing that B is playing optimally as well. 
 * 
 * This object implements an optimal dynamic programming strategy. Actually, it's recursive solution that memorizes the 
 * previously seen answers.
 * 
 * @author Andre
 */
public class PotsOfGoldRecursiveStrategy implements Player {

	Map<String, Integer> m = new HashMap<>();
	
	private final String name;
	
	public PotsOfGoldRecursiveStrategy(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @param coins
	 * @param l - left index
	 * @param r - right index
	 * @return TRUE if next move should be to take the item from ar[l], FALSE if next move should be to take ar[r]
	 */
	public boolean takeLeft(int[] coins, int l, int r) {
	    int a = coins[l] + Math.min(maxProfit(coins, l + 2, r), maxProfit(coins, l + 1, r - 1));
	    int b = coins[r] + Math.min(maxProfit(coins, l + 1, r - 1), maxProfit(coins, l, r - 2));
	    return a > b;
	}
	
	/**
	 * calculates the maximum profit that current player could make, given l and r boundaries
	 * 
	 * @param coins
	 * @param l
	 * @param r
	 * @return
	 */
	int maxProfit(int[] coins, int l, int r) {
	    if (l > r) return 0;

	    /*
	     * check if we have seen a solution for this combination of 'l' and 'r' values
	     */	    
 		String key = l + "|" + r;
 		Integer solution = m.get(key);
 		if (solution != null) {
 			return solution;
 		}
 		
 		/*
 		 * take left + min of the next possible moves
 		 */
	    int a = coins[l] + Math.min(maxProfit(coins, l + 2, r), maxProfit(coins, l + 1, r - 1));
	    
	    /*
	     * take right + min of the next two possible moves
	     */
	    int b = coins[r] + Math.min(maxProfit(coins, l + 1, r - 1), maxProfit(coins, l, r - 2));

	    int profit = Math.max(a, b);
	    m.put(key, profit);
	    
	    return profit;
	}

	int maxProfitNoMem(int[] coins, int l, int r) {
 		/*
 		 * take left + min of the next possible moves
 		 */
	    int a = coins[l] + Math.min(maxProfit(coins, l + 2, r), maxProfit(coins, l + 1, r - 1));
	    
	    /*
	     * take right + min of the next two possible moves
	     */
	    int b = coins[r] + Math.min(maxProfit(coins, l + 1, r - 1), maxProfit(coins, l, r - 2));

	    return Math.max(a, b);
	}

	
	@Override
	public String getName() {
		return name;
	}

	public static void main(String[] args) {
//		int[] ar = {1, 1, 9, 2};
//		int[] ar = {6, 1, 4, 9, 8, 5};
		int[] coins = {20, 30, 2, 3, 4, 10, 234, 345, 12, 24, 45, 34, 1, 3, 6, 87, 26, 45, 89, 89, 23, 52,63,87, 80, 43, 22, 12, 45, 12, 1, 3};		
		
		Player a = new PotsOfGoldRecursiveStrategy("Alice");
		Player b = new PotsOfGoldRecursiveStrategy("Bob");
		
		

		PotsOfGoldGame.play(coins, a, b);
	}
}
