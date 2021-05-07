package com.stablesort.fenwick;

import java.util.Random;

public class FenwickTreeXorTest {
	
	private void testPrint(boolean[] tar) {
		System.out.println("============================================================");
		for (int i = 1; i < tar.length; i++) {
			System.out.print(i + "\t");
		}	
		System.out.println("");
		for (int i = 1; i < tar.length; i++) {
			System.out.print(tar[i] ? "1\t" : "0\t");
		}	
		System.out.println("");
	}
	
	/**
	 * brute force way of toggling
	 * @param tar
	 * @param i
	 * @param j
	 */
	private void testToggle(boolean[] tar, int i, int j) {
		for (; i <= j; i++) {
			tar[i] = !tar[i];
		}
	}
	
	/**
	 * 
	 * @param len
	 * @return FALSE if fails
	 */
	private boolean test(int len) {
		boolean[] ftAr = new boolean[len];
		FenwickTreeXor ft = new FenwickTreeXor(ftAr);
				
		boolean[] tar = new boolean[len];
		Random r = new Random();
		
		for (int i = 0; i < 100000; i++) {
			int start = r.nextInt(len-1) + 1;
			int end = r.nextInt(len-1) + 1;
			
			while (end < start) {
				end = r.nextInt(len-1) + 1;
			}
			
			System.out.println("i = " + i + ", start = " + start + ", end = " + end);
			testToggle(tar, start, end);
			
			ft.update(start, true);
			ft.update(end+1, true);
			
			
			for (int k = 1; k < len; k++) {
				if (tar[k] != ft.xor(k)) {
					System.out.println(">>>>>>>>>>>>>>error found");
					this.testPrint(tar);
					ft.printIsOn();
					
					return false;
				}
			}
		}		
		
		this.testPrint(tar);
		ft.printIsOn();
		return true;
	}
	
	public static void main(String[] args) {
		FenwickTreeXorTest t = new FenwickTreeXorTest();
		t.test(10);
	}
}
