package com.stablesort.function;

/**
 * Some operations are "reversable". For example, addition: a+b=c. Then the reverse of that is subtraction, since c-b=a.
 * Other operations are no reversable. For example, max or min.
 */
public interface ReversableOperator<T> {
	
	/**
	 * For example: (a, b) -> a + b
	 * @param a
	 * @param b
	 * @return
	 */
	public T apply(T a, T b);
	
	/**
	 * This should be the reverse of apply() function.
	 * For example: (c, b) -> c - b;
	 * 
	 * @param c
	 * @param b
	 * @return
	 */
	public T undo(T c, T b);
}
