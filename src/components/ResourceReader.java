package components;

import game.WandererApp;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ResourceReader {
    public static InputStream getResourceStream(String resource) {
        if (!resource.startsWith("/")) {
            resource = "/" + resource;
        }

        return WandererApp.class.getResourceAsStream(resource);
    }

    public static BufferedImage readImage(String path) {
        try (InputStream stream = getResourceStream(path)) {
            return ImageIO.read(stream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> readArena(String path) {
        List<String> lines = new ArrayList<>();

        try (InputStreamReader reader = new InputStreamReader(ResourceReader.getResourceStream(path));
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            while (bufferedReader.ready()) {
                lines.add(bufferedReader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
