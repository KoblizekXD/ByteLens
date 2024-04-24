package lol.koblizek.bytelens.api.events;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;

public class ProjectCreationEvent implements Event {

    private final JPanel parent;

    public ProjectCreationEvent(JPanel parent) {
        this.parent = parent;
    }

    public JPanel getWindowParent() {
        return parent;
    }

    public Component[] getComponents() {
        return parent.getComponents();
    }

    public Component getById(String id) {
        for (Component component : parent.getComponents()) {
            if (component.getName().equals(id)) {
                return component;
            }
        }
        return null;
    }

    public String getName() {
        return ((JTextField)getById("name")).getText();
    }

    public Path getLocation() {
        return Path.of(((JTextField) getById("location")).getText());
    }
}
