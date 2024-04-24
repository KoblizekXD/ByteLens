package lol.koblizek.bytelens.api;

import lol.koblizek.bytelens.ByteLens;
import lol.koblizek.bytelens.api.events.ProjectCreationEvent;

import javax.swing.*;

public interface ProjectType {
    default JPanel populate(JPanel panel) {
        return panel;
    }

    default Project onProjectCreated(ProjectCreationEvent e) {
        ByteLens.getInstance().getLogger()
                .info("Preparing to create new project: " + e.getName());
        return new Project(this, new GenericProjectInformation(e.getName(), e.getLocation()));
    }
}
