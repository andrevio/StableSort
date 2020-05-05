package com.stablesort.convexhull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * unit tests class for Convex Hull algorithms
 * @author Andre
 */
class ConvexHullTest {

	/**
	 * convenience class has an additional property "include" that indicates if a given point should 
	 * be included in the convex-hull solution.
	 */
	class TestPoint extends Point {
		boolean include = false;
		TestPoint(float x, float y) {
			super(x, y);		
		}
		TestPoint(float x, float y, boolean include) {
			super(x, y);		
			this.include = include;
		}
		@Override 
		public String toString() {
			return "(" + x + ", " + y + ", " + include + ")";
		}
	}
	
	private List<TestPoint> makePoints(float[][] coords) {
		List<TestPoint> points = new ArrayList<>();
		for (int i = 0; i < coords.length; i++) {
			float x = coords[i][0];
			float y = coords[i][1];
			TestPoint p = new TestPoint(x, y);
			p.include = coords[i][2] > 0;
			points.add(p);
		}
		return points;
	}
	
	/**
	 * simplest case. The first two points are x & y. The last point if > 0, then it's included in the solution. If < 0, then it's not part of the convex hull.
	 * @return
	 */
	private List<TestPoint> makeTrianle1() {
		float[][] ar = {
			{1, 1, 1},
			{2, 2, 1},
			{1, 3, 1}
		};
		
		return makePoints(ar);
	}
	
	/**
	 * use negative coordinates
	 * @return
	 */
	private List<TestPoint> makeTrianle2() {
		float[][] ar = {
			{1, 1, 1},
			{-2, 2, 1},
			{-3, 1, 1}
		};
		
		return makePoints(ar);
	}
	
	private List<TestPoint> makeTrianle3() {
		float[][] ar = {
			{1, 1, 1},
			{-2, -2, 1},
			{1, 3, 1}
		};
		
		return makePoints(ar);
	}
	
	/**
	 * since point inside
	 */
	private List<TestPoint> makeTrianle4() {
		float[][] ar = {
			{1, 1, 1},
			{-2, -2, 1},
			{1, 3, 1},
			{0, (float) 0.5, -1}			
		};
		
		return makePoints(ar);
	}
	
	/**
	 * two points on the same line, right side
	 */
	private List<TestPoint> makeTrianle5() {
		float[][] ar = {
			{1, 1, 1},
			{-2, -2, 1},
			{1, 3, 1},
			{0, 0, -1} // collinear, not needed			
		};
		
		return makePoints(ar);
	}

	private List<TestPoint> makeTrianle6() {
		float[][] ar = {
			{1, 1, 1},
			{2, 2, -1},
			{3, 3, 1},
			{-1, 3, 1}			
		};
		
		return makePoints(ar);
	}
	
	/**
	 * multiple collinear points on right side
	 * @return
	 */
	private List<TestPoint> makeTrianle7() {
		float[][] ar = {			
			{2, 2, -1},
			{3, 3, -1},
			{4, 4, -1},
			{5, 5, 1},
			{1, 2, 1},
			{1, 1, 1}
		};
		
		return makePoints(ar);
	}
	
	/**
	 * multiple co-liear points on left side
	 * @return
	 */
	private List<TestPoint> makeTrianle8() {
		float[][] ar = {			
			{-1, -1, 1},
			{-2, -2, -1},
			{-3, -3, -1},
			{-4, -4, -1},
			{-5, -5, 1},
			{-1, -2, 1},
			
		};
		
		return makePoints(ar);
	}
	
	/**
	 * multiple co-liear points on left side, the line does not include start point
	 * @return
	 */
	private List<TestPoint> makeTrianle9() {
		float[][] ar = {			
			{-1, -1, 1},
			{-2, -2, -1},
			{-3, -3, -1},
			{-4, -4, -1},
			{-5, -5, 1},
			{-4, -6, 1}, // start point 			
			{1, -2, 1},
			
		};
		
		return makePoints(ar);
	}
	
	private List<TestPoint> makeSquare10() {
		float[][] ar = {			
			// extras
			{1, 1, -1},
			{2, 2, -1},  			
			{2, -0, -1},
			{0, 2, -1},			
			
			// square
			{0, 0, 1},
			{5, 5, 1},
			{0, 5, 1},
			{5, 0, 1},
		};
		
		return makePoints(ar);
	}

	private List<TestPoint> makeSquare11() {
		float[][] ar = {			
			// extras
			{1, 1, -1},
			{2, 2, -1},
			
			// vertical left line					
			{0, 2, -1},			
			{0, 3, -1},
			{0, 1, -1},	
			
			// vertical right line
			{5, 3, -1},
			{5, 1, -1},
			{5, 2, -1},
						
			// horizontal top
			{1, 5, -1},
			{2, 5, -1},
			{3, 5, -1},
			
			// horizontal bottom
			{2, 0, -1},
			{1, 0, -1},			
			{3, 0, -1},
						
			// square
			{0, 0, 1},
			{5, 5, 1},
			{0, 5, 1},
			{5, 0, 1},
		};
		
		return makePoints(ar);
	}

