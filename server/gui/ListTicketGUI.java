package server.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import server.model.TicketManager;

import java.awt.*;
import java.util.List;

import shared.model.Ticket;

public class ListTicketGUI extends JFrame {
    private JLabel ticketLabel;

    private JScrollPane jScrollTicketTable;
    private JTable ticketTable;

    private String columnNames[] = { "STT", "Họ và tên", "Số điện thoại", "STT Suất", "STT Khu", "Số hàng", "Số ghế" };

    public ListTicketGUI() {
        initComponent();
    }

    public void initComponent() {
        setTitle("Danh sách vé đã đặt");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ticketLabel = new JLabel("Danh sách vé đã đặt");

        ticketTable = new JTable();
        jScrollTicketTable = new JScrollPane();

        // Table
        ticketTable.setModel(new DefaultTableModel(new Object[][] {}, columnNames));
        jScrollTicketTable = new JScrollPane(ticketTable);
        jScrollTicketTable.setViewportView(ticketTable);
        jScrollTicketTable.setPreferredSize(new Dimension(550, 250));

        // Custom Component
        ticketLabel.setFont(new Font("Arial", Font.BOLD, 20));
        ticketLabel.setForeground(Color.RED);

        ticketTable.setDefaultEditor(Object.class, null);

        // Layout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(ticketLabel)
                .addComponent(jScrollTicketTable)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(ticketLabel)
                .addComponent(jScrollTicketTable)
        );

        pack();
        setLocationRelativeTo(null);

        showListTicket();
    }

    public void showListTicket() {
        List<Ticket> tickets = TicketManager.loadTicket();
        if (tickets == null) {
            return;
        }
        int size = tickets.size();

        Object[][] data = new Object[size][7];

        for (int i = 0; i < size; i++) {
            Ticket ticket = tickets.get(i);
            data[i][0] = i + 1;
            data[i][1] = ticket.getName();
            data[i][2] = ticket.getPhoneNumber();
            data[i][3] = ticket.getShowtime();
            data[i][4] = ticket.getSection();
            data[i][5] = ticket.getRow();
            data[i][6] = ticket.getSeatNumber();
        }
        ticketTable.setModel(new DefaultTableModel(data, columnNames));
    }
}
