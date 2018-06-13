import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * Laedt Bilder/Symbole fuer die Graphische Oberflaeche
 */
public class BildLader {

    /**
     * laedt das Bild
     */
    public static ImageIcon ladeBild(String path) {
        Class<?> klasse = BildLader.class;
        URL url = klasse.getResource(path);
        BufferedImage bild;
        try {
            bild = ImageIO.read(url);
        } catch (Exception e) {
            System.err.println("Bild " + path + " nicht gefunden!");
            return null;
        }
        return new ImageIcon(bild);
    }
}