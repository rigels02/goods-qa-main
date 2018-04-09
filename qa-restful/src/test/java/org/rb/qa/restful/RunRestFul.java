package org.rb.qa.restful;




import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import org.rb.qa.restful.model.JaxbFactory;
import org.rb.qa.storage.IStorageFactory;
import org.rb.qa.storage.StorageType;

/**
 *
 * @author raitis
 */
public class RunRestFul {

   
    
    public static void main(String[] args) throws IOException {
        //-----Init -------//
        
        KNBaseServer server = new KNBaseServer(KNBaseResource.class);
        HashMap<StorageType, IStorageFactory> usedStorages = new HashMap<>();
         usedStorages.put(StorageType.Default, new JaxbFactory());
         server.setStorageFactories(usedStorages);
        /**
        HashMap<String, Object> props = new HashMap<>();
        props.put("notifier", notifier);
        server.setProperties(props);
        ***/
       
        server.runServer();
        System.out.println("Restful running ........<enter> to stop");
        new Scanner(System.in).hasNextLine();
        server.stopServer();
        System.out.println("SERVER STOPPED !");
    }

}
