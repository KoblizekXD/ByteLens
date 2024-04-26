package lol.koblizek.bytelens.api;

import lol.koblizek.bytelens.ByteLens;
import lol.koblizek.bytelens.api.events.ProjectCreationEvent;
import lol.koblizek.bytelens.util.InstanceAccessor;

import javax.swing.*;

public interface ProjectType extends InstanceAccessor {

    default JPanel populate(JPanel panel) {
        return panel;
    }

    default Project onProjectCreated(ProjectCreationEvent e) {
        ByteLens.getInstance().getLogger()
                .info("Preparing to create new project: {}", e.getName());
        if (e.getLocation().resolve(e.getName()).toFile().exists()) {
            logger().warn("Project already exists at location: {}, implementation should prevent this!",
                    e.getLocation().resolve(e.getName()));
        }
        return new Project(this, new GenericProjectInformation(e.getName(),
                e.getLocation().resolve(e.getName() + "/")));
    }
}
