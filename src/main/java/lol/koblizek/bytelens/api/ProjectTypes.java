package lol.koblizek.bytelens.api;

import lol.koblizek.bytelens.ByteLens;
import lol.koblizek.bytelens.api.events.ProjectCreationEvent;

import javax.swing.*;
import java.awt.*;

public enum ProjectTypes implements ProjectType {
    NEW_PROJECT {
        @Override
        public JPanel populate(JPanel panel) {
            panel.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridy = 0;
            createTextField(panel, "Project Name:", c, "name");
            c.gridy++;
            createLocationField(panel, "Project Location:", c, "location");
            return panel;
        }

        @Override
        public Project onProjectCreated(ProjectCreationEvent e) {
            Project project = super.onProjectCreated(e);
            if (!project.getInfo().projectDir()
                    .toFile().mkdirs())
                ByteLens.getInstance().getLogger()
                        .error("Failed to create project directory: {}", project.getInfo().projectDir());

            return project;
        }
    };

    @Override
    public String toString() {
        return capitalize(super.toString().replace('_', ' ')
                .toLowerCase());
    }

    void createTextField(JPanel panel, String title, GridBagConstraints c, String id) {
        c.gridx = 0;
        panel.add(new JLabel(title), c);
        c.gridx = 1;
        JTextField field = new JTextField();
        field.setName(id);
        field.setPreferredSize(new Dimension(400, 25));
        panel.add(field, c);
    }

    void createLocationField(JPanel panel, String title, GridBagConstraints c, String location) {
        c.gridx = 0;
        panel.add(new JLabel(title), c);
        c.gridx = 1;
        JTextField field = new JTextField();
        field.setName(location);
        field.setPreferredSize(new Dimension(400, 25));
        panel.add(field, c);
        JButton browse = new JButton("...");
        browse.setPreferredSize(new Dimension(25, 25));
        browse.addActionListener(ac -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                field.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        });
        c.gridx = 2;
        panel.add(browse, c);
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
