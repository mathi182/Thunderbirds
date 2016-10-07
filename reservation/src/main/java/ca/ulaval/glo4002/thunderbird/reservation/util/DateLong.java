package ca.ulaval.glo4002.thunderbird.reservation.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by AlexisLessard on 2016-10-06.
 */
public class DateLong {
    public static long getLongCurrentDate(){
        Date now = new Date();
        return now.getTime();
    }
}
