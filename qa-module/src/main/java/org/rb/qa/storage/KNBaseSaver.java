package org.rb.qa.storage;



import java.io.OutputStream;
import org.rb.qa.model.KNBase;

/**
 * Use XmlFactory to get concrete XML serializer implementation for app.
 * For Android use :   save(OutputStream xmlOs).
 * For Android use adapted classes from package: android
 * @author raitis
 */
public class KNBaseSaver {

    public static KNBaseSaver take(KNBase knBase, IStorageFactory factory) {
        return new KNBaseSaver(knBase, factory);
    }

    private  KNBase knBase;
    private final IStorageFactory serializeFactory; 

    private KNBaseSaver(KNBase knBase, IStorageFactory factory) {
        this.knBase= knBase;
        this.serializeFactory = factory;
       // convertKNBaseBeforeSave();
        
    }
    
    
    
    public void save(String filePath) throws Exception {
       
        serializeFactory.serialize(filePath, knBase);
      
    }
    
    /**
     * Serialize knBase data to file output stream
     * @param xmlOs file output stream
     * @throws Exception
     */
    public void save(OutputStream xmlOs) throws Exception {

        serializeFactory.serialize(xmlOs, knBase);

    }
   
}
