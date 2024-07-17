package shared.model;

import java.io.Serializable;

public class Ticket implements Serializable{
    private static final long serialVersionUID = 1L;
    private String name;
    private String phoneNumber;
    private int showtime;
    private int section;
    private int row;
    private int seatNumber;

    public Ticket(String name, String phoneNumber, int showtime, int section, int row, int seatNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.showtime = showtime;
        this.section = section;
        this.row = row;
        this.seatNumber = seatNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getShowtime() {
        return showtime;
    }

    public int getSection() {
        return section;
    }

    public int getRow() {
        return row;
    }

    public int getSeatNumber() {
        return seatNumber;
    }
}
