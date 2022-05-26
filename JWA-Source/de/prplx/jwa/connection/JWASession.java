package de.prplx.jwa.connection;

import de.prplx.jwa.rendering.JWAGraphics;
import de.prplx.jwa.utilities.*;
import de.prplx.jwa.JWebApplet;
import de.prplx.jwa.rendering.JWAScene;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class JWASession {

    public static final int TOKEN_LENGTH = 16;
    public static final long TIMEOUT_THRESHOLD = 10000L;

    public static class Manager {

        public final JWAVector<JWASession> sessions = new JWAVector();

        public final JWebApplet applet;

        public Manager(JWebApplet applet) {
            this.applet = applet;
            JWAUtilities.initThread(() -> {
                while(applet.isActive()) {
                    JWAThread.sleep(1000L);
                    JWAAtomic<JWASession> inactive = new JWAAtomic();
                    sessions.forEach(session -> {
                        if(System.currentTimeMillis() - session.lastUpdate > TIMEOUT_THRESHOLD)
                        inactive.set(session);
                    });
                    if(inactive.get() != null)
                    sessions.remove(inactive.get());
                }
            });
        }

        public JWASession get(String token) {
            JWAAtomic<JWASession> requested = new JWAAtomic(null);
            sessions.forEach(session -> {
                if(session.token.equals(token))
                requested.set(session);
            });
            return requested.get();
        }

        public JWASession register() {
            Random randomizer = new Random();
            String token = new String();
            for(int i = 0; i < TOKEN_LENGTH; i++)
            token += String.valueOf(randomizer.nextInt(9));
            JWASession session = new JWASession(token, applet);
            this.sessions.add(session);
            return session;
        }

    }

    public final Map<String, Object> definitions = new HashMap();

    public void def(String label, Object value) {
        this.definitions.put(label, value);
    }

    public Object def(String label) {
        return definitions.get(label);
    }

    public final String token;
    public final JWebApplet applet;

    private BufferedImage screen;
    private JWAScene scene;

    public JWASession(String token, JWebApplet applet) {
        this.token = token;
        this.applet = applet;
        this.setScene(applet.getDefaultScene());
    }

    public BufferedImage getScreen() {
        return screen;
    }

    public JWAScene getScene() {
        return scene;
    }

    public void setScene(JWAScene scene) {
        this.scene = scene;
    }

    private long lastUpdate = -1L;

    public void renderSession(int width, int height) {
        this.lastUpdate = System.currentTimeMillis();
        float resMult = applet.getResolutionMultiplier();
        width = (int) (resMult * width);
        height = (int) (resMult * height);
        this.screen = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = screen.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.scene.render(this, new JWAGraphics(g));
    }

    private JWAPointer prevPointer = new JWAPointer(0, 0, false);

    public JWAPointer getPrevPointer() {
        return prevPointer;
    }

    public void updatePointer(int x, int y, boolean state) {
        if(!prevPointer.isPressed() && state)
        this.scene.components.forEach(component -> {
            if(component.isHovered()) component.click(this);
        });
        float resMult = applet.getResolutionMultiplier();
        x = (int) (x * resMult);
        y = (int) (y * resMult);
        this.prevPointer = new JWAPointer(x, y, state);
    }

}
