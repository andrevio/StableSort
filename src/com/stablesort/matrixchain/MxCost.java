package com.stablesort.matrixchain;

import java.util.ArrayList;
import java.util.List;

/**
 * This is not an actual matrix. Rather, it just holds dimensions for calculating the optimal multiplication sequence
 * along with the cost
 * 
 * Stable Sort youtube tutorial: https://youtu.be/JMql7zF87aE
 * 
 * @author Andre Violentyev
 */
class MxCost {
	final int rows;
	final int cols;
	final int cost;
	
	// to trace back the sequence of multiplications
	final MxCost left;
	final MxCost right;
	
	/**
	 * defaults the cost to zero, left=null, right=null. This constructor is used for the initial matrix definitions
	 * at the start of the process
	 * 
	 * @param rows
	 * @param cols
	 */
	public MxCost(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.cost = 0;		
		this.left = null;
		this.right = null;				
	}
	
	/**
	 * This constructor is used when multiplying two MxCost object together, which creates a link to the left and right
	 * parameters. This is useful when printing out the final solution. 
	 *  
	 * @param rows
	 * @param cols
	 * @param cost
	 * @param left
	 * @param right
	 */
	private MxCost(int rows, int cols, int cost, MxCost left, MxCost right) {
		this.rows = rows;
		this.cols = cols;
		this.cost = cost;
		this.left = left;
		this.right = right;
	}
	
	
	/**
	 * calculates the cost of the operation of multiplying this matrix with another
	 * 
	 * @param m
	 * @return
	 */
	private int cost(MxCost m) {
		return this.rows * this.cols * m.cols + this.cost + m.cost;
	}
	
	/**
	 * result of the multiplication is a new matrix with new dimensions
	 * 
	 * @param m
	 * @return
	 */
	public MxCost multiply(MxCost m) {
		return new MxCost(this.rows, m.cols, cost(m), this, m);
	}
		
	@Override 
	public String toString() {
		return "(" + this.rows + "x" + this.cols + " cost:" + this.cost + ")";
	}
	
	/**
	 * converts an int[] typically provided by Matrix Chain Multiplication problem definition into a List of MxCost
	 * that is easier to work with
	 * 
	 * @param dims
	 * @return
	 */
	static List<MxCost> make(int[] dims) {
		List<MxCost> l = new ArrayList<>();
		for (int i = 1; i < dims.length; i++) {
			l.add(new MxCost(dims[i-1], dims[i]));
		}
		return l;
	}
	
	/**
	 * 
	 * @param root
	 * @return example "((1x3*3x5)*5x7)"
	 */
	static String printSeq(MxCost root) {
		StringBuilder sb = new StringBuilder();
		
		if (root.left == null) return root.rows + "x" + root.cols;
		
		sb.append("(");
		sb.append(printSeq(root.left));
		sb.append("*");
		sb.append(printSeq(root.right));
		sb.append(")");
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		MxCost a = new MxCost(10, 3);
		MxCost b = new MxCost(3, 5);
		MxCost c = new MxCost(5, 2);

		System.out.println((a.multiply(b)).multiply(c));
		System.out.println(a.multiply(b.multiply(c)));
	}
}
