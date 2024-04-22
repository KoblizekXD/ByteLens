package lol.koblizek.bytelens.ui;

import javax.swing.*;

public abstract class Dialog extends JDialog {
    public Dialog() {
        super();
        initComponents();
    }

    public abstract void initComponents();

    public void showForm() {
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void add(SyntheticComponent component) {
        add(component.asComponent());
    }
}
