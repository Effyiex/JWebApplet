package de.prplx.jwa.rendering;

import de.prplx.jwa.utilities.JWAPointer;
import de.prplx.jwa.utilities.JWAUtilities;
import de.prplx.jwa.connection.JWASession;

import java.awt.*;

public abstract class JWAComponent {

    protected JWABounds<String> preBounds = new JWABounds("0p", "0p", "0p", "0p");
    protected JWABounds<Integer> postBounds = new JWABounds(0, 0, 0, 0);

    public JWAComponent(String x, String y, String width, String height) {
        this.preBounds = new JWABounds(x, y, width, height);
    }

    private boolean hovered = false;

    public void setSize(String width, String height) {
        this.preBounds.setWidth(width);
        this.preBounds.setHeight(height);
    }

    public void setLocation(String x, String y) {
        this.preBounds.setX(x);
        this.preBounds.setY(y);
    }

    public boolean isHovered() {
        return hovered;
    }

    public void preRender(JWASession session) {
        this.postBounds.setX(JWAUtilities.calcScaling(preBounds.getX(), session.getScreen().getWidth()));
        this.postBounds.setY(JWAUtilities.calcScaling(preBounds.getY(), session.getScreen().getHeight()));
        this.postBounds.setWidth(JWAUtilities.calcScaling(preBounds.getWidth(), session.getScreen().getWidth()));
        this.postBounds.setHeight(JWAUtilities.calcScaling(preBounds.getHeight(), session.getScreen().getHeight()));
        JWAPointer pointer = session.getPrevPointer();
        this.hovered = (
                pointer.getX() > postBounds.getX()
                && pointer.getY() > postBounds.getY()
                && pointer.getX() < postBounds.getX() + postBounds.getWidth()
                && pointer.getY() < postBounds.getY() + postBounds.getHeight()
        );
    }

    public abstract void render(JWASession session, JWAGraphics g);
    public abstract void click(JWASession session);

}
