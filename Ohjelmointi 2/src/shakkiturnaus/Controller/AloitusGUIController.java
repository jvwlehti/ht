package shakkiturnaus.Controller;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import shakkiturnaus.Model.Turnaus;

/**
 * @author lehti
 * @version 12.2.2021
 *
 */
public class AloitusGUIController implements ModalControllerInterface<String>{


    @FXML
    private Button aloita;

    @FXML
    private Button jatka;
    
    /*
     * hakee käyttäjän syöttämän turnauksen
     */
    @FXML
    void handleAloita() {
        nollaa();
        ModalController.showModal(AloitusGUIController.class.getResource("/shakkiturnaus/FX/ShakkiturnausGUIView.fxml"), "Rekisteröinti", null, turnaus);
    }
    
    @FXML
    void handleJatka() {
        ModalController.showModal(AloitusGUIController.class.getResource("/shakkiturnaus/FX/ShakkiturnausGUIView.fxml"), "Rekisteröinti", null, turnaus);
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setDefault(String arg0) {
        // TODO Auto-generated method stub
        
    }
    //===================================
    private Turnaus turnaus = new Turnaus();
    
    /**
     * tyhjentää tiedostot
     */
    public void nollaa() {
        turnaus.poista();
    }
}
