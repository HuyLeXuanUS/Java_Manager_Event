package client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import shared.gui.ViewShowtimeGUI;
import shared.model.Event;

public class ViewEventGUI extends JFrame {
    private BufferedReader reader;
    private PrintWriter writer;
    private ObjectInputStream in;

    private JLabel eventLabel;
    private JLabel dateTimeLabel;
    private JLabel showtimeLabel;

    private JButton viewShowtimeButton;
    private JButton bookTickeButton;
    private JButton reloadUIButton;

    private JScrollPane jScrollShowTimeTable;
    private JTable showTimeTable;

    private String columnNames[] = { "STT", "Giờ bắt đầu", "Giờ kết thúc", "Số khu vực" };
    private int selectedRow;

    private Event event;

    public ViewEventGUI(Event event) {
        this.event = event;
        initComponent(event);
    }

    public void setClientSocket(BufferedReader reader, PrintWriter writer, ObjectInputStream in) {
        this.reader = reader;
        this.writer = writer;
        this.in = in;
    }

    private void initComponent(Event event) {
        setTitle("Khách hàng - Xem sự kiện");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        eventLabel = new JLabel("Thông tin Sự kiện");
        dateTimeLabel = new JLabel("Ngày: " + event.getDay() + "/" + event.getMonth() + "/" + event.getYear());
        showtimeLabel = new JLabel("Danh sách suất chiếu:");
        viewShowtimeButton = new JButton("Xem suất chiếu");
        bookTickeButton = new JButton("Đặt vé");
        reloadUIButton = new JButton("Làm mới");

        showTimeTable = new JTable();
        jScrollShowTimeTable = new JScrollPane();

        // Table
        showTimeTable.setModel(new DefaultTableModel(new Object[][] {}, columnNames));
        showTimeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
        panel.setSize(600, 500);

        // Add components to panel
        panel.add(eventLabel);
        panel.add(dateTimeLabel);
        panel.add(viewShowtimeButton);
        panel.add(bookTickeButton);
        panel.add(reloadUIButton);
        panel.add(showtimeLabel);
        panel.add(jScrollShowTimeTable);

        // Set location for components
        layout.putConstraint(SpringLayout.WEST, eventLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, eventLabel, 10, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, dateTimeLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, dateTimeLabel, 10, SpringLayout.SOUTH, eventLabel);

        layout.putConstraint(SpringLayout.WEST, bookTickeButton, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, bookTickeButton, 20, SpringLayout.NORTH, dateTimeLabel);

        layout.putConstraint(SpringLayout.WEST, viewShowtimeButton, 10, SpringLayout.EAST, bookTickeButton);
        layout.putConstraint(SpringLayout.NORTH, viewShowtimeButton, 20, SpringLayout.NORTH, dateTimeLabel);

        layout.putConstraint(SpringLayout.EAST, reloadUIButton, 0, SpringLayout.EAST, jScrollShowTimeTable);
        layout.putConstraint(SpringLayout.NORTH, reloadUIButton, 20, SpringLayout.NORTH, dateTimeLabel);

        layout.putConstraint(SpringLayout.WEST, showtimeLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, showtimeLabel, 40, SpringLayout.NORTH, reloadUIButton);

        layout.putConstraint(SpringLayout.WEST, jScrollShowTimeTable, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, jScrollShowTimeTable, 10, SpringLayout.SOUTH, showtimeLabel);

        // Show list showtime
        showListShowtime(event);
        viewShowtimeButton.setEnabled(false);
        showTimeTable.setDefaultEditor(Object.class, null);

        selectShowtimeTableListener();
        viewShowtimeButtonListener();
        bookTickeButtonListener();
        reloadUIButtonListener();

        // Hiển thị frame
        add(panel);
        setSize(600, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void showListShowtime(Event event) {
        if (event.getShowTimes() == null) {
            return;
        }

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

    public void setUI(Event event) {
        this.event = event;
        dateTimeLabel.setText("Ngày: " + event.getDay() + "/" + event.getMonth() + "/" + event.getYear());
        showListShowtime(event);
    }

    public void setEnabledViewShowtimeButton(boolean enabled) {
        viewShowtimeButton.setEnabled(enabled);
    }

    // Button Listener
    public void viewShowtimeButtonListener() {
        viewShowtimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                new ViewShowtimeGUI(event.getShowTimes().get(selectedRow));
            }
        });
    }

    public void reloadUIButtonListener() {
        reloadUIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                sendReloadRequest();
            }
        });
    }

    public void bookTickeButtonListener() {
        bookTickeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {   
                new BookingTicketGUI(reader, writer);
            }
        });
    }

    public void selectShowtimeTableListener() {
        showTimeTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    selectedRow = showTimeTable.getSelectedRow();
                    if (selectedRow != -1) {
                        setEnabledViewShowtimeButton(true);
                    } else {
                        setEnabledViewShowtimeButton(false);
                    }
                }
            }
        });
    }

    public void sendReloadRequest() {
        try {
            writer.println("Reload");
            Event receivedEvent = (Event) in.readObject();
            setUI(receivedEvent);
            JOptionPane.showMessageDialog(null, "Làm mới thành công");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
