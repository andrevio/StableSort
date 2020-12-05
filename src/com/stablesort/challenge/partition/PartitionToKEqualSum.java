package com.stablesort.challenge.partition;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Question: Given an array of integers and a positive integer k, divide this array into k non-empty subsets whose sums are all equal.
 * @author Andre Violentyev
 */
public class PartitionToKEqualSum {
		
	/**
	 * Note - assuming that total sum of ar[] is divisible by target value. If not, driver function
	 * should halt. This is because we aim to reduce the target[] values all to zero. So if there are
	 * extras remaining in ar[], this function won't catch it.
	 * 
	 * @param ar
	 * @param i - initially call with zero, but then the recursively will be incrementing by 1
	 * @param target - initially call populated by full target value
	 * @param p - stores the partitions into this List
	 * @return
	 */
	static boolean partition(int[] ar, int i, int[] target, List<List<Integer>> p) {
		/*
		 * for each target[k], try reducing to zero 
		 */
		for (int k = 0; k < target.length && i < ar.length; k++) {
			if (target[k] >= ar[i]) {
				target[k] -= ar[i];	
				
				if (partition(ar, i+1, target, p)) {
					p.get(k).add(ar[i]); // keep the value in this partition
					break;
				} else {
					// backtrack - undo target decrease												
					target[k] += ar[i];	
				}
			}
		}
				
		return sum(target) == 0;
	}

	/**
	 * This version uses memoization to speed up calculation for cases where we know a solution does
	 * not exist and especially if there are duplicate items. Otherwise, it's the same as a more simple version below.
	 * 
	 * @param ar - List of integers that are to be divided up to match the targets. Not using
	 * 	a primitive array so as to be able to easily add/remove items
	 * @param target - initially every value of this array is set to the same target value
	 * @param trgIdx - index to target[]
	 * @param p - partition, must already be initialized with same size as target.length, K
	 * @param mem - memoization used to speed up calculation
	 * 
	 * @return true if partitioning is possible
	 */
	public static boolean partition(List<Integer> ar, int[] target, int trgIdx, List<List<Integer>> p, Map<String, Boolean> mem) {		
		Collections.sort(ar);
		String key = ar.toString() + "|" + Arrays.toString(target);
		Boolean possible = mem.get(key);
		if (possible != null) {
			return possible;
		}
		
		/*
		 * Each of K subsets need to fill up. Equivalently, each of the target[k] need to be reduced to zero
		 */
		for (int k = trgIdx; k < target.length; k++) {
			/*
			 * Any of the values in ar[] could be used to reduce target[k], so we need to try each one.
			 * Iterate backwards so that we can put back values easily.
			 */
			for (int idx = ar.size()-1; idx >= 0 && target[k] > 0; idx--) {
				
				if (target[k] >= ar.get(idx)) { // make sure not to go negative on target
					Integer value = ar.remove(idx);	
					target[k] -= value;	
					
					if (partition(ar, target, k, p, mem)) {
						// keep the removed value in this sub-list
						p.get(k).add(value);
						
					} else {
						// try removing some other value, but first put back. It's OK to add to the end since idx is decreasing
						ar.add(value);
						target[k] += value;	
					}
				}
			}

			if (target[k] != 0) {
				mem.put(key, false);
				return false;
			}
		}		
		
		mem.put(key, ar.isEmpty());
		
		return ar.isEmpty();
	}
	
	/**
	 * 
	 * @param target
	 * @return
	 */
	static int sum(int[] target) {
		int total = 0;
		for (int i = 0; i < target.length; i++) {
			total += target[i];
		}
		return total;
	}
}
