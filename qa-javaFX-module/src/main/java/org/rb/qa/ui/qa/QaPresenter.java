package org.rb.qa.ui.qa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import org.rb.qa.model.KNBase;
import org.rb.qa.storage.StorageType;
import org.rb.qa.storage.KNBaseSaver;
import org.rb.qa.model.QA;
import org.rb.qa.service.DocType;
import org.rb.qa.service.KNBaseEditor;
import org.rb.qa.service.QAGenerator;
import org.rb.qa.storage.InitKNBaseMulti;
import org.rb.qa.ui.MainApp;
import org.rb.qa.ui.qaeditview.QaeditviewPresenter;
import org.rb.qa.ui.qaeditview.QaeditviewView;
import org.rb.qa.ui.tools.Dialogs;
import org.rb.qa.storage.StorageFactories;
import org.rb.qa.storage.jaxb.JaxbFactory;
import org.rb.qa.ui.restful.RestfulPresenter;
import org.rb.qa.ui.restful.RestfulView;

/**
 *
 * @author raitis
 */
public class QaPresenter implements Initializable {

    private ResourceBundle resources = null;
    
    @Inject
    Stage primaryStage;
    @Inject
     QAGenerator qaGenerator;
     
     
   //-------
     @FXML
    private Label fmainInfo;

    @FXML
    private ListView<String> fquestionView;

     
    @FXML
    private WebView fanswerView;

     
   
    private Image icon_html;
    private Image icon_md;
     private int totalQuestions;
    private int htmlQuestion;
    private int mdQuestions;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
       
