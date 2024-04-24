package lol.koblizek.bytelens.ui.forms;

import lol.koblizek.bytelens.api.ProjectTypes;
import lol.koblizek.bytelens.ui.Dialog;
import lol.koblizek.bytelens.util.IResourceful;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class NewProjectForm extends Dialog implements IResourceful {

    public NewProjectForm(JFrame parent) {
        super(parent);
    }

    @Override
    public void initComponents() {
        setTitle(getValue("menu.main.projects"));
        setSize(new Dimension(840, 480));
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JList<String> list = createLeft();
        splitPane.setLeftComponent(list);
        splitPane.setRightComponent(createRight());
        ProjectTypes type = ProjectTypes.values()[list.getSelectedIndex()];
        type.populate((JPanel) ((JPanel) splitPane.getRightComponent()).getComponent(1));
        add(splitPane, BorderLayout.CENTER);
        pack();
    }

    private JList<String> createLeft() {
        JList<String> list = new JList<>(Arrays.stream(ProjectTypes.values()).map(ProjectTypes::toString).toArray(String[]::new));
        list.setPreferredSize(new Dimension(300, 400));
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setSelectedIndex(0);

        list.addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                ProjectTypes type = ProjectTypes.values()[list.getSelectedIndex()];
                type.populate((JPanel) ((JPanel) list.getParent().getComponent(1)).getComponent(1));
            }
        });
        return list;
    }

    private JPanel createRight() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Create New Project");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Calibri", Font.BOLD, 24));
        panel.add(label, BorderLayout.NORTH);
        panel.add(new JPanel(), BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel();
        JButton b1 = new JButton("Create");
        bottomPanel.add(b1);
        panel.add(bottomPanel, BorderLayout.PAGE_END);
        return panel;
    }
}
