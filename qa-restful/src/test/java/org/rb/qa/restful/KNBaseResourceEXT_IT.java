package org.rb.qa.restful;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
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

/**
 * Server RunKNBaseServerExt is running automatic for test
 * @author raitis
 */
public class KNBaseResourceEXT_IT {

    private static WebTarget webTarget;
    private static String host = "http://localhost:9998/knbase";
    private static Client client;
    private static RunKNBaseServerExt server;
    
    public KNBaseResourceEXT_IT() {
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
        
      server = new RunKNBaseServerExt(KNBaseResourceExt.class);
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

    
    @Test
    public void testPostKnBase_And_Stop_Server_During_Post() throws InterruptedException, ExecutionException{
        
        
        class PostTask implements Callable<Response>{
                KNBase knb;

            public PostTask(KNBase knb) {
                this.knb = knb;
            }
                
            @Override
            public Response call() throws Exception {
               Response response = webTarget.path("/knb").queryParam("file", "knb_3.xml")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(knb));
               return response;
            }
        
        }
        
        System.out.println("testPostKnBase_And_Stop_Server_During_Post()");
        
        //----------Start server ----------//
        
        server.startServerExt();
        Thread.sleep(500L);
        //------------//
        System.out.println("test: get /knb");
         KNBase knb = webTarget.path("/knb")
                 .request(MediaType.APPLICATION_JSON)
                .get(KNBase.class);
         assertTrue(knb != null);
         System.out.println("test: post /knb");
        
        FutureTask<Response> futureTask = new FutureTask<>(new PostTask(knb));
        new Thread(futureTask).start();
        Thread.sleep(1000L);
         //stop server after 1 sec
         server.stopServerExt();
        Response response = futureTask.get();
          assertTrue(response.getStatus()==Status.OK.getStatusCode());
         
        
    }
    
    @Test
    public void testTwoGetTaskThreadsRunning_And_ServerStopedDuringRunning() throws InterruptedException, ExecutionException{
    
        class GetTask implements Callable<KNBase>{
                
            @Override
            public KNBase call() throws Exception {
               KNBase knb = webTarget.path("/knb_test")
                 .request(MediaType.APPLICATION_JSON)
                .get(KNBase.class);
               return knb;
            }
        
        }
        
        System.out.println("testTwoGetTaskThreadsRunning_And_ServerStoppedDuringRunning()");
        //----------Start server ----------//
        RunKNBaseServerExt server = new RunKNBaseServerExt(KNBaseResourceExt.class);
        server.startServerExt();
        Thread.sleep(1000L);
        //------------//
         FutureTask<KNBase> futureTask1 = new FutureTask<>(new GetTask());
         FutureTask<KNBase> futureTask2 = new FutureTask<>(new GetTask());
         new Thread(futureTask1).start();
         Thread.sleep(300L);
         new Thread(futureTask2).start();
         Thread.sleep(500L);
         server.stopServerExt();
        KNBase knb1 = futureTask1.get();
         KNBase knb2 = futureTask2.get();
         assertTrue(knb1.getQaList().size()==knb2.getQaList().size());
    }
    
    
}
