package org.rb.qa.storage.simple;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import org.rb.mm.interfaceimpl.SimpleXmlParser;
import org.rb.mm.interfaces.IStorage;
import org.rb.qa.model.KNBase;
import org.rb.qa.storage.IStorageFactory;

/**
 * SimpleXml parser factory
 * @author raitis
 */
public class SimpleFactory implements IStorageFactory{
    
    private final SimpleXmlParser xmlParser;

    public SimpleFactory() {
        xmlParser = new SimpleXmlParser(org.rb.qa.storage.simple.KNBase.class);
              
    }
    
    @Override
     public org.rb.qa.model.KNBase deSerialize(String xmlFile) throws Exception {
       org.rb.qa.storage.simple.KNBase knBaseS= (org.rb.qa.storage.simple.KNBase) xmlParser.load(xmlFile);
        KNBase knb = simpleToKnBase(knBaseS);
        return knb;
    }
    
    @Override
     public void serialize(String filePath, org.rb.qa.model.KNBase knBase) throws Exception {
        org.rb.qa.storage.simple.KNBase knBaseSimple = knBaseToSimple(knBase);
          
        xmlParser.save(filePath, knBaseSimple);
     }
   
      @Override
    public IStorage getStorage() {
        return xmlParser;
    }

    protected org.rb.qa.storage.simple.KNBase knBaseToSimple(KNBase knBase) {
          org.rb.qa.storage.simple.KNBase knBaseS = new org.rb.qa.storage.simple.KNBase();
        List<org.rb.qa.storage.simple.QA> tlst = knBaseS.getQaList();
        for (org.rb.qa.model.QA qa : knBase.getQaList()) {
              org.rb.qa.storage.simple.QA nqa = new org.rb.qa.storage.simple.QA(qa.getQuestion(), qa.getAnswer());
            tlst.add(nqa);
        }
        return knBaseS;
    }
    
    protected org.rb.qa.model.KNBase simpleToKnBase(org.rb.qa.storage.simple.KNBase knBaseS) {
      org.rb.qa.model.KNBase knb = new org.rb.qa.model.KNBase();
        List<org.rb.qa.model.QA> tlst = knb.getQaList();
        for (org.rb.qa.storage.simple.QA qa : knBaseS.getQaList()) {
           org.rb.qa.model.QA nqa= new org.rb.qa.model.QA(qa.getQuestion(), qa.getAnswer());
           tlst.add(nqa);
        }
        return knb;
    }

    @Override
    public KNBase deSerialize(InputStream xmlIs) throws Exception {
    org.rb.qa.storage.simple.KNBase knBaseS= (org.rb.qa.storage.simple.KNBase) xmlParser.load(xmlIs);
        KNBase knb = simpleToKnBase(knBaseS);
        return knb;   
    }

    @Override
    public void serialize(OutputStream xmlOs, KNBase knBase) throws Exception {
     org.rb.qa.storage.simple.KNBase knBaseSimple = knBaseToSimple(knBase);
        xmlParser.save(xmlOs,knBaseSimple);  
    }

    
}
