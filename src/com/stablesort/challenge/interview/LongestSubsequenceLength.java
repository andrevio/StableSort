package com.stablesort.challenge.interview;

import java.util.*;

import com.stablesort.util.Rand;
import com.stablesort.util.StopWatch;

/**
 * https://www.youtube.com/watch?v=8HLrSZBFX9c
 * 2nd problem
 * 
 * Given a list of integers L, write a function to calc the max length of a subsequence of
 * continuous numbers from L. For example:
 * [0, 2, 99, 100, 3, 1, 4, 5, 77] --> {0, 2, 3, 1, 4, 5}.size=6, {99, 100}.size=2, so return 6
 * 
 * @author Andre
 *
 */
public class LongestSubsequenceLength {

	/**
	 * sort then loop through once, keeping track of longest continuous seq. reset sequence counter whenever there is a break
	 * 
	 * O(n log n)
	 * 
	 * @param ar
	 * @return
	 */
	static int maxSeqLenV1(int[] ar) {
		
		if (ar.length == 0) return 0;
		
		Arrays.sort(ar); // sorted in ascending order
		
		int maxSeqLen = 0;
		int currSeqLen = 1;
		
		for (int i = 1; i < ar.length; i++) {
			if (ar[i-1]+1 == ar[i]) { // if continuation of a sequence
				currSeqLen++;
				if (maxSeqLen < currSeqLen) {
					maxSeqLen = currSeqLen;
				}
			} else {
				currSeqLen = 1; // reset
			}
		}
		
		return maxSeqLen;
	}

	/**
	 * this is an O(n) function, yet the v1 outperforms it by x10 on 10M length array
	 * @param ar
	 * @return
	 */
	static int maxSeqLenV2(int[] ar) {
		
		if (ar.length == 0) return 0;

		// place all items into a set for quick access
		Set<Integer> set = new HashSet<>();
		for (int i : ar) {
			set.add(i);
		}
		
		int currSeqLen = 1;
		int maxSeqLen = 0;
		
		for (int i = 0; i < ar.length; i++) {
			
			// check if this is the left-ish end of the array
			if (!set.contains(ar[i] - 1)) {
				continue; 
			}
		
			currSeqLen = 1;
			
			// trace the sequence to the right. This is done ONLY for the start of the sequence items
			// which is why this solution is O(n)
			while (set.contains(ar[i] + currSeqLen)) {
				currSeqLen++;
				maxSeqLen = Math.max(currSeqLen+1, maxSeqLen);
			}
		}
		
		return maxSeqLen;
	}
	
	/**
	 * just tests performance v1 vs v2
	 */
	static void perfTest() {
		
		StopWatch sw = new StopWatch();
		long v1 = 0;
		long v2 = 0;
		
		for (int i = 0; i < 10; i++) {

			int[] ar1 = Rand.getRandIntAr(10_000_000);
			int[] ar2 = new int[ar1.length];
			System.arraycopy(ar1, 0, ar2, 0, ar1.length);

			if (ar1[0] % 2 == 0) { // randomize which order to run the test
				maxSeqLenV1(ar1);
				v1 += sw.duration();
				System.out.println("v1 time=" + sw);
				
				maxSeqLenV2(ar2);
				v2 += sw.duration();
				System.out.println("v2 time=" + sw);
			} else {
				maxSeqLenV2(ar2);
				v2 += sw.duration();
				System.out.println("v2 time=" + sw);

				maxSeqLenV1(ar1);
				v1 += sw.duration();
				System.out.println("v1 time=" + sw);
			}
		}

		System.out.println("v1=" + v1);
		System.out.println("v2=" + v2);			
	}
	
	public static void main(String[] args) {
		int[] ar1 = Rand.getRandIntAr(10_000_000);
		//int[] ar1 = new int[]{0, 2, 99, 100, 3, 1, 4, 5, 77}; // 6
		int[] ar2 = new int[ar1.length];
		System.arraycopy(ar1, 0, ar2, 0, ar1.length);
		StopWatch sw = new StopWatch();
		
		System.out.println("v1=" + maxSeqLenV1(ar1) + ", time=" + sw);
		System.out.println("v2=" + maxSeqLenV2(ar2) + ", time=" + sw);
		
		perfTest();
		
	}
}
