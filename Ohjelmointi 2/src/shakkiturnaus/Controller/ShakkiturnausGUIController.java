package shakkiturnaus.Controller;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import shakkiturnaus.Model.Osallistuja;
import shakkiturnaus.Model.PoikkeusException;
import shakkiturnaus.Model.Tarkistin;
import shakkiturnaus.Model.Turnaus;
import fi.jyu.mit.fxgui.StringGrid;

/**
 * @author lehti
 * @version 12.2.2021
 *
 */
public class ShakkiturnausGUIController
        implements ModalControllerInterface<Turnaus> {

    @FXML
    private Button Aloita;

    @FXML
    private TextField etunimi;

    @FXML
    private TextField sukunimi;

    @FXML
    private TextField seura;

    @FXML
    private TextField vahvuus;

    @FXML
    private TextField sposti;

    @FXML
    private TextField puhnum;
    
    @FXML
    private TextField haku;

    @FXML
    private StringGrid<Object> tableTurnaus;

    /*
     * Aloittaaturnauksen ja ohjaa käyttäjän turnausnäkymän puolelle
     */
    @FXML
    void handleAloita() {
        ModalController.showModal(ShakkiturnausGUIController.class
                .getResource("/shakkiturnaus/FX/NakymaGUIView.fxml"), "Näkymä", null, turnaus);
        ModalController.closeStage(Aloita);// riski olemassa
    }


    /**
     * Avaa muokkausnäkymän
     */
    @FXML
    void handleMuokkaa() {
    	
    	valitse();
        ModalController.<Osallistuja, MuokkausGUIController>showModal(ShakkiturnausGUIController.class
                .getResource("/shakkiturnaus/FX/MuokkausGUIView.fxml"), "Näkymä", null, valittu, ctrl -> {
                	ctrl.setTurnaus(turnaus);
                });
        taulukkoPaivita();
        
    }
    
    @FXML
    void handleHae() {
        taulukkoPaivita();
    }


    /*
     * lisää uuden pelaajan turnaukseen
     */
    @FXML
    void handleLisaa() {
        osallistujaLisa();
    }


    /**
     * pelaajan poisto turnauksesta
     */
    @FXML
    void handlePoista() {
        osallistujaPoisto();
        // Dialogs.showMessageDialog("In progress");
    }

    // =========================================================================================//
    private Turnaus turnaus;
    private Osallistuja valittu;

    /**
     * alustaa osallistujataulun
     */
    private void alusta(StringGrid<Object> grid) {
        String[] headings = { "nimi", "vahvuusluku", "seura" };
        grid.initTable(headings);
        grid.setColumnWidth(0, 123);
        taulukkoPaivita();
        tableTurnaus.setOnMouseClicked(e -> {valitse();});

    }


    /**
     * Lisää osallistujan turnaukseen ja asettaa tiedot taulukkoon
     */
    private void osallistujaLisa() {
        Osallistuja uusi = new Osallistuja();
        uusi = haeTiedot(uusi);

        String tarkistusVah = Tarkistin.tarkistusVah(uusi.getVahvuus());
        String tarkistusPuh = Tarkistin.tarkistusPuh(uusi.getPuhnum());
        if (tarkistusVah != null) {
            vahvuus.setStyle("-fx-background-color: red;");
        }
        
        if (tarkistusPuh != null) {
            puhnum.setStyle("-fx-background-color: red;");
        }
        
        if (tarkistusVah != null || tarkistusPuh != null) {
            
            if(tarkistusVah == null) {
                Dialogs.showMessageDialog(
                        "Lisääminen ei onnistu\n" + "\n" + tarkistusPuh, 
                         e -> e.getDialogPane().setPrefWidth(400));
                return;
            }
            
            if (tarkistusPuh == null) {
                Dialogs.showMessageDialog(
                        "Lisääminen ei onnistu\n"+ tarkistusVah, 
                         e -> e.getDialogPane().setPrefWidth(400));
                return;
            }
            Dialogs.showMessageDialog(
                    "Lisääminen ei onnistu\n"+ tarkistusVah +"\n\n" + tarkistusPuh, 
                     e -> e.getDialogPane().setPrefWidth(400));
            return;
        }
        vahvuus.setStyle(null);
        puhnum.setStyle(null);
        turnaus.lisaaOsallistuja(uusi);
        taulukkoPaivita();
        nollaa();
    }


    /*
     * poistaa valitun osallistujan jos mahdollista
     */
    private void osallistujaPoisto() {
        // id:n valinta taulukosta listChooser tyyliin(toimiiko samoin?)
        // mahdollinen varmistusikkuna
        // pistää tiedon eteenpäin turnausluokalle
        if (valittu == null)
            return;
        int pelit = 
        turnaus.laskePelit(valittu);
        if (pelit > 0) {
            Dialogs.showMessageDialog(
                    "ei onnistu, pelaajalla pelejä " + pelit + " kpl", 
                     e -> e.getDialogPane().setPrefWidth(400));
            return;
        }
        turnaus.poista(valittu);
        taulukkoPaivita();
    }


    /**
     * hakee kohdalla olevan osallistujan
     */
    public void valitse() {
        int rivi = tableTurnaus.getRowNr();
        valittu = (Osallistuja) tableTurnaus.getObject(rivi);
        // System.out.println(tableTurnaus.get(rivi, 0)+" "+
        // tableTurnaus.get(rivi, 1)+" "+tableTurnaus.get(rivi, 2));
    }


    /**
     * Asettaa syötetyt tiedot osallistujan parametreiksi
     * @param uusi luotava osallistuja
     * @return osallistuja jonka parametreinä ovat syötetyt tiedot
     */
    private Osallistuja haeTiedot(Osallistuja uusi) {

        uusi.setEtunimi(etunimi.getText());
        uusi.setSukunimi(sukunimi.getText());
        uusi.setKerho(seura.getText());
        uusi.setVahvuus(vahvuus.getText());
        uusi.setPuhnum(puhnum.getText());
        uusi.setSposti(sposti.getText());
        return uusi;
    }


    /**
     * päivittää osallistuja listaa
     */
    public void taulukkoPaivita() {
        tableTurnaus.clear();
        String ehto = haku.getText();

        for (Osallistuja osal : turnaus.etsiOsallistujat(ehto)) {
            tableTurnaus.add(osal,osal.getNimi(), osal.getVahvuus(), osal.getKerho());
        }
    }


    /*
     * Tyhjentää kentät
     */
    private void nollaa() {

        etunimi.setText("");
        sukunimi.setText("");
        seura.setText("");
        vahvuus.setText("");
        puhnum.setText("");
        sposti.setText("");

    }


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
        // turnaus.poista(); //TODO poista myöhemmässä vaiheessa
        try {
            turnaus.lueTied();
            turnaus.lueTulos();
        } catch (PoikkeusException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        alusta(tableTurnaus);

    }
    

}
