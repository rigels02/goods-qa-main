package org.rb.qa.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.rb.qa.model.KNBase;
import org.rb.qa.model.QA;
import org.rb.qa.service.KNBaseEditor;
import org.rb.qa.storage.InitKNBase;
import org.rb.qa.storage.KNBSelector;
import org.rb.qa.storage.StorageFactories;
import org.rb.qa.storage.jaxb.JaxbFactory;

/**
 *
 * @author raitis
 */
public class KNBSelectorTest {
    
    public KNBSelectorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
        File fi = new File(KNBSelector.propFile);
        if (fi.exists()) {
            fi.delete();
        }
        List<String> files = InitKNBaseMulti.getKnbFiles();
        for (String file : files) {
            File fi1 = new File(file);
            if (fi1.exists()) {
                fi1.delete();
            }
        }

    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

   
    @Test
    public void testWriteReadProperties(){
        File fi = new File(KNBSelector.propFile);
        if(fi.exists()){
          fi.delete();
        }
        KNBSelector knbManager = new KNBSelector();
        int idx = -1;
        try {
           idx= knbManager.getSelectedKnb();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KNBSelectorTest.class.getName()).log(Level.SEVERE, null, ex);
           fail(ex.getMessage());
        }
        assertTrue(idx==0);
        try {
            knbManager.setSelectedKnb(2);
        } catch (IOException ex) {
            Logger.getLogger(KNBSelectorTest.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Expected exception");
        }
        try {
            knbManager.setSelectedKnb(1);
        } catch (IOException ex) {
            fail(ex.getMessage());
        }
        idx = -1;
        try {
            idx= knbManager.getSelectedKnb();
        } catch (FileNotFoundException ex) {
            fail(ex.getMessage());
        }
        assertTrue(idx==1);
        assertTrue(knbManager.getTitles().size()>= 1);
        assertTrue(knbManager.getFiles().size()>= 1);
        assertTrue(knbManager.getTitles().size()== knbManager.getFiles().size());
    }
    
    @Test
    public void testIT_InitKNBase_KNBaseLoader_KNBaseSaver() throws Exception{
        System.out.println("testIT_InitKNBase_KNBaseLoader_KNBaseSaver");
        File fi = new File(KNBSelector.propFile);
        if(fi.exists()){
          fi.delete();
        }
        //with only one knb.xml file 
        //default file knb.xml has to be selected
        Map<StorageType,IStorageFactory> usedStorages = new HashMap<>();
         usedStorages.put(StorageType.Default, new JaxbFactory());
         usedStorages.put(StorageType.JaxbStorage, new JaxbFactory());
        
         StorageFactories.configFactories(usedStorages);
         
        KNBase knb = InitKNBase.go(StorageFactories.take().getFactory());
        KNBase knb1 = KNBaseEditor.take(knb).add(new QA("Question added","Answer added"));
        KNBaseSaver.take(knb1, StorageFactories.take().getFactory()).save("knb1.xml");
        KNBase knb2 = new KNBaseLoader(StorageFactories.take().getFactory()).loadFromFile("knb1.xml");
        assertTrue(knb1.getQaList().size()==knb2.getQaList().size());
        //with multiple knb.xml files
        //used knb.xml and knb_cpp.xml files
        //by default knb.xml should be selected
        KNBase knb3= InitKNBaseMulti.go(StorageFactories.take().getFactory());
        assertTrue(knb3.getQaList().size()==knb.getQaList().size()-1);
        assertTrue(InitKNBaseMulti.getKnbXML().equals("knb.xml"));
        InitKNBaseMulti.setKnbXML(1);
        KNBase knb4= InitKNBaseMulti.go(StorageFactories.take().getFactory());
        assertTrue(InitKNBaseMulti.getKnbXML().equals("knb_cpp.xml"));
        InitKNBaseMulti.setKnbXML(0);
        InitKNBaseMulti.go(StorageFactories.take().getFactory());
        assertTrue(InitKNBaseMulti.getKnbXML().equals("knb.xml"));
        fi = new File("knb1.xml");
         if(fi.exists()){
          fi.delete();
        }
    }
}
