package shared.gui;

import java.awt.Dimension;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;

import shared.model.Showtime;

public class ViewShowtimeGUI extends JFrame {
    private JLabel showtimeLabel;
    private JLabel startTimeLabel;
    private JLabel endTimeLabel;
    private JLabel sectionLabel;

    private JButton viewSectionButton;

    private JScrollPane jScrollSectionTable;
    private JTable sectionTable;

    private String columnNames[] = { "STT", "Tên khu", "Giá vé", "Số hàng", "Số ghế mỗi hàng" };
    private int selectedRow;

    private Showtime showtime;

    public ViewShowtimeGUI(Showtime showtime) {
        initComponent(showtime);
        this.showtime = showtime;
    }

    private void initComponent(Showtime showtime) {
        setTitle("Xem suất chiếu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        showtimeLabel = new JLabel("Thông tin suất chiếu");
        startTimeLabel = new JLabel("Thời gian bắt đầu:  " + showtime.getStartTime());
        endTimeLabel = new JLabel("Thời gian kết thúc:  " + showtime.getEndTime());
        sectionLabel = new JLabel("Danh sách khu vực:");

        viewSectionButton = new JButton("Xem khu vực");

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
        panel.add(viewSectionButton);
        panel.add(jScrollSectionTable);

        // Set position for each component
        layout.putConstraint(SpringLayout.WEST, showtimeLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, showtimeLabel, 10, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, startTimeLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, startTimeLabel, 50, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, endTimeLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, endTimeLabel, 80, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, viewSectionButton, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, viewSectionButton, 110, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, sectionLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, sectionLabel, 140, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, jScrollSectionTable, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, jScrollSectionTable, 10, SpringLayout.SOUTH, sectionLabel);

        // Show list section
        showListSection(showtime);
        viewSectionButton.setEnabled(false);
        sectionTable.setDefaultEditor(Object.class, null);

        selectSectionTableListener();
        viewSectionButtonListener();

        // Hiển thị frame
        add(panel);
        setSize(500, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void showListSection(Showtime showtime) {
        int size = showtime.getSections().size();
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

    public void setEnabledViewSectionButton(boolean enabled) {
        viewSectionButton.setEnabled(enabled);
    }

    public void selectSectionTableListener() {
        sectionTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    selectedRow = sectionTable.getSelectedRow();
                    if (selectedRow != -1) {
                        setEnabledViewSectionButton(true);
                    } else{
                        setEnabledViewSectionButton(false);
                    }
                }
            }
        });
    }

    public void viewSectionButtonListener() {
        viewSectionButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                new ViewSectionGUI(showtime.getSections().get(selectedRow));
            }
        });
    }
}
