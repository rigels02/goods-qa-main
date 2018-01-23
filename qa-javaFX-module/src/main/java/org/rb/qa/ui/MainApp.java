package org.rb.qa.ui;

import com.airhacks.afterburner.injection.Injector;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.rb.mm.interfaces.IStorage;
import org.rb.qa.storage.StorageFactories;
import org.rb.qa.storage.InitKNBase;
import org.rb.qa.storage.KNBaseSaver;
import org.rb.qa.model.KNBase;
import org.rb.qa.service.QAGenerator;
import org.rb.qa.storage.IStorageFactory;
import org.rb.qa.storage.StorageType;
import org.rb.qa.storage.jaxb.JaxbFactory;
import org.rb.qa.storage.simple.SimpleFactory;
import org.rb.qa.ui.qa.QaView;



public class MainApp extends Application implements IReloadAppDataConfig{

    public static MainApp app;
    
    private KNBase knBase;
    private QAGenerator qaGenerator;
    private Stage primaryStage;
    private QaView mainView;
    private Map<StorageType,IStorageFactory> usedStorages;
    
    private void initAppDataConfig() throws Exception{
    
        
        knBase = InitKNBase.go(StorageFactories.take().getFactory());
        //By adding new QA or modify existing QA the modifcation is going
        //to be saved. As result this initAppDataConfig() is called to update
        //application data
        //As the old QaPresenter still will be used therefore its member field 
        //qaGenerator must be updated with new KNBase member field reference,
        //because old is not valid anymore.
        if(qaGenerator==null){
        qaGenerator = new QAGenerator(knBase);
        }else{
          qaGenerator.setKnBase(knBase);
        }
        /*
         * Properties of any type can be easily injected.
         */
        Map<Object, Object> context = new HashMap<>();
        //context.put( "noteService", new InMemoryNoteService() );
        //context.put("knBaseEditor",knBaseEditor);
        //context.put("knBaseSaver",knBaseSaver);
        context.put("knBase",knBase);
        context.put("qaGenerator",qaGenerator);
        context.put("primaryStage",primaryStage);
        context.put("backScene",null);
        Injector.setConfigurationSource( context::get );
        
        /**
        if(mainView!=null){
          ((QaPresenter)mainView.getPresenter()).updateQAGenerator(qaGenerator);
        }
        */
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
         MainApp.app= this;
         this.primaryStage= primaryStage;
         usedStorages= new HashMap<>();
         usedStorages.put(StorageType.Default, new JaxbFactory());
         usedStorages.put(StorageType.JaxbStorage, new JaxbFactory());
         usedStorages.put(StorageType.SimpleStorage, new SimpleFactory());
         StorageFactories.configFactories(usedStorages);
        initAppDataConfig();
        
        mainView = new QaView();
        
        Scene scene = new Scene( mainView.getView() );
        // scene.getStylesheets().add("/styles/Styles.css");
        primaryStage.setTitle( "QuestionsAnswers" );
        primaryStage.setScene( scene );
        primaryStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void reloadAppDataConfig() throws Exception{
        
        KNBaseSaver.take(knBase, StorageFactories.take().getFactory()).save(InitKNBase.knbXML);
        
        initAppDataConfig();
    }

}
