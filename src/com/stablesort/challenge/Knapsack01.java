package com.stablesort.challenge;

import java.util.Arrays;

/**
 * This is a minimal implementation of 0/1 Knapsack problem. Here is the YouTube video that explains the
 * the algorithm: https://youtu.be/-kedQt2UmnE
 * @autho Andre Violentyev
 */
public class Knapsack01 {

	/**
	 * 
	 * @param values
	 * @param weights
	 * @param wMax
	 * @return
	 */
	public static int[] run(int[] values, int[] weights, int wMax) {
	    int[][] T = new int[values.length+1][]; // table that stores best solution for the given max capacity
	    boolean[][] keep = new boolean[values.length+1][]; // this table remembers if a particular object is put into the knapsack
	    int i, w, itemValue, itemWeight, newBest;

		// create DP tables
	    for (i = 0; i <= values.length; i++) {
	        T[i] = new int[wMax+1];
	        keep[i] = new boolean[wMax+1];
	    }

	    /*
	     * Each column in our table holds incrementally larger and larger knapsack capacity. While each row brings in an additional 
	     * item to be included in the calculation. Row zero, T[0] just holds zero values.
	     */
	    for (i = 1; i <= values.length; i++) {
	        for (w = 0; w <= wMax; w++) {
	            itemValue = values[i - 1];
	            itemWeight = weights[i - 1];
	            
	            if (w - itemWeight >= 0) {
	            	newBest = itemValue + T[i - 1][w - itemWeight];	
	            } else {
	            	newBest = Integer.MIN_VALUE;
	            }	            

	            if (weights[i - 1] <= w && T[i - 1][w] < newBest) {
	                T[i][w] = newBest;
	                keep[i][w] = true; // mark that a new object is put into the knapsack
	            } else {
	                T[i][w] = T[i - 1][w]; // use the solution from prevous row
	                keep[i][w] = false;
	            }
	        }
	    }

	    int[] out = new int[values.length]; // 1 to keep the value, 0 to skip
	    
		// retrieve the path by walking up from bottom right corner
	    w = wMax;
	    for (i = values.length; i > 0; i--) {
	        if (keep[i][w]) {
	            w = w - weights[i - 1];
	            out[i-1] = 1;
	            System.out.println("keep item[" + i + "]=\t" + values[i-1] + "\tw=" + w);
	        }
	    }
	    
	    System.out.println("max value = " + T[values.length][wMax]); 
	    return out;
	}
	
	public static void main(String[] args) {
		int[] values = {10, 40, 30, 60}; // values of the objects
		int[] weights = {5, 4, 6, 3}; // weights of the object
		int wMax = 10; // maximum knapsack weight capacity

		System.out.println("output = " + Arrays.toString(run(values, weights, wMax)));

	}

}
