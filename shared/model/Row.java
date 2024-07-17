package shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Row implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Seat> seats;

    public Row() {
        this.seats = new ArrayList<>();
    }

    public Row(String rowNumber) {
        this.seats = new ArrayList<>();
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public Seat getSeat(int seatNumber) {
        for (Seat seat : seats) {
            if (seat.getSeatNumber() == seatNumber) {
                return seat;
            }
        }
        return null;
    }
}
