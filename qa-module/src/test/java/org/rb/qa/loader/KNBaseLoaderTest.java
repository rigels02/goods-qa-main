package org.rb.qa.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.rb.qa.model.KNBase;
import org.rb.qa.model.QA;
import org.rb.qa.xmlmodel.XmlFactory;

/**
 *
 * @author raitis
 */
public class KNBaseLoaderTest {
    
   
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
    @Test
    public void testWriteRead() throws JAXBException, FileNotFoundException, IOException{
    
        KNBase base = new KNBase();
        base.setQaList(new ArrayList<QA>());
        //GIVEN:
        base.getQaList().add(new QA("Question31", "Answer31"));
        base.getQaList().add(new QA("Question32", "Answer32"));
        base.getQaList().add(new QA("Question33", "Answer33"));
        JAXBContext context = JAXBContext.newInstance(KNBase.class);
        Marshaller ma = context.createMarshaller();
        ma.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        //WHEN:
        final String testFile= "testDir/testwr.xml";
        ma.marshal(base, new File(testFile));
        //THEN:
        assertTrue(Files.exists(Paths.get(testFile)));
        List<String> lst = Files.readAllLines(Paths.get(testFile));
        
        assertTrue(lst.size()>0);
        for (String string : lst) {
            System.out.println("->>"+string);
        }
       
        //--------Read
        //Given: file
        Unmarshaller um = context.createUnmarshaller();
        //WHEN:
        KNBase base1 = (KNBase) um.unmarshal(new File(testFile));
        //THEN:
        assertTrue(base1.toString().equals(base.toString()));
        
        
    }
    /**
     * Test of loadByFiles method, of class KNBaseLoader.
     */
    @Test
    public void testLoadByFiles() throws Exception {
        System.out.println("loadByFiles");
        //Given:
        //Two xml files in resource assets
        ClassLoader classLoader = getClass().getClassLoader();
        URL result = classLoader.getResource("assetsj");
        File dir= new File(result.toURI());
        File[] files = dir.listFiles();
        List<File> afiles = Arrays.asList(files);
        //When: 
        KNBaseLoader instance = new KNBaseLoader(XmlFactory.take().getXmlParser());
       
        KNBase knBase = instance.loadByFiles(afiles);
        System.out.println("Result:\n"+knBase);
        //Then:
        //assertTrue(knBase.getQaList().size()==6);
    }

   

    /**
     * Test of loadByDir method, of class KNBaseLoader.
     */
    @Test
    public void testLoadByDir() throws Exception {
        System.out.println("loadByDir");
        //Given:
        //testDir with 2 files , 3 QA nodes each
        String sourceDirectory = "testDir/loaddir";
        KNBaseLoader instance = new KNBaseLoader(XmlFactory.take().getXmlParser());
        //When:
        KNBase result = instance.loadByDir(sourceDirectory);
        //assertEquals(expResult, result);
        System.out.println("Result:\n"+result);
        //Then:
        assertTrue(result.getQaList().size()==6);
    }
    
    @Test
    public void PerformanceLinkedList(){
      List <Long> timeDiffs= new ArrayList<>();
        //TODO
      KNBase base = new KNBase();
        //base.setQaList(new LinkedList<>());
        base.setQaList(new ArrayList<QA>());
        final int num= 10000;
        final int count=0;
        final int loops= 100;
        Random random = new Random();
        
        for(int k=1; k<=loops;k++){
         QA result=null;
        long start1 = System.currentTimeMillis();
        for(int i=1; i<=num;i++){
        base.getQaList().add(new QA("Question"+i, "Answer"+i));
        }
        for(int i=1; i<=num;i++){
            int idx= random.nextInt(num);
          result = base.getQaList().get(idx);
        }
          long diff = System.currentTimeMillis()-start1;
        timeDiffs.add(diff);
        System.out.println("#"+k+" Time Delta= "+diff);
        }
        long summ=0;
        int sz = timeDiffs.size();
        for (long timeDiff : timeDiffs) {
            summ= summ+timeDiff;
        }
        System.out.println("==============\nAverage time Diff= "+summ/sz);
    }
}
