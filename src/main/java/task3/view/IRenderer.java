package task3.view;

import java.awt.*;

public interface IRenderer {

    IRenderer setColor(Color color);
    IRenderer setFont(Font font);

    IRenderer drawText(int x, int y, String text);

    IRenderer drawLine(int x1, int y1, int x2, int y2);
    IRenderer drawLine(int x1, int y1, int x2, int y2, int lineW);

    IRenderer drawRect(int x, int y, int h, int w);
    IRenderer drawRect(int x, int y, int h, int w, int lineW);
    IRenderer drawRectFilled(int x, int y, int h, int w);

    IRenderer drawOval(int x, int y, int h, int w);
    IRenderer drawOval(int x, int y,  int h, int w, int lineW);
    IRenderer drawOvalFilled(int x, int y, int h, int w);

    IRenderer drawImage(int x, int y, Image image);
}
