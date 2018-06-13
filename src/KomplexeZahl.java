import java.util.Objects;

/**
 * Mit ihr koennen Rechnungen im Komplexen durchgefuehrt werden
 */
public class KomplexeZahl {

    private final double realteil;
    private final double imaginaerteil;

    /**
     * Konstruktor der Komplexen Zahl.
     *
     * @param realteil      Realteil der komplexen Zahl
     * @param imaginaerteil Imaginaerteil der komplexen Zahl
     */
    public KomplexeZahl(double realteil, double imaginaerteil) {
        this.realteil = realteil;
        this.imaginaerteil = imaginaerteil;
    }

    /**
     * Konstruktor der Komplexen Zahl.
     *
     * @param komplexeZahl Attribute dieser Klasse werden uebernommen
     */
    public KomplexeZahl(KomplexeZahl komplexeZahl) {
        realteil = komplexeZahl.getRealteil();
        imaginaerteil = komplexeZahl.getImaginaerteil();
    }

    public double getRealteil() {
        return realteil;
    }

    public double getImaginaerteil() {
        return imaginaerteil;
    }

    /**
     * gibt eine konjugiert komplexe Zahl. (a-bi anstatt a+bi)
     *
     * @param komplexeZahl eine komplexe Zahl
     * @return die konjugiert Komplexe Zahl
     */
    private KomplexeZahl konjugiertKomplexeZahl(KomplexeZahl komplexeZahl) {
        return new KomplexeZahl(komplexeZahl.getRealteil(),
                komplexeZahl.getImaginaerteil() * -1);
    }

    //Rechenoperationen.

    /**
     * "this" wird mit einer komplexen Zahl addiert
     *
     * @param summand Zahl die addiert wird
     * @return Wert der Summe
     */
    public KomplexeZahl addition(KomplexeZahl summand) {
        return new KomplexeZahl(realteil + summand.getRealteil(),
                imaginaerteil + summand.getImaginaerteil());
    }

    /**
     * "this" wird mit einer komplexen Zahl subtrahiert.
     *
     * @param subtrahend Zahl die abgezogen wird
     * @return Wert der Differenz
     */
    public KomplexeZahl subtraktion(KomplexeZahl subtrahend) {
        return new KomplexeZahl(realteil - subtrahend.getRealteil(),
                imaginaerteil - subtrahend.getImaginaerteil());
    }

    /**
     * "this" wird mit einer komplexen Zahl multipliziert.
     *
     * @param faktor Zahl die multipliziert wird
     * @return Wert des Produktes
     */
    public KomplexeZahl multiplikation(KomplexeZahl faktor) {
        return new KomplexeZahl(
                realteil * faktor.getRealteil()
                        - imaginaerteil * faktor.getImaginaerteil(),
                realteil * faktor.getImaginaerteil()
                        + imaginaerteil * faktor.getRealteil());
    }

    /**
     * "this" wird durch eine komplexen Zahl dividiert Dabei wird sie mit
     * der ihrer konjugiert komplexen Zahl malgenommen.
     *
     * @param divisor Teiler
     * @return Wert der Division
     */
    public KomplexeZahl division(KomplexeZahl divisor) {
        /*
         * Wert der Division = this/divisor = this * (1/divisor)
         *
         * z1/z2 = (z1*konjz2)/(z2*konjz2) = z1 * (konjz2)/(x2^2+y2^2)
         */

        /*
         * Konjugiert Komplexe Zahl des Divisors
         */
        KomplexeZahl konjugierterDivisor = konjugiertKomplexeZahl(divisor);
        /*
         * Quadrat des Divisors: realteil^2 + imaginaerteil^2
         */
        double divisorQuadrat = (divisor.getRealteil()
                * divisor.getRealteil())
                + (divisor.getImaginaerteil()
                * divisor.getImaginaerteil());

        /*
         * Durch 0 teilen nicht moeglich!
         */
        if (divisorQuadrat == 0) {
            return null;
        }
        return multiplikation(konjugierterDivisor)
                .division(divisorQuadrat);
    }

    /**
     * "this" wird durch eine reelle Zahl dividiert.
     *
     * @param divisor Teiler
     * @return Wert der Division
     */
    private KomplexeZahl division(double divisor) {
        return new KomplexeZahl(realteil / divisor,
                imaginaerteil / divisor);
    }

    /**
     * Gibt den Betrag (Abstand von 0|0) der Komplexen Zahl als double
     * zurueck.
     *
     * @return Betrag
     */
    public double betrag() {
        return Math.sqrt(
                (realteil * realteil) + (imaginaerteil * imaginaerteil));
    }

    /**
     * Gibt die komplexe Zahl auf n Nachkommastellen gerundet an.
     *
     * @param nachkommastellen Anzahl der Nachkommastellen auf die gerundet werden soll
     * @return gerundete Komplexe Zahl
     */
    private KomplexeZahl runden(int nachkommastellen) {
        double realteil = (Math
                .round(Math.pow(10, nachkommastellen) * this.realteil))
                / Math.pow(10, nachkommastellen);
        double imaginaerteil = (Math.round(
                Math.pow(10, nachkommastellen) * this.imaginaerteil))
                / Math.pow(10, nachkommastellen);
        return new KomplexeZahl(realteil, imaginaerteil);
    }

    /**
     * Gibt die komplexe Zahl als String zurueck.
     *
     * @return Realteil und durch "+" getrennten Imaginaerteil
     */
    public String toString() {
        KomplexeZahl ausgabe = runden(5);
        return ausgabe.getRealteil() + " + " + ausgabe.getImaginaerteil()
                + "i";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KomplexeZahl)) return false;
        KomplexeZahl that = (KomplexeZahl) o;
        return Double.compare(that.realteil, realteil) == 0 &&
                Double.compare(that.imaginaerteil, imaginaerteil) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(realteil, imaginaerteil);
    }
}