package org.rb.qa.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author raitis
 */
@XmlRootElement(namespace = "org.rb.qa.model")
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
