package com.stablesort.stringmatch;

import java.util.ArrayList;
import java.util.Arrays;
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
	static int[] calcPrefixLen(String pattern) {
		int prefixLen = 0;
		int patternLen = pattern.length();
		
		int[] ar = new int[patternLen+1];		
		ar[0] = -1; 
				
		for (int i = 1; i < patternLen; i++) {			
			if (pattern.charAt(i) == pattern.charAt(prefixLen)) {
				prefixLen++;				
			} else {
				prefixLen = 0;
			}
			
			ar[i+1] = prefixLen;
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
		String text = "abbbbbbbbbbbb";
		String pattern = "abbbc";
		System.out.println("text="+text);
		System.out.println("pattern=" + pattern);
		System.out.println(Arrays.toString(calcPrefixLen(pattern)));
		System.out.println(search(text, pattern));
	}
}
