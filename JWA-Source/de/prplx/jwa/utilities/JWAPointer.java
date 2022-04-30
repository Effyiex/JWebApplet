package de.prplx.jwa.utilities;

public class JWAPointer {

    private boolean pressed = false;

    public boolean isPressed() {
        return pressed;
    }

    private int x, y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public JWAPointer(int x, int y, boolean pressed) {
        this.x = x;
        this.y = y;
        this.pressed = pressed;
    }

}
