package org.rb.qa.restful;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;
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
    private String statusInfo;
   

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
            statusInfo=null;
            String ip=null;
        try {
            //setStorageFactories();
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(KNBaseServer.class.getName()).log(Level.SEVERE, null, ex);
        }
            URI baseUri = UriBuilder.fromUri("http://"+ip+"/").port(9998).build();
        ResourceConfig resourceConfig = new ResourceConfig(resource);
        /**
         * To avoid error: 
         * 'MessageBodyWriter not found for media type=application/json'
         * register JakcsonFeature class from package: org.​glassfish.​jersey.​jackson
         * <pre>
         * <dependency>
         *   <groupId>org.glassfish.jersey.media</groupId>
         *   <artifactId>jersey-media-json-jackson</artifactId>
         *   <version>2.26</version>
         *   <type>jar</type>
         *</dependency>
         * </pre>
         * 
         */
        //resourceConfig.register(JacksonFeature.class);
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
            statusInfo= ex.getMessage()+"\n";
        }
         String status = "SERVER RUNNING: "+baseUri.getHost()+" : "+baseUri.getPort();
         statusInfo= (statusInfo==null)?"":statusInfo;
         System.out.println(status + "\n=================================");
            statusInfo+= status;
        }
        
        /**
         * Stop server
         */
        public void stopServer(){
            System.out.println("Server stopping.....");
        statusInfo=null;
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
        
            String status="";
            try {
                server.close();
                //jettyServer.stop();
            } catch (IOException ex) {
                Logger.getLogger(KNBaseServer.class.getName()).log(Level.SEVERE, null, ex);
                status= ex.getMessage()+"\n";
            }
            String stopMsg= "SERVER STOPPED!";
            System.out.println(status + stopMsg );
            statusInfo = status+stopMsg;
        }

    public String getStatusInfo() {
        
        return statusInfo;
    }
    public void resetStatusInfo(){
     statusInfo = null;
    }
        
   
}
