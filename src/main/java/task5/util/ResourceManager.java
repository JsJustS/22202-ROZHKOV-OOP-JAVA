package task5.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private static final Map<String, BufferedImage> loadedResources = new HashMap<>();
    public static BufferedImage getMissingTexture() {
        BufferedImage texture = new BufferedImage(2, 2, BufferedImage.TYPE_INT_BGR);
        texture.setRGB(0, 0, Color.MAGENTA.getRGB());
        texture.setRGB(1, 1, Color.MAGENTA.getRGB());
        texture.setRGB(0, 1, Color.BLACK.getRGB());
        texture.setRGB(1, 0, Color.BLACK.getRGB());
        return texture;
    }

    public static BufferedImage getSprite(String filename) {
        if (loadedResources.containsKey(filename)) {
            return loadedResources.get(filename);
        }
        BufferedImage loadedImage;
        try (InputStream stream = ResourceManager.class.getResourceAsStream("/" + filename)) {
            if (stream == null) throw new IOException();
            loadedImage = ImageIO.read(stream);
            loadedResources.put(filename, loadedImage);
        } catch (IOException ignored) {
            loadedImage = ResourceManager.getMissingTexture();
        }
        return loadedImage;
    }
}
