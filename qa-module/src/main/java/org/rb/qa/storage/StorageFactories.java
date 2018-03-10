package org.rb.qa.storage;


import java.util.Map;


/**
 * Application scope Xml factory for serializer/deserializer factories
 * @author raitis
 */
public class StorageFactories {

    static Map<StorageType,IStorageFactory> factories;
   
     
    /**
     * Before use of StorageFactories the factories map must be init by this method.
     * Example:
     * <pre>
     * usedStorages= new HashMap();
     * usedStorages.put(StorageType.JaxbStorage, new JaxbFactory());
     * </pre>
     * @param mfatories 
     */
    public static void configFactories(Map<StorageType, IStorageFactory> mfatories) {
       
        factories = mfatories;
    }
     
    
     private IStorageFactory factory;
     
    /**
     * Take default factory
     * @return 
     */
    public static StorageFactories take() {
        return new StorageFactories();
    }
    /**
     * Use this type storage factory.
     * The factory of this type must be configured and available in factories
     * map.
     * @param type factory type
     * @return 
     */
    public static StorageFactories use(StorageType type){
        return new StorageFactories(type);
    }
    

    private StorageFactories() {
        
        this.factory = factories.get(StorageType.Default);
    }
    
    private StorageFactories(StorageType type){
      
    
      this.factory = factories.get(type);
      
    }

    /**
     * Get storage factory by type
     * @param storageType StorageType.Default, StorageType.JaxbStorage etc.
     * @return 
     */
   public IStorageFactory getFactory(StorageType storageType){
      return factories.get(storageType);
   }
   
   /**
    * Get default storage factory
    * @return 
    */
   public IStorageFactory getFactory(){
      return this.factory;
   }
}


