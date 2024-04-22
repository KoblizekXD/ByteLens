package lol.koblizek.bytelens.api;

import javax.swing.*;

public enum ProjectTypes implements ProjectType {
    NEW_PROJECT {
        @Override
        public JPanel populate(JPanel panel) {
            panel.add(new JLabel("Kill Project"));
            return panel;
        }
    };

    @Override
    public String toString() {
        return super.toString().replace('_', ' ')
                .toLowerCase();
    }
}
