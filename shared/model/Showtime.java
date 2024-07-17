package shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Showtime implements Serializable{
    private static final long serialVersionUID = 1L;
    private int hourStart;
    private int minuteStart;
    private int hourEnd;
    private int minuteEnd;
    private List<Section> sections;

    public Showtime() {
        sections = new ArrayList<>();
    }

    public Showtime(int hourStart, int minuteStart, int hourEnd, int minuteEnd) {
        this.hourStart = hourStart;
        this.minuteStart = minuteStart;
        this.hourEnd = hourEnd;
        this.minuteEnd = minuteEnd;
        sections = new ArrayList<>();
    }

    public int getHourStart() {
        return hourStart;
    }

    public int getMinuteStart() {
        return minuteStart;
    }

    public int getHourEnd() {
        return hourEnd;
    }

    public int getMinuteEnd() {
        return minuteEnd;
    }

    public List<Section> getSections() {
        return sections;
    }

    // Setters
    public void setHourStart(int hourStart) {
        this.hourStart = hourStart;
    }

    public void setMinuteStart(int minuteStart) {
        this.minuteStart = minuteStart;
    }

    public void setHourEnd(int hourEnd) {
        this.hourEnd = hourEnd;
    }

    public void setMinuteEnd(int minuteEnd) {
        this.minuteEnd = minuteEnd;
    }

    // Get String HH:mm
    public String getStartTime() {
        return hourStart + ":" + String.format("%02d", minuteStart);
    }

    public String getEndTime() {
        return hourEnd + ":" + String.format("%02d", minuteEnd);
    }

    public int getNumberOfAreas() {
        return sections.size();
    }

    public void addSection(Section section) {
        sections.add(section);
    }

    // Book ticket
    public boolean bookTicket(int sectionIndex, int row, int seatIndex) {
        if (sectionIndex < 1 || sectionIndex > sections.size()) {
            return false;
        }
        if (sections.get(sectionIndex - 1).bookTicket(row, seatIndex)) {
            return true;
        }
        return false;
    }

    
}
