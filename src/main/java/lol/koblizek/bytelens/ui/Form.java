package lol.koblizek.bytelens.ui;

import javax.swing.*;

public abstract class Form extends JFrame {
    public Form() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
