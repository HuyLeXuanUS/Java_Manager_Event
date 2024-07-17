package server.controller;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import server.gui.AddEventGUI;
import server.gui.AddShowtimeGUI;
import server.model.FileManager;
import shared.model.Event;
import shared.model.Showtime;

public class AddEventController {
    private final AddEventGUI addEventGUI;
    private AddShowtimeGUI addShowtimeGUI;
    private AddShowtimeController addShowtimeController;
    private Event event;

    public AddEventController(AddEventGUI addEventGUI) {
        this.event = new Event();

        this.addEventGUI = addEventGUI;
        this.addEventGUI.addShowtimeButtonListener(new AddShowtimeButtonListenerInEvenGUI());
        this.addEventGUI.addEventButtonListener(new AddEventButtonListener());
    }

    class AddShowtimeButtonListenerInEvenGUI implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            addEventGUI.setEnablePage(false);
            addShowtimeGUI = new AddShowtimeGUI();
            addShowtimeController = new AddShowtimeController(addShowtimeGUI);

            addShowtimeGUI.addShowtimeButtonListener(new AddShowtimeButtonListenerInShowtimeGUI());

            addShowtimeGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    addEventGUI.setEnablePage(true);
                    showListShowtime();
                }
            });
        }
    }

    class AddEventButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String day = addEventGUI.getDay();
            String month = addEventGUI.getMonth();
            String year = addEventGUI.getYear();

            if (day.isEmpty() || month.isEmpty() || year.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Điền đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            } else if (!isInteger(day) || !isInteger(month) || !isInteger(year)) {
                JOptionPane.showMessageDialog(null,
                        "Dữ liệu không hợp lệ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            } else if (!checkDate(Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year))) {
                JOptionPane.showMessageDialog(null,
                        "Ngày không hợp lệ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            } else if (event.getShowTimes().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Thêm ít nhất một suất chiếu!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            } else {
                event.setDay(Integer.parseInt(day));
                event.setMonth(Integer.parseInt(month));
                event.setYear(Integer.parseInt(year));
                
                FileManager.saveEvent(event);
                JOptionPane.showMessageDialog(null,
                        "Thêm sự kiện thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                addEventGUI.dispose();
            }
        }
    }

    class AddShowtimeButtonListenerInShowtimeGUI implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String startHour = addShowtimeGUI.getStartHour();
            String startMinute = addShowtimeGUI.getStarMinute();
            String endHour = addShowtimeGUI.getEndHour();
            String endMinute = addShowtimeGUI.getEndMinute();

            if (startHour.isEmpty() || startMinute.isEmpty() || endHour.isEmpty() || endMinute.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Điền đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            } else if (!isInteger(startHour) || !isInteger(startMinute) || !isInteger(endHour)
                    || !isInteger(endMinute)) {
                JOptionPane.showMessageDialog(null,
                        "Dữ liệu không hợp lệ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            } else if (!checkTime(Integer.parseInt(startHour), Integer.parseInt(startMinute), Integer.parseInt(endHour),
                    Integer.parseInt(endMinute))) {
                JOptionPane.showMessageDialog(null,
                        "Thời gian không hợp lệ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            } else if (addShowtimeController.getCountSection() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Thêm ít nhất một khu vực!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            } else {
                Showtime showtime = addShowtimeController.getShowtime();
                showtime.setHourStart(Integer.parseInt(startHour));
                showtime.setMinuteStart(Integer.parseInt(startMinute));
                showtime.setHourEnd(Integer.parseInt(endHour));
                showtime.setMinuteEnd(Integer.parseInt(endMinute));

                event.addShowtime(showtime);
                addEventGUI.showListShowtime(event);
                addShowtimeGUI.dispose();
                JOptionPane.showMessageDialog(null,
                        "Thêm suất chiếu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void showListShowtime() {
        addEventGUI.showListShowtime(this.event);
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean checkTime(int startHour, int startMinute, int endHour, int endMinute) {
        if (startHour > endHour) {
            return false;
        } else if (startHour == endHour && startMinute >= endMinute) {
            return false;
        } else if (startHour < 0 || startHour > 23 || endHour < 0 || endHour > 23) {
            return false;
        } else if (startMinute < 0 || startMinute > 59 || endMinute < 0 || endMinute > 59) {
            return false;
        }
        return true;
    }

    private boolean checkDate(int day, int month, int year) {
        if (year <= 0) {
            return false;
        }

        if (month < 1 || month > 12) {
            return false;
        }

        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if (isLeapYear(year)) {
            daysInMonth[1] = 29;
        }

        return day >= 1 && day <= daysInMonth[month - 1];
    }

    private static boolean isLeapYear(int year) {
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                return year % 400 == 0;
            }
            return true;
        }
        return false;
    }
}
