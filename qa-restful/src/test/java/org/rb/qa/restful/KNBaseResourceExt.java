package org.rb.qa.restful;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.rb.qa.model.KNBase;

/**
 *
 * @author raitis
 */
@Path("knbase")
@Singleton
public class KNBaseResourceExt extends KNBaseResource{

   
    
     @GET
    @Path("/id")
    @Produces(MediaType.TEXT_PLAIN)
     @Override
    public String getIt() {
        return super.getIt();
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
     @Override
    public KNBase getKNBase(@QueryParam("file") String fileName){
        
        return super.getKNBase(fileName);
    }
    
    /**
     * Get knbase with modification time
     * @param fileName
     * @return 
     */
    @GET
    @Path("/knbmt")
    @Produces(MediaType.APPLICATION_JSON)
     @Override
    public KNBaseMT getKNBaseMT(@QueryParam("file") String fileName){
        
        return super.getKNBaseMT(fileName);
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
    @Override
    public Response postKNBase( KNBase knb, 
            @QueryParam("file") String knbFileName) {
        System.out.println("postKNBase .... Entered .....");
       
        writeAccCounter.incrementAndGet();
        Response response = super.postKNBase(knb, knbFileName);
       
        try {
            Thread.sleep(20000L);
        } catch (InterruptedException ex) {
            Logger.getLogger(KNBaseResourceExt.class.getName()).log(Level.SEVERE, null, ex);
            writeAccCounter.decrementAndGet();
            return response;
        }
        System.out.println("postKNBase .... Exiting .....");
        writeAccCounter.decrementAndGet() ;
        return response;
    }
    
   
    /**
     * 
     * @param fileName file param in http request, can be null
     * @return 
     */
    @GET
    @Path("/date")
    @Produces(MediaType.APPLICATION_JSON)
     @Override
    public Date getModifyTime(@QueryParam("file") String fileName){
        
        return super.getModifyTime(fileName);
    }
    
    @GET
    @Path("/knb_test")
    @Produces(MediaType.APPLICATION_JSON)
    public KNBase getKNBase_1(@QueryParam("file") String fileName){
        long delay=3000L;
        if(writeAccCounter.get()>1)
            delay = 2000L; 
        writeAccCounter.incrementAndGet();
        
         KNBase knb = super.getKNBase(fileName);
         try {
             System.out.println("Thread: "+Thread.currentThread().getId());
            
             Thread.sleep(delay);
             
         } catch (InterruptedException ex) {
             Logger.getLogger(KNBaseResourceExt.class.getName()).log(Level.SEVERE, null, ex);
             writeAccCounter.decrementAndGet();
         }
         writeAccCounter.decrementAndGet();
         return knb;
    }
}
