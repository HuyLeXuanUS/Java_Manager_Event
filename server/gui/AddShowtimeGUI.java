package server.gui;

import java.awt.Dimension;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import shared.model.Showtime;

public class AddShowtimeGUI extends JFrame {
    private JLabel showtimeLabel;
    private JLabel startTimeLabel;
    private JLabel endTimeLabel;
    private JLabel sectionLabel;
    private JLabel colon_1;
    private JLabel colon_2;
    private JLabel colon_3;
    private JLabel colon_4;

    private JButton addSectionButton;
    private JButton addShowtimeButton;

    private JTextField startHourField;
    private JTextField startMinuteField;
    private JTextField endHourField;
    private JTextField endMinuteField;

    private JScrollPane jScrollSectionTable;
    private JTable sectionTable;

    private String columnNames[] = { "STT", "Tên khu", "Giá vé", "Số hàng", "Số ghế mỗi hàng" };

    public AddShowtimeGUI() {
        initComponent();
    }

    private void initComponent() {
        setTitle("Thêm suất chiếu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        showtimeLabel = new JLabel("Thêm suất chiếu");
        startTimeLabel = new JLabel("Thời gian bắt đầu:");
        endTimeLabel = new JLabel("Thời gian kết thúc:");
        sectionLabel = new JLabel("Danh sách khu vực:");
        colon_1 = new JLabel("giờ");
        colon_2 = new JLabel("phút");
        colon_3 = new JLabel("giờ");
        colon_4 = new JLabel("phút");

        addSectionButton = new JButton("Thêm khu vực");
        addShowtimeButton = new JButton("Thêm suất chiếu");

        startHourField = new JTextField(2);
        startMinuteField = new JTextField(2);
        endHourField = new JTextField(2);
        endMinuteField = new JTextField(2);

        sectionTable = new JTable();
        jScrollSectionTable = new JScrollPane();

        // Table
        sectionTable.setModel(new DefaultTableModel(new Object[][] {}, columnNames));
        jScrollSectionTable = new JScrollPane(sectionTable);
        jScrollSectionTable.setViewportView(sectionTable);
        jScrollSectionTable.setPreferredSize(new Dimension(450, 150));

        // Custom Component
        showtimeLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
        showtimeLabel.setForeground(java.awt.Color.RED);

        // Create Spring Layout
        SpringLayout layout = new SpringLayout();
        JPanel panel = new JPanel();
        panel.setLayout(layout);
        panel.setSize(500, 400);

        // Add component to panel
        panel.add(showtimeLabel);
        panel.add(startTimeLabel);
        panel.add(endTimeLabel);
        panel.add(sectionLabel);
        panel.add(colon_1);
        panel.add(colon_2);
        panel.add(colon_3);
        panel.add(colon_4);
        panel.add(addSectionButton);
        panel.add(addShowtimeButton);
        panel.add(startHourField);
        panel.add(startMinuteField);
        panel.add(endHourField);
        panel.add(endMinuteField);
        panel.add(jScrollSectionTable);

        // Set position for each component
        layout.putConstraint(SpringLayout.WEST, showtimeLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, showtimeLabel, 10, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, startTimeLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, startTimeLabel, 50, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, startHourField, 10, SpringLayout.EAST, startTimeLabel);
        layout.putConstraint(SpringLayout.NORTH, startHourField, 50, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, colon_1, 2, SpringLayout.EAST, startHourField);
        layout.putConstraint(SpringLayout.NORTH, colon_1, 50, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, startMinuteField, 2, SpringLayout.EAST, colon_1);
        layout.putConstraint(SpringLayout.NORTH, startMinuteField, 50, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, colon_2, 2, SpringLayout.EAST, startMinuteField);
        layout.putConstraint(SpringLayout.NORTH, colon_2, 50, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, endTimeLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, endTimeLabel, 80, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, endHourField, 10, SpringLayout.EAST, endTimeLabel);
        layout.putConstraint(SpringLayout.NORTH, endHourField, 80, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, colon_3, 2, SpringLayout.EAST, endHourField);
        layout.putConstraint(SpringLayout.NORTH, colon_3, 80, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, endMinuteField, 2, SpringLayout.EAST, colon_3);
        layout.putConstraint(SpringLayout.NORTH, endMinuteField, 80, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, colon_4, 2, SpringLayout.EAST, endMinuteField);
        layout.putConstraint(SpringLayout.NORTH, colon_4, 80, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, addSectionButton, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, addSectionButton, 110, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, sectionLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, sectionLabel, 140, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, jScrollSectionTable, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, jScrollSectionTable, 10, SpringLayout.SOUTH, sectionLabel);

        layout.putConstraint(SpringLayout.WEST, addShowtimeButton, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, addShowtimeButton, 10, SpringLayout.SOUTH, jScrollSectionTable);

        // Hiển thị frame
        add(panel);
        setSize(500, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void showListSection(Showtime showtime) {
        int size = showtime.getNumberOfAreas();
        Object[][] data = new Object[size][5];

        for (int i = 0; i < size; i++) {
            data[i][0] = i + 1;
            data[i][1] = showtime.getSections().get(i).getSectionName();
            data[i][2] = showtime.getSections().get(i).getPriceTicket();
            data[i][3] = showtime.getSections().get(i).getNumberOfRows();
            data[i][4] = showtime.getSections().get(i).getNumberOfSeats();
        }
        sectionTable.setModel(new DefaultTableModel(data, columnNames));
    }

    public void addSectionButtonListener(java.awt.event.ActionListener listener) {
        addSectionButton.addActionListener(listener);
    }

    public void addShowtimeButtonListener(java.awt.event.ActionListener listener) {
        addShowtimeButton.addActionListener(listener);
    }

    public void setEnablePage(boolean b) {
        this.setEnabled(b);
    }

    // Get value from text field
    public String getStartHour() {
        return startHourField.getText();
    }

    public String getStarMinute() {
        return startMinuteField.getText();
    }

    public String getEndHour() {
        return endHourField.getText();
    }

    public String getEndMinute() {
        return endMinuteField.getText();
    }
}
