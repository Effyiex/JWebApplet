package de.prplx.jwa.rendering;

import de.prplx.jwa.connection.JWASession;
import de.prplx.jwa.utilities.JWAVector;

public class JWAScene {

    private static JWAColor defaultBackground = new JWAColor(0.125F, 0.125F, 0.13F);

    public static void setDefaultBackground(JWAColor defaultBackground) {
        JWAScene.defaultBackground = defaultBackground;
    }

    public static JWAColor getDefaultBackground() {
        return defaultBackground;
    }

    public final String label;
    public final JWAVector<JWAComponent> components;

    public JWAScene(String label, JWAComponent... components) {
        this.label = label;
        this.components = new JWAVector(components);
    }

    private JWAColor background = null;

    public void setBackground(JWAColor background) {
        this.background = background;
    }

    public JWAColor getBackground() {
        return background;
    }

    public void render(JWASession session, JWAGraphics g) {
        g.color(background != null ? background : defaultBackground);
        g.rectangle(0, 0, session.getScreen().getWidth(), session.getScreen().getHeight());
        this.components.forEach(component -> {
            component.preRender(session);
            component.render(session, g);
        });
    }

}
