package com.stablesort.util;

public class NumUtils {
	public static int compareFloats(float f1, float f2, float delta) {
	    if (Math.abs(f1 - f2) < delta) {
	         return 0;
	    } else {
	        if (f1 < f2) {
	            return -1;
	        } else {
	            return 1;
	        }
	    }
	}

	/**
	 * Uses <code>0.00001f</code> for delta.
	 */
	public static int compareFloats(float f1, float f2)	{
	     return compareFloats(f1, f2, 0.00001f);
	}
}
