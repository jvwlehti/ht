package shakkiturnaus.Controller;

import java.util.ArrayList;
import java.util.List;

import fi.jyu.mit.fxgui.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.util.Pair;
import shakkiturnaus.Model.Osallistuja;
import shakkiturnaus.Model.Tulos;
import shakkiturnaus.Model.Turnaus;

/**
 * @author jvwlehti & leodanbu
 * @version 12.2.2021
 *
 */
public class NakymaGUIController implements ModalControllerInterface<Turnaus> {

    @FXML
    private ComboBoxChooser<Osallistuja> choiceP1;

    @FXML
    private ComboBoxChooser<Osallistuja> choiceP2;

    @FXML
    private Button lisaa;

    @FXML
    private Button lopeta;
    
    @FXML
    private Button poista;

    @FXML
    private StringGrid<Object> tableOsallistujat;

    @FXML
    private StringGrid<Tulos> tableKierrokset;

    /*
     * lopettaa turnauksen ja ohjaa käyttäjän takaisin hallintaikkunaan
     */
    @FXML
    void handleLopeta() {
        turnaus.kirjoitaTulos();
        turnaus.kirjoitaTiedO();
        ModalController.closeStage(lopeta);
        Platform.exit();
    }


    /*
     * Ohjaa takaisin hallintaikkunaan
     */
    @FXML
    void handleLisaa() {
        kirjoitaPelit();
        taulukkoPaivita();
    }
    
    @FXML
    void handlePoista() {
        poistaPeli();
    }


    // ===============================================================================
    private Turnaus turnaus;
    private Tulos valittu;

    @Override
    public Turnaus getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub

    }

    @Override
    public void setDefault(Turnaus oletus) {
        this.turnaus = oletus;
        alusta(tableOsallistujat);
        alustaChoice();
        alustaKierros(tableKierrokset);
        tableKierrokset.setOnMouseClicked(e -> {valitse();});

    }

    /*
     * alustaa pelaajataulukon
     */
    private void alusta(StringGrid<Object> grid) {
        String[] headings = { "nimi", "seura", "luku", "pisteet" };
        grid.initTable(headings);
        grid.setColumnWidth(-1, 76);
        grid.setColumnWidth(0, 120);
        taulukkoPaivita();

    }

    /**
     * Alustaa choiceboxiin osallistujat
     */
    public void alustaChoice() {

        for (int i = 0; i < turnaus.getLkm(); i++) {
            Osallistuja osal = turnaus.haeOsallistuja(i);
            choiceP1.add(osal.getNimi(), osal);
            choiceP2.add(osal.getNimi(), osal);
        }
    }
    
    /**
     * hakee kohdalla olevan osallistujan
     */
    public void valitse() {
        int rivi = tableKierrokset.getRowNr();
        valittu = tableKierrokset.getObject(rivi);
    }


    /*
     * alustaa kierrostaulun pelatuilla kierroksilla
     */
    private void alustaKierros(StringGrid<Tulos> tableKierrokset2) {
        String[] headings = { "Kierros" };
        tableKierrokset2.initTable(headings);
        tableKierrokset2.setColumnWidth(0, 242);
        tulosta(tableKierrokset2);
    }

    /**
     * Päivittää osallistujataulukon
     */
    public void taulukkoPaivita() {
        tableOsallistujat.clear();

        for (int i = 0; i < turnaus.getLkm(); i++) {
            Osallistuja osal = turnaus.haeOsallistuja(i);
            String pisteet = "" + osal.getPisteet();
            tableOsallistujat.add(osal.getNimi(), osal.getKerho(),
                    osal.getVahvuus(), pisteet);
        }
    }

    /**
     * Lisää kierros tauluun kierroksen tuloksen
     * @param tableKierrokset2 taulu johon lisätään
     */
    public void tulosta(StringGrid<Tulos> tableKierrokset2) {
        List<Pair<Tulos, String>> tulokset = new ArrayList<Pair<Tulos, String>>();
        tulokset = turnaus.tulokset();
        tableKierrokset2.clear();

        for (Pair<Tulos, String> tulos : tulokset) {
            String[] kys = { tulos.getValue() };
            tableKierrokset2.add(tulos.getKey() ,kys);
        }

    }


    /**
     * luo tulos luokalle vietävät tiedot syötteistä
     * @throws NullPointerException Silloin kun ei ole molemmat pelaajat valittuna
     */
    private void kirjoitaPelit() throws NullPointerException {

        Osallistuja pelaaja1 = choiceP1.getSelectedObject();
        Osallistuja pelaaja2 = choiceP2.getSelectedObject();

        try {
            if (pelaaja1.equals(pelaaja2)) {
                Dialogs.showMessageDialog(
                        "Pelaaja 1 ja Pelaaja 2 eivät voi olla samoja",
                        e -> e.getDialogPane().setPrefWidth(400));
                return;
            }
            
            int p1 = pelaaja1.getId();
            int p2 = pelaaja2.getId();
            Tulos tulos = new Tulos(p1, p2);
            turnaus.lisaaTulos(tulos);
            turnaus.lisaaPiste(p1);
            tulosta(tableKierrokset);
        } catch (NullPointerException ex) {
            Dialogs.showMessageDialog(
                    "Lisää pelaaja\nVirhe: " + ex.getMessage(),
                    e -> e.getDialogPane().setPrefWidth(400));
        }

    }
    
    private void poistaPeli() {
        if (valittu == null)
            return;
        turnaus.poistaPeli(valittu);
        alustaKierros(tableKierrokset);
    }

}
