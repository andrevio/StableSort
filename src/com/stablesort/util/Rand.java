package com.stablesort.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rand {
	public enum Option {
		positive
	}
	
	/**
	 * creates an array of specified length, populated with random numbers in the range from 0 to len.
	 * @param len
	 * @param opt - if Option.positive is passed in, then the range is from 1 to len+1
	 * @return
	 */
	public static int[] getRandIntAr(int len, Option...opt) {
		Random r = new Random();
		int[] ar = new int[len];
		
		for (int i = 0; i < ar.length; i++) {
			if (opt.length > 0 && opt[0] == Option.positive) {
				ar[i] = r.nextInt(len) + 1;
			} else {
				ar[i] = r.nextInt(len); // cap the size just for easier readability	
			}
		}
		return ar;
	}

	/**
	 * creates a List of specified length, populated with random numbers in the range from 0 to len.
	 * @param len
	 * @return
	 */
	public static List<Integer> getRandIntList(int len) {
		Random r = new Random();
		List<Integer> ar = new ArrayList<>(len);
		
		for (int i = 0; i < len; i++) {
			ar.add(r.nextInt(len)); // cap the size just for easier readability
		}
		return ar;
	}
}
