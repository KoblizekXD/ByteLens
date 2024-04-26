package lol.koblizek.bytelens.ui;

import lol.koblizek.bytelens.util.InstanceAccessor;

import javax.swing.*;

public abstract class Form extends JFrame implements InstanceAccessor {
    private boolean shown = false;

    public Form() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public abstract void initComponents();

    public void showForm() {
        if (!shown) {
            initComponents();
            shown = true;
        }
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void add(SyntheticComponent component) {
        add(component.asComponent());
    }
}
