package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import ca.ulaval.glo4002.thunderbird.reservation.util.DateLong;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CheckinSelf extends Checkin {
    private static final long MAX_LATE_CHECKIN_IN_MILLIS = 60 * 60 * 6 * 1000L;
    private static final long MAX_EARLY_CHECKIN_IN_MILLIS = 60 * 60 * 48 * 1000L;
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String SELF = "SELF";

    public CheckinSelf(@JsonProperty("passenger_hash") String passengerHash) {
        super(passengerHash, SELF);
    }

    public CheckinSelf(Checkin checkin){
        super(checkin);
    }

    @JsonIgnore
    private boolean isSelfCheckinOnTime() {
        Reservation reservation = Reservation.findByReservationNumber(getPassenger().getReservationNumber());
        String flightDate = reservation.getFlightDate();
        boolean isOnTime = false;
        try {
            SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
            long parsedFlightDate = format.parse(flightDate.replaceAll("Z$", "+0000")).getTime();
            long maxEarlySelfCheckinDate = parsedFlightDate - MAX_EARLY_CHECKIN_IN_MILLIS;
            long maxLateSelfCheckinDate = parsedFlightDate - MAX_LATE_CHECKIN_IN_MILLIS;
            long currentTime = DateLong.getLongCurrentDate();
            isOnTime = (currentTime>maxEarlySelfCheckinDate) && (currentTime<maxLateSelfCheckinDate);
        } catch (ParseException e) {
            isOnTime = false;
        }
        return isOnTime;
    }

    @JsonIgnore
    @Override
    public boolean isValid() {
        return super.isValid() && isSelfCheckinOnTime();
    }

    @JsonIgnore
    @Override
    public boolean isSelfCheckin(){
        return true;
    }
}
