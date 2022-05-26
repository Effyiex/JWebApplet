package de.prplx.jwa;

import de.prplx.jwa.connection.JWASession;
import de.prplx.jwa.rendering.JWAColor;
import de.prplx.jwa.rendering.JWAComponent;
import de.prplx.jwa.rendering.JWAGraphics;
import de.prplx.jwa.rendering.JWAScene;
import de.prplx.jwa.utilities.JWALogger;

public class JWATestApp {

    public static class Button extends JWAComponent {

        public final String label;

        public Button(String label, String x, String y, String width, String height) {
            super(x, y, width, height);
            this.label = label;
        }

        @Override
        public void render(JWASession session, JWAGraphics g) {
            g.outline(new JWAColor(1, 0.125F, 0.75F));
            g.color(isHovered() ? new JWAColor(0.5F, 0.5F, 0.5F): new JWAColor(1, 1, 1));
            g.rectangle(postBounds.getX(), postBounds.getY(), postBounds.getWidth(), postBounds.getHeight());
            g.outline(new JWAColor(0, 0, 0));
            g.color(new JWAColor(1.0F, 0.25F, 0.75F));
            g.align(0.5F, 0.5F);
            g.text(label, postBounds.getX() + postBounds.getWidth() / 2, postBounds.getY() + postBounds.getHeight() / 2);
        }

        @Override
        public void click(JWASession session) {
            System.out.println("Pressed: \"" + label + "\".");
        }

    }

    public static void main(String[] args) throws JWebApplet.PortAlreadyBoundException {
        JWALogger.setState(false);
        JWAScene scene = new JWAScene("TestLol", new Button("TestLol", "32p", "32p", "5%", "5%"));
        scene.setSmooth(false);
        scene.setBackground(new JWAColor(0, 0, 0));
        JWebApplet applet = new JWebApplet(80);
        applet.setResolutionMultiplier(0.75F);
        applet.setFramerate(30);
        applet.setDefaultScene(scene);
        applet.start();
    }

}
