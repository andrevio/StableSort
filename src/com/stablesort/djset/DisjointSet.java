package com.stablesort.djset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * this implementation works on non-negaitve integers only (0 and above)
 * 
 * @author Andre Violentyev
 */
public class DisjointSet {

	final int[] ar;
	int maxSetIdx = 0;
	
	public DisjointSet(int size) {
		ar = new int[size];
		Arrays.fill(ar, -1);
	}

	/**
	 * returns the root of the tree that i belongs to
	 * @param i
	 * @return index to ar[] where the root is (always a non-negative number). 
	 */
	public int find(int i) {
		List<Integer> paths = new ArrayList<>();
		
		while (ar[i] > 0) {
			paths.add(i);
			i = ar[i]; // walk up to the parent			
		}
		
		// path compression - point all directly to root
		for (Integer path : paths) {
			ar[path] = i;
		}
		
		return i;
	}
	
	/**
	 * 
	 * @param i
	 * @param k
	 */
	public void union(int i, int k) {
		int iParentIdx = find(i);
		int kParentIdx = find(k);
		
		if (iParentIdx == kParentIdx) return; // already in the same set
		
		int iSize = ar[iParentIdx];
		int kSize = ar[kParentIdx];

		// Merge smaller set into the big one 
		if (iSize < kSize) {
			// we are actually "increasing" the size of that set. 
			// It's just that, the size is stored as a negative number.
			ar[iParentIdx] += kSize; 
			ar[kParentIdx] = iParentIdx; 
			
			if (-ar[maxSetIdx] < -ar[iParentIdx]) {
				maxSetIdx = iParentIdx;
			}
			
		} else {
			ar[kParentIdx] += iSize;
			ar[iParentIdx] = kParentIdx; 			

			if (-ar[maxSetIdx] < -ar[kParentIdx]) {
				maxSetIdx = kParentIdx;
			}
		}
	}
	
	public int maxSetSize() {	
		return -1 * ar[maxSetIdx];
	}
	
	public int minSetSize() {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < ar.length; i++) {
            // smallest number, less than -1 will be the size of the biggest set
            if (ar[i] < -1 && ar[i] > max) {
                max = ar[i]; // this is the biggest size set
            }
        }
		
		return -1 * max;		
	}
	
	@Override
	public String toString() {
		return Arrays.toString(ar);
	}
	
	public Set<Integer> selectMaxSet() {
		return selectSet(maxSetIdx);
	}
	
	/**
	 * scans through the whole array and selects the sister items
	 * @return
	 */
	public Set<Integer> selectSet(int k) {
		Set<Integer> l = new HashSet<>();
		
		int root = find(k);
		
		for (int i = 0; i < ar.length; i++) {
			if (find(i) == root) {
				l.add(i);
			}
		}
		
		return l;
	}
	
	public static void main(String[] args) {
		DisjointSet ds = new DisjointSet(10);
		ds.union(1, 6);
		ds.union(2, 7);		
		ds.union(3, 8);
		ds.union(4, 9);		
		ds.union(2, 6);
		
		System.out.println(ds);
		System.out.println("root index of =" + ds.find(6));
		
		System.out.println("biggest set size = " + ds.maxSetSize());
		System.out.println("smallest set size = " + ds.minSetSize());
		System.out.println("max set = " + ds.selectMaxSet());
	}
	
}
