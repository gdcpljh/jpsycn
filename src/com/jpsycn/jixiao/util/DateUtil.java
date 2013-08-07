package com.jpsycn.jixiao.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	public static Date strToDate(String str) {
		return strToDate(str, "yyyy-MM-dd HH:mm:ss");
	}

	public static Date strToDate2(String str) {
		return strToDate(str, "yyyy-MM-dd");
	}

	public static Date strToDate(String str, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern,
				Locale.getDefault());
		try {
			return format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String formatToString(Date time, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern,
				Locale.getDefault());
		return format.format(new Date());
	}
}
