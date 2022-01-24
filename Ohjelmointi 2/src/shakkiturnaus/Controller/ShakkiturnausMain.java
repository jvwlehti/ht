package shakkiturnaus.Controller;


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author lehti
 * @version 3.2.2021
 *
 */
public class ShakkiturnausMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("/shakkiturnaus/FX/AloitusGUIView.fxml"));
            final Pane root = ldr.load();
            //final ShakkiturnausGUIController shakkiturnausCtrl = (ShakkiturnausGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/shakkiturnaus/FX/shakkiturnaus.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("shakkiturnaus");
            primaryStage.show();
            //primaryStage.setOnCloseRequest(e -> Platform.exit());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei k�yt�ss�
     */
    public static void main(String[] args) {
        launch(args);
    }
}