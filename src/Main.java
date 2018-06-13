import java.awt.*;

/**
 * Beinhaltet die Methode main(), die die Applikation startet.
 */
public class Main {

    /**
     * Startet die Applikation.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ZeichenFlaeche zeichenFlaeche = new ZeichenFlaeche(
                        new Dimension(500, 500));
                Menue menue = new Menue();
                Einstellungen einstellungen = new Einstellungen();
                new Modell(menue, zeichenFlaeche, einstellungen);
                menue.setFunktionsTxt("1z3-1z0");
                Steuerung.vorschau_ButtonGedrueckt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}