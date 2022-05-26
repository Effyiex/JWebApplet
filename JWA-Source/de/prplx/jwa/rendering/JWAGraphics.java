package de.prplx.jwa.rendering;

import de.prplx.jwa.utilities.JWALogger;
import de.prplx.jwa.utilities.JWAVector;

import java.awt.*;
import java.lang.reflect.Method;

public class JWAGraphics {

    private final Graphics2D awt;

    public JWAGraphics(Graphics2D awt) {
        this.awt = awt;
    }

    public void smooth() {
        this.awt.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    private JWAColor color, outline;
    private int outlineSize = 1;

    public void color(JWAColor color) {
        this.color = color;
    }

    public void outline(JWAColor color) {
        this.outline = color;
    }

    public void outline(int size) {
        this.outlineSize = size;
    }

    public void rectangle(int x, int y, int width, int height) {
        x -= Math.round(width * anker.get(0));
        y -= Math.round(height * anker.get(1));
        if(outline != null) {
            this.awt.setColor(outline.parse());
            this.awt.drawRect(x - outlineSize, y - outlineSize, width + outlineSize, height + outlineSize);
        }
        if(color != null) {
            this.awt.setColor(color.parse());
            this.awt.fillRect(x, y, width, height);
        }
    }

    private String fontFamily = "Arial";
    private int fontSize = 14;
    private boolean fontBold = false;

    private void updateFont() {
        this.awt.setFont(new Font(fontFamily, fontBold ? Font.BOLD : Font.PLAIN, fontSize));
    }

    public void font(String fontFamily) {
        this.fontFamily = fontFamily;
        this.updateFont();
    }

    public void fontSize(int fontSize) {
        this.fontSize = fontSize;
        this.updateFont();
    }

    public void fontBold(boolean fontBold) {
        this.fontBold = fontBold;
        this.updateFont();
    }

    public void text(String text, int x, int y) {
        x -= Math.round(awt.getFontMetrics().stringWidth(text) * anker.get(0));
        y += Math.round(fontSize / 3.0F * 2.0F * anker.get(1));
        // TODO: Fix Outline of Text
        /*final float outlineEffect = outlineSize * 0.25F;
        if(outline != null && color != null) {
            this.push();
            this.translate(x - ((int) outlineEffect * 2), y - ((int) outlineEffect * 2));
            this.scale(1.0F + outlineEffect, 1.0F + outlineEffect);
            this.awt.setColor(outline.parse());
            this.awt.drawString(text, 0, 0);
            this.pop();
        }*/
        if(color != null) {
            this.awt.setColor(color.parse());
            this.awt.drawString(text, x, y);
        }
    }

    public void ellipse(int x, int y, int width, int height) {
        x -= Math.round(width * anker.get(0));
        y -= Math.round(height * anker.get(1));
        if(outline != null) {
            this.awt.setColor(outline.parse());
            this.awt.drawOval(x - outlineSize, y - outlineSize, width + outlineSize, height + outlineSize);
        }
        if(color != null) {
            this.awt.setColor(color.parse());
            this.awt.fillOval(x, y, width, height);
        }
    }

    public void arc(int x, int y, int width, int height, int start, int end) {
        x -= Math.round(width * anker.get(0));
        y -= Math.round(height * anker.get(1));
        if(outline != null) {
            this.awt.setColor(outline.parse());
            this.awt.drawArc(x - outlineSize, y - outlineSize, width + outlineSize, height + outlineSize, start, end);
        }
        if(color != null) {
            this.awt.setColor(color.parse());
            this.awt.fillArc(x - outlineSize, y - outlineSize, width + outlineSize, height + outlineSize, start, end);
        }
    }

    private final JWAVector<Object> layers = new JWAVector();

    private void setLayer(Object layer) {
        try {
            Method method = null;
            for(Method declared : awt.getClass().getDeclaredMethods())
                if(declared.getName().equals("setTransform")) {
                    method = declared;
                    break;
                }
            if(layer == null) layer = method.getParameterTypes()[0].getClass().getConstructor().newInstance();
            if(method != null) method.invoke(awt, layer);
            else JWALogger.ERROR.log("Cannot invoke \"setTransform\" (JWAGraphics).");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void push() {
        this.layers.add(awt.getTransform());
        this.setLayer(null);
    }

    public void pop() {
        this.setLayer(layers.remove(layers.dimension() - 1));
    }

    private final JWAVector<Float> anker = new JWAVector(0.0F, 0.0F).seal();

    public void align(float x, float y) {
        this.anker.update(x, y);
    }

    public void translate(int x, int y) {
        this.awt.translate(x, y);
    }

    public void scale(float x, float y) {
        this.awt.scale(x, y);
    }

    public void rotate(float angle) {
        this.awt.rotate(angle);
    }



}
