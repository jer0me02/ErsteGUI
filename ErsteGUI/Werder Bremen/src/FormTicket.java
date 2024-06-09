import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class FormTicket extends JFrame {
    private JPanel FormTicket;
    private JPanel Ticket;
    private JPanel Rechnung;
    private JRadioButton radioButtonBundesliga;
    private JRadioButton radioButtonPokal;
    private JCheckBox preisnachlass;
    private JComboBox<String> comboBoxKategorie;
    private JTextField textFieldAnzahl;
    private JButton buttonRechnen;
    private JTextField textFieldBrutto;
    private JTextField textFieldMwSt;
    private JTextField textFieldNetto;
    private JPanel MainFussball;
    private static final double PREISPOKAL = 22.0;
    private static final double PREISKATEGORIE_A = 15.0;
    private static final double PREISKATEGORIE_B = 20.0;
    private static final double PREISKATEGORIE_C = 25.0;
    private static final double PREISNACHLASS = 0.8;
    private static final double MEHRWERTSTEUER = 0.19;

    private void createUIComponents() {
        comboBoxKategorie = new JComboBox<>();
    }

    public FormTicket() {
        this.setTitle("Ticket");
        this.setLocationByPlatform(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(MainFussball);
        this.pack();
        buttonRechnen.setToolTipText("Berechnet den Preis der Tickets");
        comboBoxKategorie();
        radioButtonBundesliga.setSelected(true);
        radioButtonBundesliga.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comboBoxKategorie.setEnabled(true);
            }
        });

        radioButtonPokal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comboBoxKategorie.setEnabled(false);
            }
        });

        buttonRechnen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setbuttonRechnen();
            }
        });
    }

    public void comboBoxKategorie() {
        comboBoxKategorie.removeAllItems();
        comboBoxKategorie.addItem("Kategorie A");
        comboBoxKategorie.addItem("Kategorie B");
        comboBoxKategorie.addItem("Kategorie C");
        comboBoxKategorie.setSelectedIndex(-1);
    }

    private void setbuttonRechnen() {
        try {
            int anzahl = Integer.parseInt(textFieldAnzahl.getText());

            if (anzahl > 5) {
                JOptionPane.showMessageDialog(this, "Es dürfen maximal 5 Tickets pro Bestellung verkauft werden.", "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double preisProTicket = 0.0;

            if (radioButtonBundesliga.isSelected()) {
                String kategorie = Objects.requireNonNull(comboBoxKategorie.getSelectedItem()).toString();
                switch (kategorie) {
                    case "Kategorie A":
                        preisProTicket = PREISKATEGORIE_A;
                        break;
                    case "Kategorie B":
                        preisProTicket = PREISKATEGORIE_B;
                        break;
                    case "Kategorie C":
                        preisProTicket = PREISKATEGORIE_C;
                        break;
                }
            } else if (radioButtonPokal.isSelected()) {
                preisProTicket = PREISPOKAL;
            }

            if (preisnachlass.isSelected()) {
                preisProTicket *= PREISNACHLASS;
            }

            double gesamtpreisNetto = preisProTicket * anzahl;
            double gesamtpreisMwSt = gesamtpreisNetto * MEHRWERTSTEUER;
            double gesamtpreisBrutto = gesamtpreisNetto + gesamtpreisMwSt;

            textFieldNetto.setText(String.format("%.2f", gesamtpreisNetto));
            textFieldMwSt.setText(String.format("%.2f", gesamtpreisMwSt));
            textFieldBrutto.setText(String.format("%.2f", gesamtpreisBrutto));

        } catch (NumberFormatException ex) {
            textFieldNetto.setText("Ungültige Eingabe");
            textFieldMwSt.setText("Ungültige Eingabe");
            textFieldBrutto.setText("Ungültige Eingabe");
        } catch (NullPointerException ex) {
            textFieldNetto.setText("Kategorie wählen");
            textFieldMwSt.setText("");
            textFieldBrutto.setText("");
        }
    }
}