package com.wms.newwmsapp.tool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.text.TextUtils;

@SuppressLint("SimpleDateFormat")
public class JsonDateFormate {

	public static String dataFormate(String dates) {
		if ((!TextUtils.isEmpty(dates)) && (dates.contains("("))) {
			String ss = dates.substring(dates.indexOf("(") + 1,
					dates.indexOf(")"));
			if (ss.contains("+")) {
				ss = ss.substring(0, ss.indexOf("+"));
			}
			long t = Long.parseLong(ss);
			TimeZone zone = TimeZone.getTimeZone("GMT+0800");
			Calendar c = Calendar.getInstance(zone);
			c.setTimeInMillis(t);
			Date d = c.getTime();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String result = sf.format(d);
			String sub = result.substring(0, 2);
			if (sub.equals("00")) {
				return "";
			} else {
				return result;
			}
		} else {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = StringToDate(dates);
			String str = dateFormat.format(date);
			return str;
		}
	}

	public static Date StringToDate(String strDate) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dateFormat.parse(strDate);
		} catch (ParseException e) {
			try {
				// 第二种格式方案
				date = dateFormat2.parse(strDate);
			} catch (ParseException e1) {
				e1.printStackTrace();
				return null;
			}
		}
		return date;
	}

	public static String JsonFormate(String jsonStr) {
		String json = jsonStr.toString().replace("/", "");
		json = json.replaceAll("\\\\", "");
		json = json.substring(0, json.indexOf("[") - 1)
				+ json.substring(json.indexOf("["), json.indexOf("]") + 1)
				+ json.substring(json.indexOf("]") + 2, json.length());
		;
		return json;
	}

	// 获取当前时间
	public static String getDate() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String date = sDateFormat.format(new java.util.Date());
		return date;
	}
}
