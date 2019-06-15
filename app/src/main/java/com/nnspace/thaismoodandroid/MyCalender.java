package com.nnspace.thaismoodandroid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MyCalender {
    public static final String[] monthOfYear = {"มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายม", "พฤษภาคม", "มิถุนายน", "กรกฎาคม", "สิงหาคม", "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม"};
    public static final String[] dayOfWeek = {"อาทิตย์", "จันทร์", "อังคาร", "พุธ", "พฤหัสบดี", "ศุกร์", "เสาร์"};

    public static String getMonthOfYear(int month){
        return monthOfYear[month];
    }

    public static String getDayOfWeek(int day){
        return dayOfWeek[day-1];
    }

    public static String[] findDiffDay(String startDate)throws ParseException {

        Calendar calendar = Calendar.getInstance();
        String endDate = calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        Date firstDate = sdf.parse(startDate);
        Date secondDate = sdf.parse(endDate);

        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        String[] toReturn = new String[3];
        toReturn[0] = startDate;
        toReturn[1] = endDate;
        toReturn[2] = diff + "";
        return toReturn;
    }
}
