import javax.swing.*;
import java.awt.*;

class Table extends JPanel {
    private static final int numPhil = 5;
    private final Coordinator c;
    private Fork[] forks;
    private Philosopher[] philosophers;

    public void pause() {
        c.paused();
        for (int i = 0; i < numPhil; i++) {
            philosophers[i].interrupt();
        }
    }

    public void reset() {
        c.reset();
        for (int i = 0; i < numPhil; i++) {
            philosophers[i].interrupt();
        }
        for (int i = 0; i < numPhil; i++) {
            forks[i].reset();
        }
    }

    //Allows program to redisplay properly when being opened from a minimzed state
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < numPhil; i++) {
            forks[i].draw(g);
            philosophers[i].draw(g);
        }
        g.setColor(Color.black);
        g.drawRect(0, 0, getWidth()-1, getHeight()-1);
    }

    public Table(Coordinator C, int canvas) {    // constructor
        c = C;
        forks = new Fork[numPhil];
        philosophers = new Philosopher[numPhil];
        setPreferredSize(new Dimension(canvas, canvas));

        for (int i = 0; i < numPhil; i++) {
            double angle = Math.PI/2 + 2*Math.PI/numPhil*(i-0.5);
            forks[i] = new Fork(this,
                    (int) (canvas/2.0 + canvas/6.0 * Math.cos(angle)),
                    (int) (canvas/2.0 - canvas/6.0 * Math.sin(angle)));
        }
        for (int i = 0; i < numPhil; i++) {
            double angle = Math.PI/2 + 2*Math.PI/numPhil*i;
            philosophers[i] = new Philosopher(this,
                    (int) (canvas/2.0 + canvas/3.0 * Math.cos(angle)),
                    (int) (canvas/2.0 - canvas/3.0 * Math.sin(angle)),
                    forks[i],
                    forks[(i+1) % numPhil],
                    c);
            philosophers[i].start();
        }
    }
}