package org.rb.qa.ui.qaeditview;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.inject.Inject;
import org.rb.qa.model.QA;
import org.rb.qa.service.KNBaseEditor;
import org.rb.qa.service.QAGenerator;
import org.rb.qa.ui.MainApp;
import org.rb.qa.ui.qa.QaPresenter;
import org.rb.qa.ui.tools.Dialogs;
import org.rb.qa.ui.tools.ImageCoding;

/**
 *
 * @author raitis
 */
public class QaeditviewPresenter implements Initializable {

    private ResourceBundle resources = null;
    
    @Inject
    Stage primaryStage;
    
    @Inject 
    QAGenerator qaGenerator;        
     
   //Do not use knBase injection. Instead use qaGenerator to get current selected
   //knbase file, because qaGenerator must always keep currently selected knb file.
   // @Inject
   // KNBase knBase;
    
    Scene previousScene;
    
    
    @FXML
    private TextField f_questionTxtField;

    @FXML
    private TextArea f_answerTxtArea;
    
    
    private QA editQA;
    private int questionIdx;
   

    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        
    }
    
    /**
     * 
     * @param previouseScene
     * @param qaIndex selected question index, -1 if new added
     * @param qa selected/added qa
     */
    public void initData(Scene previouseScene,int qaIndex, QA qa){
     this.previousScene= previouseScene;
     this.questionIdx=qaIndex;
     this.editQA= qa;
     
        f_questionTxtField.setText(qa.getQuestion());
        f_answerTxtArea.setText(qa.getAnswer());
     
    }
    
    //-----------------------//
     @FXML
    void onBtnCancel(ActionEvent event) {
      primaryStage.setScene(previousScene);
      primaryStage.setTitle( "QuestionsAnswers" );
      //primaryStage.show();
    }

    @FXML
    void onBtnUpdate(ActionEvent event)  {
        String answerTxt = f_answerTxtArea.getText().trim();
        QA qa = new QA(f_questionTxtField.getText().trim(),answerTxt );
        if(questionIdx== -1){
            KNBaseEditor.take(qaGenerator.getKnBase()).add(qa);
        }else {
        KNBaseEditor.take(qaGenerator.getKnBase()).add(questionIdx, qa);
        }
        try {
            MainApp.app.reloadAppDataConfig();
        } catch (Exception ex) {
            Logger.getLogger(QaeditviewPresenter.class.getName()).log(Level.SEVERE, null, ex);
            
            throw new RuntimeException(ex.getMessage());
        }
      //get QaPresenter controller reference and call   updateListView()
      ((QaPresenter)primaryStage.getScene().getUserData()).updateListView();
      primaryStage.setScene(previousScene);
      primaryStage.setTitle( "QuestionsAnswers" );
      
       
    }
    
     @FXML
    void onEmbedImage(ActionEvent event) {
        String imgPath = getImageFile(null);
        String htmlImgTag = null;
        try {
            htmlImgTag = ImageCoding.embeddedImageHtmlTag(imgPath);
        } catch (IllegalArgumentException | IOException ex) {
            Logger.getLogger(QaeditviewPresenter.class.getName()).log(Level.SEVERE, null, ex);
            Dialogs.popupError("Error", ex.getMessage());
            return;
        }
        int cursorIdx = f_answerTxtArea.getCaretPosition();
        f_answerTxtArea.insertText(cursorIdx, htmlImgTag);
    }
    
    private String getImageFile(String title){
    if(title==null || title.isEmpty()){
          title = "Select Image File";
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        Stage stage = new Stage();
        File selection = fileChooser.showOpenDialog(stage);
        System.out.println("Selection = "+selection.getPath());
        return selection.getPath();
    }
}
