import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Modell {
    private static Menue menue;
    private static ZeichenFlaeche zeichenFlaeche;
    private static Einstellungen einstellungen;

    /**
     * Wird fuer die Erstellung von Zufallszahlen benoetigt.
     */
    private final static Random zufallswerte = new Random();

    private static double minXKoordinate;
    private static double minYKoordinate;
    private static int xAufloesung;
    private static int yAufloesung;
    private static double vorschauSkalierung;
    private static double bildSkalierung;
    private static double koordSysHoehe;
    private static double koordSysBreite;
    private static boolean weicheKanten;
    private static int pixelBreite;
    private static int pixelHoehe;

    /**
     * Wie oft das Newton Verfahren maximal pro Pixel angewendet werden
     * soll.
     */
    private static int maxIteration;

    private static KomplexeZahl[] polynom;

    /**
     * Die Farben der Nullstellen.
     */
    private static ArrayList<Color> nullstellenFarbe;

    /**
     * Die Y Werte wenn x = 0.
     */
    private static ArrayList<KomplexeZahl> nullstellen;

    /**
     * Instantiates a new model.
     *
     * @param menue          the menue
     * @param zeichenFlaeche the zeichen flaeche
     * @param einstellungen  the einstellungen
     */
    public Modell(Menue menue, ZeichenFlaeche zeichenFlaeche,
                  Einstellungen einstellungen) {
        Modell.menue = menue;
        Modell.zeichenFlaeche = zeichenFlaeche;
        Modell.einstellungen = einstellungen;

        nullstellen = new ArrayList<>();
        nullstellenFarbe = new ArrayList<>();
        setzeNullstellenfarben("Farbpalette5");

        polynom = new KomplexeZahl[1];
        polynom[0] = new KomplexeZahl(1, 0);

        einstellungenAnwenden();
        aktualisiereVorschauBild();
    }

    /**
     * Ausgeben einer ganzen Zufallszahl in einer gewaehlten Menge.
     *
     * @param von kleinster Wert der die ausgegebene Zahl haben kann
     * @param bis groesster Wert der die ausgegebene Zahl haben kann
     * @return ganze zufaellig gewaehlte Zahl
     */
    private static int zufallszahl(int von, int bis) {
        return zufallswerte.nextInt(bis - von + 1) + von;
    }

    public static void oeffneEinstellungen() {
        einstellungen.setVisible(true);
    }

    public static void farbenComboBoxGedrueckt() {
        setzeNullstellenfarben(einstellungen.getFarbenComboBoxItem());
        farbPaletteMischen();
        aktualisiereVorschauBild();
    }

    public static void funktionstextGeandert(String funktionstext) {
        menue.setFunktionstextRandFarbe(
                ueberpruefeFunktionstext(funktionstext));
    }

    private static boolean ueberpruefeFunktionstext(String funktionTxt) {
        funktionTxt = funktionTxt.replaceAll("-", "+-");

        if (funktionTxt.length() == 0) {
            return false;
        }

        if (funktionTxt.charAt(0) == '+') {
            funktionTxt = funktionTxt.substring(1);
        }

        if (funktionTxt.contains("++")) {
            return false;
        }

        String[] polynomeString = funktionTxt.split("\\+");
        for (String polynomString : polynomeString) {
            if (polynomString.contains("z")) {
                String[] zahlString = polynomString.split("z");
                if (zahlString.length == 0) {
                    return false;
                }
                if (!zahlString[0].equals("-")
                        && !zahlString[0].equals("")) {
                    try {
                        Double.parseDouble(zahlString[0]);
                    } catch (Exception e1) {
                        return false;
                    }
                }
                if (zahlString.length >= 2) {
                    try {
                        Integer.parseInt(zahlString[1]);
                    } catch (Exception e1) {
                        return false;
                    }
                }
            } else {
                try {
                    Double.parseDouble(polynomString);
                } catch (Exception e1) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void setzeMenueFunktionsTxtZufaellig() {
        StringBuilder funktionstext = new StringBuilder();
        ArrayList<Integer> exponenten = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6));
        Collections.shuffle(exponenten);

        ArrayList<Integer> gewaehlteExponente = new ArrayList<>();
        for (int i = 0; i < zufallszahl(2, exponenten.size()); i++) {
            gewaehlteExponente.add(exponenten.get(i));
        }

        Collections.sort(gewaehlteExponente);

        //filtert langweilig/schlecht aussehende Fraktale aus
        boolean istSchlechtesPolynom = (gewaehlteExponente.get(0) != 0
                && gewaehlteExponente.get(0) != 1)
                || (gewaehlteExponente.get(0) == 0
                && gewaehlteExponente.size() == 2)
                || (gewaehlteExponente
                .get(gewaehlteExponente.size() - 1) < 3);
        if (istSchlechtesPolynom) {
            setzeMenueFunktionsTxtZufaellig();
            return;
        }

        Collections.reverse(gewaehlteExponente);

        for (int i = 0; i < gewaehlteExponente.size(); i++) {
            double zufallsfaktor = 0;
            while (zufallsfaktor == 0) {
                zufallsfaktor = zufallszahl(-100, 100) / 10.0;
            }

            int zufallsexponent = gewaehlteExponente.get(i);
            if (zufallsfaktor >= 0 && i != 0) {

                funktionstext.append("+");
            }
            funktionstext.append(zufallsfaktor).append("z").append(zufallsexponent);
        }
        menue.setFunktionsTxt(funktionstext.toString());
    }

    public static void setzePolynome() {
        String funktionTxt = menue.getFunktionsTxt();
        if (!ueberpruefeFunktionstext(funktionTxt)) {
            JOptionPane.showMessageDialog(null,
                    funktionTxt + " konnte nicht interpretiert werden!\n"
                            + "Die Eingabe muss aus Polynomen einer Polynomfunktion"
                            + " bestehen, die wiederum in Faktor a, Variable z,"
                            + " und Exponenten e unterteilt werden.\nDiese drei"
                            + " Komponenten werden im Eingabefeld ohne"
                            + " Rechenoperatoren dazwischen hintereinander angegeben."
                            + "\nBeispielsweise waere eine korrekte Eingabe"
                            + " fuer -3,8z^2+2,9z-9,3: -3.8z2+2.9z-9.3.\n",
                    "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        polynom = new KomplexeZahl[7];
        for (int i = 0; i < polynom.length; i++) {
            polynom[i] = new KomplexeZahl(0, 0);
        }

        funktionTxt = funktionTxt.replaceAll(" ", "");
        funktionTxt = funktionTxt.replaceAll("-", "+-");

        if (funktionTxt.charAt(0) == '+') {
            funktionTxt = funktionTxt.substring(1);
        }
        String[] polynomString = funktionTxt.split("\\+");


        for (String monomString : polynomString) {
            double faktor;
            int exponent;
            if (monomString.contains("z")) {
                String[] zahlString = monomString.split("z");
                if (zahlString[0].equals("-")) {
                    faktor = -1;
                } else {
                    if (Objects.equals(zahlString[0], "")) {
                        faktor = 1;
                    } else {
                        try {
                            faktor = Double.parseDouble(zahlString[0]);
                        } catch (Exception e1) {
                            faktor = 1;
                        }
                    }
                }

                if (zahlString.length < 2) {
                    exponent = 1;
                } else {
                    try {
                        exponent = Integer.parseInt(zahlString[1]);
                    } catch (Exception e1) {
                        exponent = 1;
                    }
                }
            } else {
                try {
                    faktor = Double.parseDouble(monomString);
                    exponent = 0;
                } catch (Exception e1) {
                    continue;
                }
            }
            polynom[exponent] = polynom[exponent].addition(new KomplexeZahl
                    (faktor, 0));
        }


        KomplexeZahl nullWert = new KomplexeZahl(0, 0);
        int neueGroesse = polynom.length;
        for (int i = polynom.length - 1; i >= 0; i--) {
            if (polynom[i].equals(nullWert)) {
                neueGroesse--;
            } else {
                break;
            }
        }
        polynom = Arrays.copyOfRange(polynom, 0, neueGroesse);
        nullstellen.clear();
    }

    public static void einstellungenAnwenden() {
        //Versucht Attribute mithilfe von Textboxen zu setzen
        double xMittlereKoordinate = einstellungen.getXKoordinate();
        double yMittlereKoordinate = einstellungen.getYKoordinate();

        xAufloesung = einstellungen.getXAufloesung();
        yAufloesung = einstellungen.getYAufloesung();

        //Die Entfernung von der Mitte zum Rand (Da das Bild 1:2 Format hat
        //ist der Abstand vom linken zum Rechten Rand 4 mal die Skalierung).
        double skalierung = einstellungen.getXSkalierung();
        maxIteration = einstellungen.getMaxIteration();

        vorschauSkalierung = einstellungen.getVorschauSkalierung();
        bildSkalierung = einstellungen.getBildSkalierung();

        weicheKanten = einstellungen.getWeicheKanten();

        minXKoordinate = xMittlereKoordinate - (skalierung / 2.0);
        minYKoordinate = yMittlereKoordinate - ((skalierung / 2.0)
                * (yAufloesung / (double) xAufloesung));

        double verhaeltnis = yAufloesung / (double) xAufloesung;

        koordSysBreite = skalierung;
        koordSysHoehe = koordSysBreite * verhaeltnis;
    }

    public static void setzeFarbPaletteZufaellig() {
        einstellungen.setFarbenComboBoxIndex(zufallszahl(2, 8));
    }

    /**
     * Wandelt die in Pixeln gemessene X Position in eine X Koordinate um.
     *
     * @param pixelPosition Die in Pixeln gemessene X Position
     * @return X Koordinate
     */
    private static double xPixelZuKoordinate(int pixelPosition) {
        return pixelPosition * koordSysBreite / pixelBreite
                + minXKoordinate;
    }

    /**
     * Wandelt die in Pixeln gemessene Y Position in eine Y Koordinate um.
     *
     * @param pixelPosition Die in Pixeln gemessene Y Position
     * @return Y Koordinate
     */
    private static double yPixelZuKoordinate(int pixelPosition) {
        return minYKoordinate + koordSysHoehe
                - pixelPosition * koordSysHoehe / pixelHoehe;
    }

    private static void setzeNullstellenfarben(String farbenset) {
        nullstellenFarbe.clear();
        switch (farbenset) {
            case "SchwarzWeiss":
                nullstellenFarbe.add(Color.white);
                break;

            case "Orange":
                nullstellenFarbe.add(new Color(255, 212, 139));
                nullstellenFarbe.add(new Color(255, 188, 130));
                nullstellenFarbe.add(new Color(255, 98, 0));
                nullstellenFarbe.add(new Color(168, 139, 255));
                nullstellenFarbe.add(new Color(130, 149, 255));
                nullstellenFarbe.add(new Color(0, 119, 255));
                break;

            case "Farbpalette1":
                nullstellenFarbe.add(new Color(26, 255, 0));
                nullstellenFarbe.add(new Color(130, 255, 143));
                nullstellenFarbe.add(new Color(135, 255, 223));
                nullstellenFarbe.add(new Color(255, 162, 139));
                nullstellenFarbe.add(new Color(255, 0, 26));
                nullstellenFarbe.add(new Color(255, 134, 130));
                break;

            case "Farbpalette2":
                nullstellenFarbe.add(new Color(8, 255, 62));
                nullstellenFarbe.add(new Color(8, 234, 255));
                nullstellenFarbe.add(new Color(86, 193, 255));
                nullstellenFarbe.add(new Color(255, 24, 8));
                nullstellenFarbe.add(new Color(255, 78, 8));
                nullstellenFarbe.add(new Color(255, 145, 86));
                break;

            case "Farbpalette3":
                nullstellenFarbe.add(new Color(255, 20, 28));
                nullstellenFarbe.add(new Color(255, 20, 130));
                nullstellenFarbe.add(new Color(255, 106, 98));
                nullstellenFarbe.add(new Color(130, 255, 20));
                nullstellenFarbe.add(new Color(98, 255, 129));
                nullstellenFarbe.add(new Color(26, 158, 14));
                break;

            case "Farbpalette4":
                nullstellenFarbe.add(new Color(230, 84, 21));
                nullstellenFarbe.add(new Color(252, 141, 22));
                nullstellenFarbe.add(new Color(255, 189, 96));
                nullstellenFarbe.add(new Color(24, 220, 255));
                nullstellenFarbe.add(new Color(15, 15, 161));
                nullstellenFarbe.add(new Color(70, 58, 138));
                break;

            case "Farbpalette5":
                nullstellenFarbe.add(new Color(11, 72, 107));
                nullstellenFarbe.add(new Color(59, 134, 134));
                nullstellenFarbe.add(new Color(121, 189, 154));
                nullstellenFarbe.add(new Color(168, 219, 168));
                nullstellenFarbe.add(new Color(207, 240, 158));
                break;

            case "Farbpalette6":
                nullstellenFarbe.add(new Color(249, 237, 105));
                nullstellenFarbe.add(new Color(240, 138, 93));
                nullstellenFarbe.add(new Color(184, 59, 94));
                nullstellenFarbe.add(new Color(106, 44, 112));
                nullstellenFarbe.add(new Color(43, 46, 74));
                break;

            case "Farbpalette7":
                nullstellenFarbe.add(new Color(73, 10, 61));
                nullstellenFarbe.add(new Color(189, 21, 80));
                nullstellenFarbe.add(new Color(233, 127, 2));
                nullstellenFarbe.add(new Color(248, 202, 0));
                nullstellenFarbe.add(new Color(138, 155, 15));
                break;
            /*
             * case "FarbpaletteX":
             *                 nullstellenFarbe.add(new Color());
             *                 nullstellenFarbe.add(new Color());
             *                 nullstellenFarbe.add(new Color());
             *                 nullstellenFarbe.add(new Color());
             *                 nullstellenFarbe.add(new Color());
             *                 break;
             */
            default:
                nullstellenFarbe.add(Color.black);
                break;
        }
    }

    /**
     * Lineare Interpolation einer Farbe mit der Hintergrundfarbe.
     *
     * @param farbe              the farbe
     * @param interpolationswert ( 0 = schwarz, 1 = "farbe")
     * @return mit der Hintergrundfarbe interpolierte Farbe
     */
    private static Color farbenInterpolation(Color farbe,
                                             float interpolationswert) {
        if (interpolationswert < 0) {
            interpolationswert = 0;
        }

        if (interpolationswert > 1) {
            interpolationswert = 1;
        }

        float red = farbe.getRed() * (interpolationswert);
        float green = farbe.getGreen() * (interpolationswert);
        float blue = farbe.getBlue() * (interpolationswert);
        return new Color(red / 255, green / 255, blue / 255);
    }

    private static KomplexeZahl newtonVerfahren(KomplexeZahl z) {

        KomplexeZahl yWert = funktionswert(z);

        //Ableitung: deltaY/deltaX = lim(h->0)(f(z+h)-f(z))/(h)
        final KomplexeZahl delta = new KomplexeZahl(1.0E-6, 1.0E-6);

        KomplexeZahl deltaY = funktionswert(z.addition(delta))
                .subtraktion(yWert);

        KomplexeZahl ableitung = deltaY.division(delta);

        //Newton Verfahren: xneu = z - (f(z))/(f'(z))
        KomplexeZahl subtrahend = funktionswert(z).division(ableitung);

        if (subtrahend == null) {
            return null;
        }

        return z.subtraktion(subtrahend);
    }

    /*
     * Kernfunktion des Programms
     */
    private static BufferedImage generiereNewtonFraktal() {

        BufferedImage bild = new BufferedImage(pixelBreite, pixelHoehe,
                BufferedImage.TYPE_INT_RGB);

        //Ein moeglichst an 0 liegender Wert
        final double schwellwert = 1.0E-6;

        final int neueNullstellenIteration = 500;

        for (int xPixelPos = 0; xPixelPos < pixelBreite; xPixelPos++) {
            for (int yPixelPos = 0; yPixelPos < pixelHoehe; yPixelPos++) {

                //Solange keine Nullstelle gefunden wurde, bleibt die
                //Farbe schwarz
                Color farbe = Color.black;

                //Startwert
                KomplexeZahl z = new KomplexeZahl(
                        xPixelZuKoordinate(xPixelPos),
                        yPixelZuKoordinate(yPixelPos));
                int iteration = 0;

                //Wird auf true gesetzt wenn eine Nullstelle gefunden wurde
                boolean nullstelleGefunden = false;

                while (!nullstelleGefunden) {

                    z = newtonVerfahren(z);

                    //Wenn durch 0 geteilt wurde wird abgebrochen
                    if (z == null) {
                        break;
                    }

                    //Gibt den Index der Nullstelle an
                    short nullstellenIndex = 0;

                    for (KomplexeZahl nullstelle : nullstellen) {
                        //Wenn z ~ Y Wert einer nullstelle
                        double differenz = z.subtraktion(nullstelle)
                                .betrag();

                        if (differenz <= schwellwert) {
                            float interpolationswert;
                            if (weicheKanten) {
                                double naheNullstelle = differenz
                                        / (schwellwert);
                                interpolationswert = (iteration
                                        + (float) naheNullstelle)
                                        / maxIteration;
                            } else {
                                interpolationswert = iteration
                                        / (float) maxIteration;
                            }

                            farbe = farbenInterpolation(
                                    nullstellenFarbe.get((nullstellenIndex)
                                            % nullstellenFarbe.size()),
                                    1 - interpolationswert);

                            //Das Newton Verfahren fuer diesen Pixel wird
                            //abgebrochen, da Nullstelle gefunden wurde
                            nullstelleGefunden = true;
                            break;
                        }
                        nullstellenIndex++;
                    }

                    /*
                      Wenn der Y Wert nahe 0 ist = wenn eine neue
                      Nullstelle gefunden wurde
                     */
                    if (funktionswert(z).betrag() <= schwellwert) {
                        if (iteration == neueNullstellenIteration) {
                            /*
                              Neue Nullstelle wird hinzugefuegt. Um die
                              Nullstelle so genau wie moeglich zu finden
                              wird sie erst bei der 500. Iteration
                              hinzugefuegt
                             */
                            nullstellen.add(z);
                            iteration = 1;
                            z = new KomplexeZahl(
                                    xPixelZuKoordinate(xPixelPos),
                                    yPixelZuKoordinate(yPixelPos));
                        }
                    }

                    /*
                      Wenn keine Nullstelle gefunden wurde und die
                      maximale Iteration erreicht wurde
                     */
                    else if (iteration >= maxIteration) {
                        /*
                          Pixel mit der Farbe die als "hintergrundfarbe"
                          gewaehlt wurde
                         */
                        break;
                    }

                    iteration++;
                }
                bild.setRGB(xPixelPos, yPixelPos, farbe.getRGB());
            }
        }
        return bild;
    }

    /**
     * Gibt f(x) zurueck.
     */
    private static KomplexeZahl funktionswert(KomplexeZahl eingabe) {
        KomplexeZahl funktionswert = new KomplexeZahl(0, 0);
        for (int i = polynom.length - 1; i >= 0; i--)
            funktionswert = polynom[i].addition(eingabe.multiplikation(funktionswert));
        return funktionswert;
    }

    public static void farbPaletteMischen() {
        Collections.shuffle(nullstellenFarbe);
    }

    public static String gebeNullstellen() {
        StringBuilder text = new StringBuilder();
        for (KomplexeZahl nullstelle : nullstellen) {
            text.append(nullstelle.toString()).append("\n");
        }
        return text.toString();
    }

    public static void bildSpeichern() {
        pixelBreite = (int) (xAufloesung * bildSkalierung);
        pixelHoehe = (int) (yAufloesung * bildSkalierung);

        JFileChooser dateiPfad = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("png",
                "png");
        dateiPfad.setCurrentDirectory(new java.io.File("."));
        dateiPfad.setDialogTitle("Speichern unter:");
        dateiPfad.setApproveButtonText("Speichern");
        dateiPfad.setSelectedFile(new File("Fraktal.png"));

        dateiPfad.setFileFilter(filter);

        int returnVal = dateiPfad.showOpenDialog(null);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = dateiPfad.getSelectedFile();

        BufferedImage bufferedImage = generiereNewtonFraktal();

        try {
            if (file.createNewFile()) {
                ImageIO.write(bufferedImage, "PNG", file);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Bild konnte nicht exportiert werden! Fehler: "
                            + e.getMessage());
            return;
        }
        JOptionPane.showMessageDialog(null, "Bild wurde exportiert!");
    }

    public static void aktualisiereVorschauBild() {
        pixelBreite = (int) (xAufloesung * vorschauSkalierung);
        pixelHoehe = (int) (yAufloesung * vorschauSkalierung);
        zeichenFlaeche.zeichneNewtonFraktal(generiereNewtonFraktal());
    }
}