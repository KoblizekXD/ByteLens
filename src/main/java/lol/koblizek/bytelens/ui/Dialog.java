package lol.koblizek.bytelens.ui;

import lol.koblizek.bytelens.util.InstanceAccessor;

import javax.swing.*;

public abstract class Dialog extends JDialog implements InstanceAccessor {
    public Dialog(JFrame parent) {
        super(parent);
        initComponents();
    }

    public abstract void initComponents();

    public void showForm() {
        setLocationRelativeTo(getParent());
        setVisible(true);
    }

    public void hideForm() {
        setVisible(false);
    }

    public void add(SyntheticComponent component) {
        add(component.asComponent());
    }

    public void messageBox(String caption, String text) {
        JOptionPane.showMessageDialog(this, text, caption, JOptionPane.INFORMATION_MESSAGE);
    }

    public void messageBox(String caption, String text, MessageBoxType type) {
        JOptionPane.showMessageDialog(this, text, caption, type.getI());
    }

    public MessageBoxReply confirmDialog(String caption, String text) {
        return MessageBoxReply.valueOf(JOptionPane.showConfirmDialog(this, text, caption, JOptionPane.YES_NO_OPTION));
    }
}
