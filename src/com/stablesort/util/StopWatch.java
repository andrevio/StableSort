package com.stablesort.util;

import java.util.Date;

/** Time the execution of any block of code. */
public final class StopWatch {
	
	private long start = -1;
	private long poll = -1;

	
	/** 
	 * default constructor starts the watch automatically
	 */
	public StopWatch() {
		start();
	}
	
	/**
	 * @param start if TRUE, then automatically starts ticking
	 */
	public StopWatch(boolean start) {
		if (start) {
			start();
		}
	}
	
	/**
	 * Start the stopwatch.
	 */
	public void start(){
		start = System.currentTimeMillis();
		poll = start;
	}
	
	/**
	 * This is the total duration since start time in milliseconds. The timer is NOT reset.
	 */
	public long duration() { 
		return System.currentTimeMillis() - start;
	}
	
	/**
	 * This is the total duration since start time. The timer is NOT reset.
	 * @return
	 */
	public String durationStr() { 
		return TimeUtils.getExecutionDurationString(duration());
	}

	/**
	 * duration since the last poll. This method resets the "poll" time. 
	 * @return
	 */
	public long poll() {
		long curr = System.currentTimeMillis();
		long delta = curr - poll;
		poll = curr;
		
		return delta;
	}
	
	/**
	 * This may not be the total time since the timer was started. This is the time since the last poll/toString call.
	 */
	public String toString() {
		 
		long curr = System.currentTimeMillis();
		String s = getDurationString(poll, curr);
		poll = curr;
		
		return s;
	}

	/**
	 * utility method for creating a nice output string that tells the duration of an execution
	 * @param long startDate
	 * @param long endDate
	 * @return String
	 */
	protected static String getDurationString(long startDate, long endDate) {
		Date sDate = new Date(startDate);
		Date eDate = new Date(endDate);
		
		return TimeUtils.getExecutionDurationString(sDate, eDate);		
	}
	
	/**
	 * An example of the use of this class to
	 * time the execution of String manipulation code.
	 */
	public static void main (String[] arguments) {
		StopWatch stopwatch = new StopWatch();

		//do stuff
		StringBuilder messageOne = new StringBuilder();
		int numIterations = 50000;
		for(int idx=0; idx < numIterations; ++idx){
			messageOne.append("blah");
		}

		System.out.println("The reading for StringBuilder is: " + stopwatch);

		//do stuff again
		String messageTwo = null;
		for(int idx=0; idx < numIterations; ++idx){
			messageTwo = messageTwo + "blah";
		}

		System.out.println("duration = " + stopwatch);
		System.out.println("total duration = " + stopwatch.durationStr());
	}

}

