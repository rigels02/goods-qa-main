package org.rb.qa.ui.restful;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.rb.qa.restful.INotifier;
import org.rb.qa.restful.KNBaseResource;
import org.rb.qa.restful.KNBaseServer;

/**
 *
 * @author raitis
 */
public class RestfulPresenter implements Initializable, INotifier {

    private ResourceBundle resources = null;
    
    
    @FXML
    private Button btnStart;

    @FXML
    private Button btnStop;

    @FXML
    private TextArea txtReport;

    @FXML
    private Button btnClose;
    
    
    private Stage parentStage;
    private Stage thisStage;
    private KNBaseServer server;
    private Thread serverThread;

   
    /**
     * Notifier info received/called from Restful resource methods
     * @param msg message received from Restful resource
     * @return 
     */
    @Override
    public String info(String msg) {
        txtReport.appendText(msg+"\n");
        return msg;
    }
   
    private enum Mode {
     ServerRun,
     ServerStopped
    };
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        
    }
    
    
    
    @FXML
    void onBtnStart(ActionEvent event) {
        setButtonsStatus(Mode.ServerRun);
        server.resetStatusInfo();
        serverThread= new Thread(() -> {
            server.runServer();
        });
        serverThread.start();
        while(server.getStatusInfo()==null){
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                Logger.getLogger(RestfulPresenter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
        txtReport.appendText(server.getStatusInfo()+"\n");
        
        }

    @FXML
    void onBtnStop(ActionEvent event) {
        setButtonsStatus(Mode.ServerStopped);
        server.stopServer();
        try {
            serverThread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(RestfulPresenter.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtReport.appendText(server.getStatusInfo()+"\n");
    }
    
     @FXML
    void onBtnClose(ActionEvent event) {

        //close and put into garbage
        thisStage.close();
    }

    public void initData(Stage parentStage, Stage thisStage) {
        this.parentStage = parentStage;
        this.thisStage = thisStage;
        setButtonsStatus(Mode.ServerStopped);
        //instantiate server with Restful resource KNBaseResource.class
        server = new KNBaseServer(KNBaseResource.class);
        Map<String,Object> prop= new HashMap<>();
        prop.put(KNBaseResource.NOTIFIER_NAME, this);
        server.setProperties(prop);
        
    }
    
   
    
    private void setButtonsStatus(Mode mode){
        switch(mode){
            case ServerRun:
                btnStart.setDisable(true);
                btnClose.setDisable(true);
                btnStop.setDisable(false);
                break;
            case ServerStopped:
                btnStart.setDisable(false);
                btnClose.setDisable(false);
                btnStop.setDisable(true);
                break;
        }
    }
}
