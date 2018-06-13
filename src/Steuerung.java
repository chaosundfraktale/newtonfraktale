/**
 * Beinhaltet, fuer welche Eingaben welche Funktionen ausgefuehrt werden
 * sollen
 */
public class Steuerung {

    public static void zufallswerte_ButtonGedrueckt() {
        Modell.einstellungenAnwenden();
        Modell.setzeMenueFunktionsTxtZufaellig();
        Modell.setzePolynome();
        Modell.setzeFarbPaletteZufaellig();
        Modell.aktualisiereVorschauBild();
    }

    public static void funktionstextGeandert(String funktionstext) {
        Modell.funktionstextGeandert(funktionstext);
    }

    public static void vorschau_ButtonGedrueckt() {
        Modell.einstellungenAnwenden();
        Modell.setzePolynome();
        Modell.aktualisiereVorschauBild();
    }

    public static void alsBildSpeichern_ButtonGedrueckt() {
        Modell.einstellungenAnwenden();
        Modell.bildSpeichern();
    }

    public static void einstellungen_ButtonGedrueckt() {
        Modell.oeffneEinstellungen();
    }

    public static void nullstellen_ButtonGedrueckt() {
        new NullstellenAnzeige(Modell.gebeNullstellen());
    }

    public static void mischen_ButtonGedrueckt() {
        Modell.farbPaletteMischen();
        Modell.aktualisiereVorschauBild();
    }

    public static void zufallsefarbeButtonGedrueckt() {
        Modell.setzeFarbPaletteZufaellig();
    }

    public static void EinstellungenAnwenden_ButtonGedrueckt() {
        Modell.einstellungenAnwenden();
        Modell.aktualisiereVorschauBild();
    }

    public static void farbenComboBoxGedrueckt() {
        Modell.farbenComboBoxGedrueckt();
    }
}
