package org.rb.qa.storage.jaxb;

import java.io.InputStream;
import java.io.OutputStream;
import org.rb.qa.storage.IStorageFactory;
import java.util.List;
import org.rb.mm.interfaceimpl.JaxbXmlParser;
import org.rb.mm.interfaces.IStorage;
import org.rb.qa.model.KNBase;

/**
 *
 * JAXB parser factory
 * @author raitis
 */
public class JaxbFactory implements IStorageFactory  {

    private final JaxbXmlParser xmlParser;

    public JaxbFactory() {
         xmlParser= new JaxbXmlParser(org.rb.qa.storage.jaxb.KNBase.class);
             
    }
    
    @Override
     public KNBase deSerialize(String xmlFile) throws Exception {
        org.rb.qa.storage.jaxb.KNBase knBaseJaxb = (org.rb.qa.storage.jaxb.KNBase) xmlParser.load(xmlFile);
        KNBase knb = jaxbToKnBase(knBaseJaxb);
        return knb;
    }

    @Override
    public void serialize(String filePath, KNBase knBase) throws Exception {
        org.rb.qa.storage.jaxb.KNBase knBaseJaxb = knBaseToJaxb(knBase);
        xmlParser.save(filePath, knBaseJaxb);
    }

    @Override
    public IStorage getStorage() {
        return xmlParser;
    }
     
    
  
    private org.rb.qa.storage.jaxb.KNBase knBaseToJaxb(KNBase knBase){
        org.rb.qa.storage.jaxb.KNBase knBaseJaxb = new org.rb.qa.storage.jaxb.KNBase();
        List<org.rb.qa.storage.jaxb.QA> tlst = knBaseJaxb.getQaList();
        for (org.rb.qa.model.QA qa : knBase.getQaList()) {
            org.rb.qa.storage.jaxb.QA nqa = new org.rb.qa.storage.jaxb.QA(qa.getQuestion(), qa.getAnswer());
            tlst.add(nqa);
        }
        return knBaseJaxb;
    }
    
   
    private org.rb.qa.model.KNBase jaxbToKnBase(org.rb.qa.storage.jaxb.KNBase knBaseJaxb) {
      org.rb.qa.model.KNBase knb = new org.rb.qa.model.KNBase();
        List<org.rb.qa.model.QA> tlst = knb.getQaList();
        for (org.rb.qa.storage.jaxb.QA qa : knBaseJaxb.getQaList()) {
           org.rb.qa.model.QA nqa= new org.rb.qa.model.QA(qa.getQuestion(), qa.getAnswer());
           tlst.add(nqa);
        }
        return knb;
    }

    @Override
    public KNBase deSerialize(InputStream xmlIs) throws Exception {
     org.rb.qa.storage.jaxb.KNBase knBaseJaxb = (org.rb.qa.storage.jaxb.KNBase) xmlParser.load(xmlIs);
        KNBase knb = jaxbToKnBase(knBaseJaxb);
        return knb;  
    }

    @Override
    public void serialize(OutputStream xmlOs, KNBase knBase) throws Exception {
    org.rb.qa.storage.jaxb.KNBase knBaseJaxb = knBaseToJaxb(knBase);
        xmlParser.save(xmlOs, knBaseJaxb);   
    }

    
}
