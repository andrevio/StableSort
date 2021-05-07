package com.stablesort.kdtree.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.stablesort.kdtree.KDTree;
import com.stablesort.kdtree.KDTree.KDNode;
import com.stablesort.kdtree.KDTree.KDPoint;
import com.stablesort.util.StopWatch;

public class KDTreeTest {
	
	static final int numPoints = 100_000; 
	static final int numTests = 100;
	
	static long treeSearchTimeTotal = 0;
	static long linearSearchTimeTotal = 0;
	
	static void test1() {
		Random r = new Random();		
		KDPoint target = new KDPoint(Arrays.asList(r.nextInt(1000), r.nextInt(1000), r.nextInt(1000)));
		
		KDNode root = new KDNode(Arrays.asList(0, 0, 0));									
		KDTree tree = new KDTree(root);
		
		long bestDistance = KDTree.dist(target, root.point);		
		KDNode bestPoint = root;		
		
		// create a tree and keep track of the closest neighbor while doing it
		List<KDPoint> points = new ArrayList<>();
		
		for (int i = 0; i < numPoints; i++) {
			KDPoint p = new KDPoint(Arrays.asList(r.nextInt(1000), r.nextInt(1000), r.nextInt(1000)));
			KDNode n = new KDNode(p);
			tree.add(n);
			
			points.add(p); 
			
			if (KDTree.dist(target, n.point) < bestDistance) {
				bestDistance = KDTree.dist(target, n.point);
				bestPoint = n;
			}
		}
		
//		System.out.println("bestDistance = " + bestDistance + ", point=" + bestPoint);
		
		StopWatch sw = new StopWatch();
		
		// run the search algorithm and check if finds the same node
		KDNode nn = tree.nearestNeighbor(target);
		treeSearchTimeTotal += sw.duration();
		
		
		long linearSearchBestDist = Integer.MAX_VALUE;
		for (KDPoint point : points) {
			linearSearchBestDist = Math.min(linearSearchBestDist, KDTree.dist(target, point));
		}
		linearSearchTimeTotal += sw.duration();
		
		
		if (KDTree.dist(target, nn.point) != bestDistance) {
			System.out.println(tree);
			System.out.println(points);
			throw new RuntimeException("bestDistance = " + bestDistance + ", point=" + bestPoint + ", calculated: nn = " + nn + ", dist=" + KDTree.dist(target, nn.point) + ", target=" + target);
			
		}
		
//		System.out.println("calculated: nn = " + nn + ", dist=" + tree.distanceSquared(target, nn.point)); 
	}
	
	
	
	public static void main(String[] args) {
		for (int i = 0; i < numTests; i++) {
			test1();	
		}
		
		System.out.println("tree search time = " + treeSearchTimeTotal + ", linear search time = " + linearSearchTimeTotal);
		System.out.println("SUCCESS");
	}
}
