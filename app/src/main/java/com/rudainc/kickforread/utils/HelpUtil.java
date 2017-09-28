package com.rudainc.kickforread.utils;

import java.util.Calendar;
import java.util.Date;

public class HelpUtil {

    public static Date covertLongToDate(long input){
        return new Date(input);
    }

    public static Calendar setCalendarDate(long input){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(covertLongToDate(input));
        return calendar;
    }
}
