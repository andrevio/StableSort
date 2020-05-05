package com.stablesort.util;

import java.util.Arrays;

public class MatrixUtils {

	public static String print(int[][] mx) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mx.length; i++) {
			sb.append(Arrays.toString(mx[i]).replace(' ', '\t'));
			sb.append('\n');
		}
		
		return sb.toString();
	}
	
//	private static String pad(String s, char c, int len) {
//		StringBuilder sb = new StringBuilder(s);
//		for (int i = 0, numMissing = s.length() - len; i < numMissing; i++) {
//			sb.append(c);
//		}
//		return sb.toString();
//	}
}
