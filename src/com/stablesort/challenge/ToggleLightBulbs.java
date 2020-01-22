package com.stablesort.challenge;

import com.stablesort.fenwick.FenwickTreeXor;

/**
 * https://www.careercup.com/question?id=5668664122540032
 * Given n light bulbs, write two methods.
 * isOn(i) to determine if a light bulb is on
 * toggle(start, end) to toggle all the light bulbs in the range
 * One caveat, write toggle so that it is less than O(n) complexity
 * ----------------------------------------------------------------------------------------
 * It could be solved using following trick:
 * When we need to toggle range (s,e), we could add 1 to cell s, and -1 to cell e+1.
 * Now, every time that we wanna know about the state of a i-th cell, we need to know whether the cumulative sum from 1 to i is even or odd.
 * 
 * For having an efficient updatable cummulative sum array, we could use Binary Index Tree.
 * So updating and reading the state of a cell would be O(logN)
 * ----------------------------------------------------------------------------------------
 * My solution is to use a boolean Fenwick tree that instead of addition uses XOR operation. It's not any faster but a little more ellegant. 
 * 
 * 
 * @author Andre
 *
 */
public class ToggleLightBulbs {
	
	private final FenwickTreeXor t;
	
	public ToggleLightBulbs(boolean[] bulbs) {
		this.t = new FenwickTreeXor(bulbs);
	}
	
	public boolean isOn(int i) {
		return t.xor(i);
	}
	
	public void toggle(int i, int j) {
		t.update(i, true);
		t.update(j+1, true);
	}
}
