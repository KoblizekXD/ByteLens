package lol.koblizek.bytelens.ui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class FieldComponent extends JPanel {
    public FieldComponent(String title, int textWidth) {
        JLabel label = new JLabel(title);
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(textWidth, 25));
        add(label);
        add(field);
        setBorder(new LineBorder(Color.BLACK));
        setLayout(new FlowLayout(FlowLayout.LEFT));
    }
}
