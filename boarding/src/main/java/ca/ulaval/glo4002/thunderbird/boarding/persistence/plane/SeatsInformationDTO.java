package ca.ulaval.glo4002.thunderbird.boarding.persistence.plane;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SeatsInformationDTO {
    class PriceClassDTO {
        public String name;
        public int[] rows;
    }

    class SeatDTO {
        public int row;
        public String seat;
        public int legroom;
        public boolean window;
        @JsonProperty("clear_view")
        public boolean clearView;
        public double price;
    }

    @JsonProperty("exit_rows")
    public int[] exitRows;
    public PriceClassDTO[] classes;
    public SeatDTO[] seats;
}
