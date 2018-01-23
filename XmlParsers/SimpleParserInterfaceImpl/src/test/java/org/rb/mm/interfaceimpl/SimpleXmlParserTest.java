package org.rb.mm.interfaceimpl;

import java.io.File;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.rb.mm.interfaceimpl.model.KNBase;
import org.rb.mm.interfaceimpl.model.QA;

/**
 *
 * @author raitis
 */
public class SimpleXmlParserTest {

    private static final String filePath="TestXml.xml";
    
    public SimpleXmlParserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
        File fi=null;
       
       if((fi=new File(filePath)).exists()){
         fi.delete();
       }
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    static final String HTMLTPL="<!DOCTYPE html>\n" +
"<html>\n" +
"    <head>\n" +
"        <title>TODO supply a title</title>\n" +
"        <meta charset=\"UTF-8\">\n" +
"        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
"    </head>\n" +
"    <body>\n" +
"        <div>TODO write content</div>\n" +
"    </body>\n" +
"</html>";
    
    /**
     * Test of save method, of class SimpleXmlParser.
     */
    @Test
    public void testSaveAndRead() throws Exception {
        System.out.println("testSaveAndRead");
       
       KNBase knBase= new KNBase();
         ArrayList<QA> qaList= new ArrayList<>();
         qaList.add(new QA("Question1Ā","Answer1Ā"));
         qaList.add(new QA("Question2ņ","Answer2ņ"));
         qaList.add(new QA("Question3Š",HTMLTPL));
         knBase.setQaList(qaList);
        SimpleXmlParser instance = new SimpleXmlParser(KNBase.class);
        // WHEN
        instance.save(filePath, knBase);
       //----- now read from file ---//
       SimpleXmlParser instance1 = new SimpleXmlParser(KNBase.class);
        //And WHEN
        KNBase knBase1 =  (KNBase) instance1.load(filePath);
        //THEN
        assertEquals(knBase.toString(), knBase1.toString());
    }

     @Test
    public void testSaveAndRead_1() throws Exception {
    System.out.println("testSaveAndRead_1");
        
       KNBase knBase= new KNBase();
         
        SimpleXmlParser instance = new SimpleXmlParser(KNBase.class);
        // WHEN
        instance.save(filePath, knBase);
       //----- now read from file ---//
       
        //And WHEN
        KNBase knBase1 =  (KNBase) instance.load(filePath);
        //THEN
        assertEquals(knBase.toString(), knBase1.toString());
    }
    
}
