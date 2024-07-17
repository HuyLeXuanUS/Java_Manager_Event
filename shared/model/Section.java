package shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Section implements Serializable {
    private static final long serialVersionUID = 1L;
    private String sectionName;
    private int priceTicket;
    private List<Row> rows;

    public Section() {
        this.rows = new ArrayList<>();
    }

    public Section(String sectionName, int priceTicket, int numRows, int numSeats) {
        this.sectionName = sectionName;
        this.priceTicket = priceTicket;
        this.rows = new ArrayList<>();

        for (int i = 0; i < numRows; i++) {
            Row row = new Row();
            for (int j = 0; j < numSeats; j++) {
                Seat seat = new Seat(j + 1);
                row.getSeats().add(seat);
            }
            this.rows.add(row);
        }
    }

    public String getSectionName() {
        return sectionName;
    }

    public int getPriceTicket() {
        return priceTicket;
    }

    public List<Row> getRows() {
        return rows;
    }

    public int getNumberOfRows() {
        return rows.size();
    }

    public int getNumberOfSeats() {
        return rows.get(0).getSeats().size();
    }

    public void addRow(Row row) {
        this.rows.add(row);
    }

    public boolean isBooked(int row, int seat) {
        return rows.get(row - 1).getSeats().get(seat - 1).isBooked();
    }

    // Book a ticket
    public boolean bookTicket(int row, int seat) {
        if (row < 1 || row > getNumberOfRows() || seat < 1 || seat > getNumberOfSeats()) {
            return false;
        }
        if (isBooked(row, seat)) {
            return false;
        }
        rows.get(row - 1).getSeats().get(seat - 1).setBooked(true);
        return true;
    }
}
