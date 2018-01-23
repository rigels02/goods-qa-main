package org.rb.mm.interfaceimpl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
public class JaxbXmlParserTest {
    
     private static final String filePath="TestXml.xml";
    
    public JaxbXmlParserTest() {
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
   
    static QA[] qas = {
        new QA("Question1","Answer1"),
        new QA("Question2","Answer2"),
        new QA("Question3","Answer23")
    };
    static QA[] qas1 = {
        new QA("Question1Ā","Answer1Ā"),
        new QA("Question2ņ","Answer2ņ"),
        new QA("Question3Š","Answer3Š")
    };
   
    /**
     * Test of saveAndRead method, of class JaxbXmlParser.
     */
    @Test
    public void testSaveAndRead() throws Exception {
        System.out.println("testSaveAndRead");
        KNBase knBase= new KNBase();
         List<QA> qaList= Arrays.asList(qas);
         knBase.setQaList(qaList);
       
        JaxbXmlParser instance = new JaxbXmlParser(KNBase.class);
        instance.save(filePath, knBase);
         //-------Read ----------//
         KNBase knBase1 = (KNBase) instance.load(filePath);
         assertEquals(knBase.toString(), knBase1.toString());
        
    }

    @Test
    public void testSaveAndRead_1() throws Exception {
        System.out.println("testSaveAndRead_1");
        KNBase knBase= new KNBase();
        
        JaxbXmlParser instance = new JaxbXmlParser(KNBase.class);
        instance.save(filePath, knBase);
         //-------Read ----------//
         KNBase knBase1 = (KNBase) instance.load(filePath);
         assertEquals(knBase.toString(), knBase1.toString());
        
    }
    
    
}
