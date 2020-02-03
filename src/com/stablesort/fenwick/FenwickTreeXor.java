package com.stablesort.fenwick;

import java.util.Arrays;

/**
 * Fenwick Tree (Binary Index Tree) implementation that uses XOR for its range operation
 * @author Andre Violentyev
 */
public class FenwickTreeXor {

	final private boolean[] tree;
	
	public FenwickTreeXor(boolean[] ar) {
		this.tree = this.make(ar);
	}
	
	/**
	 * Assumes data starts at index 1. Value at index zero is ignored.
	 * @param ar
	 * @return
	 */
	boolean[] make(boolean[] ar) {
		boolean[] tree = Arrays.copyOf(ar, ar.length);
		
		for (int i = 1; i < tree.length; i++) {
			int p = i + (i & -i); // index to parent range
			if (p < tree.length) {
				tree[p] = tree[p] ^ tree[i];
			}
		}
		
		return tree;
	}

	/**
	 * Returns the xor from index 1 to i (inclusive)
	 * @param i
	 */
	public boolean xor(int i) {	
	    boolean sum = false;
	    while (i > 0) { 
	        sum = sum ^ tree[i];
	        i -= i & -i; // zeroes the least significant bit of value 1
	    }
	    return sum;
	}
	
	/**
	 * returns the xor from i to j (inclusive)
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean xor(int i, int j) {
		return xor(j) ^ xor(i-1);
	}
	
	/**
	 * just the value from the "original array" at index i
	 * @param i
	 * @return
	 */
	public boolean valueAt(int i) {
		return xor(i) ^ xor(i-1);
	}
	
	/**
	 * XOR k with element at index i, propagating the change to the right end of tree so that range operations would still work 
	 * @param i
	 * @param k
	 */
	public void update(int i, boolean k) {	
	    while (i < tree.length) { 
	        tree[i] = tree[i] ^ k;
	        i += i & -i; // take the least significant set bit and add to i
	    }
	}
	
	public void printIsOn() {
		
		System.out.println("-----------------------------------------------------------");
		for (int i = 1; i < tree.length; i++) {
			System.out.print(i + "\t");
		}	
		System.out.println("");
		for (int i = 1; i < tree.length; i++) {
			System.out.print(xor(i) ? "1\t" : "0\t");
		}	
		System.out.println("");
	}
	

	
	
}
