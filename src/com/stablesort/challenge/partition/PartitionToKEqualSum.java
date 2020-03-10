package com.stablesort.challenge.partition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Question: Given an array of integers and a positive integer k, divide this array into k non-empty subsets whose sums are all equal.
 * @author Andre Violentyev
 */
public class PartitionToKEqualSum {
	/**
	 * 
	 * @param ar - List of integers that are to be divided up to match the targets. Not using
	 * 	a primitive array so at to be able to easily add/remove items
	 * @param target - initially every value of this array is set to the same target value
	 * @param p - partition, must already be initialized with same size as target.length, K
	 * @return true if partitioning is possible
	 */
	public static boolean partition(List<Integer> ar, int[] target, List<List<Integer>> p) {
		
		if (ar.isEmpty() && allZero(target)) {
			return true;
		}
		
		/*
		 * Each of K subsets need to filled up. Equivalently, each of the target[k] need to be reduced to zero
		 */
		for (int k = 0; k < target.length; k++) {
			/*
			 * Any of the values in ar[] could be used to reduce target[k], so we need to try each one.
			 * Iterate backwards so that we can put back values easily.
			 */
			for (int idx = ar.size()-1; idx >= 0 && target[k] > 0; idx--) {
				
				if (target[k] >= ar.get(idx)) { // make sure not to go negative on target
					Integer value = ar.remove(idx);	
					target[k] -= value;	
					
					
					if (partition(ar, target, p)) {
						// keep the removed value in this sub-list; we can break out of this loop but it'll halt automatically
						p.get(k).add(value);
						
					} else {
						// try removing some other value, but first put back. It's OK to add to the end since idx is decreasing
						ar.add(value);
						target[k] += value;	
					}
				}
			}

			if (target[k] != 0) return false;
		}		
		
		return ar.isEmpty();
	}

	private static boolean allZero(int[] target) {
		for (int i = 0; i < target.length; i++) {
			if (target[i] != 0) return false;
		}
		return true;
	}
	
	private static int sum(int[] target) {
		int total = 0;
		for (int i = 0; i < target.length; i++) {
			total += target[i];
		}
		return total;
	}

	public static void main(String[] args) {
		
//		int[] ar = {3, 1, 4, 2, 2, 1};
//		int[] ar = {1, 5, 6, 10};
//		int k = 2;

		int[] ar = {3, 2, 3, 5, 2, 1, 4};
		int k = 2;
		
		int targetValue = sum(ar) / k;
		System.out.println(Arrays.toString(ar) + ", k = " + k + ", targetValue = " + targetValue);
		
		List<List<Integer>> p = new ArrayList<>();
		int[] target =  new int[k];
		
		for (int t = 0; t < target.length; t++) {
			target[t] = targetValue;
			p.add(new ArrayList<>());
		}
		
		List<Integer> list = IntStream.of(ar).boxed().collect(Collectors.toList());
		
		boolean isPossible = partition(list, target, p);
		System.out.println("is partition possible = " + isPossible + ": " + p);

	}
}
