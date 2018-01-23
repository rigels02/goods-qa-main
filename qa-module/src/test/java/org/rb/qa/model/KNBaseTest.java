package org.rb.qa.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author raitis
 */
public class KNBaseTest {
    
    private final static String xmlFile="xmlTest.xml";
    
    public KNBaseTest() {
    }
    
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    
    @Test
     public void testWriteReadXML() throws JAXBException, FileNotFoundException {
     
         KNBase knBase= new KNBase();
         ArrayList<QA> qaList= new ArrayList<>();
         qaList.add(new QA("Question1","Answer1"));
         qaList.add(new QA("Question2","Answer2"));
         qaList.add(new QA("Question3","Answer3"));
         knBase.setQaList(qaList);
         // create JAXB context and instantiate marshaller
        JAXBContext context = JAXBContext.newInstance(KNBase.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        
        // Write to System.out
        m.marshal(knBase, System.out);
        // Write to File
        m.marshal(knBase, new File(xmlFile));
        //-----------read
        Unmarshaller um = context.createUnmarshaller();
        KNBase knBase2 = (KNBase) um.unmarshal(new FileReader(xmlFile));
        System.out.println("Read:\n"+knBase2.toString());
     }
     
     private static final String HTMLTMPL="<!DOCTYPE html>\n" +
"<html>\n" +
"    <head>\n" +
"        <title>TODO supply a title</title>\n" +
"        <meta charset=\"UTF-8\">\n" +
"        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
"    </head>\n" +
"    <body>\n" +
"        <div>TODO write content</div>\n" +
"    </body>\n" +
"</html>\n" +
"";
     
     @Test
     public void testWriteReadXML_1() throws JAXBException, FileNotFoundException {
     
         System.out.println("testWriteReadXML_1()");
        org.rb.qa.xmlmodel.KNBase knBase = new org.rb.qa.xmlmodel.KNBase();
         ArrayList<org.rb.qa.xmlmodel.QA> qaList= new ArrayList<>();
         qaList.add(new org.rb.qa.xmlmodel.QA("Question1","Answer1"));
         qaList.add(new org.rb.qa.xmlmodel.QA("Question2","Answer2"));
         qaList.add(new org.rb.qa.xmlmodel.QA("Question3",HTMLTMPL));
         knBase.setQaList(qaList);
         // create JAXB context and instantiate marshaller
        JAXBContext context = JAXBContext.newInstance(org.rb.qa.xmlmodel.KNBase.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        
        // Write to System.out
        m.marshal(knBase, System.out);
        // Write to File
        m.marshal(knBase, new File(xmlFile));
        //-----------read
        Unmarshaller um = context.createUnmarshaller();
        org.rb.qa.xmlmodel.KNBase knBase2 = (org.rb.qa.xmlmodel.KNBase) um.unmarshal(new FileReader(xmlFile));
        System.out.println("Read:\n"+knBase2.toString());
     }
}
