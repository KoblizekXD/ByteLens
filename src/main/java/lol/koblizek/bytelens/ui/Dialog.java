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

    public enum MessageBoxReply {
        OK(0),
        CANCEL(2),
        YES(0),
        NO(1);

        private final int i;

        MessageBoxReply(int i) {
            this.i = i;
        }

        public int getI() {
            return i;
        }

        public static MessageBoxReply valueOf(int i) {
            for (MessageBoxReply value : values()) {
                if (value.getI() == i) {
                    return value;
                }
            }
            return null;
        }
    }

    public MessageBoxReply confirmDialog(String caption, String text) {
        return MessageBoxReply.valueOf(JOptionPane.showConfirmDialog(this, text, caption, JOptionPane.YES_NO_OPTION));
    }
}
