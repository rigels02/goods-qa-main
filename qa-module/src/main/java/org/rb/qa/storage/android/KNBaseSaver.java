package org.rb.qa.storage.android;

import java.io.OutputStream;
import org.rb.qa.model.KNBase;
import org.rb.qa.storage.IStorageFactory;

/**
 * Storage KNBaseSaver adapter class for Android
 * @author raitis
 */
public class KNBaseSaver {
    
    org.rb.qa.storage.KNBaseSaver saver;

    private KNBaseSaver(KNBase knBase, IStorageFactory factory){
        saver = org.rb.qa.storage.KNBaseSaver.take(knBase,factory);

    }

    public static KNBaseSaver take(KNBase knBase, IStorageFactory factory) {
        return new KNBaseSaver(knBase, factory);
    }

    /**
     * Save current KNBase data to file output stream
     * @param xmlOs file output stream passed from Android
     * @throws Exception
     */
    public void save(OutputStream xmlOs) throws Exception{

        saver.save(xmlOs);
    }
}
