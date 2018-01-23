package org.rb.qa.xmlmodel;

import org.rb.mm.interfaceimpl.JaxbXmlParser;
import org.rb.mm.interfaceimpl.SimpleXmlParser;
import org.rb.qa.model.KNBase;
import org.rb.mm.interfaces.IStorage;

/**
 * Application scope Xml serializer/deserializer factory
 * @author raitis
 */
public class XmlFactory {

    public static XmlFactory take() {
        return new XmlFactory();
    }
    public static XmlFactory use(ParserType parser){
        return new XmlFactory(parser);
    }
    
    private IStorage parser = new JaxbXmlParser(KNBase.class);
   

    private XmlFactory() {
        this.parser = instantiateParser(ParserType.JaxbParser);
    }
    
    private XmlFactory(ParserType parser){
      
      this.parser = instantiateParser(parser);
      
    }
    
    public IStorage getXmlParser() {
        return parser;
    }
 
    public void setXmlParser(ParserType parser){
     this.parser = instantiateParser(parser);
    }

    private IStorage instantiateParser(ParserType parser) {
        IStorage xmlParser;  
      switch(parser){
          case JaxbParser:
              xmlParser= new JaxbXmlParser(KNBase.class);
              break;
          case SimpleParser:
              xmlParser = new SimpleXmlParser(KNBase.class);
              break;
          default:
              xmlParser= new JaxbXmlParser(KNBase.class);
      }  
      return xmlParser;
    }
}


