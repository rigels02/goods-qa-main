package org.rb.qa.restful;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.rb.qa.model.KNBase;
import org.rb.qa.storage.AbstractStorageFactory;
import org.rb.qa.storage.KNBaseLoader;
import org.rb.qa.storage.KNBaseSaver;
import org.rb.qa.storage.StorageFactories;



/**
 * KNBase Restful resource (Jersey).
 * FILEPATH is default KNBase file
 * @author raitis
 */
@Path("knbase")
public class KNBaseResource implements IWriteAccessCounter{

    public static final String NOTIFIER_NAME="notifier";
    
    private static final String FILEPATH = "knb_1.xml";
    private static final String ID= "50f0f8f0-53ea-413c-b2a3-f3dc387709c0";

    //--------messages -----------//
    private static final String MSG_DWNL = "KNB has been downloaded...";   
    private static final String MSG_NOT_DWNL = "KNB was not downloaded";
    private static final String MSG_DATANULL = "KNB data == NULL!";
    private static final String MSG_POST_OK = "KNB data posted successfuly!";
    private static final String GETIDREQUESTED="ID Requested.";
    private static final String RESOURCEINIT="Resource KNBaseResource initiated...";
    private static final String DATETIME="KNB date time: ";
     private static final String KNBFILE="KNB file: ";
     
    //----------------------------//
    private INotifier notifier;
    private String ErrDetails;
    
    protected static AtomicInteger writeAccCounter = new AtomicInteger(0);
    
    @Context
    private Configuration config;
   
   
    
    
  
   
    
    /**
     * Get passed properties from server.
     * This class use notifier interface.
     * @see INotifier
     */
    @PostConstruct
    public void init(){
        System.out.println("properties: "+config.getProperties());
       notifier = (INotifier) config.getProperty(NOTIFIER_NAME);
       notify(RESOURCEINIT);
    }
    
    /**
     * Send message by using INotifier interface.
     * @see INotifier
     * @param msg 
     */
    private void notify(String msg){
      if(notifier != null){
         String timeStamp = new Date().toString();
          notifier.info(timeStamp+":\n"+msg);
      }
    }
    
    @GET
    @Path("/id")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        notify(GETIDREQUESTED);
        return ID;
    }
    
    
    /**
     * Load knb data from file by using default storage factory.
     * @param fileName
     * @return 
     */
    private KNBase loadKNBaseData(String fileName){
    KNBase knb= null; 
        try {
            //Use default Storage Factory
            
          knb=  new KNBaseLoader(StorageFactories.take().getFactory())
                    .loadFromFile(fileName);
        } catch (Exception ex) {
            Logger.getLogger(KNBaseResource.class.getName()).log(Level.SEVERE, null, ex);
            ErrDetails = ex.getMessage();
            return null;
        }
        return knb;
    }
    
    /**
     *
     * @param fileName file param in http request, can be null.
     * If null the default resource file will be taken. 
     * See field FILEPATH.
     * @return
     */
    @GET
    @Path("/knb")
    @Produces(MediaType.APPLICATION_JSON)
    public KNBase getKNBase(@QueryParam("file") String fileName){
        if(fileName==null || fileName.isEmpty()){
          fileName = FILEPATH;
        }
        KNBase knb= loadKNBaseData(fileName);
        if(knb!=null){
         
         Logger.getLogger(KNBaseResource.class.getName()).log(Level.INFO, MSG_DWNL);
            
            notify(KNBFILE+fileName+MSG_DWNL+", QA records number= "+knb.getQaList().size());
        }else {
          notify(MSG_NOT_DWNL+" : "+ErrDetails);
        }
        return knb;
    }
    
    /**
     * Get knbase with modification time
     * @param fileName
     * @return 
     */
    @GET
    @Path("/knbmt")
    @Produces(MediaType.APPLICATION_JSON)
    public KNBaseMT getKNBaseMT(@QueryParam("file") String fileName){
        if(fileName==null || fileName.isEmpty()){
          fileName = FILEPATH;
        }
        KNBase knb= loadKNBaseData(fileName);
        if(knb!=null)
         Logger.getLogger(KNBaseResource.class.getName()).log(Level.INFO, "KNB have been got...");
        
        Date modTime = null;
        try { 
            //Retrieve the modify date what has been got by last loadKNBaseData call.
            modTime = ((AbstractStorageFactory)StorageFactories.take().getFactory()).getDataModifyDate();
        }catch (Exception ex){
         Logger.getLogger(KNBaseResource.class.getName()).log(Level.SEVERE, null, ex);
            notify("Error: "+ex.getMessage());
        }
        KNBaseMT knbmt = new KNBaseMT();
        knbmt.setModifyTime(modTime);
        List<org.rb.qa.restful.QA> qas = new ArrayList<>();
        for (org.rb.qa.model.QA qa : knb.getQaList()) {
            qas.add(new QA(qa.getQuestion(), qa.getAnswer()));
        }
        knbmt.setQaList(qas);
        
        return knbmt;
    }
   
    
    
    /**
     *
     * @param knb
     * @param knbFileName file param in http request, can be null
     * @return
     */
    @POST
    @Path("/knb")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postKNBase(
             KNBase knb, 
            @QueryParam("file") String knbFileName){
        
        if(knbFileName==null || knbFileName.isEmpty()){
         knbFileName = FILEPATH;
        }
        if(knb == null){
        Logger.getLogger(KNBaseResource.class.getName()).log(Level.WARNING, MSG_DATANULL);
        return Response.notModified(MSG_DATANULL).build();
        }
        try {
            writeAccCounter.incrementAndGet();
            KNBaseSaver.take(knb, 
                    StorageFactories.take().getFactory()
                            ).save(knbFileName);
        } catch (Exception ex) {
            Logger.getLogger(KNBaseResource.class.getName()).log(Level.SEVERE, null, ex);
            writeAccCounter.decrementAndGet();
            notify("Error: "+ex.getMessage());
            return Response.notModified(ex.getMessage()).build();
        }
         writeAccCounter.decrementAndGet();
        Logger.getLogger(KNBaseResource.class.getName()).log(Level.INFO, MSG_POST_OK);
        notify(MSG_POST_OK+", QA records number= "+knb.getQaList().size());
        return Response.ok(MSG_POST_OK).build();
    }
    
    /**
     * Get xml file last modify date.
     * @param fileName file param in http request, can be null
     * @return 
     */
    @GET
    @Path("/date")
    @Produces(MediaType.APPLICATION_JSON)
    public Long getModifyTime(@QueryParam("file") String fileName){
        if(fileName==null || fileName.isEmpty()){
          fileName = FILEPATH;
        }
         Date modTime =null;
        KNBase knb = loadKNBaseData(fileName);
        if(knb == null){
            notify("Error: Requested file: "+fileName+" not found!");
            return null;
        }
        try { 
         //Retrieve the modify date what has been got by last loadKNBaseData call.   
         modTime = ((AbstractStorageFactory)StorageFactories.take().getFactory()).getDataModifyDate();
        }catch( Exception ex){
         Logger.getLogger(KNBaseResource.class.getName()).log(Level.SEVERE, null, ex);
            notify("Error: "+ex.getMessage());
        }
        
        notify(KNBFILE+fileName+DATETIME+modTime);
        return modTime.getTime();
    }
 
    
}
