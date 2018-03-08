package org.rb.qa.storage.android;

import java.io.InputStream;
import org.rb.qa.model.KNBase;
import org.rb.qa.storage.IStorageFactory;

/**
 * storage KNBaseLoader adapter class to use in Android
 * @author raitis
 */
public class KNBaseLoader {
    
    org.rb.qa.storage.KNBaseLoader loader;

    public KNBaseLoader(IStorageFactory factory){
        loader = new org.rb.qa.storage.KNBaseLoader(factory);

    }

    public KNBase load(InputStream is) throws Exception{
       return loader.loadFromStream(is);
    }
}
