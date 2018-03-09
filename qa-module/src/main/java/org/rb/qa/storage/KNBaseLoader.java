package org.rb.qa.storage;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.rb.qa.model.KNBase;




/**
 * Uses XmlFactory to get application's xml deserializer
 * <pre>
 * !!! For Android use : load(InputStream is) only
 * </pre>
 * For Android use adapted classes from package: android
 * 
 * @author raitis
 */
public class KNBaseLoader {

   private static final Logger LOG = Logger.getLogger(KNBaseLoader.class.getName());
    
    private KNBase knBase;
    private IStorageFactory deserializerFactory;
    
    public KNBaseLoader(IStorageFactory factory) {
        deserializerFactory  = factory; //Get XML Deserializer
        this.knBase = new KNBase();
       // this.knBase.setQaList(new LinkedList<>());
       this.knBase.setQaList(new ArrayList<>());
    }
   
   
    private void load(String xmlFile) throws Exception{
      
        //KNBase knBase1 = (KNBase) deserializer.load(xmlFile);
        KNBase knBase1 = (KNBase) deserializerFactory.deSerialize(xmlFile);
        this.knBase.getQaList().addAll(knBase1.getQaList());
    }
    
    public KNBase loadByFiles(List<File> files) throws Exception{
        
        for (File file : files) {
            try {
                //load(file.getAbsolutePath());
                 load(file.getPath());
            } catch (Exception ex) {
                Logger.getLogger(KNBaseLoader.class.getName()).log(Level.SEVERE, null, ex.getMessage());
                throw new RuntimeException(ex.getMessage());
            }
        }
        return knBase;
    }
    
    public KNBase loadByFileNames(List<String> fileList) throws Exception{
        for (String filename : fileList) {

            if( ! new File(filename).exists()){
               throw new RuntimeException("File: "+filename+" not exists!");
            }
            try {
                load(filename);
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, null, ex.getMessage());
                throw new RuntimeException(ex.getMessage());
            }
        }
        return knBase;
    }

    
    public KNBase loadFromFile(String fileName) throws Exception {
        List<String> flist = new ArrayList<>();
        flist.add(fileName);
        return loadByFileNames(flist);
    }

    public KNBase loadFromStream(InputStream is) throws Exception {
        KNBase knBase1 = (KNBase) deserializerFactory.deSerialize(is);
        knBase.getQaList().clear();
        this.knBase.getQaList().addAll(knBase1.getQaList());
        return knBase;
    }
     
}