        icon_html= new Image(getClass().getResourceAsStream("/images/icons8-html-48.png"));
         icon_md= new Image(getClass().getResourceAsStream("/images/icons8-markdown-48.png"));
        updateListView();
        fquestionView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("clicked on "+ fquestionView.getSelectionModel().getSelectedIndex()
                        + " Item: "+fquestionView.getSelectionModel().getSelectedItem());
                updateAnswerView(fquestionView.getSelectionModel().getSelectedIndex());
            }

            
        });
        fquestionView.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(fquestionView.getSelectionModel().getSelectedIndex()<0)
                    return;
                if(event.getCode() == KeyCode.ENTER){
                    updateAnswerView(fquestionView.getSelectionModel().getSelectedIndex());
                }
            }
        });
        countDocs();
    }
    
    private void countDocs() {
        
        htmlQuestion = 0;
        mdQuestions = 0;
        Date lastModified = ((JaxbFactory)StorageFactories.take().getFactory()).getDataModifyDate();
        totalQuestions = qaGenerator.getKnBase().getQaList().size();
        String selectedKnb = String.format("%s(%s)", InitKNBaseMulti.getKnbXML(),InitKNBaseMulti.getKnbTitle());
        for (QA qa : qaGenerator.getKnBase().getQaList()) {
            DocType type = qaGenerator.getAnswerDocType(qa.getAnswer());
            //type == DocType.HTML
            switch(type){
                case HTML:
                    htmlQuestion++;
                    break;
                case Markdown:
                    mdQuestions++;
                    break;
                    
            }
        }
        fmainInfo.setText(String.format("Total Questions:%d, HTML= %d, MD= %d, Modified: %s, Selected: %s",
                totalQuestions, htmlQuestion, mdQuestions,lastModified,selectedKnb));
    }
    
    public void updateListView(){
        
        List<String> questions = qaGenerator.getAllQuestionsList();
        ObservableList<String> olist= FXCollections.observableList(questions);
        fquestionView.setItems(olist);
        
        fquestionView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>(){
                   
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty); 
                      if(empty){
                          setText(null);
                          setGraphic(null);
                      }else {
                            
                            DocType type = qaGenerator.getAnswerDocTypeForQuestion(item);
                            
                            if(type==DocType.HTML){
                                ImageView rect = new ImageView(icon_html);
                                resizeImageView(rect);
                                setGraphic(rect);
                               
                            }else if(type==DocType.Markdown){
                                 ImageView rect = new ImageView(icon_md);
                                 resizeImageView(rect);
                                setGraphic(rect);
                               
                            }
                            setText(item);
                            
                      }
                  
                    }

                    private void resizeImageView(ImageView rect) {
                        
                        //rect.setFitHeight(rect.getFitHeight() / 2);
                        //rect.setFitWidth(rect.getFitWidth() / 2);
                        rect.setFitWidth(24);
                        rect.setPreserveRatio(true);
                        
                    }
                   
                };
            }
        });
        fquestionView.refresh();
        updateAnswerView(fquestionView.getSelectionModel().getSelectedIndex());
        countDocs();
     }
    
    private void updateAnswerView(int selectedItemIdx) {
        if(selectedItemIdx< 0) {
            fanswerView.getEngine().loadContent("");
            return;
        }
                String answer = qaGenerator.getAnswerforQuestionByIndex(selectedItemIdx);
                fanswerView.getEngine().loadContent(answer);
            }
    
    
    //===========================//
    
    @FXML
    void onBtnAdd(ActionEvent event) {
       
        /**
       fquestionView.getItems().add("Put question here...");
        
        fquestionView.getSelectionModel().selectLast();
         if(fquestionView.getSelectionModel().getSelectedIndex()<0) return; //??
         */
         takeSelectedAndStartEditView(true);
    }

    @FXML
    void onBtnDelete(ActionEvent event) {
        if(fquestionView.getSelectionModel().getSelectedIndex()<0) return;
        int idx = fquestionView.getSelectionModel().getSelectedIndex();
        boolean ok = Dialogs.popupConfirmMsg("Confirm", 
                          "Delete selected Question record?\nQ= "+
                                  qaGenerator.getQuestionByIndex(idx));
        if(!ok) return;
        KNBaseEditor.take(qaGenerator.getKnBase()).delete(idx);
        try {
            MainApp.app.reloadAppDataConfig();
        } catch (Exception ex) {
            Logger.getLogger(QaPresenter.class.getName()).log(Level.SEVERE, null, ex);
        }
        updateListView();
    }

    @FXML
    void onBtnMove(ActionEvent event) {
        if(fquestionView.getSelectionModel().getSelectedIndex()<0) return;
        int idx = fquestionView.getSelectionModel().getSelectedIndex();
        int maxIdx = qaGenerator.getAllQuestionsList().size()-1;
        //TODO
        int newIdx = Dialogs.NumInputDialog("Select position to move on", maxIdx, "Input pos number");
         //System.out.println("Result = "+newIdx);
         if(newIdx == -1)
             return;
         
        try {
            KNBaseEditor.take(qaGenerator.getKnBase()).moveItem(idx, newIdx);
            MainApp.app.reloadAppDataConfig();
        } catch (Exception ex) {
            Logger.getLogger(QaPresenter.class.getName()).log(Level.SEVERE, null, ex);
            Dialogs.popupError("Error", ex.getMessage());
        }
        updateListView();
    }
    
    private void takeSelectedAndStartEditView(boolean addNew){
    Scene oldScene = primaryStage.getScene();
        QaeditviewView editView = new QaeditviewView();
        QaeditviewPresenter qeditPresenter = (QaeditviewPresenter)editView.getPresenter();
        int idx= -1;
        QA qa;
        if(!addNew){
            idx = fquestionView.getSelectionModel().getSelectedIndex();
            qaGenerator.setUseConverter(false); //switch off converter
            qa = qaGenerator.getQuestionAnswer(idx);
            qaGenerator.setUseConverter(true); //switch on
        }else{
            
           qa = new QA(String.format("Q%d. Put Question here...", (totalQuestions+1)),"Put Answer here...");
        }
        qeditPresenter.initData(oldScene,idx,qa);
        Scene scene = new Scene( editView.getView() );
        //keep QaPresenter reference
        scene.setUserData(this);
        // scene.getStylesheets().add("/styles/Styles.css");
        primaryStage.setTitle( "Edit Question Answer" );
        primaryStage.setScene( scene );
        primaryStage.show();
    }

    @FXML
    void onBtnEdit(ActionEvent event) {
            if(fquestionView.getSelectionModel().getSelectedIndex()<0) return;
        takeSelectedAndStartEditView(false);
            
    }

    
    @FXML
    void onExportKNBase(ActionEvent event) throws Exception{
        File file = saveFile("Select file to save");
        if(file==null) return;
        try {
            KNBaseSaver.take(
                    qaGenerator.getKnBase(), StorageFactories.take().getFactory()
                    )
                    .save(file.getPath());
        } catch (JAXBException | FileNotFoundException ex) {
            Logger.getLogger(QaPresenter.class.getName()).log(Level.SEVERE, null, ex);
            Dialogs.popupError("Error", ex.getMessage());
           return;
        }
        Dialogs.popupMsg("File "+file.getName()+" saved!");
        
    }
    
     @FXML
    void onExportBySimpleXml(ActionEvent event) {
        File file = saveFile("Select file to save");
        if(file==null) return;
        try {
            KNBaseSaver.take(
                    qaGenerator.getKnBase(), StorageFactories.use(StorageType.SimpleStorage).getFactory()
                    )
                    .save(file.getPath());
          
        } catch (Exception ex) {
            Logger.getLogger(QaPresenter.class.getName()).log(Level.SEVERE, null, ex);
            Dialogs.popupError("Error", ex.getMessage());
            return;
        }
         Dialogs.popupMsg("File "+file.getName()+" saved!");
    }

    private File saveFile(String title){
    if(title==null || title.isEmpty()){
          title = "Output File";
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open "+ title);
        Stage stage = new Stage();
        File selection = fileChooser.showSaveDialog(stage);
        System.out.println("Selection = "+selection);
        return selection;
    }
    
    
      @FXML
    void onRestfulServer(ActionEvent event) {
        Scene oldScene = primaryStage.getScene();
        Stage server = new Stage(StageStyle.UTILITY);
        //Disable close button
        server.setOnCloseRequest((WindowEvent e) -> {
            e.consume();
        });
        server.initModality(Modality.APPLICATION_MODAL);
        
        RestfulView restfulView = new RestfulView();
        RestfulPresenter restfulPresenter = (RestfulPresenter)restfulView.getPresenter();
        
        restfulPresenter.initData(primaryStage,server);
        Scene scene = new Scene( restfulView.getView() );
        //keep QaPresenter reference
        //scene.setUserData(this);
        // scene.getStylesheets().add("/styles/Styles.css");
        server.setTitle( "Restful Server" );
        server.setScene( scene );
        server.show();
    
    }
    
    
    @FXML
    void onPreferencesSelected(ActionEvent event) {
        List<String> titles = InitKNBaseMulti.getKnbTitles();
        List<String> files = InitKNBaseMulti.getKnbFiles();
        int selected = InitKNBaseMulti.getKnbIdx();
        List<String> selectionList = new ArrayList<>();
        for (int i=0; i< titles.size(); i++) {
            selectionList.add(String.format("%s (%s)", titles.get(i),files.get(i)));
        }
        
        ChoiceDialog<String> dialog = new ChoiceDialog<>(selectionList.get(selected),selectionList);
        dialog.setTitle("Selecte KNB file");
        dialog.setHeaderText("Choice KNB:");
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()){
            System.out.println("file selected: "+result.get());
            String choosen = result.get();
            int idx = selectionList.indexOf(choosen);
            try {
                InitKNBaseMulti.setKnbXML(idx);
            } catch (IOException ex) {
                Logger.getLogger(QaPresenter.class.getName()).log(Level.SEVERE, null, ex);
                Alert errDlg = new Alert(Alert.AlertType.ERROR);
                errDlg.setContentText("Error to save selection: "+ex.getMessage());
                errDlg.showAndWait();
                return;
            }
            KNBase knBase= null;
            try {
                //TODO load selected KNB data
              knBase =  InitKNBaseMulti.go(StorageFactories.take().getFactory());
            } catch (Exception ex) {
                Logger.getLogger(QaPresenter.class.getName()).log(Level.SEVERE, null, ex);
                Alert errDlg = new Alert(Alert.AlertType.ERROR);
                errDlg.setContentText("Error to load knBase: "+ex.getMessage());
                errDlg.showAndWait();
                return;
            }
            qaGenerator.setKnBase(knBase);
            updateListView();
        }
    }

    @FXML
    void onQuit(ActionEvent event) {
        Platform.exit();
    }  
}
