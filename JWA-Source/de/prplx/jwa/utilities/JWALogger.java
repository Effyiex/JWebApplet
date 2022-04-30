package de.prplx.jwa.utilities;

import java.io.IOException;
import java.io.OutputStream;

public enum JWALogger {

    INFO(System.out),
    WARNING(System.out),
    ERROR(System.err);

    private static boolean active = true;

    public static boolean isActive() {
        return active;
    }

    public static void setState(boolean state) {
        JWALogger.active = state;
    }

    public final OutputStream stream;
    public final String prefix;

    JWALogger(OutputStream stream) {
        this.stream = stream;
        this.prefix = "[JWA-" + name() + "]: ";
    }

    public void log(String text) {
        if(!active) return;
        try {
            this.stream.write((prefix + text + '\n').getBytes());
            this.stream.flush();
        } catch (IOException e) {
            return;
        }
    }

}
