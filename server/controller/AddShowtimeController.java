package server.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import server.gui.AddSectionGUI;
import server.gui.AddShowtimeGUI;
import shared.model.Section;
import shared.model.Showtime;

public class AddShowtimeController {
    private final AddShowtimeGUI addShowtimeGUI;
    private AddSectionGUI addSectionGUI;
    private Showtime showtime;

    public AddShowtimeController(AddShowtimeGUI addShowtimeGUI) {
        this.showtime = new Showtime();

        this.addShowtimeGUI = addShowtimeGUI;
        this.addShowtimeGUI.addSectionButtonListener(new AddSectionListener());
    }

    // Getters
    public Showtime getShowtime() {
        return showtime;
    }

    public int getCountSection() {
        return showtime.getSections().size();
    }

    class AddSectionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            addShowtimeGUI.setEnablePage(false);
            addSectionGUI = new AddSectionGUI();

            addSectionGUI.addSectionButtonListener(new AddSectionButtonListener());

            addSectionGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    addShowtimeGUI.setEnablePage(true);
                    showListSection();
                }
            });
        }
    }

    private void showListSection() {
        addShowtimeGUI.showListSection(this.showtime);
    }

    class AddSectionButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String sectionName = addSectionGUI.getSectionName();
            String priceTicket = addSectionGUI.getPriceTicket();
            String numRows = addSectionGUI.getNumRows();
            String numSeats = addSectionGUI.getNumSeats();

            if (sectionName.isEmpty() || priceTicket.isEmpty() || numRows.isEmpty() || numSeats.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Điền đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            } else if (!isInteger(priceTicket) || !isInteger(numRows) || !isInteger(numSeats)) {
                JOptionPane.showMessageDialog(null,
                        "Dữ liệu không hợp lệ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            } else if (!priceTicket.matches("[0-9]+") || !numRows.matches("[0-9]+") || !numSeats.matches("[0-9]+")) {
                JOptionPane.showMessageDialog(null,
                        "Giá vé, số hàng, số ghế phải là số nguyên dương!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            } else {
                Section section = new Section(sectionName, Integer.parseInt(priceTicket), Integer.parseInt(numRows),
                        Integer.parseInt(numSeats));
                showtime.addSection(section);
                addShowtimeGUI.showListSection(showtime);
                addSectionGUI.dispose();
                JOptionPane.showMessageDialog(null,
                        "Thêm khu vực chiếu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
