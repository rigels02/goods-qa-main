package org.rb.qa.storage;

import java.io.IOException;
import java.util.List;
import org.rb.qa.model.KNBase;

/**
 * Init KNBase if multiple knb.xml files are available.
 * Manage KNBase file selection.
 * Dependency from KNBSelector and InitKNBase classes.
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
    
    /**
     * Get selected KNBase title.
     * @return 
     */
    public static String getKnbTitle(){
      return new KNBSelector().getSelectedTitle();
    }
    
    /**
     * Get selected KNBase file name.
     * @return 
     */
    public static String getKnbXML() {
        return new KNBSelector().getSelectedFile();
    }
    public static int getKnbIdx(){
     return new KNBSelector().getSelectedIdx();
    }
    /**
     * Select KNBase file by index.
     * The allowed indexes are bounded into the List of KnbTitles or KnbFiles.
     * To get the list use methods  getKnbTitles() or getKnbFiles().
     * @see getKnbTitles()
     * @see getKnbFiles()
     * @param knbXMLFileIndex
     * @throws IOException 
     */
    public static void setKnbXML(int knbXMLFileIndex) throws IOException {
        KNBSelector knbSelector = new KNBSelector();
        knbSelector.setSelectedKnb(knbXMLFileIndex);
        InitKNBase.setKnbXML(knbSelector.getSelectedFile());
        
    }
    
    /**
     * Get all available KNBase files titles.
     * @return 
     */
     public static List<String> getKnbTitles(){
       return new KNBSelector().getTitles();
    }
     /**
      * Get all available KNBase files filename.
      * @return 
      */
    public static List<String> getKnbFiles(){
      return new KNBSelector().getFiles();
    }
   
}
