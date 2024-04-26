package lol.koblizek.bytelens.ui.windows;

import lol.koblizek.bytelens.ByteLens;
import lol.koblizek.bytelens.resource.ResourceManager;
import lol.koblizek.bytelens.ui.Form;
import lol.koblizek.bytelens.ui.TreeView;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;

public class MainMenuForm extends Form {
    @Override
    public void initComponents() {
        setTitle("ByteLens - Main Menu");
        setSize(840, 480);
        setLayout(new BorderLayout());
        r = ResourceManager.getInstance();
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(createLeft());
        splitPane.setRightComponent(createRight());
        add(splitPane, BorderLayout.CENTER);
    }

    ResourceManager r;

    private JPanel createLeft() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, panel.getHeight()));
        panel.setLayout(new BorderLayout());
        TreeView tree = new TreeView();
        Path[] projects = ByteLens.getInstance().getProjects();
        for (Path p : projects) {
            tree.addNode(new TreeView.Node(p.getFileName().toString(),
                    new TreeView.Node("sources/"),
                    new TreeView.Node("resources/"),
                    new TreeView.Node("libraries/")));
        }
        JLabel label = new JLabel(r.get("menu.main.projects"));
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.add(label, BorderLayout.NORTH);
        panel.add(tree.asComponent());
        panel.setMinimumSize(new Dimension(250, panel.getHeight()));
        return panel;
    }

    private JPanel createRight() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(createInner(), c);
        return panel;
    }

    private JPanel createInner() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("ByteLens");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Calibri", Font.BOLD, 42));
        panel.add(label);
        JLabel desc = new JLabel(ResourceManager.getInstance().get("menu.main.desc"));
        desc.setFont(new Font("Calibri", Font.PLAIN, 16));
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(desc);
        panel.add(Box.createRigidArea(new Dimension(0, 75)));
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 3, 5, 5));
        JButton b1 = new JButton(r.get("menu.main.new_proj"));
        b1.addActionListener(ac -> {
            new NewProjectDialog(this).showForm();
        });
        JButton b2 = new JButton(r.get("menu.main.open_proj"));
        JButton b3 = new JButton(r.get("menu.main.options"));
        buttons.add(b1);
        buttons.add(b2);
        buttons.add(b3);
        panel.add(buttons);
        return panel;
    }
}
