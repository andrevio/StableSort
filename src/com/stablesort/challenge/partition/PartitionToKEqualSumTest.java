package com.stablesort.challenge.partition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.stablesort.util.Rand;
import com.stablesort.util.Rand.Option;

public class PartitionToKEqualSumTest {
	/**
	 * simpler algorithm, no memorization
	 * 
	 * @param ar
	 * @param numPartitions
	 */
	private static boolean run1(int[] ar, int numPartitions) {
		int sum = PartitionToKEqualSum.sum(ar);
		
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
		
		boolean isPossible = PartitionToKEqualSum.partition(ar, 0, target, p);
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
		int sum = PartitionToKEqualSum.sum(ar);
		
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
		boolean isPossible = PartitionToKEqualSum.partition(list, target, 0, p, new HashMap<>());
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
//		int[] ar = {10_000, 10_000, 10_000, 10_000, 10_000, 1000, 1000, 1000, 1000, 1000, 100, 100, 100, 100, 100, 10, 10, 10, 10, 10, 1, 1, 1, 1, 6};
//		int[] ar = {1000, 1000, 1000, 1000, 100, 100, 100, 100, 10, 10, 10, 10, 1, 1, 1, 5};
//		int[] ar = {100, 100, 100, 10, 10, 10, 1, 1, 2}; // not possible
		int[] ar = {-14, 1, -2, 10, -8, 0, 6, 10, 3}; // target = 2, 3 partitions
		
//		int[] ar = Rand.getRandIntAr(20, Option.positive);
		int numPartitions = 3;
		
		int sum = PartitionToKEqualSum.sum(ar);
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
