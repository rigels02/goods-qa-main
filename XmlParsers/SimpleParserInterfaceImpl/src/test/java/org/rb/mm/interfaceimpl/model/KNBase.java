package org.rb.mm.interfaceimpl.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author raitis
 */
public class KNBase {

    private List<QA> qaList;

    public KNBase() {
        qaList= new ArrayList<>();
    }

    
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
