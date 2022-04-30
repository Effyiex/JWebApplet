package de.prplx.jwa.rendering;

import de.prplx.jwa.connection.JWASession;
import de.prplx.jwa.utilities.JWAVector;

import java.awt.*;

public class JWAScene {

    private static Color defaultBackground = new Color(33, 33, 36);

    public static void setDefaultBackground(Color defaultBackground) {
        JWAScene.defaultBackground = defaultBackground;
    }

    public static Color getDefaultBackground() {
        return defaultBackground;
    }

    public final String label;
    public final JWAVector<JWAComponent> components;

    public JWAScene(String label, JWAComponent... components) {
        this.label = label;
        this.components = new JWAVector(components);
    }

    private Color background = JWAScene.defaultBackground;

    public void setBackground(Color background) {
        this.background = background;
    }

    public Color getBackground() {
        return background;
    }

    public void render(JWASession session, Graphics2D g) {
        g.setColor(background);
        g.fillRect(0, 0, session.getScreen().getWidth(), session.getScreen().getHeight());
        this.components.forEach(component -> {
            component.preRender(session);
            component.render(session, g);
        });
    }

}
