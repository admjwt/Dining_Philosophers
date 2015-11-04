import java.awt.*;
import java.util.Random;

class Philosopher extends Thread {
    private static final Color thinkColor = Color.red;
    private static final Color waitColor = Color.blue;
    private static final Color eatColor = Color.green;
    private static final double thinkTime = 4.0;
    private static final double waitTime = 2.0; // time between becoming hungry and grabbing first fork
    private static final double eatTime = 3.0;

    private Coordinator c;
    private Table t;
    private static final int xsize = 50;
    private static final int ysize = 50;
    private int x;
    private int y;
    private Fork LFork;
    private Fork RFork;
    private Random rand;
    private Color color;

    public Philosopher(Table T, int cx, int cy,Fork lf, Fork rf, Coordinator C) {
        t = T;
        x = cx;
        y = cy;
        LFork = lf;
        RFork = rf;
        c = C;
        rand = new Random();
        color = thinkColor;
    }

    public void run() {
        for (;;) {
            try {
                if (c.gate()) delay(eatTime/2.0);
                think();
                if (c.gate()) delay(thinkTime/2.0);
                hunger();
                if (c.gate()) delay(waitTime/2.0);
                eat();
            } catch(ResetException e) {
                color = thinkColor;
                t.repaint();
            }
        }
    }


    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x-xsize/2, y-ysize/2, xsize, ysize);
    }


    private static final double delay = 0.2;
    private void delay(double secs) throws ResetException {
        double ms = 1000 * secs;
        int window = (int) (2.0 * ms * delay);
        int add_in = rand.nextInt() % window;
        int original_duration = (int) ((1.0-delay) * ms + add_in);
        int duration = original_duration;
        for (;;) {
            try {
                Thread.sleep(duration);
                return;
            } catch(InterruptedException e) {
                if (c.isReset()) {
                    throw new ResetException();
                } else {       
                    c.gate();   
                    duration = original_duration / 2;
                   
                }
            }
        }
    }

    private void think() throws ResetException {
        color = thinkColor;
        t.repaint();
        delay(thinkTime);
    }

    private void hunger() throws ResetException {
        color = waitColor;
        t.repaint();
        delay(waitTime);
        LFork.acquire(x, y);
        yield();
        RFork.acquire(x, y);
    }

    private void eat() throws ResetException {
        color = eatColor;
        t.repaint();
        delay(eatTime);
        LFork.release();
        yield();
        RFork.release();
    }
}
