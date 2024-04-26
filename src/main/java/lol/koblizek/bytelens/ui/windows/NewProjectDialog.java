package lol.koblizek.bytelens.ui.windows;

import lol.koblizek.bytelens.api.Project;
import lol.koblizek.bytelens.api.ProjectType;
import lol.koblizek.bytelens.api.ProjectTypes;
import lol.koblizek.bytelens.api.events.ProjectCreationEvent;
import lol.koblizek.bytelens.ui.Dialog;

import javax.swing.*;
import java.awt.*;

public class NewProjectDialog extends Dialog {

    public NewProjectDialog(JFrame parent) {
        super(parent);
    }

    @Override
    public void initComponents() {
        setTitle(translate("menu.main.projects"));
        setSize(new Dimension(840, 480));
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JList<ProjectType> list = createLeft();
        splitPane.setLeftComponent(list);
        splitPane.setRightComponent(createRight());
        splitPane.setDividerLocation(200);
        ProjectTypes type = ProjectTypes.values()[list.getSelectedIndex()];
        type.populate((JPanel) ((JPanel) splitPane.getRightComponent()).getComponent(1));
        add(splitPane, BorderLayout.CENTER);
        pack();
    }

    private JList<ProjectType> createLeft() {
        JList<ProjectType> list = new JList<>(ProjectTypes.values());
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
        JLabel label = new JLabel(translate("menu.main.new_proj.create_proj"));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Calibri", Font.BOLD, 24));
        panel.add(label, BorderLayout.NORTH);
        JPanel inner = new JPanel();
        panel.add(inner, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel();
        JButton b1 = new JButton(translate("menu.main.new_proj.create"));
        b1.addActionListener(e -> {
            JList<ProjectType> list = (JList<ProjectType>)((JSplitPane) panel.getParent()).getLeftComponent();
            Project project = list.getSelectedValue().onProjectCreated(new ProjectCreationEvent(inner));
            if (project == null) {
                logger().warn("Project creation cancelled by user");
            }
        });
        bottomPanel.add(b1);
        panel.add(bottomPanel, BorderLayout.PAGE_END);
        return panel;
    }
}
