package task3.view;

import javax.swing.*;
import java.awt.*;

public class RenderImplSwing implements IRenderer {
    private JFrame frame;
    private Color currentColor;
    private Font currentFont;

    public RenderImplSwing(int w, int h) {
        frame = new JFrame();
        frame.setSize(w, h);
    }

    @Override
    public IRenderer setColor(Color color) {
        currentColor = color;
        return this;
    }

    @Override
    public IRenderer setFont(Font font) {
        currentFont = font;
        return this;
    }

    @Override
    public IRenderer drawText(int x, int y, String text) {
        return this;
    }

    @Override
    public IRenderer drawLine(int x1, int y1, int x2, int y2) {
        return this;
    }

    @Override
    public IRenderer drawLine(int x1, int y1, int x2, int y2, int lineW) {
        return this;
    }

    @Override
    public IRenderer drawRect(int x, int y, int h, int w) {
        return this;
    }

    @Override
    public IRenderer drawRect(int x, int y, int h, int w, int lineW) {
        return this;
    }

    @Override
    public IRenderer drawRectFilled(int x, int y, int h, int w) {
        return this;
    }

    @Override
    public IRenderer drawOval(int x, int y, int h, int w) {
        return this;
    }

    @Override
    public IRenderer drawOval(int x, int y, int h, int w, int lineW) {
        return this;
    }

    @Override
    public IRenderer drawOvalFilled(int x, int y, int h, int w) {
        return this;
    }

    @Override
    public IRenderer drawImage(int x, int y, Image image) {
        return this;
    }
}
