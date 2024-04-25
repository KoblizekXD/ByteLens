package lol.koblizek.bytelens.ui;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class TreeView implements SyntheticComponent {

    private final JTree tree;
    private final JScrollPane scrollPane;

    public TreeView(String rootName) {
        tree = new JTree(new DefaultMutableTreeNode(rootName));
        this.scrollPane = new JScrollPane();
    }

    public TreeView() {
        tree = new JTree(new DefaultMutableTreeNode("Projects"));
        tree.setRootVisible(false);
        this.scrollPane = new JScrollPane();
    }

    public record Node(String text, Node... children) {
    }

    public TreeView addNode(Node n) {
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(n.text);
        for (Node child : n.children) {
            addNode0(newNode, child);
        }
        model.insertNodeInto(newNode, root, root.getChildCount());
        model.reload();
        return this;
    }

    private void addNode0(DefaultMutableTreeNode root, Node n) {
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(n.text);
        root.add(newNode);
        for (Node child : n.children) {
            addNode0(newNode, child);
        }
    }

    private DefaultMutableTreeNode getNode(TreeNode root, String name) {
        for (int i = 0; i < root.getChildCount(); i++) {
            var node = root.getChildAt(i);
            if (node.toString().equals(name)) {
                return (DefaultMutableTreeNode) node;
            }
        }
        return null;
    }

    @Override
    public JComponent asComponent() {
        scrollPane.setViewportView(tree);
        return scrollPane;
    }
}
