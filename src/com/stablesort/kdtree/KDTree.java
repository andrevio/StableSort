package com.stablesort.kdtree;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * YouTube tutorial: https://www.youtube.com/watch?v=Glp7THUpGow 
 * https://en.wikipedia.org/wiki/K-d_tree
 * Basic implementation of a KD-Tree for finding the nearest neighbor. KDNode is a typical tree node with left and right pointers, but instead of a
 * single value, it stores a List of values, encapsulated by KDPoint class.
 * 
 * @author Andre Violentyev
 */
public class KDTree {
	
	public static class KDNode {	
		KDNode left = null;
		KDNode right = null;
		final int numDims;
		
		// This is the data. Each node has K different properties
		public final KDPoint point;
		
		public KDNode(List<Integer> props) {
			this.point = new KDPoint(props);
			this.numDims = props.size();
		}
		
		public KDNode(KDPoint point) {
			this.point = point;
			this.numDims = point.props.size();
		}
		
		private void add(KDNode n) {
			this.add(n, 0);
		}
		
		private void add(KDNode n, int k) {
			if (n.point.get(k) < point.get(k)) {
				if (left == null) {
					left = n;
				} else {
					left.add(n, k+1);
				}
			} else {
				if (right == null) {
					right = n;
				} else {
					right.add(n, k+1);
				}
			}
		}
		
		@Override
		public String toString() {
			return "(point: " + this.point.toString() + ")";
		}
	}
	
	public static class KDPoint {
		final List<Integer> props;
		
		public KDPoint(List<Integer> props) {
			this.props = props;
		}
		
		/**
		 * MOD is automatically applied
		 * @param depth
		 * @return
		 */
		Integer get(int depth) {
			return props.get(depth % props.size());
		}
		
		Integer size() {
			return props.size();
		}
		
		@Override
		public String toString() {
			return props.toString();
		}
	}	

	KDNode root = null;
	final int numDims;
	
	public KDTree(int numDims) {
		this.numDims = numDims;
	}
	
	public KDTree(KDNode root) {
		this.root = root;
		this.numDims = root.point.size();
	}
	
	/**
	 * note that all points must have exactly the same number of dimensions
	 * @param points
	 */
	public KDTree(List<List<Integer>> points) {
		numDims = points.get(0).size();
		root = new KDNode(points.get(0));
		
		for (int i = 1, numPoints = points.size(); i < numPoints; i++) {
			List<Integer> point = points.get(i);
			KDNode n = new KDNode(point);
			root.add(n);
		}
	}

	/**
	 * @param point
	 */
	public void add(KDNode point) {
		if (root == null) {
			root = point;
		} else {
			root.add(point);
		}
	}
	
	/**
	 * @param point
	 */
	public void add(List<Integer> point) {
		KDNode n = new KDNode(point);
		if (root == null) {
			root = n;
		} else {
			root.add(n);
		}
	}
	
	/**
	 * 
	 * @param target - representing a single point, such as (x, y)
	 * @return
	 */
	public KDNode nearestNeighbor(KDPoint target) {
		return nearestNeighbor(root, target, 0);
	}
		
	/**
	 * 
	 * @param root
	 * @param target
	 * @param depth
	 * @return
	 */
	private KDNode nearestNeighbor(KDNode root, KDPoint target, int depth) {
		
		if (root == null) return null;
		
		KDNode nextBranch = null;
		KDNode otherBranch = null;

		// compare the property appropriate for the current depth
		if (target.get(depth) < root.point.get(depth)) {
			nextBranch = root.left;
			otherBranch = root.right;
		} else {	    
			nextBranch = root.right;
			otherBranch = root.left;
		}
		
		// recurse down the branch that's best according to the current depth
		KDNode temp = nearestNeighbor(nextBranch, target, depth + 1);
		KDNode best = closest(temp,	root, target);

		long radiusSquared = distSquared(target, best.point);

		/*
	     * We may need to check the other side of the tree. If the other side is closer than the radius,
	     * then we must recurse to the other side as well. 'dist' is either a horizontal or a vertical line
	     * that goes to an imaginary line that is splitting the plane by the root point.
	     */
		long dist = target.get(depth) - root.point.get(depth);
		
		if (radiusSquared >= dist * dist) {
			temp = nearestNeighbor(otherBranch, target, depth + 1);
			best = closest(temp, best, target);
		}

		return best;
	}
	
	/**
	 * Determines whether n0 or n1 is closer to the target. Does NOT recurse any deeper.
	 * 
	 * @param n0
	 * @param n1
	 * @param target
	 * @return
	 */
	KDNode closest(KDNode n0, KDNode n1, KDPoint target) {
	    if (n0 == null) return n1;

	    if (n1 == null) return n0;

	    long d1 = distSquared(n0.point, target);
	    long d2 = distSquared(n1.point, target);

	    if (d1 < d2)
	        return n0;
	    else
	        return n1;
	}
	
	/**
	 * @param node
	 * @param point
	 * @return
	 */
	public static long dist(KDPoint p0, KDPoint p1) {
		return (long) Math.sqrt(distSquared(p0, p1));
	}
	
	/**
	 * this is distance squared - square root is NOT calculated
	 * 
	 * @param p0
	 * @param p1
	 * @return
	 */
	static long distSquared(KDPoint p0, KDPoint p1) {
		long total = 0;
		int numDims = p0.props.size();
		
		for (int i = 0; i < numDims; i++) {
			int diff = Math.abs(p0.get(i) - p1.get(i));
			total += Math.pow(diff, 2);
		}		
		return total; 
	}
	
	/**
	 * row by row view of the tree
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Queue<KDNode> q = new LinkedList<>();
		q.add(this.root);
		
		while (!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				KDNode n = q.poll();				
				if (n != null) {
					sb.append(n.point).append(", ");
					q.add(n.left);
					q.add(n.right);
				} else {
					sb.append("null, ");
				}
			}
			sb.append('\n');
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {

		int[][] ar = new int[][] {
			{50, 50, 1},
			{80, 40, 2},
			{10, 60, 3},
			{51, 38, 4},
			{48, 38, 5}
			
		};

		KDTree tree = new KDTree(2);
		
		for (int[] coord : ar) {
			KDNode n = new KDNode(Arrays.asList(coord[0], coord[1]));
			tree.add(n);
		}
		
		System.out.println("tree=" + tree.toString());
		System.out.println(tree.nearestNeighbor(new KDPoint(Arrays.asList(40, 40))));
		
	}
}
