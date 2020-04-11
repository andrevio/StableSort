package com.stablesort.stringmatch;

import java.util.ArrayList;
import java.util.List;

/**
 * KMP string searching algorithm implementation. Video explanation: https://www.youtube.com/watch?v=8xWrNQOC1Ts
 * @author Andre Violentyev
 */
public class KnuthMorrisPratt {
	/**
	 * creates an array of length pattern.length()+1
	 * @param pattern "abcxxxabcyyy"
	 * @return [-1, 0, 0, 0, 0, 0, 0, 1, 2, 3, 0, 0, 0]
	 */
	public static int[] calcPrefixLen(String pattern) {
		int patternLen = pattern.length();
		int[] ar = new int[patternLen + 1];
		ar[0] = -1;
		ar[1] = 0;

		int left = 0;
		int right = 1;
		
		while (right < patternLen) {			
			if (pattern.charAt(left) == pattern.charAt(right)) { 
				left++;
				right++;
				ar[right] = left;
				
			} else if (left > 0) {
				/*
				 * Move 'left' backwards. Note that we do not increment right here.
				 */
				left = ar[left];
				
			} else {
				/*
				 * 'left' reached 0, so set prefix length to zero and more forward
				 */
				right++;
				ar[right] = 0;
			}
		}
		
		return ar;
	}
	
	/**
	 * implementation is based on the pseudo code from Wikipedia: https://en.wikipedia.org/wiki/Knuth%E2%80%93Morris%E2%80%93Pratt_algorithm
	 * @param text
	 * @param pattern
	 * @return
	 */
	public static List<Integer> search(String text, String pattern) {
		int t = 0; // the position of the current character in text
		int p = 0; // the position of the current character in pattern
		
		int tLen = text.length();
		int pLen = pattern.length();
		
		List<Integer> matches = new ArrayList<>();
		int[] prefixLen = calcPrefixLen(pattern);
		
		while (t < tLen) {
			if (pattern.charAt(p) == text.charAt(t)) {				
				p++;
				t++;
				
				if (p == pLen) {
					// occurrence found, if only first occurrence is needed then you could halt here
					matches.add(t-p);
					p = prefixLen[p]; // reset
				}				
			} else {
				p = prefixLen[p];
				if (p < 0) {
					t++;
					p++;
				}
			}
		}
		
		return matches;
	}
	 
	public static void main(String[] args) {
		String text = "---abcxxxab------abcxxxabcy---";
		String pattern = "abcxxxabcy";
		
		System.out.println("text="+text);
		System.out.println("pattern=" + pattern);
		
		System.out.println(search(text, pattern));
	}
}
