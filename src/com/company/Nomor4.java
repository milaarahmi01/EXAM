package com.company;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;

public class Nomor4 extends JPanel
{

    private final JPanel barPanel;
    private final JPanel labelPanel;
    private final List<Bar> bars = new ArrayList<Bar>();

    public Nomor4() {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new BorderLayout());

        barPanel = new JPanel(new GridLayout(1, 0, 10, 0));
        Border outer = new MatteBorder(1, 1, 1, 1, Color.BLACK);
        Border inner = new EmptyBorder(10, 10, 0, 10);
        Border compound = new CompoundBorder(outer, inner);
        barPanel.setBorder( compound );

        labelPanel = new JPanel( new GridLayout(1, 0, 10, 0) );
        labelPanel.setBorder( new EmptyBorder(5, 10, 0, 10) );

        add(barPanel, BorderLayout.CENTER);
        add(labelPanel, BorderLayout.PAGE_END);
    }

    public void addBar(String label, int value, Color color) {
        Bar bar = new Bar(label, value, color);
        bars.add( bar );
    }

    public void layoutHistogram() {
        barPanel.removeAll();
        labelPanel.removeAll();

        int maxValue = 0;
        int totalValue = 0;

        for (Bar bar: bars) {
            totalValue += bar.getValue();
            maxValue = Math.max(maxValue, bar.getValue());
        }


        for (Bar bar: bars) {
            JLabel label = new JLabel(bar.getValue() + "(" + Math.round((double)bar.getValue()* 100/totalValue ) + "%)");
            label.setHorizontalTextPosition(JLabel.CENTER);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.TOP);
            label.setVerticalAlignment(JLabel.BOTTOM);

            int histogramHeight = 250;
            int barWidth = 50;
            int barHeight = (bar.getValue() * histogramHeight) / maxValue;
            Icon icon = new ColorIcon(bar.getColor(), barWidth, barHeight);

            label.setIcon(icon);
            barPanel.add(label);

            JLabel barLabel = new JLabel(bar.getLabel());
            barLabel.setHorizontalAlignment(JLabel.CENTER);
            labelPanel.add(barLabel);
        }
    }

    private class Bar {
        private final String label;
        private final int value;
        private final Color color;

        public Bar(String label, int value, Color color) {
            this.label = label;
            this.value = value;
            this.color = color;
        }

        public String getLabel() {
            return label;
        }

        public int getValue() {
            return value;
        }

        public Color getColor() {
            return color;
        }
    }

    private class ColorIcon implements Icon {
        private final Color color;
        private final int width;
        private final int height;

        public ColorIcon(Color color, int width, int height) {
            this.color = color;
            this.width = width;
            this.height = height;
        }

        public int getIconWidth() {
            return width;
        }

        public int getIconHeight() {
            return height;
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(color);
            g.fillRect(x, y, width - 3, height);
            g.setColor(Color.GRAY);
            g.fillRect(x + width - 3, y + 3, 3, height - 3);
        }
    }


    public static void main(String[] args) {
        Nomor4 panel = new Nomor4();
        panel.addBar("Java", 1000, Color.RED);
        panel.addBar("Oracle",550 , Color.BLUE);
        panel.addBar("SAP", 710, Color.GREEN);
        panel.addBar("ODOO", 740, Color.YELLOW);
        panel.layoutHistogram();

        JFrame frame = new JFrame("Prosentase Data User");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible( true );
    }
}