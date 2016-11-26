package ca.ulaval.glo4002.thunderbird.boarding.persistence.plane;

import ca.ulaval.glo4002.thunderbird.boarding.domain.seat.Seat;

import java.util.*;

public class SeatsAssembler {
    public List<Seat> toDomain(SeatsInformationDTO dto) {
        HashMap<Integer, Seat.SeatClass> rowClasses = getClassesByRow(dto.classes);
        Set<Integer> exitRows = getExitRowSet(dto.exitRows);
        List<Seat> seats = new ArrayList<>(dto.seats.length);

        for (SeatsInformationDTO.SeatDTO seatDTO : dto.seats) {
            Seat.SeatClass seatClass = rowClasses.get(seatDTO.row);
            boolean isExitRow = exitRows.contains(seatDTO.row);
            Seat seat = new Seat(seatDTO.row, seatDTO.seat, seatDTO.legroom, seatDTO.window, seatDTO.clearView,
                    seatDTO.price, seatClass, isExitRow, true);
            seats.add(seat);
        }

        return seats;
    }

    private HashMap<Integer, Seat.SeatClass> getClassesByRow(SeatsInformationDTO.PriceClassDTO[] classes) {
        HashMap<Integer, Seat.SeatClass> rowClasses = new HashMap<>();
        for (SeatsInformationDTO.PriceClassDTO priceClass : classes) {
            for (int row : priceClass.rows) {
                String name = priceClass.name.toUpperCase().replace("-", "_");
                rowClasses.put(row, Seat.SeatClass.valueOf(name));
            }
        }
        return rowClasses;
    }

    private Set<Integer> getExitRowSet(int[] exitRows) {
        Set<Integer> rowSet = new HashSet<>();

        for (int row : exitRows) {
            rowSet.add(row);
        }

        return rowSet;
    }
}
