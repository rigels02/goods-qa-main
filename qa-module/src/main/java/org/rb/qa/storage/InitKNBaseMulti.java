package org.rb.qa.storage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.rb.qa.model.KNBase;

/**
 * Init KNBase if multiple knb.xml files are available.
 * Dependency from KNBSelector class and InitKNBase class.
 * @author raitis
 */
public class InitKNBaseMulti {
   
    private InitKNBaseMulti() {
    }
    
    public static KNBase go(IStorageFactory storageFactory) throws Exception{
        KNBSelector knbSelector = new KNBSelector();
        InitKNBase.setKnbXML(knbSelector.getSelectedFile());
        KNBase knb = InitKNBase.go(storageFactory);
        return knb;
    }
    
    /**
     * Create new KNBase file
     * @param storageFactory
     * @return
     * @throws IOException
     * @throws Exception 
     */
    public static KNBase create(IStorageFactory storageFactory) throws IOException, Exception{
        KNBSelector knbSelector = new KNBSelector();
        InitKNBase.setKnbXML(knbSelector.getSelectedFile());
        return InitKNBase.create(storageFactory);
    }
    
    public static String getKnbXML() {
        return new KNBSelector().getSelectedFile();
    }

    public static void setKnbXML(int knbXMLFileIndex) throws IOException {
        KNBSelector knbSelector = new KNBSelector();
        knbSelector.setSelectedKnb(knbXMLFileIndex);
        InitKNBase.setKnbXML(knbSelector.getSelectedFile());
        
    }
    
     public static List<String> getKnbTitles(){
       return new KNBSelector().getTitles();
    }
    public static List<String> getKnbFiles(){
      return new KNBSelector().getFiles();
    }
   
}
