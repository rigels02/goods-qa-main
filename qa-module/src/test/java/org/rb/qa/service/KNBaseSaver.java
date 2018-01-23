package org.rb.qa.service;



import org.rb.qa.model.KNBase;
import org.rb.qa.model.QA;
import org.rb.mm.interfaces.IStorage;

/**
 * Use XmlFactory to get concrete XML serializer implementation for app.
 * @author raitis
 */
public class KNBaseSaver {

    public static KNBaseSaver take(KNBase knBase, IStorage parser) {
        return new KNBaseSaver(knBase, parser);
    }

    private  KNBase knBase;
    private final IStorage xmlSerializer; //from XmlFactory

    private KNBaseSaver(KNBase knBase, IStorage parser) {
        this.knBase= knBase;
        this.xmlSerializer = parser;
       // convertKNBaseBeforeSave();
        
    }
    
    
    private void convertKNBaseBeforeSave(){
        KNBase n_knBase = new KNBase();
    for (QA qa : knBase.getQaList()) {
            QA nqa= new QA(qa.getQuestion(), qa.getAnswer());
            nqa.setAnswer(ConverterFactory.take(nqa.getAnswer())
                    .getConverter(true)
                    .convert(nqa.getAnswer()));
            //this.knBase.getQaList().add(nqa);
            n_knBase.getQaList().add(nqa);
        }
      this.knBase = n_knBase;
    }
    public void save(String filePath) throws Exception {
    
        xmlSerializer.save(filePath, knBase);
    }
}
