package org.rb.qa.storage;

import java.io.InputStream;
import java.io.OutputStream;

import org.rb.mm.interfaces.IStorage;
import org.rb.qa.model.KNBase;

/**
 *
 * Template (abstract class) for concrete StorageFactory class. 
 * T is the same as org.rb.qa.model.KNBase but with specific fields annotations
 * and located in separate package.
 * The reason why StorageFactory has been introduced is requirement to use different
 * xml parsers with the same QA model. For example, SimpleXml and JAXBXml parsers.
 * Both parsers use different annotations, therefore, each might have owning 
 * different model classes to serialize/deserialize the same KNBase data of QA model.
 * A class extending AbstractStorageFactory class before serialize/deserialize 
 * must convert QA model knBase data to/from its own model classes by implementation
 * of abstract methods:
 * <pre>
 *  T knBaseToTypeT(KNBase knBase)
 *  org.rb.qa.model.KNBase typeTtoKnBase(T knBaseS)
 * </pre>
 * @author raitis
 * @param <T> KNBase model, for example, org.rb.qa.storage.simple.KNBase for SimpleXmlParser
 */
public abstract class AbstractStorageFactory<T> implements IStorageFactory{
    
    private final IStorage<T> ioStorage;
    

    public AbstractStorageFactory(IStorage<T> storage) {
       // xmlParser = new SimpleXmlParser(org.rb.qa.storage.simple.KNBase.class);
        ioStorage =  storage;
       
    }
    
    @Override
     public org.rb.qa.model.KNBase deSerialize(String xmlFile) throws Exception {
       T knBaseS= (T) ioStorage.load(xmlFile);
        KNBase knb = typeTtoKnBase(knBaseS);
        return knb;
    }
    
    @Override
     public void serialize(String filePath, org.rb.qa.model.KNBase knBase) throws Exception {
       T knBaseSimple = knBaseToTypeT(knBase);
          
        ioStorage.save(filePath, knBaseSimple);
     }
   
      @Override
    public IStorage getStorage() {
        return ioStorage;
    }

    /**
     * Convert org.​rb.​qa.​model.KNBase to T .
     * T is the same as KNBase but with specific fields annotations.
     * <pre>
     * Example:
     * --------
     * 
     * protected org.rb.qa.storage.simple.KNBase knBaseToTypeT(KNBase knBase) {
     *    org.rb.qa.storage.simple.KNBase knBaseS = new org.rb.qa.storage.simple.KNBase();
     *    List<org.rb.qa.storage.simple.QA> tlst = knBaseS.getQaList();
     *    for (org.rb.qa.model.QA qa : knBase.getQaList()) {
     *         org.rb.qa.storage.simple.QA nqa = new org.rb.qa.storage.simple.QA(qa.getQuestion(), qa.getAnswer());
     *       tlst.add(nqa);
     *    }
     *    return knBaseS;
     * }
     * </pre>
     * @param knBase org.​rb.​qa.​model.KNBase
     * @return T , example: org.rb.qa.storage.simple.KNBase
     */
    protected abstract T knBaseToTypeT(KNBase knBase);
    
    
    /**
     * Convert T KNBase to org.​rb.​qa.​model.KNBase.
     * T is the same as KNBase but with specific fields annotations.
     * <pre>
     * Example:
     * --------
     * 
     * protected org.rb.qa.model.KNBase typeTtoKnBase(org.rb.qa.storage.simple.KNBase knBaseS) {
     *   org.rb.qa.model.KNBase knb = new org.rb.qa.model.KNBase();
     *   List<org.rb.qa.model.QA> tlst = knb.getQaList();
     *   for (org.rb.qa.storage.simple.QA qa : knBaseS.getQaList()) {
     *      org.rb.qa.model.QA nqa= new org.rb.qa.model.QA(qa.getQuestion(), qa.getAnswer());
     *      tlst.add(nqa);
     *   }
     *   return knb;
     * }
     * </pre>
     * 
     * @param knBaseS T
     * @return org.​rb.​qa.​model.KNBase
     */
    protected abstract org.rb.qa.model.KNBase typeTtoKnBase(T knBaseS);
   
    
    
    @Override
    public KNBase deSerialize(InputStream xmlIs) throws Exception {
        T knBaseS= (T) ioStorage.load(xmlIs);
        KNBase knb = typeTtoKnBase(knBaseS);
        return knb;   
    }

    @Override
    public void serialize(OutputStream xmlOs, KNBase knBase) throws Exception {
     T knBaseSimple = knBaseToTypeT(knBase);
        ioStorage.save(xmlOs,knBaseSimple);  
    }

    
}
