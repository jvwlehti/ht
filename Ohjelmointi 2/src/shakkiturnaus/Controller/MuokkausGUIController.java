package shakkiturnaus.Controller;




import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import shakkiturnaus.Model.Osallistuja;
import shakkiturnaus.Model.Turnaus;


/**
 * @author jvwlehti
 * @version 3.2.2021
 *
 */
public class MuokkausGUIController implements ModalControllerInterface<Osallistuja> {
      

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
        private Button muokkaa;

        
        /*
         * avaa muokkausnäkymän
         */
        @FXML
        private void handleMuokkaus() {
            //Dialogs.showMessageDialog("muokkaus in progress");
        	muokkaa();
        	ModalController.closeStage(muokkaa);
        	
        	}
 
//=====================================================================================       
        private Turnaus turnaus;
        private Osallistuja osal;
        
        /**
         * @param turnaus turnaus joka controllerille tuodaan
         */
        public void setTurnaus(Turnaus turnaus) {
        	this.turnaus = turnaus;
        }

        
        @Override
        public void handleShown() {
            // TODO Auto-generated method stub
            
        }
        
		@Override
		public Osallistuja getResult() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public void setDefault(Osallistuja oletus) {
			this.osal = oletus;
			asetaTiedot();
			
		}
		
		/**
		 * asettaa tekstipaikkoihin osallistujan tiedot
		 */
		private void asetaTiedot() {
			etunimi.setText(osal.getEtunimi());
			sukunimi.setText(osal.getSukunimi());
			seura.setText(osal.getKerho());
			vahvuus.setText(osal.getVahvuus());
			puhnum.setText(osal.getPuhnum());
			sposti.setText(osal.getSposti());
			
		}
		
		/**
		 * asettaa osallistujalle tekstipaikkojen tiedot 
		 */
		private void muokkaa() {
			osal.setEtunimi(etunimi.getText());
	        osal.setSukunimi(sukunimi.getText());
	        osal.setKerho(seura.getText());
	        osal.setVahvuus(vahvuus.getText());
	        osal.setPuhnum(puhnum.getText());
	        osal.setSposti(sposti.getText());
	        
	        turnaus.korvaa(osal);
			
		}


        
        
        
}