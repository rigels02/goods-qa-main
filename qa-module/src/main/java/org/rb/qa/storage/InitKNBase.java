package org.rb.qa.storage;

import java.io.File;
import java.io.IOException;
import org.rb.qa.model.KNBase;
import org.rb.qa.utils.Resources;

/**
 *
 * For Android use adapted classes from package: android
 * @author raitis
 */
public class InitKNBase {

    public final static String knbXML = "knb.xml";
    
    
    /**
     * Check knbXML file existence in application local directory
     * If its not there then copy knbXML file from Resource folder 
     * (also from jar file).
     * Then load KNBase data from knbXML file.
     * @param storageFactory
     * @return KNBase
     * @throws IOException 
     */
    public static KNBase go(IStorageFactory storageFactory) throws Exception {
        
        //if( !Files.exists(Paths.get(knbXML)) ){
        if( ! new File(knbXML).exists() ){
            Resources resources = new Resources();
            if(InitKNBase.isAndroid()) {
                throw new IllegalAccessException("Illegal method call from Android!");
            }
                resources.copyResourceToLocal("/assetsj/"+knbXML);
            
        }
        if( !new File(knbXML).exists() ){
            throw new RuntimeException(knbXML+" was not copied!");
        }
        
        KNBaseLoader loader = new KNBaseLoader(storageFactory);
        return loader.loadFromFile(knbXML);
       
    }

    /**
     * Create new KNBase file
     * @param storageFactory
     * @return
     * @throws IOException
     * @throws Exception 
     */
    public static KNBase create(IStorageFactory storageFactory) throws IOException, Exception{
        boolean file = new File(knbXML).createNewFile();
        KNBase knBase = new KNBase();
        KNBaseSaver.take(knBase, storageFactory).save(knbXML);
        KNBase kb = new KNBaseLoader(storageFactory).loadFromFile(knbXML);
        return kb;
    }
    
    private InitKNBase() {
    }

    public static boolean isAndroid(){
      return System.getProperty("java.vm.name").equalsIgnoreCase("Dalvik");
    }
    
}
