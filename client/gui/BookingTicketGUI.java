package client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import shared.model.Ticket;

public class BookingTicketGUI extends JFrame {
    private BufferedReader reader;
    private PrintWriter writer;

    private JLabel booking;
    private JLabel nameLabel;
    private JLabel phoneNumberLabel;
    private JLabel showtimeLabel;
    private JLabel sectionLabel;
    private JLabel rowLabel;
    private JLabel seatNumberLabel;

    private JTextField nameField;
    private JTextField phoneNumberField;
    private JTextField showtimeField;
    private JTextField sectionField;
    private JTextField rowField;
    private JTextField seatNumberField;

    private JButton bookingButton;

    public BookingTicketGUI(BufferedReader reader, PrintWriter writer) {
        this.reader = reader;
        this.writer = writer;
        initComponent();
    }

    private void initComponent() {
        setTitle("Đặt vé xem phim");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        booking = new JLabel("Đặt vé xem phim");
        nameLabel = new JLabel("Họ tên:");
        phoneNumberLabel = new JLabel("Số điện thoại:");
        showtimeLabel = new JLabel("STT Suất:");
        sectionLabel = new JLabel("STT Khu:");
        rowLabel = new JLabel("Dãy:");
        seatNumberLabel = new JLabel("Số ghế:");

        nameField = new JTextField(15);
        phoneNumberField = new JTextField(15);
        showtimeField = new JTextField(3);
        sectionField = new JTextField(3);
        rowField = new JTextField(3);
        seatNumberField = new JTextField(3);

        bookingButton = new JButton("Đặt vé");

        Font font = new Font("Arial", Font.BOLD, 20);
        booking.setFont(font);
        booking.setForeground(Color.RED);

        // Create Spring Layout
        SpringLayout layout = new SpringLayout();
        JPanel panel = new JPanel();
        panel.setLayout(layout);
        panel.setSize(270, 300);

        // Add components to panel
        panel.add(booking);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(phoneNumberLabel);
        panel.add(phoneNumberField);
        panel.add(showtimeLabel);
        panel.add(showtimeField);
        panel.add(sectionLabel);
        panel.add(sectionField);
        panel.add(rowLabel);
        panel.add(rowField);
        panel.add(seatNumberLabel);
        panel.add(seatNumberField);
        panel.add(bookingButton);

        // Set constraints
        layout.putConstraint(SpringLayout.WEST, booking, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, booking, 10, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, nameLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, nameLabel, 30, SpringLayout.NORTH, booking);

        layout.putConstraint(SpringLayout.WEST, nameField, 0, SpringLayout.WEST, phoneNumberField);
        layout.putConstraint(SpringLayout.NORTH, nameField, 30, SpringLayout.NORTH, booking);

        layout.putConstraint(SpringLayout.WEST, phoneNumberLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, phoneNumberLabel, 30, SpringLayout.NORTH, nameLabel);

        layout.putConstraint(SpringLayout.WEST, phoneNumberField, 10, SpringLayout.EAST, phoneNumberLabel);
        layout.putConstraint(SpringLayout.NORTH, phoneNumberField, 30, SpringLayout.NORTH, nameLabel);

        layout.putConstraint(SpringLayout.WEST, showtimeLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, showtimeLabel, 30, SpringLayout.NORTH, phoneNumberLabel);

        layout.putConstraint(SpringLayout.WEST, showtimeField, 0, SpringLayout.WEST, phoneNumberField);
        layout.putConstraint(SpringLayout.NORTH, showtimeField, 30, SpringLayout.NORTH, phoneNumberLabel);

        layout.putConstraint(SpringLayout.WEST, sectionLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, sectionLabel, 30, SpringLayout.NORTH, showtimeLabel);

        layout.putConstraint(SpringLayout.WEST, sectionField, 0, SpringLayout.WEST, phoneNumberField);
        layout.putConstraint(SpringLayout.NORTH, sectionField, 30, SpringLayout.NORTH, showtimeLabel);

        layout.putConstraint(SpringLayout.WEST, rowLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, rowLabel, 30, SpringLayout.NORTH, sectionLabel);

        layout.putConstraint(SpringLayout.WEST, rowField, 0, SpringLayout.WEST, phoneNumberField);
        layout.putConstraint(SpringLayout.NORTH, rowField, 30, SpringLayout.NORTH, sectionLabel);

        layout.putConstraint(SpringLayout.WEST, seatNumberLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, seatNumberLabel, 30, SpringLayout.NORTH, rowLabel);

        layout.putConstraint(SpringLayout.WEST, seatNumberField, 0, SpringLayout.WEST, phoneNumberField);
        layout.putConstraint(SpringLayout.NORTH, seatNumberField, 30, SpringLayout.NORTH, rowLabel);

        layout.putConstraint(SpringLayout.WEST, bookingButton, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, bookingButton, 30, SpringLayout.NORTH, seatNumberLabel);

        addBookingButtonListener();
        
        // Add panel to frame
        add(panel);
        setSize(270, 300);
        setResizable(false);
        setVisible(true);
    }

    public void addBookingButtonListener() {
        bookingButton.addActionListener(
                (ActionListener) e -> {
                    if (nameField.getText().isEmpty() || phoneNumberField.getText().isEmpty()
                            || showtimeField.getText().isEmpty() || sectionField.getText().isEmpty()
                            || rowField.getText().isEmpty() || seatNumberField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin");
                    } else if (!isInteger(showtimeField.getText()) || !isInteger(sectionField.getText())
                            || !isInteger(rowField.getText()) || !isInteger(seatNumberField.getText())) {
                        JOptionPane.showMessageDialog(null, "Dữ liệu không hợp lệ");
                    } else {
                        Ticket ticket = new Ticket(nameField.getText(), phoneNumberField.getText(),
                                Integer.parseInt(showtimeField.getText()),
                                Integer.parseInt(sectionField.getText()),
                                Integer.parseInt(rowField.getText()),
                                Integer.parseInt(seatNumberField.getText()));
                        sendBookingRequest(ticket);
                    }
                });
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void sendBookingRequest(Ticket ticket) {
        try {
            String ticketInfo = String.format("Booking+%s+%s+%d+%d+%d+%d", 
                ticket.getName(), ticket.getPhoneNumber(), ticket.getShowtime(), 
                ticket.getSection(), ticket.getRow(), ticket.getSeatNumber());

            writer.println(ticketInfo);
            String response = reader.readLine();

            if (response.equals("Booking success")) {
                JOptionPane.showMessageDialog(null, "Đặt vé thành công");
            } else {
                JOptionPane.showMessageDialog(null, "Đặt vé thất bại");
            }


        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Không thể kết nối đến server");
        }
    }
}
