package server.gui;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class AddSectionGUI extends JFrame {
    private JLabel sectionLabel;
    private JLabel sectionNameLabel;
    private JLabel priceTicketLabel;
    private JLabel numRowsLabel;
    private JLabel numSeatsLabel;

    private JTextField sectionNameField;
    private JTextField priceTicketField;
    private JTextField numRowsField;
    private JTextField numSeatsField;

    private JButton addSectionButton;

    public AddSectionGUI() {
        initComponent();
    }

    private void initComponent() {
        setTitle("Thêm khu vực");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        sectionLabel = new JLabel("Thêm khu vực");
        sectionNameLabel = new JLabel("Tên khu vực:");
        priceTicketLabel = new JLabel("Giá vé:");
        numRowsLabel = new JLabel("Số hàng:");
        numSeatsLabel = new JLabel("Số ghế:");

        sectionNameField = new JTextField(20);
        priceTicketField = new JTextField(20);
        numRowsField = new JTextField(5);
        numSeatsField = new JTextField(5);

        addSectionButton = new JButton("Thêm khu vực");

        // Custom Component
        sectionLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
        sectionLabel.setForeground(java.awt.Color.RED);

        // // Create Spring Layout
        SpringLayout layout = new SpringLayout();
        JPanel panel = new JPanel();
        panel.setLayout(layout);
        panel.setSize(400, 350);

        // Add component to panel
        panel.add(sectionLabel);
        panel.add(sectionNameLabel);
        panel.add(priceTicketLabel);
        panel.add(numRowsLabel);
        panel.add(numSeatsLabel);

        panel.add(sectionNameField);
        panel.add(priceTicketField);
        panel.add(numRowsField);
        panel.add(numSeatsField);

        panel.add(addSectionButton);

        // Set position
        layout.putConstraint(SpringLayout.WEST, sectionLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, sectionLabel, 10, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, sectionNameLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, sectionNameLabel, 50, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, sectionNameField, 100, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, sectionNameField, 50, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, priceTicketLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, priceTicketLabel, 100, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, priceTicketField, 100, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, priceTicketField, 100, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, numRowsLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, numRowsLabel, 150, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, numRowsField, 100, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, numRowsField, 150, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, numSeatsLabel, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, numSeatsLabel, 200, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, numSeatsField, 100, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, numSeatsField, 200, SpringLayout.NORTH, panel);

        layout.putConstraint(SpringLayout.WEST, addSectionButton, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, addSectionButton, 250, SpringLayout.NORTH, panel);

        // Add panel to frame
        add(panel);
        setSize(400, 350);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Get data from text field
    public String getSectionName() {
        return sectionNameField.getText();
    }

    public String getPriceTicket() {
        return priceTicketField.getText();
    }

    public String getNumRows() {
        return numRowsField.getText();
    }

    public String getNumSeats() {
        return numSeatsField.getText();
    }

    public void addSectionButtonListener(ActionListener listener) {
        addSectionButton.addActionListener(listener);
    }

    public static void main(String[] args) {
        new AddSectionGUI();
    }
}
