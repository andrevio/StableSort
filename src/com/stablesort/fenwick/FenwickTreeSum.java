package com.stablesort.fenwick;

import java.util.Arrays;

/**
 * Fenwich Tree (Binary Index Tree) implementation that uses addition for its range operation
 * @author Andre Violentyev
 */
public class FenwickTreeSum {

	final private int[] tree;
	
	/**
	 * 
	 * @param ar - data starts at index 1, ar[0] is ignored
	 */
	public FenwickTreeSum(int[] ar) {
		this.tree = this.make(ar);
	}
	
	/**
	 * Assumes data starts at index 1. Value at index zero is ignored.
	 * @param ar
	 * @return
	 */
	int[] make(int[] ar) {
		int[] tree = Arrays.copyOf(ar, ar.length);
		
		for (int i = 1; i < tree.length; i++) {
			int p = i + (i & -i); // index to parent range
			if (p < tree.length) {
				tree[p] = tree[p] + tree[i];
			}
		}
		
		return tree;
	}
	

	/**
	 * Returns the sum from index 1 to i (inclusive)
	 * @param i
	 */
	public int sum(int i) {	
	    int sum = 0;
	    while (i > 0) { 
	        sum += tree[i];
	        i -= i & -i; // zeroes the least significant bit of value 1
	    }
	    return sum;
	}
	
	/**
	 * returns the sum from i to j (inclusive)
	 * @param i
	 * @param j
	 * @return
	 */
	public int sum(int i, int j) {
		return sum(j) - sum(i-1);
	}
	
	/**
	 * just the value from the "original array" at index i
	 * @param i
	 * @return
	 */
	public int valueAt(int i) {
		return sum(i) - sum(i-1);
	}
	
	/**
	 * Adds k to element at index i, propagating the change to the right end of the tree so that range operations still work
	 * @param i
	 * @param k
	 */
	private void add(int i, int k) {	
	    while (i < tree.length) { 
	        tree[i] += k;
	        i += i & -i; // take the least significant set bit and add to i
	    }
	}
	
	/**
	 * updates the 
	 * @param i
	 * @param value
	 */
	public void update(int i, int value) {
		int orig = valueAt(i);
		add(i, value - orig);
	}
	
	public static void main(String[] args) {
		
		// data starts at index 1. ar[0] is ignored
		int ar[] = new int[]{0, 5, 2, 9, -3, 5, 20, 10, -7, 2, 3, -4, 0, -2, 15, 5};		
		FenwickTreeSum ft = new FenwickTreeSum(ar);
		System.out.println("value at 3 = " + ft.valueAt(3));
		System.out.println("sum(1, 3) = " + ft.sum(1, 3));
		

	}	
}
