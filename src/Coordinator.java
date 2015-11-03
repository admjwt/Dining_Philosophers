class Coordinator {
    public enum State { paused, running, reset }
    private State state = State.paused;

    public synchronized boolean ispaused() {
        return (state == State.paused);
    }

    public synchronized void paused() {
        state = State.paused;
    }

    public synchronized boolean isReset() {
        return (state == State.reset);
    }

    public synchronized void reset() {
        state = State.reset;
    }

    public synchronized void resume() {
        state = State.running;
        notifyAll();
    }

    public synchronized boolean gate() throws ResetException {
        if (state == State.paused || state == State.reset) {
            try {
                wait();
            } catch(InterruptedException e) {
                if (isReset()) {
                    throw new ResetException();
                }
            }
            return true;
        }
        return false;
    }
}
