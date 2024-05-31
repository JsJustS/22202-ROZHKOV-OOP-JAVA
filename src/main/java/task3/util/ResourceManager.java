package task3.util;

import task3.view.menu.MainMenu;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ResourceManager {
    public static BufferedImage getMissingTexture() {
        BufferedImage texture = new BufferedImage(2, 2, BufferedImage.TYPE_INT_BGR);
        texture.setRGB(0, 0, Color.MAGENTA.getRGB());
        texture.setRGB(1, 1, Color.MAGENTA.getRGB());
        texture.setRGB(0, 1, Color.BLACK.getRGB());
        texture.setRGB(1, 0, Color.BLACK.getRGB());
        return texture;
    }

    public static BufferedImage loadImage(String filename) {
        BufferedImage loadedImage;
        try (InputStream stream = MainMenu.class.getResourceAsStream("/" + filename)) {
            if (stream == null) throw new IOException();
            loadedImage = ImageIO.read(stream);
        } catch (IOException ignored) {
            loadedImage = ResourceManager.getMissingTexture();
        }
        return loadedImage;
    }
}
