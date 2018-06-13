import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Das Layout des Einstellungs-Menues (mit Button Listenern)
 */
public class Einstellungen extends JDialog {

    private final JTextField xKoordinateTxt;
    private final JTextField yKoordinateTxt;
    private final JTextField xAufloesungTxt;
    private final JTextField yAufloesungTxt;
    private final JTextField xSkalierungTxt;
    private final JSpinner maxIterationenSpinner;
    private final JSpinner vorschauSkalierungSpinner;
    private final JSpinner bildSkalierungSpinner;
    private final JComboBox farbenComboBox;
    private final JCheckBox weicheKantenCheckbox;

    public Double getXKoordinate() {
        try {
            return Double.parseDouble(xKoordinateTxt.getText());
        } catch (Exception e) {
            return 0.0;
        }
    }

    public Double getYKoordinate() {
        try {
            return Double.parseDouble(yKoordinateTxt.getText());
        } catch (Exception e) {
            return 0.0;
        }
    }

    public int getXAufloesung() {
        int xAufloesung;
        final int minXAufloesung = 100;
        try {
            xAufloesung = Integer.parseInt(xAufloesungTxt.getText());
        } catch (Exception e) {
            xAufloesung = 500;
            xAufloesungTxt.setText("" + xAufloesung);
        }
        if (xAufloesung < minXAufloesung) {
            xAufloesung = minXAufloesung;
            xAufloesungTxt.setText("" + xAufloesung);
        }
        return xAufloesung;
    }

    public int getYAufloesung() {
        int yAufloesung;
        final int minYAufloesung = 100;
        try {
            yAufloesung = Integer.parseInt(yAufloesungTxt.getText());
        } catch (Exception e) {
            yAufloesung = 500;
            yAufloesungTxt.setText("" + yAufloesung);
        }
        if (yAufloesung < minYAufloesung) {
            yAufloesung = minYAufloesung;
            yAufloesungTxt.setText("" + yAufloesung);
        }
        return yAufloesung;
    }

    public Double getXSkalierung() {
        double xSkalierung;
        final double minXSkalierung = 0.0001;
        try {
            xSkalierung = Double.parseDouble(xSkalierungTxt.getText());
        } catch (Exception e) {
            xSkalierung = 1.5;
            xSkalierungTxt.setText("" + xSkalierung);
        }
        if (xSkalierung < minXSkalierung) {
            xSkalierung = minXSkalierung;
            xSkalierungTxt.setText("" + xSkalierung);
        }
        return xSkalierung;
    }

    public int getMaxIteration() {
        try {
            return (int) maxIterationenSpinner.getValue();
        } catch (Exception e) {
            return 0;
        }
    }

    public Double getVorschauSkalierung() {
        try {
            return ((int) vorschauSkalierungSpinner.getValue()) / 100.0;
        } catch (Exception e) {
            return 0.0;
        }
    }

    public Double getBildSkalierung() {
        try {
            return ((int) bildSkalierungSpinner.getValue()) / 100.0;
        } catch (Exception e) {
            return 0.0;
        }
    }

    public boolean getWeicheKanten() {
        try {
            return weicheKantenCheckbox.isSelected();
        } catch (Exception e) {
            return false;
        }
    }

    public String getFarbenComboBoxItem() {
        return (String) farbenComboBox.getSelectedItem();
    }

