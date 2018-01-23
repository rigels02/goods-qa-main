package org.rb.qa.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.rb.qa.model.KNBase;
import org.rb.qa.utils.Resources;
import org.rb.mm.interfaces.IStorage;

/**
 *
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
        
        if( !Files.exists(Paths.get(knbXML)) ){
            Resources resources = new Resources();
            if(!InitKNBase.isAndroid()){
                resources.copyResourceToLocal("/assetsj/"+knbXML);
            }else{
                resources.copyResourceToLocal("/assets/"+knbXML);
            }
        }
        if( !Files.exists(Paths.get(knbXML)) ){
            throw new RuntimeException(knbXML+" was not copied!");
        }
        
        KNBaseLoader loader = new KNBaseLoader(storageFactory);
        return loader.loadFromFile(knbXML);
       
    }

    private InitKNBase() {
    }

    public static boolean isAndroid(){
      return System.getProperty("java.vm.name").equalsIgnoreCase("Dalvik");
    }
    
}
