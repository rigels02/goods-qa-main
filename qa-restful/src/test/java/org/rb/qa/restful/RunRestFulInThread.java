package org.rb.qa.restful;

import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.rb.qa.restful.model.JaxbFactory;
import org.rb.qa.storage.IStorageFactory;
import org.rb.qa.storage.StorageType;


/**
 *
 * @author raitis
 */
public class RunRestFulInThread {

    public static void main(String[] args) {
        KNBaseServer server = new KNBaseServer(KNBaseResource.class);
        
        /***
        HashMap<StorageType, IStorageFactory> usedStorages = new HashMap<>();
         usedStorages.put(StorageType.Default, new JaxbFactory());
         server.setStorageFactories(usedStorages);
         ***/
        
        Thread thr = new Thread(new Runnable() {
            @Override
            public void run() {
                server.runServer();
            }
        });
        //server.runServer();
        thr.start();
        System.out.println("Restful running ........<enter> to stop");
        new Scanner(System.in).hasNextLine();
        server.stopServer();
        try {
            thr.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(RunRestFulInThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("SERVER STOPPED !");
    }
    
}
