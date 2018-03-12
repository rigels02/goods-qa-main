package org.rb.mm.interfaceimpl;

import com.sun.xml.bind.marshaller.DataWriter;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.rb.mm.interfaces.*;

/**
 * IStorage implementation with JAXB xml parser
 * @author raitis
 * @param <T>
 */
public class JaxbXmlParser<T> implements IStorage<T>{
    private final Class<T> clazz;

    public JaxbXmlParser(Class<T> clazz) {
        this.clazz = clazz;
                
    }
    
    
    @Override
    public synchronized  void save(String filePath, T knBase) throws Exception {
     JAXBContext context = JAXBContext.newInstance(clazz);
        Marshaller ma = context.createMarshaller();
        ma.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        //ma.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8");    
        
        //ma.setProperty(CharacterEscapeHandler.class.getName(), new JaxbCharacterEscapeHandler());
        
         // The below code will take care of avoiding the conversion of < to &lt; and > to &gt; etc
                        StringWriter stringWriter = new StringWriter();
                        PrintWriter printWriter = new PrintWriter(stringWriter);
                       // DataWriter dataWriter = new DataWriter(printWriter, "UTF-8", new JaxbCharacterEscapeHandler());
                       //Do not escape
                       DataWriter dataWriter = new DataWriter(printWriter, "UTF-8");
                        // Perform Marshalling operation
                        ma.marshal(knBase, dataWriter);
        
        try (PrintStream prn = new PrintStream(filePath)) {
            prn.print(stringWriter.toString());
           
        }
         // ma.marshal(knBase, new File(filePath));
    }

    @Override
    public T load(String xmlFile) throws Exception {
        // create JAXB context and instantiate marshaller
        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller um = context.createUnmarshaller();
        T knBase1 = (T) um.unmarshal(new FileReader(xmlFile));
        return knBase1;
    }

    @Override
    public synchronized   T load(InputStream xmlIs) throws Exception {
        // create JAXB context and instantiate marshaller
        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller um = context.createUnmarshaller();
        T knBase1 = (T) um.unmarshal(xmlIs);
        return knBase1;
    }

    /**
     * TODO: NOT IMPLEMENTED YET!
     * @param xmlOs
     * @param knBase
     * @throws Exception 
     */
    @Override
    public synchronized  void save(OutputStream xmlOs, T knBase) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
