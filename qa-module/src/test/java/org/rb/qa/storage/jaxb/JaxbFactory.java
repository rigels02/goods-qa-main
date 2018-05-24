package org.rb.qa.storage.jaxb;

import java.util.Date;
import java.util.List;
import org.rb.mm.interfaceimpl.JaxbXmlParser;

import org.rb.qa.storage.AbstractStorageFactory;

/**
 * JAXB parser factory.
 * T is org.​rb.​qa.storage.jaxb.KNBase.
 * Factory based on JaxbXmlParser.
 * Date dataModifyDate field added.
 * @author raitis
 */
public class JaxbFactory extends AbstractStorageFactory<KNBase>{

    
    public JaxbFactory() {
        super(new JaxbXmlParser(KNBase.class));
    }

    @Override
    protected KNBase knBaseToTypeT(org.rb.qa.model.KNBase knBase) {
        KNBase knBaseS = new KNBase();
    List  tlst = knBaseS.getQaList();
    for (org.rb.qa.model.QA qa : knBase.getQaList()) {
       QA nqa = new QA(qa.getQuestion(), qa.getAnswer());
       tlst.add(nqa);
    }
    knBaseS.setModifyTime(new Date());
    return knBaseS;
    }

    @Override
    protected org.rb.qa.model.KNBase typeTtoKnBase(KNBase knBaseS) {
        org.rb.qa.model.KNBase knb = new org.rb.qa.model.KNBase();
        this.dataModifyDate = knBaseS.getModifyTime();
        List  tlst = knb.getQaList();
        for (QA qa : knBaseS.getQaList()) {
            org.rb.qa.model.QA nqa= new org.rb.qa.model.QA(qa.getQuestion(), qa.getAnswer());
            tlst.add(nqa);
        }
        return knb;
    }

   
    
}
