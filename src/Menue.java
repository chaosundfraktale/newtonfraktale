import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * Das Layout des Hauptmenues (mit Button Listenern)
 */
public class Menue extends JFrame {

    private static JTextField funktionTxt;

    private static final long serialVersionUID = 1L;

    static final Color hintergrundfarbe = new Color(38, 50, 56);

    static final Color akkzentFarbe = new Color(255, 111, 0);

    public void setFunktionsTxt(String funktionstext) {
        funktionTxt.setText(funktionstext);
    }

    public String getFunktionsTxt() {
        return funktionTxt.getText();
    }

    public void setFunktionstextRandFarbe(boolean istAkkzentfarbe) {
        Color farbe;
        if (istAkkzentfarbe) {
            farbe = akkzentFarbe;
        } else {
            farbe = Color.RED;
        }
        funktionTxt.setBorder(BorderFactory.createLineBorder(farbe));
    }

    public void funktionstextGeaendert() {
        Steuerung.funktionstextGeandert(funktionTxt.getText());
    }

    public Menue() {
        setVisible(true);
        setTitle("Newton-Fraktal Generator");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(200, 200, 400, 150);
        JPanel contentPane = new JPanel();
        contentPane.setBackground(hintergrundfarbe);
        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel_1.setBackground(hintergrundfarbe);
        contentPane.add(panel_1, BorderLayout.CENTER);
        panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

        JLabel lblFx = new JLabel("f(z) = ");
        panel_1.add(lblFx);
        lblFx.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblFx.setForeground(Color.WHITE);
        lblFx.setHorizontalAlignment(SwingConstants.CENTER);

        funktionTxt = new JTextField();
        funktionTxt.setMaximumSize(new Dimension(10000, 20));
        panel_1.add(funktionTxt);
        funktionTxt.setFont(new Font("Tahoma", Font.PLAIN, 12));
        funktionTxt.setForeground(Color.WHITE);
        funktionTxt.setBackground(hintergrundfarbe);
        funktionTxt.setColumns(10);
        funktionTxt.getDocument()
                .addDocumentListener(new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        funktionstextGeaendert();
                    }

                    public void removeUpdate(DocumentEvent e) {
                        funktionstextGeaendert();
                    }

                    public void insertUpdate(DocumentEvent e) {
                        funktionstextGeaendert();
                    }
                });

        JPanel panel = new JPanel();
        panel.setMaximumSize(new Dimension(200, 100));
        panel.setBackground(akkzentFarbe);
        contentPane.add(panel, BorderLayout.SOUTH);
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

        JButton alsBildSpeichern_Button = new JButton(
                BildLader.ladeBild("ic_get_app_white_24dp.png"));
        alsBildSpeichern_Button.setRolloverIcon(
                BildLader.ladeBild("ic_get_app_black_24dp.png"));
        alsBildSpeichern_Button.setContentAreaFilled(false);
        alsBildSpeichern_Button
                .setBorder(BorderFactory.createEmptyBorder());
        panel.add(alsBildSpeichern_Button);
        alsBildSpeichern_Button.setBackground(new Color(255, 140, 0));
        alsBildSpeichern_Button.addActionListener(arg0 -> Steuerung.alsBildSpeichern_ButtonGedrueckt());

        JButton einstellungen_Button = new JButton(
                BildLader.ladeBild("ic_settings_white_24dp.png"));
        panel.add(einstellungen_Button);
        einstellungen_Button.setRolloverIcon(
                BildLader.ladeBild("ic_settings_black_24dp.png"));
        einstellungen_Button.setContentAreaFilled(false);
        einstellungen_Button.setBorder(BorderFactory.createEmptyBorder());
        einstellungen_Button.setBackground(new Color(255, 140, 0));
        einstellungen_Button.addActionListener(e -> Steuerung.einstellungen_ButtonGedrueckt());

        JButton nullstellen_Button = new JButton(
                BildLader.ladeBild("ic_info_outline_white_24dp.png"));
        nullstellen_Button.setRolloverIcon(
                BildLader.ladeBild("ic_info_outline_black_24dp.png"));
        nullstellen_Button.setContentAreaFilled(false);
        nullstellen_Button.setBorder(BorderFactory.createEmptyBorder());
        panel.add(nullstellen_Button);
        nullstellen_Button.setBackground(new Color(255, 140, 0));
        nullstellen_Button.addActionListener(e -> Steuerung.nullstellen_ButtonGedrueckt());

        JButton zufallswerte_Button = new JButton(
                BildLader.ladeBild("ic_shuffle_white_24dp.png"));
        zufallswerte_Button.setRolloverIcon(
                BildLader.ladeBild("ic_shuffle_black_24dp.png"));
        zufallswerte_Button.setContentAreaFilled(false);
        zufallswerte_Button.setBorder(BorderFactory.createEmptyBorder());
        panel.add(zufallswerte_Button);
        zufallswerte_Button.setBackground(new Color(255, 140, 0));
        zufallswerte_Button.addActionListener(arg0 -> Steuerung.zufallswerte_ButtonGedrueckt());

        JButton vorschau_Button = new JButton(
                BildLader.ladeBild("ic_done_white_24dp.png"));
        vorschau_Button.setRolloverIcon(
                BildLader.ladeBild("ic_done_black_24dp.png"));
        panel.add(vorschau_Button);
        vorschau_Button.setContentAreaFilled(false);
        vorschau_Button.setBorder(BorderFactory.createEmptyBorder());
        vorschau_Button.setBackground(new Color(255, 165, 0));
        vorschau_Button.addActionListener(e -> Steuerung.vorschau_ButtonGedrueckt());
    }
}