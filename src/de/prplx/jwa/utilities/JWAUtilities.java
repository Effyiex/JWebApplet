package de.prplx.jwa.utilities;

import java.io.IOException;
import java.io.InputStream;

public interface JWAUtilities {

    static Thread initThread(Runnable runnable) {
        JWAThread thread = new JWAThread(runnable);
        thread.start();
        return thread;
    }

    static String read(Class<?> parent, String path) {
        byte[] buffer = new byte[0];
        try {
            InputStream stream = parent.getResourceAsStream(path);
            buffer = new byte[stream.available()];
            stream.read(buffer);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(buffer);
    }

    static int calcScaling(String func, int mapping) {
        String[] tokens = func.split(", ");
        int value = 0;
        for(int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            char unit = token.charAt(token.length() - 1);
            float num = Float.parseFloat(token.substring(0, token.length() - 1));
            switch (unit) {

                case '%':
                    value += (int) ((num * 0.01) * mapping);
                    break;

                case 'p':
                    value += num;
                    break;

                default:
                    continue;

            }
        }
        return value;
    }

}
