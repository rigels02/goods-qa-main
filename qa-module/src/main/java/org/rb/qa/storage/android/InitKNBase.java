package org.rb.qa.storage.android;


import org.rb.qa.model.KNBase;
import org.rb.qa.storage.IStorageFactory;

import java.io.InputStream;

/**
 * InitKNBase adapter class for Android
 * @author raitis
 */
public class InitKNBase {
   

    private static InputStream androidResourceInputStream;


    public static KNBase go(IStorageFactory storageFactory) throws Exception{
        if(! org.rb.qa.storage.InitKNBase.isAndroid()
           || androidResourceInputStream == null){
            throw new IllegalAccessException("androidResourceInputStream= NULL, not assigned!");
        }
        KNBaseLoader loader = new KNBaseLoader(storageFactory);
        return loader.load(androidResourceInputStream);

    }
    public static KNBase go(IStorageFactory storageFactory,
                            InputStream knBase) throws Exception {
        if(! org.rb.qa.storage.InitKNBase.isAndroid()){
          throw new IllegalAccessException("Not Android jvm!");
        }
        KNBaseLoader loader = new KNBaseLoader(storageFactory);
        return loader.load(knBase);
    }


    private InitKNBase() {
    }

    /**
     * Called from Android
     * @param is knBase input stream
     */
    public static void configAndroidResource(InputStream is){
        androidResourceInputStream = is;

    }
}
