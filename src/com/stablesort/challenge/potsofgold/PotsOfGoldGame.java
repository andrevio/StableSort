package com.stablesort.challenge.potsofgold;

import java.util.Arrays;

import com.stablesort.util.Rand;

/**
 * This object just runs the game, asking each player to make a move, one after another. It does not have any game strategy implemented. 
 * @author Andre
 *
 */
public class PotsOfGoldGame {

	interface Player {
		/**
		 * 
		 * @param coins
		 * @param l - inclusive
		 * @param r - inclusive
		 * @return
		 */
		public boolean takeLeft(int[] coins, int l, int r);
		
		/**
		 * used for announcing the winer
		 * @return
		 */
		public String getName();
	}
	
	public static void play(int[] ar, Player a, Player b) {
		int l = 0, r = ar.length-1;
		int playerA = 0, playerB = 0;		
		boolean playerATurn = true;
		
		while (l <= r) {
			if (playerATurn) {
				if (a.takeLeft(ar, l, r)) {	
					System.out.println("A takes LEFT " + ar[l]);
					playerA += ar[l];	
					l++;
				} else {
					System.out.println("A takes RIGHT " + ar[r]);
					playerA += ar[r];	
					r--;
				}
			} else {
				if (b.takeLeft(ar, l, r)) {	
					System.out.println("B takes LEFT " + ar[l]);
					playerB += ar[l];	
					l++;
				} else {
					System.out.println("B takes RIGHT " + ar[r]);
					playerB += ar[r];	
					r--;
				}
				
			}
			playerATurn = !playerATurn; // flip
		}
		
		System.out.println("Player A, " + a.getName() + ", profit = " + playerA);
		System.out.println("Player B, " + b.getName() + ", profit = " + playerB);
	}
	
	public static void main(String[] args) {
		int[] coins = {20, 30, 2, 3, 4, 10, 234, 345, 12, 24, 45, 34, 1, 3, 6, 87, 26, 45, 89, 89, 23, 52,63,87, 80, 43, 22, 12, 45, 12, 1, 3};
//		int[] coins = {1, 1, 9, 2};
		
		Player recursive = new PotsOfGoldRecursiveStrategy("Recursive");
		Player evenOdd = new PotsOfGoldOddEvenStrategy("EvenOdd", coins);
		Player orderN = new PotsOfGoldOrderN("OrderN");
		
		play(coins, recursive, orderN);
	}
}
