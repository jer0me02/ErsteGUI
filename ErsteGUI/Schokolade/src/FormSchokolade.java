import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormSchokolade extends JFrame {
    private JPanel MainSchokolade;
    private JComboBox<String> comboBoxKategorie;
    private JPanel Rechnung;
    private JCheckBox checkBoxCrispy;
    private JCheckBox checkBoxKaramell;
    private JCheckBox checkBoxGoldblattt;
    private JTextField textFieldAnzahl;
    private JTextField textFieldNetto;
    private JTextField textFieldMwSt;
    private JTextField textFieldBrutto;
    private JButton buttonNeu;
    private JButton buttonRechnen;
    private static final double MEHRWERTSTEUER = 0.19;
    private static final double VOLLMILCH = 1.2;
    private static final double WEISSE = 1.5;
    private static final double DUNKLE = 1.0;
    private static final double OREO = 1.8;
    private static final double CRISPY = 0.2;
    private static final double KARAMELL = 0.5;
    private static final double GOLDBLATT = 2.0;

    private void createUIComponents() {
        comboBoxKategorie = new JComboBox<>();
    }

    public FormSchokolade() {
        setTitle("Schokolade");
        setLocationByPlatform(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(MainSchokolade);
        pack();
        buttonRechnen.setToolTipText("Berechnet den Preis der Schokolade");
        comboBoxKategorie();

        buttonNeu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setButtonNeu();
            }
        });

        buttonRechnen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setButtonRechnen();
            }
        });
    }

    private void comboBoxKategorie() {
        comboBoxKategorie.removeAllItems();
        comboBoxKategorie.addItem("Vollmilch");
        comboBoxKategorie.addItem("Dunkle");
        comboBoxKategorie.addItem("Weisse");
        comboBoxKategorie.addItem("Oreo");
        comboBoxKategorie.setSelectedIndex(-1);
    }

    private void setButtonNeu() {
        buttonNeu.setToolTipText("Löscht alle Eingaben");
        textFieldAnzahl.setText("");
        textFieldNetto.setText("");
        textFieldMwSt.setText("");
        textFieldBrutto.setText("");
        checkBoxCrispy.setSelected(false);
        checkBoxKaramell.setSelected(false);
        checkBoxGoldblattt.setSelected(false);
        comboBoxKategorie.setSelectedIndex(-1);
    }

    private void setButtonRechnen() {
        try {
            int anzahl = Integer.parseInt(textFieldAnzahl.getText());

            if (anzahl > 10) {
                JOptionPane.showMessageDialog(this, "Es dürfen maximal 10 Schokoladen pro Bestellung verkauft werden.", "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double preisProSchoko = 0.0;

            String kategorie = (String) comboBoxKategorie.getSelectedItem();
            if (kategorie == null) {
                JOptionPane.showMessageDialog(this, "Bitte eine Kategorie auswählen.", "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }

            switch (kategorie) {
                case "Vollmilch":
                    preisProSchoko = VOLLMILCH;
                    break;
                case "Dunkle":
                    preisProSchoko = DUNKLE;
                    break;
                case "Weisse":
                    preisProSchoko = WEISSE;
                    break;
                case "Oreo":
                    preisProSchoko = OREO;
                    break;
            }

            if (checkBoxCrispy.isSelected()) {
                preisProSchoko += CRISPY;
            }

            if (checkBoxKaramell.isSelected()) {
                preisProSchoko += KARAMELL;
            }

            if (checkBoxGoldblattt.isSelected()) {
                preisProSchoko += GOLDBLATT;
            }

            double gesamtpreisNetto = preisProSchoko * anzahl;
            double gesamtpreisMwSt = gesamtpreisNetto * MEHRWERTSTEUER;
            double gesamtpreisBrutto = gesamtpreisNetto + gesamtpreisMwSt;

            textFieldNetto.setText(String.format("%.2f", gesamtpreisNetto));
            textFieldMwSt.setText(String.format("%.2f", gesamtpreisMwSt));
            textFieldBrutto.setText(String.format("%.2f", gesamtpreisBrutto));

        } catch (NumberFormatException ex) {
            textFieldNetto.setText("Ungültige Eingabe");
            textFieldMwSt.setText("Ungültige Eingabe");
            textFieldBrutto.setText("Ungültige Eingabe");
        }
    }
}
