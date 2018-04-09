package org.rb.qa.restful;

import java.util.HashMap;
import org.rb.qa.restful.model.JaxbFactory;
import org.rb.qa.storage.IStorageFactory;
import org.rb.qa.storage.StorageType;

/**
 * Server Runner for test KNBaseResourceExt
 * @author raitis
 */
public class RunKNBaseServerExt extends KNBaseServer{

    
    public RunKNBaseServerExt(Class resource) {
        super(resource);
        System.out.println("Server ready to start.....");
    }
    
    public  void startServerExt(){
    
        HashMap<StorageType, IStorageFactory> usedStorages = new HashMap<>();
        usedStorages.put(StorageType.Default, new JaxbFactory());
        super.setStorageFactories(usedStorages);   
         
     super.runServer();
    }
    
    public  void stopServerExt(){
      super.stopServer();
    }
}
