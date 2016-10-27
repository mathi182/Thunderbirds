package ca.ulaval.glo4002.thunderbird.reservation.infrastructure.util;

import java.util.Date;

public class DateLong {
    public static long getLongCurrentDate() {
        Date now = new Date();
        return now.getTime();
    }
}
