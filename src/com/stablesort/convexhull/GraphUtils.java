package com.stablesort.convexhull;

import java.util.Collection;
import java.util.Iterator;

public class GraphUtils {
		
	/**
	 * finds the bottom most, left most point
	 * @param points
	 * @return
	 */
	static Point getMinY(Collection<? extends Point> points) {
		
		Iterator<? extends Point> it = points.iterator();
		Point min = it.next();
		
		while (it.hasNext()) {
			Point point = it.next();
			if (point.y <= min.y) {
				if (point.y < min.y) {
					min = point;
				} else if (point.x < min.x) { // point.y==min.y, pick left most one
					min = point;
				}
			}
		}
		
		return min;
	}

	/**
	 * Line drawn from 'a' to 'b' to 'c'. We make use of the cross product, which calculates the area
	 * of the parallelogram. If vector ab is on the left of vector ac, then 
	 * 
	 * @param a - origin
	 * @param b
	 * @param c
	 * @return 1 if counter-clockwise, -1 if clockwise, 0 if collinear
	 */
	static int ccw(Point a, Point b, Point c) {
		/*
		 * determinant (the cross product) calculates the signed area of parallelogram
		 */
		float area = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
		
		if (area < 0) return -1; // clockwise
		
		if (area > 0) return 1; // counter-clockwise
		
		return 0; // collinear
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return TRUE if strictly counter clock wise
	 */
	static boolean isCcwStrict(Point a, Point b, Point c) {
		return ccw(a, b, c) > 0;
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	static double dist(Point a, Point b) {
		return Math.sqrt(((a.x - b.x) * (a.x - b.x)) + ((a.y - b.y) * (a.y - b.y)));
	}
	
	/**
	 * calculates the angle between the horizontal and the line drawn from 'a' to 'b'
	 * @param a
	 * @param b
	 * @return
	 */
	static float angle(Point a, Point b) {
		float angle = (float) Math.toDegrees(Math.atan2(b.y - a.y, b.x - a.x));
		if (angle < 0) {
			angle += 360;
		}
		return angle;
	}
}
