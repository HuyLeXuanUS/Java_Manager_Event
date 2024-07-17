package shared.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import shared.model.Section;

public class ViewSectionGUI extends JFrame {
    private static final int PADDING = 10;
    private static final int CELL_SIZE = 50;

    public ViewSectionGUI(Section section) {
        initComponent(section);
    }

    private void initComponent(Section section) {
        int ROWS = section.getNumberOfRows();
        int COLS = section.getNumberOfSeats();

        JPanel topTextPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel topLabel1 = new JLabel("Khu vực: " + section.getSectionName());
        JLabel topLabel2 = new JLabel("Giá vé: " + section.getPriceTicket());
        topTextPanel.add(topLabel1);
        topTextPanel.add(topLabel2);
        add(topTextPanel, BorderLayout.NORTH);

        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addLegendItem(legendPanel, new Color(0, 153, 0), "Còn trống");
        addLegendItem(legendPanel, new Color(255, 102, 102), "Đã đặt"); // Màu đỏ nhạt
        add(legendPanel, BorderLayout.SOUTH);

        JPanel matrixWrapperPanel = new JPanel(new BorderLayout());
        matrixWrapperPanel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
        add(matrixWrapperPanel, BorderLayout.CENTER);

        JPanel matrixPanel = new JPanel(new GridLayout(ROWS, COLS));
        for (int i = 1; i <= ROWS; i++) {
            for (int j = 1; j <= COLS; j++) {
                final int finalI = i;
                final int finalJ = j;
                JPanel cell = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2d = (Graphics2D) g.create();
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                        g2d.setColor(
                                section.isBooked(finalI, finalJ) ? new Color(255, 102, 102) : new Color(0, 153, 0));
                        g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
                        g2d.dispose();
                    }
                };
                cell.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                JLabel label = new JLabel(i + "." + j);
                label.setHorizontalAlignment(JLabel.CENTER);
                cell.add(label);
                matrixPanel.add(cell);
            }
        }
        matrixWrapperPanel.add(matrixPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addLegendItem(JPanel panel, Color color, String text) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JPanel colorPanel = new JPanel();
        colorPanel.setBackground(color);
        colorPanel.setPreferredSize(new Dimension(20, 20));
        itemPanel.add(colorPanel);

        JLabel label = new JLabel(text);
        itemPanel.add(label);

        panel.add(itemPanel);
    }
}
