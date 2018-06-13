import javax.swing.*;
import java.awt.*;
import java.awt.Window.Type;
import java.awt.image.BufferedImage;

/**
 * Hierauf wird das Newton Fraktal als Vorschau gezeichnet
 */
public class ZeichenFlaeche extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JFrame vorschau;

    private BufferedImage leinwand;

    public ZeichenFlaeche(Dimension dimension) {
        setVisible(true);

        vorschau = new JFrame("Vorschau");
        vorschau.setSize(dimension);
        vorschau.setType(Type.UTILITY);
        vorschau.getContentPane().add(ZeichenFlaeche.this);
        vorschau.setResizable(false);
        vorschau.setVisible(true);
        vorschau.setLocationRelativeTo(null);

        leinwand = new BufferedImage((int) dimension.getWidth(),
                (int) dimension.getHeight(), BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).drawImage(leinwand, null, null);
    }

    public void zeichneNewtonFraktal(BufferedImage bufferedImage) {
        leinwand = bufferedImage;

        Dimension neueDimension = new Dimension(bufferedImage.getWidth(),
                bufferedImage.getHeight());
        Dimension aktuelleDimension = getPreferredSize();

        setPreferredSize(neueDimension);
        if (!neueDimension.equals(aktuelleDimension)) {
            vorschau.pack();
        }
        vorschau.setVisible(true);
        repaint();
    }
}