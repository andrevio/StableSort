package com.stablesort.matrixchain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.stablesort.util.Rand;
import com.stablesort.util.Rand.Option;

/**
 * Matrix Chain Multiplication is a classic problem in computer science that involves finding the most optimal way of multiplying a chain of 2 dimensional matrices.
 * 
 * Since matrix multiplication is associative, matrixes could be multiplied simply sequentially. Or, as in this example, A2 could be multiplied with A3 and then A1 could 
 * be multiplied with the result of the previous operation. In both cases the resulting matrices are identical but the costs of these operations, which is defined as the 
 * number of arithmetic operations involved in the process, may be different. So the problem is to find the best sequence of operations so as to minimize the total cost.
 * 
 * Stable Sort youtube tutorial: https://youtu.be/JMql7zF87aE
 * 
 * https://en.wikipedia.org/wiki/Matrix_chain_multiplication
 * @author Andre Violentyev
 */
public class MatrixChainMultiplicationDP {
    /**
     * 
     * @param dims
     * @return
     */
    public static MxCost cost(List<MxCost> dims) {
        int n = dims.size();
        MxCost tbl[][] = new MxCost[n][n]; 
        
        for (int i = 0; i < n; i++) {
            tbl[i][i] = dims.get(i); // all have cost of zero along the diagonal
        }

        for (int len = 1; len < n; len++) { // repeat for each sub-sequence length
            for (int i = 0; i < n - len; i++) {
                for (int k = i, j = i + len; k < j; k++) { // check for minimum cost for given sequence length
                    
                	MxCost temp = tbl[i][k].multiply(tbl[k+1][j]);
                    if (tbl[i][j] == null || temp.cost < tbl[i][j].cost) {
                        tbl[i][j] = temp;
                    }
                }
            }
        }
        
        return tbl[0][n-1];
    }
	
	private static void test(int[] dims) {
		List<MxCost> l1 = MxCost.make(dims);
		MxCost mx1 = MatrixChainMultiplicationRecursive.cost(l1, 0, l1.size()-1, new HashMap<>());

		List<MxCost> l2 = MxCost.make(dims);
		MxCost mx2 = cost(l2);

		if (mx1.cost != mx2.cost) throw new RuntimeException("reference cost " + mx1.cost + " != " + mx2.cost + ", dims=" + Arrays.toString(dims));
	}
	
	private static void test() {
		for (int i = 0; i < 1000; i++) {
			System.out.println("test " + i);
			int[] dims = Rand.getRandIntAr(100, Option.positive);
			test(dims);
		}
		System.out.println("test SUCCESS");
	}
	
	public static void main(String[] args) {
//		int[] dims = {1, 3, 5}; // 15
//		int[] dims = {1, 3, 5, 7}; // 50
//		int[] dims = {40, 20, 30, 10, 30}; // 26000 = (A(BC))D --> 20*30*10 + 40*20*10 + 40*10*30
//		int[] dims = {10, 20, 30, 40, 30}; // 30K
//		int[] dims = {2, 2, 8, 6, 9, 9, 2, 1, 9, 6}; // 287
//		int[] dims = {2, 2, 8, 6, 9, 9, 2, 1,}; // 221
//		int[] dims = {2, 3, 7, 10, 4}; 
		int[] dims = {7, 1, 5, 2, 20};
//		int[] dims = Rand.getRandIntAr(20, Rand.Option.positive);
		
		System.out.println(Arrays.toString(dims));
		
		List<MxCost> l = MxCost.make(dims);
		System.out.println(l);
		
		MxCost mx = cost(l);
		System.out.println("converted="+mx.cost);
		System.out.println("printSeq=" + MxCost.printSeq(mx)); 

//		test();
	}
}
