package com.stablesort.util;

import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import java.time.temporal.ChronoUnit;

public class TimeUtils {
	public static final int minYear = 1977;
	
	/**
	 * @param timeout in milliseconds. Must be >= zero.
	 * @throws InterruptedException
	 */
	public static void pause(long timeout) {
		if (timeout < 0) throw new IllegalArgumentException("TimeUtils: pause: timeout must be >= 0");
		
		long curr = System.currentTimeMillis();
		long later = curr + timeout;
		
		while (curr < later) {
			try {
				Thread.sleep(later - curr);
			} catch (InterruptedException e) {
			} finally {
				curr = System.currentTimeMillis();
			}
		}
	}
	
	/**
	 * drop off milliseconds and even seconds if the time duration is large enough.
	 * @param durInMillis - duration in milliseconds
	 * @return
	 */
	public static String getApproxExecutionDurationString(long durInMillis) {
		/*
		 * suppose durInMills = 1*60*60*1000 + 2*60*1000 + 3*1000 + 4 =
		 * 1h 2m 3s 4millis
		 */
		long hours   =  durInMillis / 1000 / 60 / 60;
		long minutes =  durInMillis / 1000 / 60 - hours * 60;
		long seconds =  durInMillis / 1000 - hours * 60 * 60 - minutes * 60;
		long millis  =  durInMillis - hours * 1000 * 60 * 60 - minutes * 1000 * 60 - seconds * 1000;

		durInMillis = durInMillis - millis; // remove milliseconds all together
		
		if (hours * 60 + minutes > 2) { // if time is bigger than 2 minutes, we remove the seconds
			durInMillis = durInMillis - seconds * 1000;
		}
		
		return getExecutionDurationString(durInMillis);
	}
	
	/**
	 * makes a human readable time duration
	 * @param durInMillis - in milliseconds
	 * @return "10 minutes and 6 seconds"
	 */
	public static String getExecutionDurationString(long durInMillis) {
		long hours = 0;
		long minutes = 0;
		long seconds = 0;
		long milliseconds = 0;		
		
		if (durInMillis / 1000 / 60 / 60 > 0) { // if time is more than one hour
			hours = durInMillis / 1000 / 60 / 60;
			minutes = durInMillis / 1000 / 60 - hours * 60;  
			
			String hs = "";
			if (hours != 1) {
				hs = "s";
			}
			
			String ms = "";
			if (minutes != 1) {
				ms = "s";
			}
			
			if (minutes > 0) {
				return hours + " hour" + hs + " and " + minutes + " minute" + ms;	
			} else {
				return hours + " hour" + hs; // don't include "and 0 minutes"
			}
			
		} else if (durInMillis / 1000 / 60 > 0) { // if time is more than one minute
			minutes = durInMillis / 1000 / 60;
			seconds = durInMillis / 1000 - minutes * 60;  

			String ms = "";
			if (minutes != 1) {
				ms = "s";
			}

			String s = "";
			if (seconds != 1) {
				s = "s";
			}
			
			if (seconds > 0) {
				return minutes + " minute" + ms + " and " + seconds + " second" + s;
			} else {
				return minutes + " minute" + ms; // don't include "and 0 seconds"
			}
			
			
		} else if (durInMillis / 1000 > 0) { // if time is more than one second
			seconds = durInMillis / 1000;
			milliseconds = durInMillis - seconds * 1000;  
			
			String s = "";
			if (seconds != 1) {
				s = "s";
			}
			
			String ms = "";
			if (milliseconds != 1) {
				ms = "s";
			}
			
			if (milliseconds > 0) {
				return seconds + " second" + s + " and " + milliseconds + " millisecond" + ms;
			} else {
				return seconds + " second" + s; // don't include "and 0 milliseconds"	
			}
			
		} else { // if time is in milliseconds (less than a second)
			
			String ms = "";
			if (milliseconds != 1) {
				ms = "s";
			}
			
			return durInMillis + " millisecond" + ms;
		}	
	}
	
	/**
	 * utility method for creating a nice output string that tells the duration of an execution
	 * @param java.util.Date startDate
	 * @param java.util.Date endDate
	 * @return String
	 */
	public static String getExecutionDurationString(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return "unknown";
		}
		
		long diff = endDate.getTime() - startDate.getTime();
		return getExecutionDurationString(diff);
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isValidDateForMySQL(Date date) {
		if (date == null) {
			return false;
		}
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		return minYear < c.get(Calendar.YEAR);
	}	

	/**
	 * Creates a Timestamp of today's date, but sets it's hours, minute, seconds and millisecond components to zero.
	 * For example: 2015-05-27 00:00:00.0
	 */
	public static Timestamp getTodayWithoutTime() {
		LocalDate today = LocalDate.now();
		String s = today.toString();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			return new Timestamp(sdf.parse(s).getTime());
		} catch (ParseException e) {
			// never happens
			throw new RuntimeException(e);
		}			
	}
	
	/**
	 * Get a diff between two dates. Example: getDateDiff(date1,date2).
	 * Defaults to using TimeUtil.DAYS
	 * 
	 * @param date1 the oldest date
	 * @param date2 the newest date
	 * @return the diff value
	 */
	public static long getDateDiff(Date date1, Date date2) {
		return getDateDiff(date1, date2, TimeUnit.DAYS);
	}

	/**
	 * 
	 * @param date1 - 1/10/2010
	 * @param date2 - 3/31/2010
	 * @return 90
	 */
	public static long getDayDiffInclusive(LocalDate date1, LocalDate date2) {
		return ChronoUnit.DAYS.between(date1, date2.plusDays(1)); // +1 to make it inclusive
	}
	
	/**
	 * Get a diff between two dates. Example: getDateDiff(date1,date2,TimeUnit.MINUTES);
	 * 
	 * @param date1 the oldest date
	 * @param date2 the newest date
	 * @param timeUnit the unit in which you want the diff
	 * @return the diff value, in the provided unit
	 */
	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return timeUnit.convert(diffInMillies, timeUnit);
	}

	public static LocalDate toLocalDate(Date dt) {
		return new java.sql.Date(dt.getTime()).toLocalDate();
	}
	
	public static Date toDate(LocalDate localDate) {
		return java.sql.Date.valueOf(localDate);
	}
	
	public static Timestamp toTimestamp(LocalDate localDate) {
		return Timestamp.valueOf(localDate.atStartOfDay());
	}
	
	/**
	 * 
	 * @param month 1 = January
	 * @return
	 */
	public String getMonth(int month) {
	    return new DateFormatSymbols().getMonths()[month-1];
	}
	
	public static void main(String argv[]) {
		System.out.println(getApproxExecutionDurationString(0*60*60*1000 + 1*60*1000 + 3*1000 + 4));
	}
}
