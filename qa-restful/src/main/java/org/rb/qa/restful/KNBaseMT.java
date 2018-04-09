package org.rb.qa.restful;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author raitis
 */
public class KNBaseMT {
    
    
    private Date modifyTime;
    
    //@XmlElementWrapper(name = "qaList")
    // XmlElement sets the name of the entities
    //@XmlElement(name = "qa")
    private List<QA> qaList;

    public KNBaseMT() {
        qaList= new ArrayList<>();
    }

    
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
        return "KNBaseMT{" + "qaList=" + qaList + '}';
    }

}
