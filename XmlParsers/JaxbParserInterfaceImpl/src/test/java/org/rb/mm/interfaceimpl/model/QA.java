package org.rb.mm.interfaceimpl.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author raitis
 */
@XmlRootElement(name = "qa")
@XmlType(propOrder = {"question", "answer"})
public class QA {
    
   
    private String question;
    
    private String answer;

    public QA() {
    }

    
    public QA(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    @XmlElement(name = "Question") 
    public String getQuestion() {
        return question;
    }
    
    
    public void setQuestion(String question) {
        this.question = question;
    }

    @XmlElement(name = "Answer")
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "QA{" + "question=" + question + ", answer=" + answer + '}';
    }
    
    
}
