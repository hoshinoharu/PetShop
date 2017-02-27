package shop.mgzj.petshop.harubase.tools;

import java.util.Date;
import java.util.TimeZone;

/**
 * Created by 星野悠 on 2017/1/22.
 */

public class DateTool {
    public static final int SECONDS_IN_DAY = 60 * 60 * 24;
    public static final long MILLIS_IN_DAY = 1000L * SECONDS_IN_DAY;

    public static boolean isSameDay(Date date1, Date date2){
        return isSameDayOfMillis(date1.getTime(), date2.getTime()) ;
    }

    public static boolean isSameDayOfMillis(final long ms1, final long ms2) {
        final long interval = ms1 - ms2;
        return interval < MILLIS_IN_DAY
                && interval > -1L * MILLIS_IN_DAY
                && toDay(ms1) == toDay(ms2);
    }
    private static long toDay(long millis) {
        return (millis + TimeZone.getDefault().getOffset(millis)) / MILLIS_IN_DAY;
    }
}
