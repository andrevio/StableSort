package com.stablesort.challenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import com.stablesort.util.ComparablePair;

/**
 * You have k lists of sorted integers. Find the smallest range that includes at least one number from each of the k lists.
 * 
 * For example,
 * List 1: [4, 10, 15, 24, 26]
 * List 2: [0, 9, 12, 20]
 * List 3: [5, 18, 22, 30]
 * 
 * The smallest range here would be [20, 24] as it contains 24 from list 1, 20 from list 2, and 22 from list 3.
 * https://www.careercup.com/question?id=16759664
 ****************************************************************************************************************
 * Solution by aasshishh:
 * 
 * There are k lists of sorted integers. Make a min heap of size k containing 1 element from each list. Keep track 
 * of min and max element and calculate the range.In min heap, minimum element is at top. Delete the minimum element 
 * and another element instead of that from the same list to which minimum element belong. 
 * Repeat the process till any one of the k list gets empty. Keep track of minimum range.
 **************************************************************************************************************** 
 * 
 * My implementation is basically the same as described. PriorityQueue runs in O(log k), we iterate through all of 
 * n elements of all the lists, so the running time is O(n log k)
 * 
 * @author Andre Violentyev
 */
public class KSortedListsMinRange {

	/**
	 * 
	 * @param lists
	 * @return [min, max]
	 */
	public static int[] getMinRange(Collection<List<Integer>> lists) {
		int[] range = new int[2];
		range[0] = Integer.MAX_VALUE; // set lower boundary	
		Integer maxInQueue = Integer.MIN_VALUE;
		
		/*
		 * the queue holds a single integer from each of the lists, along with references to which list a given integer belongs to 
		 */
		Queue<ComparablePair<Integer, Iterator<Integer>>> q = new PriorityQueue<>();
		
		/*
		 * there are k lists, adding an element into the queue takes log(k) time
		 */
		for (List<Integer> list : lists) {
			Integer i = list.get(0);
			
			if (maxInQueue.compareTo(i) < 0) {
				maxInQueue = i;
			}
			
			if (range[0] > i) {
				range[0] = i;
			}
			
			q.add(new ComparablePair<>(i, list.iterator()));
		}
		
		range[1] = maxInQueue; // set upper boundary
		
		while (!q.isEmpty()) {
			ComparablePair<Integer, Iterator<Integer>> p = q.poll();
			Integer i = p.getA();
			Iterator<Integer> it = p.getB();
		
			if (range[1] - range[0] > maxInQueue - i) {
				range[0] = i; // update lower boundary
				range[1] = maxInQueue;
			}
			
			if (it.hasNext()) {
				i = it.next();
				if (i > maxInQueue) {
					maxInQueue = i;
				}
				
				/*
				 * add next integer from the same list as the one we just took out from
				 */
				q.add(new ComparablePair<Integer, Iterator<Integer>>(i, it));
			} else {
				break; // one of the lists ran out
			}
		}
		
		return range;
	}
	
	public static void main(String[] args) {
		Collection<List<Integer>> lists = new ArrayList<>();
		lists.add(Arrays.asList(10, 20, 30));
		lists.add(Arrays.asList(2, 4, 8, 16));
		lists.add(Arrays.asList(5, 7, 13, 17));
		
		System.out.println(Arrays.toString(getMinRange(lists)));
	}
}
