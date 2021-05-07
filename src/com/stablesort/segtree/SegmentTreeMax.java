package com.stablesort.segtree;

import java.util.Arrays;

/**
 * This implementation, unlike the more general SegmentTree, does only max operation. But it is about x2 faster for querying
 * and x10 faster constructing. Also, a little bit easier to understand.
 * 
 * @author Andre Violentyev
 * Implementation inspired by Al.Cash, from: https://codeforces.com/blog/entry/18051
 */
public class SegmentTreeMax {
	private final int[] tree;
	private final int n; // input array length
	
	public SegmentTreeMax(int[] ar) {
		n = ar.length;		
		tree = new int[n * 2];
		
		// store into the right end of the array
		System.arraycopy(ar, 0, tree, n, n);
		
		for (int i = n - 1; i > 0; i--) {			
			tree[i] = Math.max(tree[2 * i], tree[2 * i + 1]);
		}
	}
	
	/**
	 * @param i - index to original array, inclusive
	 * @param value - new value to be saved off
	 */
	public void update(int i, int value) {
        i += n;
        tree[i] = value;
        int newValue;
        
        while (i > 1) {
            i >>= 1; // shift right is the same as divide by 2
            newValue = Math.max(tree[2 * i],tree[2 * i + 1]);
            
            if (tree[i] != newValue) {
            	tree[i] = newValue;	
            } else {
            	return; // since no update is made 
            }
        }
    }

	/**
	 * Starts at the bottom of the tree and works its way up until reaching nodes that cover the requested range.
	 * Note how we always start from the right end of the array. Then on each iteration of the loop, the index variables ‘from’ and ‘to’ are halved. 
	 * Thus, on each iteration we go up one level on the tree.
	 * 
	 * Note that we only invoke Math.max() function if the indexes ‘from’ and ‘to’ are odd. Why is that? It’s because if the index is even, then it’s 
	 * left child node. We don’t need to bother reading it since we’ll get another chance at doing it on the next level up. Of course, this same 
	 * logic applies on the next level up.
	 * 
	 * @param from - inclusive
	 * @param to - exclusive
	 * @return
	 */
    public int max(int from, int to) {
        from += n; // go to second half of the array
        to += n;
        int max = Integer.MIN_VALUE;

        while (from < to) {
            if ((from & 1) == 1) { // 'from' is odd, so it is the right child of its parent, then interval includes node 'from' but doesn't include its parent
                max = Math.max(max, tree[from]);
                from++;
            }
            if ((to & 1) == 1) { // 'to' is odd, so it's the right child of its parent, then might as well use the parent
                to--;
                max = Math.max(max, tree[to]);
                                
            }
            from >>= 1; // shift right is the same as divide by 2 but a little faster
            to >>= 1;            
        }
        
        return max;
    }
	
	public static void main(String[] args) {
		//int[] ar = new int[]{6, 10, 5, 2, 7, 1, 0, 9};
		int[] ar = new int[]{12, 7, 1, 0, 9};
		SegmentTreeMax tree = new SegmentTreeMax(ar);
		System.out.println(Arrays.toString(tree.tree));
		//System.out.println("max=" + tree.max(1,  7));
		
	}
}
