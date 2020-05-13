package com.gsd.sreenidhi.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Sreenidhi, Gundlupet
 *
 */
public class CalendarUtils {

	/**
	 * Convert Java Date to String
	 * @param date Date
	 * @return string format of Date
	 */
	public static String dateToString(Date date) {
		String dt;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		dt = df.format(date);
		return dt;
	}
	
	/**
	 * Convert Java Date to String
	 * @param date Date
	 * @return string format of Date
	 */
	public static String dateToString(Date date, String format) {
		String dt;
		DateFormat df = new SimpleDateFormat(format);
		dt = df.format(date);
		return dt;
	}
	
	/**
	 * Returns the current time stamp.
	 * @return Current Timestamp
	 */
	public static String getCurrentTimeStamp() {
		DateFormat dF = DateFormat.getDateTimeInstance();
		Date dte = new Date();
		return dF.format(dte);
		
	}
	
	/**
     * Convert a millisecond duration to a string format
     * 
     * @param millis A duration to convert to a string form
     * @return A string of the form "X Days Y Hours Z Minutes A Seconds".
     */
    public static String getDurationBreakdown(long millis)
    {
        if(millis < 0)
        {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder sb = new StringBuilder(64);
        sb.append(days);
        sb.append(" Days ");
        sb.append(hours);
        sb.append(" Hours ");
        sb.append(minutes);
        sb.append(" Minutes ");
        sb.append(seconds);
        sb.append(" Seconds");

        return sb.toString();
    }
}
