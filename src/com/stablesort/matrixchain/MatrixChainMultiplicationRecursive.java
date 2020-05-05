package com.stablesort.matrixchain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stablesort.util.Rand;

/**
 * Two recursive versions implemented, one with and the other without memorization.
 * 
 * Matrix Chain Multiplication is a classic problem in computer science that involves finding the most optimal way of multiplying a chain of 2 dimensional matrices.
 * 
 * Since matrix multiplication is associative, matrixes could be multiplied simply sequentially. Or, as in this example, A2 could be multiplied with A3 and then A1 could 
 * be multiplied with the result of the previous operation. In both cases the resulting matrices are identical but the costs of these operations, which is defined as the 
 * number of arithmetic operations involved in the process, may be different. So the problem is to find the best sequence of operations so as to minimize the total cost.
 * 
 * Stable Sort youtube tutorial: https://youtu.be/JMql7zF87aE
 * 
 * https://en.wikipedia.org/wiki/Matrix_chain_multiplication
 * 
 * @author Andre Violentyev
 */
public class MatrixChainMultiplicationRecursive {
	
	/**
	 * Reference solution is efficient but hard to read. Places parenthesis at different places between first and last matrix, 
	 * recursively calculate count of multiplications for each parenthesis placement and return the minimum count 
	 * 
	 * @param p
	 * @param i
	 * @param j
	 * @return
	 */
	static int cost(int p[], int i, int j) { 
        if (i == j) return 0; 
  
        int min = Integer.MAX_VALUE; 
  
        for (int k = i; k < j; k++) { 
            int count = cost(p, i, k) + cost(p, k+1, j) + p[i-1]*p[k]*p[j]; 
  
            if (count < min) {
            	min = count;
            }
        } 
  
        return min; 
    } 

	/**
	 * recursive solution, easy to read but slow, O(2^n)
	 * 
	 * @param dims
	 * @param i - left inclusive index to dims
	 * @param j - right inclusive index to dims
	 * @return
	 */
	static MxCost cost(List<MxCost> dims, int i, int j) {
		if (i == j) {
			return dims.get(i); // just one matrix, nothing to multiply so cost is zero
		}
		
		MxCost min = null;
		for (int k = i; k < j; k++) {
			MxCost left = cost(dims, i, k);
			MxCost right = cost(dims, k+1, j);			
			MxCost result = left.multiply(right);
			
			if (min == null || min.cost > result.cost) {
				min = result; // found a better solution
			}
		}

		return min;
	}
	
	/**
	 * recursive solution, easy to read and uses memorization to speedup operation to O(n^3)
	 * 
	 * @param dims
	 * @param i - left inclusive index to dims
	 * @param j - right inclusive index to dims
	 * @param solutions - key is a combination of i&j
	 * @return
	 */
	static MxCost cost(List<MxCost> dims, int i, int j, Map<String, MxCost> solutions) {
		if (i == j) {
			return dims.get(i); // just one matrix, nothing to multiply so cost is zero
		}
		
		String key = i + "." + j;
		MxCost solution = solutions.get(key);
		if (solution != null) {
			return solution;
		}
		
		MxCost min = null;
		for (int k = i; k < j; k++) {
			MxCost leftMx = cost(dims, i, k, solutions);
			MxCost rightMx = cost(dims, k+1, j, solutions);			
			MxCost result = leftMx.multiply(rightMx);
			
			if (min == null || min.cost > result.cost) {
				min = result; // found a better solution
			}
		}

		solutions.put(key, min);
		return min;
	}
	
	
	public static void main(String[] args) {
//		int[] dims = {1, 3, 5}; // 15
//		int[] dims = {1, 3, 5, 7}; // 50
//		int[] dims = {40, 20, 30, 10, 30}; // 26000 = (A(BC))D --> 20*30*10 + 40*20*10 + 40*10*30
//		int[] dims = {10, 20, 30, 40, 30}; // 30K
//		int[] dims = {2, 2, 8, 6, 9, 9, 2, 1, 9, 6}; // 287
		int[] dims = Rand.getRandIntAr(20, Rand.Option.positive);
		
		System.out.println(Arrays.toString(dims));
		
		List<MxCost> l = MxCost.make(dims);
		System.out.println(l);
		
		MxCost mx = cost(l, 0, l.size()-1, new HashMap<>());
		System.out.println("final="+ mx.cost);
		System.out.println("MxCost chain = " + MxCost.printSeq(mx));
		
//		MxDim a = new MxDim(40, 20);
//		MxDim b = new MxDim(20, 30);
//		MxDim c = new MxDim(30, 10);
//		MxDim d = new MxDim(10, 30);
//		
//		MxDim bc = b.multiply(c);
//		MxDim abc = a.multiply(bc);
//		MxDim abcd = abc.multiply(d);
//		System.out.println("abcd" +abcd + ", cost=" + abcd.cost);
//		
//		MxDim abcd2= (a.multiply(b.multiply(c))).multiply(d);
//		System.out.println("2 abcd=" + abcd2 + ", cost=" + abcd2.cost);
	}
}
