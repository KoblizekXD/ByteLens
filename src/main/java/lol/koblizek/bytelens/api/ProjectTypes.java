package lol.koblizek.bytelens.api;

import lol.koblizek.bytelens.ByteLens;
import lol.koblizek.bytelens.api.events.ProjectCreationEvent;
import lol.koblizek.bytelens.ui.Dialog;
import lol.koblizek.bytelens.util.InstanceAccessor;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public enum ProjectTypes implements ProjectType, InstanceAccessor {
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
            c.gridx = 1;
            c.gridy++;
            JLabel label = new JLabel("Project will be created as directory: ");
            label.setName("target_loc");
            panel.add(label, c);
            return panel;
        }

        @Override
        public Project onProjectCreated(ProjectCreationEvent e) {
            Project project = super.onProjectCreated(e);
            try {
                Dialog dialog = (Dialog) SwingUtilities.getWindowAncestor(e.getWindowParent());
                if (project.getProjectDirectory().toFile().exists()
                        || instance().projectRegistered(project.getProjectFile().toPath())){
                    var res = dialog.confirmDialog("Warning", "Files are present selected location, are you sure you want to overwrite them?");
                    if (res == Dialog.MessageBoxReply.NO) {
                        return null;
                    }
                }
                if (!project.getInfo().projectDir()
                        .toFile().mkdirs())
                    instance().getLogger()
                            .error("Failed to create project directory: {}", project.getInfo().projectDir());
                Files.createFile(project.getProjectFile().toPath());
                var dir = project.getProjectDirectory();
                Files.createDirectories(dir.resolve("sources/"));
                Files.createDirectories(dir.resolve("resources/"));
                Files.createDirectories(dir.resolve("libraries/"));
                dialog.hideForm();
                instance().addProject(project.getProjectFile().toPath());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
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
        addOnChange(field, () -> {
            JLabel label = (JLabel) getComponentByName(panel, "target_loc");
            JTextField loc = (JTextField) getComponentByName(panel, "location");
            label.setText("Project will be created as directory: " +
                    Path.of(loc.getText(), field.getText(), "/"));
        });
        panel.add(field, c);
    }

    void createLocationField(JPanel panel, String title, GridBagConstraints c, String location) {
        c.gridx = 0;
        panel.add(new JLabel(title), c);
        c.gridx = 1;
        JTextField field = new JTextField();
        field.setName(location);
        field.setPreferredSize(new Dimension(400, 25));
        addOnChange(field, () -> {
            JLabel label = (JLabel) getComponentByName(panel, "target_loc");
            JTextField name = (JTextField) getComponentByName(panel, "name");
            label.setText("Project will be created as directory: " +
                    Path.of(field.getText(), name.getText(), "/"));
        });
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

    JComponent getComponentByName(Container container, String name) {
        for (Component component : container.getComponents()) {
            if (component instanceof JComponent && component.getName() != null) {
                if (component.getName().equals(name)) {
                    return (JComponent) component;
                }
            }
            if (component instanceof Container) {
                JComponent comp = getComponentByName((Container) component, name);
                if (comp != null) {
                    return comp;
                }
            }
        }
        return null;
    }

    void addOnChange(JTextComponent com, Runnable r) {
        com.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                r.run();
            }
        });
    }
}
