package org.rb.qa.storage;



import org.rb.qa.model.KNBase;

/**
 * Use XmlFactory to get concrete XML serializer implementation for app.
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
    
   
}