	private List<TestPoint> makePenta12() {
		float[][] ar = {						 		
			{0, 0, 1},
			{1, 0, 1},
			{2, 1, 1},
			{1, 3, 1},
			{-1, 1, 1},
			{(float) 0.5, (float) 0.5, -1},
		};
		
		return makePoints(ar);
	}

	private List<TestPoint> makePenta13() {
		float[][] ar = {						 		
			{0, 0, 1},
			{1_000, 0, 1},
			{(float) 2_000.00001, (float) 0.0005, 1},
			{1, 3, 1},
			{-1, 1, 1},
			{(float) 0.5, (float) 0.5, -1},
		};
		
		return makePoints(ar);
	}
	
	/**
	 * lots of colinear points
	 * @return
	 */
	private List<TestPoint> makeTriangle14() {
		float[][] ar = {						 		
			{0, 3, -1},
			{1, 2, -1}, // inside
			{-1, 3, 1},
			{0, 2, -1},				
			{2, 2, -1},		
			{2, 3, -1},
			{1, 1, 1},
			{1, 3, -1},
			{3, 3, 1},
		};
		
		return makePoints(ar);
	}
	
	private List<TestPoint> makeStar15() {
		float[][] ar = {						 								
			{1, 1, -1},
			{0, 3, 1},
			{0, -3, 1},
			{-1, 1, -1},
			{3, 0, 1},
			{-1, -1, -1},
			{1, -1, -1},
			{-3, 0, 1},
			{0, 0, -1},
		};
		
		return makePoints(ar);
	}
	
	/**
	 * lots of collinear points, testing the graham scan sort
	 * @return
	 */
	private List<TestPoint> makeTriangle16() {		
		float[][] ar = {						 		
			{0, 3, -1},
			{0, -1, 1},
			{-1, 5, 1},
			{0, 1, -1},
			{0, 2, -1},
			{0, 0, -1},			
			{0, 6, -1},
			{0, 7, 1},
			{0, 4, -1},
			{0, 5, -1},
		};
		
		return makePoints(ar);
	}
	
	private List<TestPoint> makeChevron17() {		
		float[][] ar = {						 		
			{0, 0, 1},
			{1, 1, -1},
			{2, 2, -1},
			{3, 3, 1},
			{3, 4, -1},
			
			{3, 5, 1},			
			{2, 4, -1},
			
			{1, 3, -1},
			{0, 3, -1},
			{-1, 3, -1},
			
			{-2, 4, -1},
			{-3, 5, 1},
			
			{-3, 4, -1},
			{-3, 3, 1},
			
			{-2, 2, -1},
			{-1, 1, -1},									
		};
		
		return makePoints(ar);
	}
	
	private List<TestPoint> makeChevron18() {		
		float[][] ar = {						 		
			{3, 3, 1},
			{1, 1, -1},
			{2, 2, -1},
			{-1, 1, -1},
			{2, 4, -1},
			{3, 4, -1},
			{0, 3, -1},
			{0, 0, 1},
			{3, 5, 1},			
			{1, 3, -1},
			{-3, 5, 1},
			{-1, 3, -1},			
			{-2, 4, -1},
			{-3, 3, 1},
			{-2, 2, -1},
			{-3, 4, -1},
		};
		
		return makePoints(ar);
	}
	
	private List<TestPoint> makeTrianle19() {		
		float[][] ar = {						 		
			{0, 0, 1},
			{0, 3, -1},
			{0, 9, -1},
			{5, 5, 1},
			{0, 10, 1},
			{0, 7, -1},
			{0, 5, -1},			
			{0, 6, -1},			
			{0, 4, -1},
			{0, 8, -1},
		};
		
		return makePoints(ar);
	}
	
	private List<TestPoint> makeTrianle20() {		
		float[][] ar = {						 		
			{0, 0, 1},
			{0, 3, -1},
			{0, 9, -1},
			{-5, 5, 1},
			{0, 10, 1},
			{0, 7, -1},
			{0, 5, -1},			
			{0, 6, -1},			
			{0, 4, -1},
			{0, 8, -1},
		};
		
		return makePoints(ar);
	}
	
	/**
	 * shift left all of the coordinates to check if the algorithm still works in a different quadrant
	 * 
	 * @param points
	 * @param amount
	 * @return
	 */
	private List<TestPoint> shiftLeft(List<TestPoint> points, int amount) {
		List<TestPoint> l = new ArrayList<>(points.size());
		for (TestPoint point : points) {
			l.add(new TestPoint(point.x-amount, point.y, point.include));
		}
		return l;
	}

	private List<TestPoint> shiftRight(List<TestPoint> points, int amount) {
		List<TestPoint> l = new ArrayList<>(points.size());
		for (TestPoint point : points) {
			l.add(new TestPoint(point.x+amount, point.y, point.include));
		}
		return l;
	}

	private List<TestPoint> shiftBottom(List<TestPoint> points, int amount) {
		List<TestPoint> l = new ArrayList<>(points.size());
		for (TestPoint point : points) {
			l.add(new TestPoint(point.x, point.y-amount, point.include));
		}
		return l;
	}

