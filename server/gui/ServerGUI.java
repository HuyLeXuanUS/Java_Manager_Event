package server.gui;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionListener;

import shared.gui.ViewShowtimeGUI;
import shared.model.Event;
import server.controller.AddEventController;
import server.model.FileManager;

public class ServerGUI extends JFrame {
    private JLabel eventLabel;
    private JLabel dateTimeLabel;
    private JLabel showtimeLabel;

    private JButton addShowtimeButton;
    private JButton viewShowtimeButton;
    private JButton deleteShowtimeButton;
    private JButton addEventButton;
    private JButton listTicketButton;

    private JScrollPane jScrollShowTimeTable;
    private JTable showTimeTable;

    private String columnNames[] = { "STT", "Giờ bắt đầu", "Giờ kết thúc", "Số khu vực" };

    private int selectedRow;

    public ServerGUI() {
        initComponent();
    }

    private void initComponent() {
        Event event = FileManager.loadEvent();

        if (event != null) {
            setTitle("Máy chủ - Quản lý sự kiện");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            eventLabel = new JLabel("Thông tin Sự kiện");
            dateTimeLabel = new JLabel("Ngày: " + event.getDay() + "/" + event.getMonth() + "/" + event.getYear());
            showtimeLabel = new JLabel("Danh sách suất chiếu:");

            addShowtimeButton = new JButton("Thêm suất chiếu");
            viewShowtimeButton = new JButton("Xem suất chiếu");
            deleteShowtimeButton = new JButton("Xóa suất chiếu");
            addEventButton = new JButton("Làm mới sự kiện");
            listTicketButton = new JButton("Danh sách vé");

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
            panel.add(addShowtimeButton);
            panel.add(viewShowtimeButton);
            panel.add(addEventButton);
            panel.add(listTicketButton);
            panel.add(showtimeLabel);
            panel.add(deleteShowtimeButton);
            panel.add(jScrollShowTimeTable);

            // Set location for components
            layout.putConstraint(SpringLayout.WEST, eventLabel, 10, SpringLayout.WEST, panel);
            layout.putConstraint(SpringLayout.NORTH, eventLabel, 10, SpringLayout.NORTH, panel);

            layout.putConstraint(SpringLayout.WEST, dateTimeLabel, 10, SpringLayout.WEST, panel);
            layout.putConstraint(SpringLayout.NORTH, dateTimeLabel, 10, SpringLayout.SOUTH, eventLabel);

            layout.putConstraint(SpringLayout.WEST, addShowtimeButton, 10, SpringLayout.WEST, panel);
            layout.putConstraint(SpringLayout.NORTH, addShowtimeButton, 20, SpringLayout.NORTH, dateTimeLabel);

            layout.putConstraint(SpringLayout.WEST, viewShowtimeButton, 10, SpringLayout.EAST, addShowtimeButton);
            layout.putConstraint(SpringLayout.NORTH, viewShowtimeButton, 20, SpringLayout.NORTH, dateTimeLabel);

            layout.putConstraint(SpringLayout.WEST, deleteShowtimeButton, 10, SpringLayout.EAST, viewShowtimeButton);
            layout.putConstraint(SpringLayout.NORTH, deleteShowtimeButton, 20, SpringLayout.NORTH, dateTimeLabel);

            layout.putConstraint(SpringLayout.EAST, addEventButton, 0, SpringLayout.EAST, jScrollShowTimeTable);
            layout.putConstraint(SpringLayout.NORTH, addEventButton, 20, SpringLayout.NORTH, dateTimeLabel);

            layout.putConstraint(SpringLayout.WEST, showtimeLabel, 10, SpringLayout.WEST, panel);
            layout.putConstraint(SpringLayout.NORTH, showtimeLabel, 40, SpringLayout.NORTH, addShowtimeButton);

            layout.putConstraint(SpringLayout.WEST, jScrollShowTimeTable, 10, SpringLayout.WEST, panel);
            layout.putConstraint(SpringLayout.NORTH, jScrollShowTimeTable, 10, SpringLayout.SOUTH, showtimeLabel);

            layout.putConstraint(SpringLayout.WEST, listTicketButton, 10, SpringLayout.WEST, panel);
            layout.putConstraint(SpringLayout.NORTH, listTicketButton, 20, SpringLayout.SOUTH, jScrollShowTimeTable);

            // Show list showtime
            showListShowtime(event);
            addShowtimeButton.setEnabled(true);
            viewShowtimeButton.setEnabled(false);
            deleteShowtimeButton.setEnabled(false);
            showTimeTable.setDefaultEditor(Object.class, null);

            selectShowtimeTableListener();
            viewShowtimeButtonListener();
            addEventButtonListener();
            deleteShowtimeButtonListener(new ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    Event event = FileManager.loadEvent();
                    event.getShowTimes().remove(selectedRow);
                    FileManager.saveEvent(event);
                    ResetGUI();
                }
            });
            listTicketButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    setEnablePage(false);
                    ListTicketGUI listTicketGUI = new ListTicketGUI();
                    listTicketGUI.setVisible(true);
                    listTicketGUI.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosed(java.awt.event.WindowEvent e) {
                            setEnablePage(true);
                        }
                    });
                }
            });

            // Hiển thị frame
            add(panel);
            setSize(600, 500);
            setResizable(false);
            setLocationRelativeTo(null);
            setVisible(true);
        } else {
            setTitle("Máy chủ - Quản lý sự kiện");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            eventLabel = new JLabel("Thông tin Sự kiện");
            dateTimeLabel = new JLabel("Chưa có sự kiện nào được tạo!");
            showtimeLabel = new JLabel("Danh sách suất chiếu:");

            addShowtimeButton = new JButton("Thêm suất chiếu");
            viewShowtimeButton = new JButton("Xem suất chiếu");
            deleteShowtimeButton = new JButton("Xóa suất chiếu");
            addEventButton = new JButton("Làm mới sự kiện");
            listTicketButton = new JButton("Danh sách vé");

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
            panel.add(addShowtimeButton);
            panel.add(viewShowtimeButton);
            panel.add(addEventButton);
            panel.add(listTicketButton);
            panel.add(showtimeLabel);
            panel.add(deleteShowtimeButton);
            panel.add(jScrollShowTimeTable);

            // Set location for components
            layout.putConstraint(SpringLayout.WEST, eventLabel, 10, SpringLayout.WEST, panel);
            layout.putConstraint(SpringLayout.NORTH, eventLabel, 10, SpringLayout.NORTH, panel);

            layout.putConstraint(SpringLayout.WEST, dateTimeLabel, 10, SpringLayout.WEST, panel);
            layout.putConstraint(SpringLayout.NORTH, dateTimeLabel, 10, SpringLayout.SOUTH, eventLabel);

            layout.putConstraint(SpringLayout.WEST, addShowtimeButton, 10, SpringLayout.WEST, panel);
            layout.putConstraint(SpringLayout.NORTH, addShowtimeButton, 20, SpringLayout.NORTH, dateTimeLabel);

            layout.putConstraint(SpringLayout.WEST, viewShowtimeButton, 10, SpringLayout.EAST, addShowtimeButton);
            layout.putConstraint(SpringLayout.NORTH, viewShowtimeButton, 20, SpringLayout.NORTH, dateTimeLabel);

            layout.putConstraint(SpringLayout.WEST, deleteShowtimeButton, 10, SpringLayout.EAST, viewShowtimeButton);
            layout.putConstraint(SpringLayout.NORTH, deleteShowtimeButton, 20, SpringLayout.NORTH, dateTimeLabel);

            layout.putConstraint(SpringLayout.EAST, addEventButton, 0, SpringLayout.EAST, jScrollShowTimeTable);
            layout.putConstraint(SpringLayout.NORTH, addEventButton, 20, SpringLayout.NORTH, dateTimeLabel);

            layout.putConstraint(SpringLayout.WEST, showtimeLabel, 10, SpringLayout.WEST, panel);
            layout.putConstraint(SpringLayout.NORTH, showtimeLabel, 40, SpringLayout.NORTH, addShowtimeButton);

            layout.putConstraint(SpringLayout.WEST, jScrollShowTimeTable, 10, SpringLayout.WEST, panel);
            layout.putConstraint(SpringLayout.NORTH, jScrollShowTimeTable, 10, SpringLayout.SOUTH, showtimeLabel);

            layout.putConstraint(SpringLayout.WEST, listTicketButton, 10, SpringLayout.WEST, panel);
            layout.putConstraint(SpringLayout.NORTH, listTicketButton, 20, SpringLayout.SOUTH, jScrollShowTimeTable);

            // Show list showtime
            addShowtimeButton.setEnabled(false);
            viewShowtimeButton.setEnabled(false);
            deleteShowtimeButton.setEnabled(false);
            listTicketButton.setEnabled(false);
            showTimeTable.setDefaultEditor(Object.class, null);

            selectShowtimeTableListener();
            viewShowtimeButtonListener();
            addEventButtonListener();
            deleteShowtimeButtonListener(new ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    Event event = FileManager.loadEvent();
                    event.getShowTimes().remove(selectedRow);
                    FileManager.saveEvent(event);
                    ResetGUI();
                }
            });
            listTicketButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    setEnablePage(false);
                    ListTicketGUI listTicketGUI = new ListTicketGUI();
                    listTicketGUI.setVisible(true);
                    listTicketGUI.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosed(java.awt.event.WindowEvent e) {
                            setEnablePage(true);
                        }
                    });
                }
            });

            // Hiển thị frame
            add(panel);
            setSize(600, 500);
            setResizable(false);
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    public void ResetGUI() {
        Event event = FileManager.loadEvent();
        dateTimeLabel.setText("Ngày: " + event.getDay() + "/" + event.getMonth() + "/" + event.getYear());
        showListShowtime(event);
        addShowtimeButton.setEnabled(true);
        viewShowtimeButton.setEnabled(false);
        deleteShowtimeButton.setEnabled(false);
        listTicketButton.setEnabled(true);
    }

    public void showListShowtime(Event event) {
        if (event == null) {
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

    public void setEnabledViewShowtimeButton(boolean enabled) {
        viewShowtimeButton.setEnabled(enabled);
        deleteShowtimeButton.setEnabled(enabled);
    }

    public void setEnablePage(boolean b) {
        this.setEnabled(b);
    }

    // Button Listener
    public void addShowtimeButtonListener(ActionListener listener) {
        addShowtimeButton.addActionListener(listener);
    }

    public void viewShowtimeButtonListener() {
        viewShowtimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                Event event = FileManager.loadEvent();
                new ViewShowtimeGUI(event.getShowTimes().get(selectedRow));
            }
        });
    }

    public void addEventButtonListener() {
        addEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                setEnablePage(false);
                AddEventGUI addEventGUI = new AddEventGUI();
                addEventGUI.setVisible(true);
                new AddEventController(addEventGUI);
                addEventGUI.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        ResetGUI();
                        setEnablePage(true);
                    }
                });
            }
        });
    }

    public void deleteShowtimeButtonListener(ActionListener listener) {
        deleteShowtimeButton.addActionListener(listener);
    }

    public void selectShowtimeTableListener() {
        showTimeTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    selectedRow = showTimeTable.getSelectedRow();
                    if (selectedRow != -1) {
                        setEnabledViewShowtimeButton(true);
                    } else{
                        setEnabledViewShowtimeButton(false);
                    }
                }
            }
        });
    }
}
