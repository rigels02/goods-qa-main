
package org.rb.qa.ui.tools;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 *
 * @author raitis
 */
public class Dialogs {

    public static void popupMsg(String msg) {
        final Stage myDialog = new Stage();
        myDialog.initModality(Modality.APPLICATION_MODAL);
        Button okButton = new Button("Ok");
        okButton.setOnAction((ActionEvent arg0) -> {
            myDialog.close();
        });
        
        VBox vBox = new VBox(30,new Text("Message: \n \t "+ msg), okButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10));
        Scene myDialogScene = new Scene(vBox);
        
        myDialog.setScene(myDialogScene);
        //myDialog.showAndWait();
        myDialog.show();
    }
    
   
    public static boolean popupConfirmMsg(String title, String msg) {
        boolean ok = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(msg);
        alert.setContentText("Please, accept by OK or Cancel?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        }
        return ok;
    }
    public static void popupError(String title,String msg){
    
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(msg);

        alert.show();
        
    }

    public static void showWeb(String html){
    
        Stage stage = new Stage();
        stage.setTitle("HTML");
        stage.setWidth(500);
        stage.setHeight(500);
        Scene scene = new Scene(new Group());

        VBox root = new VBox();     

        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(browser);
        webEngine.loadContent(html);

        root.getChildren().addAll(scrollPane);
        scene.setRoot(root);

        stage.setScene(scene);
        stage.show();
      
        
    }

    
}
