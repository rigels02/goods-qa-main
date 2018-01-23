package org.rb.qa.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.rb.qa.model.KNBase;
import org.rb.qa.model.QA;

/**
 *
 * @author raitis
 */
public class QAGenerator {

    
    private KNBase knBase;
    private boolean useConverter;
    
    public QAGenerator(KNBase knBase) {
        if(knBase==null) throw new RuntimeException("knBase NULL not allowed!");
        this.knBase = knBase;
        this.useConverter=true;
    }

    public boolean isUseConverter() {
        return useConverter;
    }

    public void setUseConverter(boolean useConverter) {
        this.useConverter = useConverter;
    }
    
    
    public Map<Integer,String> getAllQuestions(){
        Map<Integer, String> map= new HashMap<>();
        for(int i=0; i< knBase.getQaList().size();i++){
         map.put(i, knBase.getQaList().get(i).getQuestion().trim());
        }
        return map;
    }
    public List<String> getAllQuestionsList(){
      List<String> qlist = new ArrayList<>();
      for(int i=0; i< knBase.getQaList().size();i++){
          qlist.add(knBase.getQaList().get(i).getQuestion().trim());
        
        }
      return qlist;
    }
    
    public String getAnswerforQuestionByIndex(int index){
        String answer = knBase.getQaList().get(index).getAnswer();
        return (this.useConverter)? 
                ConverterFactory.take(answer).getConverter().convert(answer)
                : answer.trim();
      
    }
    public DocType getAnswerDocTypeForQuestion(String question){
        List<QA> klist = knBase.getQaList();
        for (QA qa : klist) {
            if(qa.getQuestion().trim().equals(question.trim())){
                return ConverterFactory.take(qa.getAnswer()).getDocType();
            }
        }
        return DocType.Markdown;
    }
    public DocType getAnswerDocType(String answer){
       return ConverterFactory.take(answer).getDocType();
    }
    public QA getQuestionAnswer(int index){
        QA qa = knBase.getQaList().get(index);
        if(this.useConverter){
          qa.setAnswer(ConverterFactory.take(qa.getAnswer())
                                        .getConverter()
                                        .convert(qa.getAnswer()).trim());
        }
        return qa;
    }
    
    public String getAnswerforQuestionByIndex(int index,boolean useConverter){
        String answer = knBase.getQaList().get(index).getAnswer();
        return (useConverter)? 
                ConverterFactory.take(answer).getConverter().convert(answer).trim()
                : answer.trim();
      
    }
    public String convertAnswerTextForXML(String input){
        return ConverterFactory.take(input).getConverter(true).convert(input);
    }
    
    public String printAllQuestionsAnswers(){
        StringBuilder sb = new StringBuilder();
         for (QA qa : knBase.getQaList()) {
            String answer = qa.getAnswer().trim();
            String convAnswer = (this.useConverter)?
                    ConverterFactory.take(answer).getConverter().convert(answer)
                    :answer;
            sb.append(qa.getQuestion()).append(" -> \n")
              .append(convAnswer).append("\n");
        }
         return sb.toString();
    }
    
    public Map<Integer, String> getRandomQuestions(int questNumber) {
        if (questNumber <= 0 || questNumber > knBase.getQaList().size()) {
            throw new IllegalArgumentException("questNumber is out of range!");
        }
        Map<Integer, String> map = new HashMap<>();
        Random random = new Random();
        List<QA> qaList = new ArrayList<>();
        qaList.addAll(knBase.getQaList());
        while (map.size()< questNumber) {
            int idx = random.nextInt(qaList.size());
            QA qa = qaList.get(idx);
            map.put(idx, qa.getQuestion().trim());
           
        }
        
        return map;
    }

    public String getQuestionByIndex(int questionIdx) {
        return knBase.getQaList().get(questionIdx).getQuestion();
    }

    public KNBase getKnBase() {
        return knBase;
    }

    public void setKnBase(KNBase knBase){
      this.knBase = knBase;
    }
    

}
