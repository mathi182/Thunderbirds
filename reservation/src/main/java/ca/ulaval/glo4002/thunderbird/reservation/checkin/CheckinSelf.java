package ca.ulaval.glo4002.thunderbird.reservation.checkin;

import ca.ulaval.glo4002.thunderbird.reservation.reservation.Reservation;
import ca.ulaval.glo4002.thunderbird.reservation.util.DateLong;

import java.text.ParseException;
import java.text.SimpleDateFormat;

class CheckinSelf extends CheckinAgentId {
    private static final long MAX_LATE_CHECKIN_IN_MILLIS = 60 * 60 * 6 * 1000L;
    private static final long MAX_EARLY_CHECKIN_IN_MILLIS = 60 * 60 * 48 * 1000L;
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static final String SELF = "SELF";

    public CheckinSelf(String passengerHash) {
        super(passengerHash, SELF);
    }

    @Override
    public boolean isValid() {
        return super.isValid() && isSelfCheckinOnTime();
    }

    private boolean isSelfCheckinOnTime() {
        Reservation reservation = Reservation.findByReservationNumber(getPassenger().getReservationNumber());
        String flightDate = reservation.getFlightDate();
        try {
            SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
            long parsedFlightDate = format.parse(flightDate.replaceAll("Z$", "+0000")).getTime();
            long maxEarlySelfCheckinDate = parsedFlightDate - MAX_EARLY_CHECKIN_IN_MILLIS;
            long maxLateSelfCheckinDate = parsedFlightDate - MAX_LATE_CHECKIN_IN_MILLIS;
            long currentTime = DateLong.getLongCurrentDate();
            return (currentTime > maxEarlySelfCheckinDate) && (currentTime < maxLateSelfCheckinDate);
        } catch (ParseException e) {
            return false;
        }
    }
}