    public void setFarbenComboBoxIndex(int index) {
        farbenComboBox.setSelectedIndex(index);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public Einstellungen() {
        setType(Type.UTILITY);
        setTitle("Einstellungen");
        setResizable(false);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        setVisible(false);
        setBounds(100, 100, 220, 570);
        getContentPane().setLayout(new BorderLayout());
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Menue.hintergrundfarbe);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JPanel buttonPane = new JPanel();
        buttonPane.setBackground(Menue.akkzentFarbe);
        FlowLayout flButtonPane = new FlowLayout(FlowLayout.RIGHT);
        buttonPane.setLayout(flButtonPane);
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        xKoordinateTxt = new JTextField();
        xKoordinateTxt.setForeground(Color.WHITE);
        xKoordinateTxt.setBorder(
                BorderFactory.createLineBorder(Menue.akkzentFarbe));
        xKoordinateTxt.setBackground(Menue.hintergrundfarbe);
        xKoordinateTxt.setText("0.0");
        xKoordinateTxt.setHorizontalAlignment(SwingConstants.CENTER);
        xKoordinateTxt.setColumns(10);
        xKoordinateTxt.setBounds(50, 201, 50, 20);
        contentPanel.add(xKoordinateTxt);

        yKoordinateTxt = new JTextField();
        yKoordinateTxt.setBackground(Menue.hintergrundfarbe);
        yKoordinateTxt.setBorder(
                BorderFactory.createLineBorder(Menue.akkzentFarbe));
        yKoordinateTxt.setForeground(Color.WHITE);
        yKoordinateTxt.setText("0.0");
        yKoordinateTxt.setHorizontalAlignment(SwingConstants.CENTER);
        yKoordinateTxt.setColumns(10);
        yKoordinateTxt.setBounds(129, 201, 50, 20);
        contentPanel.add(yKoordinateTxt);

        maxIterationenSpinner = new JSpinner();
        maxIterationenSpinner.setBorder(
                BorderFactory.createLineBorder(Menue.akkzentFarbe));
        maxIterationenSpinner.setForeground(Color.BLUE);
        maxIterationenSpinner.setBackground(Menue.hintergrundfarbe);
        maxIterationenSpinner
                .setModel(new SpinnerNumberModel(20, 1, 100, 1));
        maxIterationenSpinner.setBounds(121, 290, 40, 20);
        contentPanel.add(maxIterationenSpinner);

        xAufloesungTxt = new JTextField();
        xAufloesungTxt.setBorder(
                BorderFactory.createLineBorder(Menue.akkzentFarbe));
        xAufloesungTxt.setForeground(Color.WHITE);
        xAufloesungTxt.setBackground(Menue.hintergrundfarbe);
        xAufloesungTxt.setText("500");
        xAufloesungTxt.setHorizontalAlignment(SwingConstants.CENTER);
        xAufloesungTxt.setColumns(10);
        xAufloesungTxt.setBounds(31, 49, 50, 20);
        contentPanel.add(xAufloesungTxt);

        yAufloesungTxt = new JTextField();
        yAufloesungTxt.setBorder(
                BorderFactory.createLineBorder(Menue.akkzentFarbe));
        yAufloesungTxt.setForeground(Color.WHITE);
        yAufloesungTxt.setBackground(Menue.hintergrundfarbe);
        yAufloesungTxt.setText("500");
        yAufloesungTxt.setHorizontalAlignment(SwingConstants.CENTER);
        yAufloesungTxt.setColumns(10);
        yAufloesungTxt.setBounds(101, 49, 50, 20);
        contentPanel.add(yAufloesungTxt);

        vorschauSkalierungSpinner = new JSpinner();
        vorschauSkalierungSpinner.setBorder(
                BorderFactory.createLineBorder(Menue.akkzentFarbe));
        vorschauSkalierungSpinner.setForeground(Menue.hintergrundfarbe);
        vorschauSkalierungSpinner.setBackground(Menue.hintergrundfarbe);
        vorschauSkalierungSpinner
                .setModel(new SpinnerNumberModel(80, 5, 200, 10));
        vorschauSkalierungSpinner.setBounds(91, 111, 40, 20);
        contentPanel.add(vorschauSkalierungSpinner);

        bildSkalierungSpinner = new JSpinner();
        bildSkalierungSpinner.setBorder(
                BorderFactory.createLineBorder(Menue.akkzentFarbe));
        bildSkalierungSpinner.setBackground(Menue.hintergrundfarbe);
        bildSkalierungSpinner
                .setModel(new SpinnerNumberModel(100, 10, 1000, 10));
        bildSkalierungSpinner.setBounds(91, 141, 40, 20);
        contentPanel.add(bildSkalierungSpinner);

        xSkalierungTxt = new JTextField();
        xSkalierungTxt.setBorder(
                BorderFactory.createLineBorder(Menue.akkzentFarbe));
        xSkalierungTxt.setForeground(Color.WHITE);
        xSkalierungTxt.setBackground(Menue.hintergrundfarbe);
        xSkalierungTxt.setText("3");
        xSkalierungTxt.setHorizontalAlignment(SwingConstants.CENTER);
        xSkalierungTxt.setColumns(10);
        xSkalierungTxt.setBounds(50, 261, 50, 20);
        contentPanel.add(xSkalierungTxt);

        weicheKantenCheckbox = new JCheckBox("weiche Kanten");
        weicheKantenCheckbox.setSelected(false);
        weicheKantenCheckbox.setBackground(Menue.hintergrundfarbe);
        weicheKantenCheckbox.setForeground(Color.WHITE);
        weicheKantenCheckbox.setBounds(21, 317, 130, 23);
        contentPanel.add(weicheKantenCheckbox);

        farbenComboBox = new JComboBox();
        farbenComboBox.setForeground(Color.WHITE);
        farbenComboBox.setBackground(Menue.hintergrundfarbe);
        farbenComboBox.setBorder(
                BorderFactory.createLineBorder(Menue.akkzentFarbe));
        farbenComboBox.setModel(new DefaultComboBoxModel(
                new String[]{"SchwarzWeiss", "Farbpalette1",
                        "Farbpalette2", "Farbpalette3", "Farbpalette4",
                        "Farbpalette5", "Farbpalette6", "Farbpalette7"}));
        farbenComboBox.setBounds(31, 374, 120, 20);
        contentPanel.add(farbenComboBox);
        farbenComboBox.addActionListener(e -> Steuerung.farbenComboBoxGedrueckt());

        JButton zufallsefarbeButton = new JButton("Zufallsfarbe");
        zufallsefarbeButton.setBorder(
                BorderFactory.createLineBorder(Menue.akkzentFarbe));
        zufallsefarbeButton.setForeground(Color.WHITE);
        zufallsefarbeButton.setBackground(Menue.hintergrundfarbe);
        zufallsefarbeButton.setBounds(31, 434, 120, 23);
        contentPanel.add(zufallsefarbeButton);
        zufallsefarbeButton.addActionListener(e -> Steuerung.zufallsefarbeButtonGedrueckt());

        JButton einstellungenAnwendenButton = new JButton(
                BildLader.ladeBild("ic_done_white_24dp.png"));
        einstellungenAnwendenButton.setRolloverIcon(
                BildLader.ladeBild("ic_done_black_24dp.png"));
        einstellungenAnwendenButton.setBounds(585, 5, 32, 32);
        einstellungenAnwendenButton.setContentAreaFilled(false);
        einstellungenAnwendenButton
                .setBorder(BorderFactory.createEmptyBorder());
        einstellungenAnwendenButton.setBackground(new Color(255, 140, 0));
        einstellungenAnwendenButton.setForeground(Color.BLACK);
        einstellungenAnwendenButton.setActionCommand("OK");
        buttonPane.add(einstellungenAnwendenButton);
        getRootPane().setDefaultButton(einstellungenAnwendenButton);
        einstellungenAnwendenButton
                .addActionListener(e -> Steuerung.EinstellungenAnwenden_ButtonGedrueckt());

        JButton mischenButton = new JButton("Mischen");
        mischenButton.setBorder(
                BorderFactory.createLineBorder(Menue.akkzentFarbe));
        mischenButton.setForeground(Color.WHITE);
        mischenButton.setBackground(Menue.hintergrundfarbe);
        mischenButton.setBounds(31, 404, 120, 23);
        contentPanel.add(mischenButton);
        mischenButton.addActionListener(e -> Steuerung.mischen_ButtonGedrueckt());

        JLabel lblMaxiterationen = new JLabel("MaxIterationen:");
        lblMaxiterationen.setForeground(Color.WHITE);
        lblMaxiterationen.setHorizontalAlignment(SwingConstants.LEFT);
        lblMaxiterationen.setBounds(22, 290, 100, 20);
        contentPanel.add(lblMaxiterationen);

        JLabel lblX = new JLabel("x");
        lblX.setForeground(Color.WHITE);
        lblX.setBackground(Menue.hintergrundfarbe);
        lblX.setHorizontalAlignment(SwingConstants.CENTER);
        lblX.setBounds(81, 49, 20, 20);
        contentPanel.add(lblX);

        JLabel VorschauLabel = new JLabel("Vorschau");
        VorschauLabel.setForeground(Color.WHITE);
        VorschauLabel.setHorizontalAlignment(SwingConstants.LEFT);
        VorschauLabel.setBounds(31, 111, 70, 20);
        contentPanel.add(VorschauLabel);

        JLabel PNGLabel = new JLabel("PNG");
        PNGLabel.setForeground(Color.WHITE);
        PNGLabel.setHorizontalAlignment(SwingConstants.LEFT);
        PNGLabel.setBounds(31, 141, 50, 20);
        contentPanel.add(PNGLabel);

        JLabel lblMittlereKoordinate = new JLabel("Mittlere Koordinate:");
        lblMittlereKoordinate.setForeground(Color.WHITE);
        lblMittlereKoordinate.setHorizontalAlignment(SwingConstants.LEFT);
        lblMittlereKoordinate.setBounds(21, 170, 120, 20);
        contentPanel.add(lblMittlereKoordinate);

        JLabel label5 = new JLabel("Skalierung:");
        label5.setForeground(Color.WHITE);
        label5.setHorizontalAlignment(SwingConstants.LEFT);
        label5.setBounds(21, 230, 100, 20);
        contentPanel.add(label5);

        JLabel label2 = new JLabel("X");
        label2.setForeground(Color.WHITE);
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setBounds(31, 261, 20, 20);
        contentPanel.add(label2);

        JLabel lblVollbildskalierung = new JLabel("Skalierung:");
        lblVollbildskalierung.setForeground(Color.WHITE);
        lblVollbildskalierung.setHorizontalAlignment(SwingConstants.LEFT);
        lblVollbildskalierung.setBounds(22, 80, 100, 20);
        contentPanel.add(lblVollbildskalierung);

        JLabel lblAuflsung = new JLabel("Aufl\u00F6sung:");
        lblAuflsung.setForeground(Color.WHITE);
        lblAuflsung.setHorizontalAlignment(SwingConstants.LEFT);
        lblAuflsung.setBounds(21, 20, 60, 20);
        contentPanel.add(lblAuflsung);

        JLabel lblFarbe = new JLabel("Farbe:");
        lblFarbe.setForeground(Color.WHITE);
        lblFarbe.setHorizontalAlignment(SwingConstants.LEFT);
        lblFarbe.setBounds(22, 347, 50, 14);
        contentPanel.add(lblFarbe);

        JLabel label = new JLabel("X");
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(31, 201, 20, 20);
        contentPanel.add(label);

        JLabel label1 = new JLabel("Y");
        label1.setForeground(Color.WHITE);
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setBounds(110, 201, 20, 20);
        contentPanel.add(label1);

        JLabel label6 = new JLabel("%");
        label6.setHorizontalAlignment(SwingConstants.LEFT);
        label6.setForeground(Color.WHITE);
        label6.setBounds(135, 111, 20, 20);
        contentPanel.add(label6);

        JLabel label7 = new JLabel("%");
        label7.setHorizontalAlignment(SwingConstants.LEFT);
        label7.setForeground(Color.WHITE);
        label7.setBounds(135, 141, 20, 20);
        contentPanel.add(label7);
    }
}