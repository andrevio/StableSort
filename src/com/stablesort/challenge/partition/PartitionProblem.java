package com.stablesort.challenge.partition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.stablesort.challenge.Knapsack01;

/**
 * Partition Problem: Partition a set of positive integers into two subsets such that the sum of the numbers in each subset 
 * adds up to the same amount, as closely as possible. This is an NP-complete problem, dubbed as “the easiest hard problem”.
 * 
 * Youtube tutorial that explains the solution:
 * https://youtu.be/7BynUy5ml0I
 * 
 * Example:
 * Input:  ar[] = {1, 5, 7, 10} 
 * Output: {1, 10} or {5, 7}
 *
 * @author Andre Violentyev
 */
public class PartitionProblem {
	/**
	 * creates a new List that contains numbers that add up as close as possible to the target, such that the remaining
	 * elements in ar[] would also add up as close as possible to the target.
	 * 
	 * @param ar - {1, 5, 10, 7}
	 * @param i - index to ar[]
	 * @param target (1+5+10+7)/2
	 * @return {1, 10} or {5, 7}
	 */
	public List<Integer> select(int[] ar, int i, int target) {
		
		if (i >= ar.length) return new ArrayList<>();

		/*
		 * we either select ar[idx], in which case we decrease the target amount
		 * or we skip ar[idx], in which case the target is not modified 
		 */
		List<Integer> l1 = select(ar, i + 1, target - ar[i]); 
		l1.add(ar[i]);
		
		List<Integer> l2 = select(ar, i + 1, target);
		
		int t1 = sum(l1); 	
		int t2 = sum(l2);

		if (absDiff(t1, target) < absDiff(t2, target)) {
			return l1;
		
		} else {
			return l2;
		}
	}		

	/**
	 * Simply sums up all of the numbers in the list. Note: you could keep the total in a separate variable and 
	 * free yourself from having to recalculate this every time.
	 * 
	 * @param l
	 * @return
	 */
	private int sum(List<Integer> l) {
		return l.stream().mapToInt(num -> num).sum();
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @return always a positive integer difference, or zero if a==b
	 */
	private int absDiff(int a, int b) {
		return a > b ? a - b : b - a;
	}

	/**
	 * This problem could be solved directly using the solution to the 0/1 Knapsack problem. The weights and values are
	 * set to be the same as input ar[], with max capacity being the total/2.
	 * 
	 * @param ar
	 * @param target
	 */
	public void use01KnapsackSolution(int[] ar, int target) {
		System.out.println(Arrays.toString(Knapsack01.run(ar, ar, target)));
	}
	
	public static void main(String[] args) {
//		int[] ar = {3, 1, 4, 2, 2, 1};
		int[] ar = {1, 5, 7, 10};
		
		int total = 0; // sum all in the array
		for (int i = 0; i < ar.length; i++) {
			total += ar[i];
		}
		
		PartitionProblem challenge = new PartitionProblem();
		
		System.out.println(Arrays.toString(ar));
		System.out.println(challenge.select(ar, 0, total/2));
		
		challenge.use01KnapsackSolution(ar, total/2);
	}
}
