
package org.rb.qa.ui.tools;

import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;


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

    public static int NumInputDialog(String headerTxt, int maxIdx, String prompTxt) {
        
        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("Input Dialog");
        dialog.setHeaderText(headerTxt);
        Label prompt = new Label(prompTxt+" 0..."+maxIdx);
        Spinner<Integer> box = new Spinner<>(0, maxIdx, 0);
        box.setEditable(true);
        GridPane grid = new GridPane();
        grid.add(prompt,1,1);
        grid.add(box, 2,1);
        dialog.getDialogPane().setContent(grid);
        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);
        
        //ObservableList<ButtonType> buttons = dialog.getDialogPane().getButtonTypes();
        dialog.setResultConverter(new Callback<ButtonType, Integer>() {
            @Override
            public Integer call(ButtonType btn) {
                if(btn.equals(buttonTypeCancel) )
                    return null;
                Integer val = box.getValue();
                return val;
            }
        });
        /**
         * Spinner box is set as editable. When i have typed digits I must to press ENTER
         * to accept the typed value. To force spinner box to accept the value on focus-lost the
         * below listener was defined.
         * Reference: https://stackoverflow.com/questions/32340476/manually-typing-in-text-in-javafx-spinner-is-not-updating-the-value-unless-user
         */
        box.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if(!newValue)
                box.increment(0); // won't change value, but will commit editor nad it
                                //forces Spinner to call it's private commitEditorText().
            }
        });
        
        // Traditional way to get the response value.
        Optional<Integer> result = dialog.showAndWait();
        if (result.isPresent()) {
           
           return result.get();
        }
        return -1;
    }
    
   
}
