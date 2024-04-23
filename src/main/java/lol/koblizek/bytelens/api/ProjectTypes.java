package lol.koblizek.bytelens.api;

import lol.koblizek.bytelens.ui.FieldComponent;

import javax.swing.*;
import java.awt.*;

public enum ProjectTypes implements ProjectType {
    NEW_PROJECT {
        @Override
        public JPanel populate(JPanel panel) {
            panel.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;
            createField(panel, "Project Name:", c);
            c.gridy = 1;
            createField(panel, "Project Location:", c);
            return panel;
        }
    };

    @Override
    public String toString() {
        return capitalize(super.toString().replace('_', ' ')
                .toLowerCase());
    }

    void createField(JPanel panel, String title, GridBagConstraints c) {
        c.gridx = 0;
        panel.add(new JLabel(title), c);
        c.gridx = 1;
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(400, 25));
        panel.add(field, c);
    }

    private String capitalize(String str) {
        StringBuilder sb = new StringBuilder();
        for (String s : str.split(" ")) {
            sb.append(s.substring(0, 1).toUpperCase())
                    .append(s.substring(1))
                    .append(" ");
        }
        return sb.toString();
    }
}
