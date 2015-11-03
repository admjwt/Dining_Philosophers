import java.awt.*;

class Fork {
    private Table t;
    private static final int xsize = 10;
    private static final int ysize = 10;
    private int xOrigin;
    private int yOrigin;
    private int x;
    private int y;

    public Fork(Table T, int cx, int cy) {
        t = T;
        xOrigin = cx;
        yOrigin = cy;
        x = cx;
        y = cy;
    }

    public void reset() {
        clear();
        x = xOrigin;
        y = yOrigin;
        t.repaint();
    }

    public void acquire(int px, int py) {
        clear();
        x = (xOrigin + px)/2;
        y = (yOrigin + py)/2;
        t.repaint();
    }

    public void release() {
        reset();
    }

    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.fillOval(x-xsize/2, y-ysize/2, xsize, ysize);
    }

    private void clear() {
        Graphics g = t.getGraphics();
        g.setColor(t.getBackground());
        g.fillOval(x-xsize/2, y-ysize/2, xsize, ysize);
    }
}
