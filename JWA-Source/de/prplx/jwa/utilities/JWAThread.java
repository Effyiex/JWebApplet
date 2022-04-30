package de.prplx.jwa.utilities;

public class JWAThread extends Thread {

    public static final JWAVector<Thread> ACTIVE_THREADS = new JWAVector();

    public final Runnable runnable;

    public JWAThread(Runnable runnable) {
        super();
        this.runnable = runnable;
    }

    @Override
    public void run() {
        ACTIVE_THREADS.add(this);
        this.runnable.run();
        ACTIVE_THREADS.remove(this);
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            return;
        }
    }

}
