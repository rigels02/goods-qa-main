package org.rb.qa.storage.jaxb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB Xml specific Data model.
 * modifyTime field added.
 * @author raitis
 */
@XmlRootElement
public class KNBase {

     private Date modifyTime;
     
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

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    
    @Override
    public String toString() {
        return "KNBase{" + "qaList=" + qaList + '}';
    }

    
    
}
