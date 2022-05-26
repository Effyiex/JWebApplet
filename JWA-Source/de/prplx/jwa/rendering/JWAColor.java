package de.prplx.jwa.rendering;

import java.awt.*;

public final class JWAColor {

    private float r, g, b, a;

    public JWAColor(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public JWAColor(float r, float g, float b) {
        this(r, g, b, 1);
    }



    public Color parse() {
        return new Color(r, g, b, a);
    }



}
