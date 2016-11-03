package ca.ulaval.glo4002.thunderbird.boarding.persistence.plane;

import ca.ulaval.glo4002.thunderbird.boarding.domain.plane.Seat;

import java.util.*;

public class SeatsAssembler {
    public List<Seat> toDomain(SeatsInformationDTO dto) {
        HashMap<Integer, String> rowClasses = new HashMap<>();
        Set<Integer> exitRows = new HashSet<>();

        for (SeatsInformationDTO.PriceClassDTO priceClass : dto.classes) {
            for (int row : priceClass.rows) {
                rowClasses.put(row, priceClass.name);
            }
        }

        for (int row : dto.exitRows) {
            exitRows.add(row);
        }

        List<Seat> seats = new ArrayList<>(dto.seats.length);

        for (SeatsInformationDTO.SeatDTO seatDTO : dto.seats) {
            String priceClass = rowClasses.get(seatDTO.row);
            boolean isExitRow = exitRows.contains(seatDTO.row);
            Seat seat = new Seat(seatDTO.row, seatDTO.seat, seatDTO.legroom, seatDTO.window, seatDTO.clearView,
                    seatDTO.price, priceClass, isExitRow, false);
            seats.add(seat);
        }

        return seats;
    }
}
