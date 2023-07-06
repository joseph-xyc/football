package com.glowworm.football.booking.service.util;

import com.glowworm.football.booking.domain.common.enums.TrueFalse;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author xuyongchang
 * @date 2023-04-14 17:07
 */
public class DateUtils {

    public static Timestamp getNow() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Timestamp getSomeDayAfterToday(int d) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, d);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return new Timestamp(cal.getTime().getTime());
    }

    public static String getTimestamp2String(Timestamp timestamp) {
        getTimestamp2String(timestamp, "yyyy-MM-dd");
    }

    public static String getTimestamp2String(Timestamp timestamp, String format) {
        if (timestamp == null) {
            return null;
        }

        SimpleDateFormat sim = new SimpleDateFormat(format);
        return sim.format(timestamp);
    }

    public static Timestamp getToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return new Timestamp(cal.getTime().getTime());
    }

    public static Timestamp getTimestamp(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return new Timestamp(sdf.parse(time).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Timestamp getSomeDayAfter(Timestamp now, int i) {
        return new Timestamp(now.getTime() + i * 24 * 60 * 60 * 1000L);
    }

    public static Timestamp getBeginOfDay(Timestamp day) {
        if (day == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(day);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return new Timestamp(cal.getTime().getTime());
    }

    public static List<Timestamp> getEachDays(Timestamp fromDay, Timestamp toDay) {
        if (null == fromDay || null == toDay) {
            return Collections.emptyList();
        }
        if (fromDay.getTime() > toDay.getTime()) {
            return Collections.emptyList();
        }
        List<Timestamp> result = new ArrayList<>();
        Timestamp begin = getBeginOfDay(fromDay);
        Timestamp end = getBeginOfDay(toDay);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(begin);
        Timestamp temp = new Timestamp(calendar.getTimeInMillis());
        while (temp.before(end) || temp.getTime() == end.getTime()) {
            result.add(temp);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            temp = new Timestamp(calendar.getTimeInMillis());
        }
        return result;
    }

    public static int getWeekDay(Timestamp time) {
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        int day = c.get(Calendar.DAY_OF_WEEK) - 1;

        return day == 0 ? 7 : day;
    }

    public static Integer isWeekend (Timestamp time) {

        int weekDay = getWeekDay(time);
        return TrueFalse.getByBoolean(weekDay == 6 || weekDay == 7).getCode();
    }

    public static String getWeekName (Timestamp time) {

        int weekDay = getWeekDay(time);

        String weekName = "未知";
        switch (weekDay) {

            case 1:
                weekName = "周一";
                break;
            case 2:
                weekName = "周二";
                break;
            case 3:
                weekName = "周三";
                break;
            case 4:
                weekName = "周四";
                break;
            case 5:
                weekName = "周五";
                break;
            case 6:
                weekName = "周六";
                break;
            case 7:
                weekName = "周日";
                break;
            default:
                return weekName;
        }

        return weekName;
    }

    public static Timestamp getBeginDayOfWeek(Timestamp timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());
        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);

        return new Timestamp(cal.getTimeInMillis());
    }

    public static Timestamp getEndDayOfWeek(Timestamp timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());
        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);

        cal.add(Calendar.DAY_OF_WEEK, 6);

        return new Timestamp(cal.getTimeInMillis());
    }
}
