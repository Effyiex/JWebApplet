package de.prplx.jwa.rendering;

import java.awt.*;

public class JWAGraphics {

    private final Graphics2D awt;

    public JWAGraphics(Graphics2D awt) {
        this.awt = awt;
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
        if(outline != null && color != null) {
            this.awt.setColor(outline.parse());
            int prevFontSize = fontSize;
            this.fontSize(fontSize + outlineSize * 2);
            this.awt.drawString(text, x - outlineSize, y - outlineSize);
            this.fontSize(prevFontSize);
        }
        if(color != null) {
            this.awt.setColor(color.parse());
            this.awt.drawString(text, x, y);
        }
    }

    public void ellipse(int x, int y, int width, int height) {
        if(outline != null) {
            this.awt.setColor(outline.parse());
            this.awt.drawOval(x - outlineSize, y - outlineSize, width + outlineSize * 2, height + outlineSize * 2);
        }
        if(color != null) {
            this.awt.setColor(color.parse());
            this.awt.fillOval(x, y, width, height);
        }
    }

}
