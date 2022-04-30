package de.prplx.jwa.rendering;

public final class JWABounds<Type> {

    private Type x, y, width, height;

    public JWABounds(Type x, Type y, Type width, Type height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setX(Type x) {
        this.x = x;
    }

    public void setY(Type y) {
        this.y = y;
    }

    public void setWidth(Type width) {
        this.width = width;
    }

    public void setHeight(Type height) {
        this.height = height;
    }

    public Type getX() {
        return x;
    }

    public Type getY() {
        return y;
    }

    public Type getWidth() {
        return width;
    }

    public Type getHeight() {
        return height;
    }

}
