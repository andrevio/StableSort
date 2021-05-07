package com.stablesort.fenwick;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.stablesort.function.ReversableOperator;

/**
 * Generic Fenwich Tree (Binary Index Tree) implementation that could use any "reversable" operator, such as addition, multiplication, and xor.
 * It uses ArrayList, which makes it significantly (x2) slower than using arrays[]. For an array based implementation, see com.stablesort.fenwick.FenwickTreeSum
 * 
 * @author Andre Violentyev
 */
public class FenwickTree<T> {

	final private List<T> tree;
	final private ReversableOperator<T> f;
	
	/**
	 * 
	 * @param ar - data starts at index 1, ar[0] is ignored
	 * @param f - the operator should be reversable. For example, the reverse of c = add(a, b) is a = subtract(c, b) 
	 */
	public FenwickTree(List<T> ar, ReversableOperator<T> f) {
		this.f = f;
		
		tree = new ArrayList<>(ar);
		int size = tree.size();
		
		for (int i = 1; i < size; i++) {
			int p = i + (i & -i); // index to parent range
			if (p < size) {
				tree.set(p, f.apply(tree.get(p), tree.get(i)));
			}
		}
	}
	
	/**
	 * Returns the f(tree[i]) from index 1 to i, inclusive
	 * @param i - inclusive
	 */
	public T query(int i) {
	    T q = null;
	    while (i > 0) {
	    	if (q == null) {
	    		q = tree.get(i);
	    	} else {
	    		q = f.apply(q, tree.get(i));
	    	}
	    	
	        i -= i & -i; // zeroes the least significant bit of value 1
	    }
	    return q;
	}
	
	/**
	 * returns the sum from i to j (inclusive)
	 * @param i
	 * @param j
	 * @return
	 */
	public T query(int i, int j) {
		if (i > 1) {
			return f.undo(query(j), query(i-1));	
		} else {
			return query(j);
		}
	}
	
	/**
	 * just the value from the "original array" at index i
	 * @param i
	 * @return
	 */
	public T valueAt(int i) {
		/*
		 * for example if operator is addition, then to get value at original index i, is: sum(1, i) - sum(1, i-1) 
		 */
		return f.undo(query(i), query(i-1));
	}
	
	/**
	 * Adds k to element at index i, propagating the change to the right end of the tree so that range operations still work
	 * @param i
	 * @param k
	 */
	private void apply(int i, T k) {	
	    int size = tree.size();
		while (i < size) { 
	        tree.set(i, f.apply(tree.get(i), k)); // example: tree[i] += k;			
	        i += i & -i; // take the least significant set bit and add to i
	    }
	}
	
	/**
	 * updates the 
	 * @param i
	 * @param value
	 */
	public void update(int i, T value) {
		T orig = valueAt(i);
		apply(i, f.undo(value, orig));
	}
	
	public static void main(String[] args) {
		// data starts at index 1. ar[0] is ignored
		List<Integer> ar = Arrays.asList(0, 5, 2, 9, -3, 5, 20, 10, -7, 2, 3, -4, 0, -2, 15, 5);
				
		ReversableOperator<Integer> o = new ReversableOperator<Integer>() {
			@Override
			public Integer apply(Integer a, Integer b) {
				return a.intValue() + b.intValue();
			}

			@Override
			public Integer undo(Integer c, Integer b) {
				return c.intValue() - b.intValue();
			}		
		};
		
		FenwickTree<Integer> ft = new FenwickTree<>(ar, o);
		
		System.out.println("value at 3 = " + ft.valueAt(3));
		System.out.println("sum(1, 3) = " + ft.query(1, 3));
		
		ft.update(2, 20);
		System.out.println("sum(1, 3) = " + ft.query(1, 3));

	}	
}
