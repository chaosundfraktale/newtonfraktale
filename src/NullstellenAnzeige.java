import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Der Dialogs, der die Nullstellen eines Graphen zeigt
 */
public class NullstellenAnzeige extends JDialog {

    private static final long serialVersionUID = 1L;

    public NullstellenAnzeige(String text) {

        setVisible(true);
        setBounds(100, 100, 250, 250);
        getContentPane().setLayout(new BorderLayout());
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Menue.hintergrundfarbe);
        contentPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));
        setTitle("Nullstellen");

        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Tahoma", Font.PLAIN, 16));
        textArea.setForeground(Color.WHITE);
        textArea.setBackground(Menue.hintergrundfarbe);
        textArea.setText(text);
        textArea.setRows(6);
        textArea.setEditable(false);
        contentPanel.add(textArea);

        JLabel lblNewLabel = new JLabel("Nullstellen:");
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        contentPanel.add(lblNewLabel, BorderLayout.NORTH);

        pack();

    }
}