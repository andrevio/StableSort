package com.stablesort.segtree.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.stablesort.segtree.SegmentTree;
import com.stablesort.segtree.SegmentTreeMax;
import com.stablesort.util.Rand;
import com.stablesort.util.StopWatch;

/*
 * for testing SegmentTree and SegmentTreeMax classes
 */
public class SegmentTreeTest {
	
	int arLen = 10_000_000;
	
	
	
	/**
	 * linear running time function, used for checking for correctness and as a performance reference
	 * 
	 * @param ar
	 * @param from - inclusive
	 * @param to - exclusive
	 * @return
	 */
	private int dumbMax(int[]ar, int from, int to) {
		int max = Integer.MIN_VALUE;
		for (int i = from; i < to; i++) {
			if (ar[i] > max) {
				max = ar[i];
			}
		}
		return max;
	}
	
	/**
	 * tests SegmentTree for correctness against the dumbMax() function
	 */
	public void testSegmentTree() {				
		int[] ar = Rand.getRandIntAr(arLen);
		List<Integer> l = new ArrayList<>(arLen);
		for (int i = 0; i < ar.length; i++) {
			l.add(ar[i]);
		}
		 
		SegmentTree<Integer> st = new SegmentTree<>(l, (a,b) -> Math.max(a, b));
		Random r = new Random();
		
		int numTrials = 1000;
		
		/*
		 * Randomly pick intervals and check if the max calculated using the Segment Tree matches to that of linear search max.
		 */
		for (int i = 0; i < numTrials; i++) {
			
			int from = r.nextInt(arLen);
			int len = arLen-from;
			int to = len > 0 ? r.nextInt(len) : from;
			
			to += from+1;
			
			if (dumbMax(ar, from, to) != st.query(from, to)) {
				System.out.println(Arrays.toString(ar));
				throw new RuntimeException("Mismatch: [" + from + " to " + to + "] --> " + dumbMax(ar, from, to) + " <> " + st.query(from, to));
			}
			
			// also make an update
			ar[from] = from;
			st.update(from, from);
		}
		
		System.out.println("testSegmentTree: SUCCESS");
	}
	
	/**
	 * tests SegmentTreeMax for correctness against the dumbMax() function
	 */
	public void testSegmentTreeMax() {				
		int[] ar = Rand.getRandIntAr(arLen);
		SegmentTreeMax stMax = new SegmentTreeMax(ar);
		Random r = new Random();
		
		int numTrials = 1000;
		
		/*
		 * Randomly pick intervals and check if the max calculated using the Segment Tree matches to that of linear search max.
		 */
		for (int i = 0; i < numTrials; i++) {
			
			int from = r.nextInt(arLen);
			int len = arLen-from;
			int to = len > 0 ? r.nextInt(len) : from;
			
			to += from+1;
			
			if (dumbMax(ar, from, to) != stMax.max(from, to)) {
				System.out.println(Arrays.toString(ar));
				throw new RuntimeException("Mismatch: [" + from + " to " + to + "] --> " + dumbMax(ar, from, to) + " <> " + stMax.max(from, to));
			}
			
			ar[from] = from; // make a 'random' update
			stMax.update(from, from);
		}
		
		System.out.println("SUCCESS");
	}
	
	/**
	 * checks to see how fast do SegmentTree and SegmentTreeMax operate
	 */
	public void comparePerf() {
		int[] ar = Rand.getRandIntAr(arLen);		
		List<Integer> l = new ArrayList<>(arLen);
		for (int i = 0; i < ar.length; i++) {
			l.add(ar[i]);
		}
		
		StopWatch sw = new StopWatch();
		SegmentTree<Integer> st = new SegmentTree<>(l, (a,b) -> Math.max(a, b));
		System.out.println("new SegmentTree = " + sw);
		
		SegmentTreeMax stMax = new SegmentTreeMax(ar);
		System.out.println("new SegmentTreeMax = " + sw);
		
		Random r = new Random();
		int numTrials = 1000;
		int qSt = 0;
		int qStMax = 0;
		int qDumb = 0;
		int dummy = 0; // to make sure the compiler does not over-smart us and actually call the function
		
		/*
		 * Randomly pick intervals and check if the max calculated using the Segment Tree matches with linear search.
		 */
		for (int i = 0; i < numTrials; i++) {
			
			int from = r.nextInt(arLen);
			int len = arLen-from;
			int to = len > 0 ? r.nextInt(len) : from;
			
			to += from+1;
			
			sw.poll();
			dummy += st.query(from, to);
			qSt += sw.poll();
			
			dummy += stMax.max(from, to);
			qStMax += sw.poll();
			
			dummy += dumbMax(ar, from, to);
			qDumb += sw.poll();
		}
		
		System.out.println("SegmentTree took " + qSt);
		System.out.println("SegmentTreeMax took " + qStMax);
		System.out.println("SegmentTreeDumb took " + qDumb);
		System.out.println("dummy = " + dummy);
	}

	public static void main(String[] args) {
		StopWatch sw = new StopWatch();
		SegmentTreeTest test = new SegmentTreeTest();
		test.testSegmentTree();
//		test.testSegmentTreeMax();
//		test.comparePerf();
		System.out.println(sw);
	}

}
