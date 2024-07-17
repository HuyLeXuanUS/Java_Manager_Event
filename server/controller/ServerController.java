package server.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

import server.gui.AddShowtimeGUI;
import server.gui.ServerGUI;
import server.model.FileManager;
import shared.model.Event;
import shared.model.Showtime;

public class ServerController {
    private final ServerGUI serverGUI;
    private AddShowtimeGUI addShowtimeGUI;
    private AddShowtimeController addShowtimeController;

    public ServerController(ServerGUI serverGUI) {
        this.serverGUI = serverGUI;
        this.serverGUI.addShowtimeButtonListener(new AddShowtimeButtonListenerInServerGUI());
    }

    class AddShowtimeButtonListenerInServerGUI implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            serverGUI.setEnablePage(false);
            addShowtimeGUI = new AddShowtimeGUI();
            addShowtimeController = new AddShowtimeController(addShowtimeGUI);

            addShowtimeGUI.addShowtimeButtonListener(new AddShowtimeButtonListenerInShowtimeGUI());
            addShowtimeGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    serverGUI.setEnablePage(true);
                    showListShowtime();
                }
            });
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

                Event event = FileManager.loadEvent();
                event.addShowtime(showtime);
                FileManager.saveEvent(event);
                serverGUI.showListShowtime(event);
                addShowtimeGUI.dispose();
                JOptionPane.showMessageDialog(null,
                        "Thêm suất chiếu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void showListShowtime() {
        serverGUI.showListShowtime(FileManager.loadEvent());
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
        }
        return true;
    }
    
}
