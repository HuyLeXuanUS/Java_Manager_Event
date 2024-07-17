package server.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import server.controller.AddEventController;
import shared.model.Event;

import java.awt.*;
import java.awt.event.ActionListener;

public class AddEventGUI extends JFrame {
    private JLabel eventLabel;
    private JLabel dateTimeLabel;
    private JLabel dayLabel;
    private JLabel monthLabel;
    private JLabel yearLabel;
    private JLabel showtimeLabel;
    private JButton addShowtimeButton;
    private JButton addEventButton;
    private JTextField dayField;
    private JTextField monthField;
    private JTextField yearField;

    private JScrollPane jScrollShowTimeTable;
    private JTable showTimeTable;

    private String columnNames[] = {"STT", "Giờ bắt đầu", "Giờ kết thúc", "Số khu vực"};

    public AddEventGUI() {
        initComponent();
    }

    private void initComponent() {
        setTitle("Thêm sự kiện chiếu phim");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        eventLabel = new JLabel("Thêm mới sự kiện");
        dateTimeLabel = new JLabel("Nhập ngày chiếu:");
        dayLabel = new JLabel("Ngày:");
        monthLabel = new JLabel("Tháng:");
        yearLabel = new JLabel("Năm:");
        showtimeLabel = new JLabel("Danh sách suất chiếu:");
        addShowtimeButton = new JButton("Thêm suất chiếu");
        addEventButton = new JButton("Thêm sự kiện");

        showTimeTable = new JTable();
        jScrollShowTimeTable = new JScrollPane();

        // Table
        showTimeTable.setModel(new DefaultTableModel(new Object[][]{}, columnNames));
        jScrollShowTimeTable = new JScrollPane(showTimeTable);
        jScrollShowTimeTable.setViewportView(showTimeTable);
        jScrollShowTimeTable.setPreferredSize(new Dimension(550, 250));

        // Custom Component
        Font font = new Font("Arial", Font.BOLD, 20);
        eventLabel.setFont(font);
        eventLabel.setForeground(Color.RED);

        // Create Spring Layout
        SpringLayout layout = new SpringLayout();
        JPanel panel = new JPanel();
        panel.setLayout(layout);
        panel.setSize(600,500);

        // Add components to panel
        panel.add(eventLabel);
        panel.add(dateTimeLabel);
        panel.add(dayLabel);
        panel.add(monthLabel);
        panel.add(yearLabel);
        panel.add(dayField = new JTextField(2));
        panel.add(monthField = new JTextField(2));
        panel.add(yearField = new JTextField(5));
        panel.add(addShowtimeButton);
        panel.add(addEventButton);
        panel.add(showtimeLabel);
        panel.add(jScrollShowTimeTable);

        // Set location for components
        layout.putConstraint(SpringLayout.WEST, eventLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, eventLabel, 10, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, dateTimeLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, dateTimeLabel, 10, SpringLayout.SOUTH, eventLabel);

        layout.putConstraint(SpringLayout.WEST, dayLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, dayLabel, 30, SpringLayout.NORTH, dateTimeLabel);

        layout.putConstraint(SpringLayout.WEST, dayField, 5, SpringLayout.EAST, dayLabel);
        layout.putConstraint(SpringLayout.NORTH, dayField, 30, SpringLayout.NORTH, dateTimeLabel);

        layout.putConstraint(SpringLayout.WEST, monthLabel, 20, SpringLayout.EAST, dayField);
        layout.putConstraint(SpringLayout.NORTH, monthLabel, 30, SpringLayout.NORTH, dateTimeLabel);

        layout.putConstraint(SpringLayout.WEST, monthField, 5, SpringLayout.EAST, monthLabel);
        layout.putConstraint(SpringLayout.NORTH, monthField, 30, SpringLayout.NORTH, dateTimeLabel);

        layout.putConstraint(SpringLayout.WEST, yearLabel, 20, SpringLayout.EAST, monthField);
        layout.putConstraint(SpringLayout.NORTH, yearLabel, 30, SpringLayout.NORTH, dateTimeLabel);

        layout.putConstraint(SpringLayout.WEST, yearField, 5, SpringLayout.EAST, yearLabel);
        layout.putConstraint(SpringLayout.NORTH, yearField, 30, SpringLayout.NORTH, dateTimeLabel);

        layout.putConstraint(SpringLayout.WEST, addShowtimeButton, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, addShowtimeButton, 60, SpringLayout.NORTH, dateTimeLabel);

        layout.putConstraint(SpringLayout.WEST, showtimeLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, showtimeLabel, 40, SpringLayout.NORTH, addShowtimeButton);

        layout.putConstraint(SpringLayout.WEST, jScrollShowTimeTable, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, jScrollShowTimeTable, 10, SpringLayout.SOUTH, showtimeLabel);

        layout.putConstraint(SpringLayout.WEST, addEventButton, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, addEventButton, 10, SpringLayout.SOUTH, jScrollShowTimeTable);

        // Hiển thị frame
        add(panel);
        setSize(600, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void showListShowtime(Event event) {
        int size = event.getShowTimes().size();
        Object[][] data = new Object[size][4];

        for (int i = 0; i < size; i++) {
            data[i][0] = i + 1;
            data[i][1] = event.getShowTimes().get(i).getStartTime();
            data[i][2] = event.getShowTimes().get(i).getEndTime();
            data[i][3] = event.getShowTimes().get(i).getNumberOfAreas();
        }
        showTimeTable.setModel(new DefaultTableModel(data, columnNames));
    }

    // Getters
    public String getDay() {
        return dayField.getText();
    }

    public String getMonth() {
        return monthField.getText();
    }

    public String getYear() {
        return yearField.getText();
    }

    // Button Listener
    public void addShowtimeButtonListener(ActionListener listener) {
        addShowtimeButton.addActionListener(listener);
    }

    public void addEventButtonListener(ActionListener listener) {
        addEventButton.addActionListener(listener);
    }

    public void setEnablePage(boolean b) {
        this.setEnabled(b);
    }

    public static void main(String[] args) {
        new AddEventController(new AddEventGUI());
    }
}
