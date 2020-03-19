package com.stablesort.challenge.partition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.stablesort.util.Rand;
import com.stablesort.util.Rand.Option;

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
	 * @param i - initially call with zero, but then the recursively all will be incrementing by 1
	 * @param target - initially call populated by full target value
	 * @param p - stores the partitions into this List
	 * @return
	 */
	static boolean partition(int[] ar, int i, int[] target, List<List<Integer>> p) {
		int value = ar[i];
		/*
		 * for each target[k], try reducing to zero 
		 */
		for (int k = 0; k < target.length && i < ar.length; k++) {
			if (target[k] >= value) {
				target[k] -= value;	
				
				if (partition(ar, i+1, target, p)) {
					p.get(k).add(value); // keep the value in this partition
					break;
				} else {
					// backtrack - undo target decrease												
					target[k] += value;	
				}
			}
		}
				
		return sum(target) == 0;
	}

	/**
	 * This version uses memorization to speed up calculation for cases where we know a solution does
	 * not exist and especially if there are duplicate items. Otherwise, it's exactly the same as a more simple version below.
	 * 
	 * @param ar - List of integers that are to be divided up to match the targets. Not using
	 * 	a primitive array so at to be able to easily add/remove items
	 * @param target - initially every value of this array is set to the same target value
	 * @param trgIdx - index to target[]
	 * @param p - partition, must already be initialized with same size as target.length, K
	 * @param mem - memorization used to speed up calculation
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
	private static int sum(int[] target) {
		int total = 0;
		for (int i = 0; i < target.length; i++) {
			total += target[i];
		}
		return total;
	}
	
	/**
	 * simpler algorithm, no memorization
	 * 
	 * @param ar
	 * @param numPartitions
	 */
	private static boolean run1(int[] ar, int numPartitions) {
		int sum = sum(ar);
		
		if (sum % numPartitions != 0) {
			System.out.println("run1: paritioning not possible as total sum " + sum + " is not divisibly by " + numPartitions);
			return false;
		}
		
		final int targetValue = sum / numPartitions;
		
		List<List<Integer>> p = new ArrayList<>();
		int[] target =  new int[numPartitions];
		
		for (int t = 0; t < target.length; t++) {
			target[t] = targetValue;
			p.add(new ArrayList<>());
		}
		
		boolean isPossible = partition(ar, 0, target, p);
		System.out.println("run1: is partition possible = " + isPossible + ": " + p);
		return isPossible;
	}
	
	/**
	 * uses memorization
	 * 
	 * @param ar
	 * @param numPartitions
	 */
	private static boolean run2(int[] ar, int numPartitions) {
		int sum = sum(ar);
		
		if (sum % numPartitions != 0) {
			System.out.println("run2: paritioning not possible as total sum " + sum + " is not divisibly by " + numPartitions);
			return false;
		}
		
		final int targetValue = sum / numPartitions;
		
		List<List<Integer>> p = new ArrayList<>();
		int[] target =  new int[numPartitions];
		
		for (int t = 0; t < target.length; t++) {
			target[t] = targetValue;
			p.add(new ArrayList<>());
		}
		
		List<Integer> list = IntStream.of(ar).boxed().collect(Collectors.toList());
		boolean isPossible = partition(list, target, 0, p, new HashMap<>());
		System.out.println("run2: is partition possible = " + isPossible + ": " + p);
		return isPossible;
	}
	
	public static void main(String[] args) {
		
//		int[] ar = {4, 3, 2, 3, 5, 2, 1}; // possible with 4=[[1, 4], [2, 3], [2, 3], [5]]
//		int[] ar = {1, 5, 6, 10};

//		int[] ar = {5, 3, 10, 9, 10, 1, 5, 7, 7, 1}; // 3-not possible - not divisible by 3
//		int[] ar = {4, 7, 3, 5, 3, 6, 2, 3, 5, 6}; // 2:possible, 3 - not possible
//		int[] ar = {6, 10, 9, 5, 8, 6, 4, 10, 4, 9}; // 3 - not possible
//		int[] ar = {1, 1, 1, 1, 1, 1, 1, 1, 1, 11}; // not possible
//		int[] ar = {4, 2, 3, 6, 3, 1, 5, 4, 2, 3, 6, 3, 1, 5, 6}; // 3: possible [[6, 6, 6], [4, 4, 5, 5], [1, 1, 2, 2, 3, 3, 3, 3]]
//		int[] ar = {4, 2, 3, 6, 3, 1, 5, 4, 2, 3, 3, 3, 6}; // 3 - possible: [3, 6, 6], [2, 4, 4, 5], [1, 2, 3, 3, 3, 3]
		int[] ar = {10_000, 10_000, 10_000, 10_000, 10_000, 1000, 1000, 1000, 1000, 1000, 100, 100, 100, 100, 100, 10, 10, 10, 10, 10, 1, 1, 1, 1, 6};
//		int[] ar = {1000, 1000, 1000, 1000, 100, 100, 100, 100, 10, 10, 10, 10, 1, 1, 1, 5};
//		int[] ar = {100, 100, 100, 10, 10, 10, 1, 1, 2}; // not possible
//		int[] ar = Rand.getRandIntAr(20, Option.positive);
		int numPartitions = 2;
		
		int sum = sum(ar);
		final int targetValue = sum / numPartitions;
//		int remainder = sum % numPartitions;		
//		System.out.println("remainder = " + remainder);
		
		// adjust to pass simple division based check
//		if (remainder > 0) {
//			ar[0] = ar[0] + (numPartitions-remainder);
//			sum += (numPartitions-remainder);			
//		}		
		
		System.out.println(Arrays.toString(ar) + ", numPartitions = " + numPartitions + ", targetValue = " + targetValue);
		
//		if ((sum % numPartitions != 0) || numPartitions < 1 || numPartitions > ar.length) {
//			System.out.println("partition is not possible");
//			System.exit(0);
//		}
		
		for (int i = 0; i < 10000; i++) {
			ar = Rand.getRandIntAr(10, Option.positive);
//			ar = Arrays.copyOfRange(ar, 0, 10);
			System.out.println("i=" + i + ", ar = " + Arrays.toString(ar));
			if (run1(ar, numPartitions) != run2(ar, numPartitions)) {
				System.err.println("BUG found for input " + Arrays.toString(ar));
				System.exit(1);
			}
		}
		
		run1(ar, numPartitions);
		run2(ar, numPartitions);
	}
}
