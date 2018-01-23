package org.rb.qa.service;

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.rb.qa.loader.KNBaseLoader;
import org.rb.qa.model.KNBase;
import org.rb.qa.model.QA;
import org.rb.qa.xmlmodel.XmlFactory;

/**
 *
 * @author raitis
 */
public class QAGeneratorTest {
    
    public QAGeneratorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        knBase = new KNBase();
        for(int i=1; i<=6; i++){
        knBase.getQaList().add(new QA("Question"+i, "Answer"+i));
        }
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

    private static KNBase knBase;
    /**
     * Test of getAllQuestions method, of class QAGenerator.
     */
    @Test
    public void testGetAllQuestions() {
        System.out.println("getAllQuestions");
        //GIVEN:
        QAGenerator instance = new QAGenerator(knBase);
        instance.setUseConverter(false);
        Map<Integer, String> expResult = new HashMap<>();
        for(int i=0; i< knBase.getQaList().size();i++){
           expResult.put(i, knBase.getQaList().get(i).getQuestion());
        }
        //WHEN:
        Map<Integer, String> result = instance.getAllQuestions();
        //THEN:
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getAnserforQuestionByIndex method, of class QAGenerator.
     */
    @Test
    public void testGetAnswerforQuestionByIndex() {
        System.out.println("testGetAnswerforQuestionByIndex");
        //GIVEN:
        int index = 3;
        QAGenerator instance = new QAGenerator(knBase);
        instance.setUseConverter(false);
        String expResult = knBase.getQaList().get(index).getAnswer();
        //WHEN:
        String result = instance.getAnswerforQuestionByIndex(index);
        //THEN:
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getRandomQuestions method, of class QAGenerator.
     */
    @Test
    public void testGetRandomQuestions() {
        System.out.println("getRandomQuestions");
        //GIVEN:
        QAGenerator instance = new QAGenerator(knBase);
        instance.setUseConverter(false);
        Map<Integer, String> expResult = null;
        
        for(int i=1;i<=knBase.getQaList().size();i++){
        int questNumber = i;
        //WHEN:
        Map<Integer, String> result = instance.getRandomQuestions(questNumber);
        //THEN:
        assertTrue(result.size()==questNumber);
        System.out.println("Result:\n"+result);
        }
    }
    
    @Test
    public void testKNBaseEditor(){
        System.out.println("testKNBaseEditor()");
        KNBase workBase = KNBaseEditor.take(knBase).makeCopy();
        KNBase result = KNBaseEditor.take(workBase).add(new QA("Q1","A1"));
        assertEquals(result, workBase);
        int idx = KNBaseEditor.take(workBase).findIndexByQuestion("Q1");
        KNBaseEditor.take(workBase).add(idx,new QA("Q2", "A2"));
         KNBaseEditor.take(workBase).delete(idx);
         assertEquals(result, workBase);
         workBase.getQaList().forEach(qa-> System.out.println("->"+qa.toString()));
    }

    @Test
    public void testKNBaseLoadSave() throws Exception{
        System.out.println("testKNBaseLoadSave()");
        String knbXMLPath= "testDir/knb.xml";
        String knbXMLPath1= "testDir/knb1.xml";
        //GIVEN:
        KNBaseLoader loader = new KNBaseLoader(XmlFactory.take().getXmlParser());
        KNBase knBase1 = loader.loadFromFile(knbXMLPath);
        //JaxbXMLSerializer serializer = new JaxbXMLSerializer();
        //-------------------//
        //WHEN:
        KNBaseSaver.take(knBase1,XmlFactory.take().getXmlParser()).save(knbXMLPath1);
        //THEN:
        loader = new KNBaseLoader(XmlFactory.take().getXmlParser());
        KNBase knBase2 = loader.loadFromFile(knbXMLPath1);
        assertEquals(knBase1.toString(), knBase2.toString());
    }
    
}
