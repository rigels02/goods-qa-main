package org.rb.qa.storage.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB Xml specific Data model
 * @author raitis
 */
@XmlRootElement
public class KNBase {

    //@XmlElementWrapper(name = "qaList")
    // XmlElement sets the name of the entities
    //@XmlElement(name = "qa")
    private List<QA> qaList;

    public KNBase() {
        qaList= new ArrayList<>();
    }

    
    @XmlElementWrapper(name = "qaList")
    // XmlElement sets the name of the entities
    @XmlElement(name = "QA")
    public List<QA> getQaList() {
        return qaList;
    }
    

    public void setQaList(List<QA> qaList) {
        this.qaList = qaList;
    }

    @Override
    public String toString() {
        return "KNBase{" + "qaList=" + qaList + '}';
    }

    
    
}
