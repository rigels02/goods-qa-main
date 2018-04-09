package org.rb.qa.storage;

import java.io.InputStream;
import java.io.OutputStream;
import org.rb.mm.interfaces.IStorage;
import org.rb.qa.model.KNBase;

/**
 * XML storage factory interface.
 * Details in AbstractStorageFactory.
 * @see AbstractStorageFactory
 * @author raitis
 */
public interface IStorageFactory {
    
   
     KNBase deSerialize(String xmlFile) throws Exception;

     KNBase deSerialize(InputStream xmlIs) throws Exception;

     void serialize(String filePath, KNBase knBase) throws Exception;

     void serialize(OutputStream xmlOs, KNBase knBase) throws Exception;

     public IStorage getStorage();
    
}
