package org.rb.qa.restful;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.rb.qa.model.KNBase;
import org.rb.qa.model.QA;

/**
 * Before run this test run Server RunRestFul
 * @author raitis
 */
public class KNBaseResourceIT {

    private static WebTarget webTarget;
    private static String host = "http://localhost:9998/knbase";
    private static Client client;
    
    public KNBaseResourceIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        ClientConfig clientConfig = new ClientConfig()
                .property(ClientProperties.READ_TIMEOUT, 30000)
                .property(ClientProperties.CONNECT_TIMEOUT, 5000);
 
        client = ClientBuilder
                .newClient(clientConfig);
        webTarget= client
                //.register(new LoggingFilter())
                .target(host);
    }
    
    @AfterClass
    public static void tearDownClass() {
        client.close();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    

    /**
     * Test of getKNBase method, of class KNBaseResource.
     */
    @Test
    public void testGetKNBase() {
        System.out.println("getKNBase");
        KNBase knb = webTarget.path("/knb").request(MediaType.APPLICATION_JSON)
                .get(KNBase.class);
        assertTrue(knb != null);
        assertTrue( !knb.getQaList().isEmpty() );
        
    }

    /**
     * Test of getModifyTime method, of class KNBaseResource.
     */
    @Test
    public void testGetModifyTime() {
        System.out.println("getModifyTime");
        Date modifyDate = webTarget.path("/date").request(MediaType.APPLICATION_JSON)
                .get(Date.class);
        assertTrue(modifyDate != null);
        System.out.println("time= "+modifyDate);
    }

    /**
     * Test of postKNBase method, of class KNBaseResource.
     */
    @Test
    public void testPostKNBase() {
        KNBase knb = new KNBase();
        knb.setQaList(Arrays.asList(new QA[]{
            new QA("Question 1Ņ"," Answer 1Ņ"),
             new QA("Question 2Ņ"," Answer 2Ņ"),
              new QA("Question 3Ņ"," Answer 3Ņ")
        }));
        System.out.println("postKNBase");
        Response response = webTarget.path("/knb").queryParam("file", "knb_2.xml")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(knb));
        assertTrue(response.getStatus()==Status.OK.getStatusCode());
        KNBase knb1 = webTarget.path("/knb").queryParam("file", "knb_2.xml")
                .request(MediaType.APPLICATION_JSON)
                .get(KNBase.class);
        assertTrue(knb1 != null);
        System.out.println("knb1 = "+knb1);
        knb1 = webTarget.path("/knb").queryParam("file", "knb99.xml")
                .request(MediaType.APPLICATION_JSON)
                .get(KNBase.class);
        assertTrue(knb1 == null);
    }
    
    @Test
    public void testPostKnBaseBig(){
        System.out.println("testPostKnBaseBig()");
         KNBase knb = webTarget.path("/knb")
                 .request(MediaType.APPLICATION_JSON)
                .get(KNBase.class);
         assertTrue(knb != null);
         Response response = webTarget.path("/knb").queryParam("file", "knb_3.xml")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(knb));
          assertTrue(response.getStatus()==Status.OK.getStatusCode());
        KNBase knb1 = webTarget.path("/knb").queryParam("file", "knb_3.xml")
                .request(MediaType.APPLICATION_JSON)
                .get(KNBase.class);
        assertTrue(knb1 != null);
        
    }
    
    class GetKNbaseTask implements Callable<KNBase>{

        @Override
        public KNBase call() throws Exception {
            System.out.println("ENTERING GetKNbaseTask.call()...Thread: "+
                    Thread.currentThread().getName()+" : "+Thread.currentThread().getId()+
                    " Time: "+System.currentTimeMillis()+" ms"); 
           KNBase knb = webTarget.path("/knb").request(MediaType.APPLICATION_JSON)
                .get(KNBase.class);
           
           System.out.println("EXITING GetKNbaseTask.call()...Thread: "+
                    Thread.currentThread().getName()+" : "+Thread.currentThread().getId()+
                   " Time: "+System.currentTimeMillis()+" ms"); 
           return knb;
        }
    
    }
    
    @Test
    public void testGetKNBaseMultiThreads() throws InterruptedException {
        System.out.println("testGetKNBaseMultiThreads");
        int threadNumber =10;
        ExecutorService service = Executors.newFixedThreadPool(threadNumber);
        List<GetKNbaseTask> getKnbTasks= new ArrayList<>();
        List<Future<KNBase>> futuresList ;
        List<KNBase> results = new ArrayList<>();
        
        for(int i=1; i<= threadNumber; i++){
        getKnbTasks.add(new GetKNbaseTask());
        }
        futuresList = service.invokeAll(getKnbTasks);
        
        for (Future<KNBase> future : futuresList) {
            try {
                results.add(future.get());
            } catch (ExecutionException ex) {
                Logger.getLogger(KNBaseResourceIT.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       service.shutdownNow();
        assertTrue(results.size()==10);
        
    }
    
    class BPostTasks implements Callable<Response>{
        KNBase knb;

        public BPostTasks(KNBase knb) {
            this.knb = knb;
        }
        
        @Override
        public Response call() throws Exception {
            System.out.printf("BPostTasks.call()...Thread: %s : %s, Time= %s ms\n",
                    Thread.currentThread().getName(),Thread.currentThread().getId(),
                    System.currentTimeMillis()); 
             Response post =null;
            try { 
            post = webTarget.path("/knb").queryParam("file", "knb_3.xml")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(knb));
            }catch(Exception ex){
             Logger.getLogger(BPostTasks.class.getName()).log(Level.SEVERE, null, ex);
            }
            return post;
        }
    
    }
    
    class BGetTasks implements Callable<KNBase>{

        @Override
        public KNBase call() throws Exception {
          System.out.printf("BGetTasks.call()...Thread: %s : %s, Time= %s ms\n",
                    Thread.currentThread().getName(),Thread.currentThread().getId(),
                    System.currentTimeMillis());  
            KNBase get = null;
            try {
            get = webTarget.path("/knb").queryParam("file", "knb_3.xml")
                    .request(MediaType.APPLICATION_JSON)
                    .get(KNBase.class);
            }catch (Exception ex){
            Logger.getLogger(BGetTasks.class.getName()).log(Level.SEVERE, null, ex);
            }
            return get;
        }
    
    }
    
    
    @Test
    public void testPostKnBaseBigThreads(){
        System.out.println("testPostKnBaseBig()");
         KNBase knb = webTarget.path("/knb")
                 .request(MediaType.APPLICATION_JSON)
                .get(KNBase.class);
         assertTrue(knb != null);
         
         int threadNumber =10;
        ExecutorService service = Executors.newFixedThreadPool(threadNumber);
        List<BPostTasks> posKnbTasks= new ArrayList<>();
         List<BGetTasks> getKnbTasks= new ArrayList<>();
        List<Future<Response>> postFuturesList=null ;
        List<Future<KNBase>> getFuturesList=null ;
        List<Response> postResults = new ArrayList<>();
        List<KNBase> getResults = new ArrayList<>();
        
        for(int i=1; i<= threadNumber/2; i++){
        posKnbTasks.add(new BPostTasks(knb));
        getKnbTasks.add(new BGetTasks());
        }
        try {
            postFuturesList = service.invokeAll(posKnbTasks);
            getFuturesList = service.invokeAll(getKnbTasks);
        } catch (InterruptedException ex) {
            Logger.getLogger(KNBaseResourceIT.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (Future<Response> future : postFuturesList) {
            try {
                
                postResults.add(future.get());
            } catch (ExecutionException | InterruptedException ex) {
                Logger.getLogger(KNBaseResourceIT.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (Future<KNBase> future : getFuturesList) {
            try {
                getResults.add(future.get());
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(KNBaseResourceIT.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
 
        assertTrue(postResults.size()==5);
        assertTrue(getResults.size()==5);
        assertTrue(getResults.get(0).toString().equals(knb.toString()));
        for (Response postResult : postResults) {
            assertTrue(postResult.getStatus()==200);
            postResult.close();
        }
        service.shutdown();
    }
    
    @Test
    /**
     * Found thread unsafety for concurrent load/save in Module JaxbParserInterfaceImpl 
     * in class org.rb.mm.interfaceimpl.JaxbXmlParser. 
     * About 50% fails.
     * If define load/save methods as synchronized then test passed.
     */
    public void testPostGetWithMultiThreads_StressTest(){
        System.out.println("testPostGetWithMultiThreads_StressTest()");
         KNBase knb = webTarget.path("/knb")
                 .request(MediaType.APPLICATION_JSON)
                .get(KNBase.class);
         assertTrue(knb != null);
         
         int threadNumber =10;
        ExecutorService service = Executors.newFixedThreadPool(threadNumber);
        List<Callable> knbTasks= new ArrayList<>();
         List<Future> futuresList=new ArrayList<>() ;
        
        List<Response> postResults = new ArrayList<>();
        List<KNBase> getResults = new ArrayList<>();
        
        for(int i=1; i<= threadNumber/2; i++){
        knbTasks.add(new BPostTasks(knb));
        knbTasks.add(new BGetTasks());
        }
        try {
            for (Callable knbTask : knbTasks) {
                Future future = service.submit(knbTask);
                futuresList.add(future);
            }
 
        } catch (Exception ex) {
            Logger.getLogger(KNBaseResourceIT.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (Future future : futuresList) {
            try {
                Object obj = future.get();
               if( obj instanceof Response){
                postResults.add((Response) obj);
               }
               if( obj instanceof KNBase){
                getResults.add((KNBase) obj);
               }
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(KNBaseResourceIT.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        assertTrue(postResults.size()==5);
        assertTrue(getResults.size()==5);
        assertTrue(getResults.get(0).toString().equals(knb.toString()));
        for (Response postResult : postResults) {
            assertTrue(postResult.getStatus()==200);
            postResult.close();
        }
        service.shutdownNow();
    }
    
   
}
