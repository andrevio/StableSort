package com.stablesort.challenge;

import com.stablesort.fenwick.FenwickTreeXor;

/**
 * Given a Boolean array of districts, write two methods:
 * isDem(i) - to determine if the district at position i is Democratic or Republican
 * toggle(i, j) - to toggle all of the districts in the range form i to j
 * But here is the kicker - both methods should run in less than O(n) time complexity.
 * ----------------------------------------------------------------------------------------
 * Originally this problem was stated here:
 * https://www.careercup.com/question?id=5668664122540032
 * Given n light bulbs, write two methods.
 * isOn(i) to determine if a light bulb is on
 * toggle(start, end) to toggle all the light bulbs in the range
 * One caveat, write toggle so that it is less than O(n) complexity
 * ----------------------------------------------------------------------------------------
 * Solution by MehrdadAP: It could be solved using following trick:
 * When we need to toggle range (s,e), we could add 1 to cell s, and -1 to cell e+1.
 * Now, every time that we wanna know about the state of a i-th cell, we need to know whether the cumulative sum from 1 to i is even or odd.
 * 
 * For having an efficient updatable cummulative sum array, we could use Binary Index Tree.
 * So updating and reading the state of a cell would be O(logN)
 * ----------------------------------------------------------------------------------------
 * My solution is to use a boolean Fenwick tree that instead of addition uses XOR operation. It's not any faster but a little more elegant. 
 * 
 * @author Andre
 */
public class DistrictChallenge {
	
	private final FenwickTreeXor tree;
	
	public DistrictChallenge(boolean[] ar) {
		this.tree = new FenwickTreeXor(ar);
	}
	
	public boolean isOn(int i) {
		return tree.xor(i); // calculate cumulative XOR 1 to i
	}
	
	public void toggle(int i, int j) {
		tree.update(i, true); // XOR with TRUE flips the bit
		tree.update(j+1, true);
	}
}
