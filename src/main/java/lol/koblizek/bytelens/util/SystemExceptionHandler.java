package lol.koblizek.bytelens.util;

import lol.koblizek.bytelens.ByteLens;

import javax.swing.*;

public class SystemExceptionHandler implements Thread.UncaughtExceptionHandler, InstanceAccessor {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        try {
            JOptionPane.showMessageDialog(ByteLens.getInstance().currentMainForm, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            logger().error("Uncaught exception in thread {}:", t.getName());
            logger().error("", e);
        } catch (Exception ex) {
            e.printStackTrace();
        }
    }
}
