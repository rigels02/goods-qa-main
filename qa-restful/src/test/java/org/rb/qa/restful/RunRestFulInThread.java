package org.rb.qa.restful;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author raitis
 */
public class RunRestFulInThread {

    public static void main(String[] args) {
        KNBaseServer server = new KNBaseServer(KNBaseResource.class);
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
