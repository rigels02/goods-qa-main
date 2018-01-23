package org.rb.qa.storage;

import org.rb.mm.interfaces.IStorage;
import org.rb.qa.model.KNBase;

/**
 *
 * @author raitis
 */
public interface IStorageFactory {
    
   
     KNBase deSerialize(String xmlFile) throws Exception;

     void serialize(String filePath, KNBase knBase) throws Exception;
     
     public IStorage getStorage();
    
}
