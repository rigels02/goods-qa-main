package org.rb.qa.restful;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.simple.SimpleContainerFactory;
import org.glassfish.jersey.simple.SimpleServer;
import org.rb.qa.storage.IStorageFactory;
import org.rb.qa.storage.StorageFactories;
import org.rb.qa.storage.StorageType;

/**
 * Run simple container server to expose Jersey RestFul service.
 * 
 * @author raitis
 * @param <T> resource class
 */
public class KNBaseServer<T> {

    //private Server jettyServer;
    private final Class<T> resource;
    private SimpleServer server;
    private Map<String, Object> properties;
    
   

    public KNBaseServer(Class<T> resource) {
        this.resource = resource;
    }
    
        public void setStorageFactories(Map<StorageType, IStorageFactory> usedStorages){
        /**    
        HashMap<StorageType, IStorageFactory> usedStorages = new HashMap<>();
         usedStorages.put(StorageType.Default, StorageFactories.take().getFactory());
        **/
         StorageFactories.configFactories(usedStorages);
        }
        
        /**
         * Pass properties to Restful resource class.
         * <pre>
         * Example: 
         *
         * HashMap&lt;String, Object&gt; props = new HashMap();
         * props.put("notifier", notifier);
         * server.setProperties(props);
         * </pre>
         * @param props 
         */
        public void setProperties(Map<String, Object> props){
            this.properties = props;
        }
        
        /**
         * Start server
         */
        public void runServer(){
            System.out.println("KNBaseServer starting ........");
            //setStorageFactories();
            URI baseUri = UriBuilder.fromUri("http://localhost/").port(9998).build();
        ResourceConfig resourceConfig = new ResourceConfig(resource);
        if(properties != null){
            resourceConfig.setProperties(properties);
        }
        
            
        //server = JdkHttpServerFactory.createHttpServer(baseUri, config);
            
        try {
            server = SimpleContainerFactory.create(baseUri, resourceConfig);
               
            // jettyServer = JettyHttpContainerFactory.createServer(baseUri, config);
            //jettyServer.start();
            
        } catch (Exception ex) {
            Logger.getLogger(KNBaseServer.class.getName()).log(Level.SEVERE, null, ex);
        }
            System.out.println("SERVER RUNNING: "+baseUri.getHost()+" : "+baseUri.getPort()+
                               "\n=================================");
        }
        
        /**
         * Stop server
         */
        public void stopServer(){
            System.out.println("Server stopping.....");
       
        Type sclazz = resource.getGenericSuperclass();
        Class<?> chkResource = resource;
        if(sclazz.getTypeName().contains("KNBaseResource")){
           chkResource = KNBaseResource.class;
        }
        Class<?>[] itfs = chkResource.getInterfaces();
        boolean callItfMethod=false;
            for (Class<?> itf : itfs) {
                if(itf.getName().contains("IWriteAccessCounter")){
                    callItfMethod = true;
                    break;
                }
            }
            
         if(callItfMethod){
               
             while(true){
                 int val = IWriteAccessCounter.getWriteAccCounter();
                  System.out.println("Val= "+val);
                  if(val==0){
                        break;
                    }
                 try {
                     Thread.sleep(1000L);
                 } catch (InterruptedException ex) {
                     Logger.getLogger(KNBaseServer.class.getName()).log(Level.SEVERE, null, ex);
                     break;
                 }
               }
         }   
        
            
            try {
                server.close();
                //jettyServer.stop();
            } catch (IOException ex) {
                Logger.getLogger(KNBaseServer.class.getName()).log(Level.SEVERE, null, ex);
            
            }
            System.out.println("SERVER STOPPED!");
        }
    
}
