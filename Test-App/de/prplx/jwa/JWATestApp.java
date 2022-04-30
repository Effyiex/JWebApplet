package de.prplx.jwa;

import de.prplx.jwa.connection.JWASession;
import de.prplx.jwa.rendering.JWAComponent;
import de.prplx.jwa.rendering.JWAScene;
import de.prplx.jwa.utilities.JWALogger;

import java.awt.*;

public class JWATestApp {

    public static class Button extends JWAComponent {

        public final String label;

        public Button(String label, String x, String y, String width, String height) {
            super(x, y, width, height);
            this.label = label;
        }

        @Override
        public void render(JWASession session, Graphics2D g) {
            g.setColor(isHovered() ? Color.GRAY: Color.WHITE);
            g.fillRect(postBounds.getX(), postBounds.getY(), postBounds.getWidth(), postBounds.getHeight());
        }

        @Override
        public void click(JWASession session) {
            System.out.println("Pressed: \"" + label + "\".");
        }

    }

    public static void main(String[] args) throws JWebApplet.PortAlreadyBoundException {
        JWALogger.setState(false);
        JWAScene scene = new JWAScene("TestLol", new Button("TestLol", "32p", "32p", "5%", "5%"));
        scene.setBackground(Color.BLACK);
        JWebApplet applet = new JWebApplet(80);
        applet.setDefaultScene(scene);
        applet.start();
    }

}