	private List<TestPoint> shiftTop(List<TestPoint> points, int amount) {
		List<TestPoint> l = new ArrayList<>(points.size());
		for (TestPoint point : points) {
			l.add(new TestPoint(point.x+amount, point.y+amount, point.include));
		}
		return l;
	}
	
	private List<TestPoint> shiftLeftBottom(List<TestPoint> points, int amount) {
		List<TestPoint> l = new ArrayList<>(points.size());
		for (TestPoint point : points) {
			l.add(new TestPoint(point.x-amount, point.y-amount, point.include));
		}
		return l;
	}
	
	/**
	 * flipst the x and y coordinates
	 * @param points
	 * @return
	 */
	private List<TestPoint> flip(List<TestPoint> points) {
		List<TestPoint> l = new ArrayList<>(points.size());
		for (TestPoint point : points) {
			l.add(new TestPoint(point.y, point.x, point.include));
		}
		return l;
	}
	
	public void run1() {
		List<List<TestPoint>> testCases = new ArrayList<>();
		testCases.add(makeTrianle1());
		testCases.add(makeTrianle2());
		testCases.add(makeTrianle3());
		testCases.add(makeTrianle4());
		testCases.add(makeTrianle5());
		testCases.add(makeTrianle6());
		testCases.add(makeTrianle7());
		testCases.add(makeTrianle8());
		testCases.add(makeTrianle9());
		testCases.add(makeSquare10());
		testCases.add(makeSquare11());
		testCases.add(makePenta12());
		testCases.add(makePenta13());
		testCases.add(makeTriangle14());
		testCases.add(makeStar15());
		testCases.add(makeTriangle16());
		testCases.add(makeChevron17());
		testCases.add(makeChevron18());
		testCases.add(makeTrianle19());
		testCases.add(makeTrianle20());
		
		List<List<TestPoint>> shiftedTestCases = new ArrayList<>();
		for (List<TestPoint> testCase : testCases) {
			shiftedTestCases.add(shiftLeft(testCase, 100));
			shiftedTestCases.add(shiftLeftBottom(testCase, 100));
			shiftedTestCases.add(shiftBottom(testCase, 100));
			shiftedTestCases.add(shiftRight(testCase, 100));
			shiftedTestCases.add(shiftTop(testCase, 100));
		}
		testCases.addAll(shiftedTestCases);

		shiftedTestCases = new ArrayList<>();
		for (List<TestPoint> testCase : testCases) {
			shiftedTestCases.add(flip(testCase));
		}
		testCases.addAll(shiftedTestCases);
		
		int i = 1;		
		for (List<TestPoint> testCase : testCases) {
			System.out.println("\n\nStarting test #" + i++);
			
			Set<Point> referenceSolution = testCase.stream()
					.filter(point -> point.include)
					.collect(Collectors.toSet());
			
			ConvexHullJarvisMarch ch = new ConvexHullJarvisMarch();
			List<Point> hull = ch.march(testCase);

//			ConvexHullGrahamScan2 ch = new ConvexHullGrahamScan2();
//			List<Point> hull = ch.scan(testCase);
			
			// check if all returned points should be included in the solution
			for (Point point : hull) {
				if (!referenceSolution.remove(point)) {
					throw new RuntimeException("Reference solution does not include point " + point + ", input = " + testCase);
				}
			}
			
			// make sure we didn't miss any points
			if (!referenceSolution.isEmpty()) {
				throw new RuntimeException("Reference solution includes more points: \n" + referenceSolution + ", testCase= \n" + testCase + ", hull=\n" + hull);
				
			}
		}
	}

	/**
	 * construct a random data set and checks if Jarvis-march produces the same solution as Graham-scan
	 */
	public void run2() {
		Random r = new Random();
		float[][] ar = 	new float[1000][3];
		
		for (int i = 0; i < ar.length; i++) {
			ar[i][0] = r.nextFloat() * (r.nextBoolean() ? -100 : 100);
			ar[i][1] = r.nextFloat() * (r.nextBoolean() ? -100 : 100);
			ar[i][2] = 0;
		}
		
		ConvexHullJarvisMarch alg1 = new ConvexHullJarvisMarch();
		List<Point> hull1 = alg1.march(makePoints(ar));
		
		ConvexHullGrahamScan alg2 = new ConvexHullGrahamScan();
		List<Point> hull2 = alg2.scan(makePoints(ar));
		
		if (hull1.size() != hull2.size()) throw new RuntimeException("size of hull1 != hull2: " + hull1 + "\n" + hull2);
		
		for (Point p1 : hull1) {
			boolean foundMatch = false;
			for (Point p2 : hull2) {
				if (p1.equals(p2)) {
					foundMatch = true;
					continue;
				}
			}
			
			if (!foundMatch) {
				throw new RuntimeException("did not find a matching point " + p1);
			}
		}
	}

	public static void main(String[] args) {
		ConvexHullTest cht = new ConvexHullTest();
		cht.run1();
		cht.run2();
		System.out.println("ConvexHullTest: Success");
	}
}
