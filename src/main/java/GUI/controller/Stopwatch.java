package GUI.controller;

public class Stopwatch {
    private long startTime;

    public Stopwatch() {
        startTime = System.currentTimeMillis();
    }

    public void reset() {
        startTime = System.currentTimeMillis();
    }

    /**
     * @return Time elapsed in milliseconds
     */
    public long getTimeElapsed() {
        return (System.currentTimeMillis() - startTime);
    }
}
