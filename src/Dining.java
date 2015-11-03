import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.*;
import java.lang.*;
import java.lang.Thread.*;

public class Dining{
    private static final int canvas = 360;

    private void start(final RootPaneContainer pane) {
        final Coordinator c = new Coordinator();
        final Table t = new Table(c, canvas);
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    new UI(pane, c, t);
                }
            });
        } catch (Exception e) {
            System.err.println("unable to create GUI");
        }
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Dining Philosphers");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dining me = new Dining();
        me.start(f);
        f.pack();            //
        f.setVisible(true);
    }
}
