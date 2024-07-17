package shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Event implements Serializable{
    private static final long serialVersionUID = 1L;
    private int day;
    private int month;
    private int year;
    private List<Showtime> showTimes;

    public Event() {
        showTimes = new ArrayList<>();
    }

    public Event(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
        showTimes = new ArrayList<>();
    }

    // Getters and Setters
    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public List<Showtime> getShowTimes() {
        return showTimes;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void addShowtime(Showtime showtime) {
        showTimes.add(showtime);
    }
    
    public boolean bookTicket(int showtimeIndex, int sectionIndex, int row, int seatIndex) {
        if (showtimeIndex < 1 || showtimeIndex > showTimes.size()) {
            return false;
        }
        if (showTimes.get(showtimeIndex - 1).bookTicket(sectionIndex, row, seatIndex)) {
            return true;
        }
        return false;
    }
}
