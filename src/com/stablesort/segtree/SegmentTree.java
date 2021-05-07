package com.stablesort.segtree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator ;

/**
 * This implementation works on any binary associate function, such as min/max/addition/multiplication/xor/etc.
 * Simply pass it in to the constructor. For example: 
 * 
 * 		BiFunction<Double, Double, Double> f = (a, b) -> Math.max(a, b);
 * 		SegmentTreeGeneral<Double> tree = new SegmentTreeGeneral<Double>(ar, f);
 * 
 * Works with any Number types (Integers, Float, etc.)
 * 
 * Implementation inspired by Al.Cash, from: https://codeforces.com/blog/entry/18051
 * @author Andre Violentyev
 */
public class SegmentTree<T> {
	private final List<T> tree;
	private final int n; // input array length
	private final BinaryOperator<T> f;
	
	/**
	 * @param ar
	 * @param f - any binary associate function, such as min/max/addition/multiplication/xor/etc. For example: (a, b) -> Math.max(a, b)
	 */
	public SegmentTree(List<T> ar, BinaryOperator<T> f){
		n = ar.size();		
		tree = new ArrayList<>(2*n);
		this.f = f;
		
		// make space for it so that we don't have to do inserts
		for (int i = 0; i < n; i++) {
			tree.add(null);
		}

		// store into the right end of the array
		for (int i = 0; i < n; i++) {
			tree.add(ar.get(i));
		}
						
		for (int i = n - 1; i > 0; i--) {			
			tree.set(i, f.apply(tree.get(2 * i), tree.get(2 * i + 1)));
		}
	}
	
	/**
	 * @param i - index to original array
	 * @param value - new value to be saved off
	 */
	public void update(int i, T value) {
        i += n;
        tree.set(i, value);
        T newValue;
        
        while (i > 1) {
            i >>= 1; // shift right is the same as divide by 2
            newValue = f.apply(tree.get(2 * i), tree.get(2 * i + 1));
            
            if (!newValue.equals(tree.get(i))) {
            	tree.set(i, newValue);	
            } else {
            	return; // since no update to propagate up the tree
            }
        }
    }

	/**
	 * Starts at the bottom of the tree and works its way up until reaching nodes that cover the requested range.
	 * Note how we always start from the right end of the array. Then on each iteration of the loop, the index variables 'from' and 'to' are halved. 
	 * Thus, on each iteration we go up one level on the tree.
	 * 
	 * Note that we only invoke binary operation function (such as Math.max(a, b)) if the indexes 'from' and 'to' are odd. It's because if the index is even, then 
	 * it is the left child node. We don't need to bother reading it since we'll get another chance at doing it on the next level up. Of course, this same 
	 * logic applies on the next level up.
	 * 
	 * @param from - inclusive
	 * @param to - exclusive
	 * @return
	 */
    public T query(int from, int to) {
    	from += n; // go to second half of the array
        to += n;
        T q = null;

        while (from < to) {
            if ((from & 1) == 1) { // 'from' is odd, so it is the right child of its parent, then interval includes node 'from' but doesn't include its parent
                q = q == null ? tree.get(from) : f.apply(q, tree.get(from));
                from++;
            }
            if ((to & 1) == 1) { // 'to' is odd, so it's the right child of its parent, then might as well use the parent
                to--;
                q = q == null ? tree.get(to) : f.apply(q, tree.get(to));
                                
            }
            from >>= 1; // shift right is the same as divide by 2 but a little faster
            to >>= 1;            
        }
        
        return q;
    }
    
	public static void main(String[] args) {
		List<Double> ar = Arrays.asList(6.1, 10.5, 5.2, 2.0, 7.1, 1.3, 0.3, 9.2);
		BinaryOperator<Double> f = (a, b) -> Math.max(a, b);
		SegmentTree<Double> tree = new SegmentTree<Double>(ar, f);
		System.out.println("q=" + tree.query(2,  8));
		
		tree.update(2, 20.0);
		System.out.println("q=" + tree.query(0,  8));
	}
}
