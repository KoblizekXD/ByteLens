package lol.koblizek.bytelens.api;

import javax.swing.*;

public interface ProjectType {
    default JPanel populate(JPanel panel) {
        return panel;
    }
}
