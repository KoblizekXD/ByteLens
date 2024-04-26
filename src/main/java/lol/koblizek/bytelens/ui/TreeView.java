package lol.koblizek.bytelens.ui;

import lol.koblizek.bytelens.ByteLens;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.BeanProperty;
import java.nio.file.Path;
import java.util.Objects;

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

    private void expandAllNodes(JTree tree, int startingIndex, int rowCount){
        for(int i = startingIndex; i < rowCount; i++){
            tree.expandRow(i);
        }

        if(tree.getRowCount() != rowCount){
            expandAllNodes(tree, rowCount, tree.getRowCount());
        }
    }

    public static class Node<T> {
        private final T text;
        private final Node<?>[] children;

        public Node(T text, Node<?>... children) {
            this.text = text;
            this.children = children;
        }

        public String text() {
            return text.toString();
        }

        public Node<?>[] children() {
            return children;
        }
    }

    public static final class PathNode extends Node<Path> {
        public PathNode(Path text, Node<?>... children) {
            super(text, children);
        }
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
        expandAllNodes(tree, 0, tree.getRowCount());
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

    @BeanProperty(preferred = true, description = "Popup to show")
    public void setComponentPopupMenu() {
        tree.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
                if (e.isPopupTrigger()) {
                    int row = tree.getRowForLocation(e.getX(), e.getY());
                    if (row == -1) {
                        return;
                    }
                    tree.setSelectionRow(row);
                    var path = model.getPathToRoot((DefaultMutableTreeNode)tree.getLastSelectedPathComponent());
                    JPopupMenu popup = new JPopupMenu();
                    JMenuItem i = new JMenuItem(path[1].toString());
                    JMenuItem delete = new JMenuItem("Delete");
                    delete.addActionListener(a -> {
                        ByteLens.getInstance().deleteProject((Path) ((DefaultMutableTreeNode)path[1]).getUserObject());
                    });
                    i.setEnabled(false);
                    popup.add(i);
                    popup.add(delete);
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public JPopupMenu getComponentPopupMenu() {
        return tree.getComponentPopupMenu();
    }
}
