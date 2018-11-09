package org.rb.qa.service;

import java.util.List;
import org.rb.qa.model.KNBase;
import org.rb.qa.model.QA;

/**
 *
 * @author raitis
 */
public class KNBaseEditor {

    public static KNBaseEditor take(KNBase knBase) {
        return new KNBaseEditor(knBase);
    }

private  KNBase knBase;

    private KNBaseEditor(KNBase knBase) {
        this.knBase = knBase;
    }

    public KNBase add(QA qa){
       knBase.getQaList().add(qa);
       return knBase;
    }
    public KNBase delete(int idx){
      knBase.getQaList().remove(idx);
      return knBase;
    }
    public KNBase add(int idx,QA qa){
     knBase.getQaList().remove(idx);
     knBase.getQaList().add(idx, qa);
     return knBase;
    }
    /**
     * Move existing (selected) item to other position in the list
     * @param oldIdx current position
     * @param newIdx new position
     * @return modified knBase
     */
    public KNBase moveItem(int oldIdx, int newIdx) throws IllegalArgumentException{
        int sz = knBase.getQaList().size();
        String emsg1 = "oldIdx out of scope!";
         String emsg2 = "newIdx out of scope!";
        if(oldIdx< 0 || oldIdx > sz - 1)
            throw new IllegalArgumentException(emsg1);
        if(newIdx< 0 || newIdx > sz - 1)
            throw new IllegalArgumentException(emsg2);
        QA qa = knBase.getQaList().get(oldIdx);
        knBase.getQaList().remove(oldIdx);
        knBase.getQaList().add(newIdx, qa);
        
        return knBase;
    }
    
    public KNBase deleteAll(){
     knBase.getQaList().clear();
     return knBase;
    }
    public KNBase makeCopy(){
        KNBase nknBase = new KNBase();
        for (QA qa : knBase.getQaList()) {
            QA nqa = new QA();
            nqa.setQuestion(qa.getQuestion());
            nqa.setAnswer(qa.getAnswer());
            nknBase.getQaList().add(nqa);
        }
        
        return nknBase;
    }
    public int findIndexByQuestion(String question){
      for(int i=0; i<knBase.getQaList().size(); i++){
        if(knBase.getQaList().get(i).getQuestion().equals(question)){
          return i;
        }
      }
      return -1;
    }
}
