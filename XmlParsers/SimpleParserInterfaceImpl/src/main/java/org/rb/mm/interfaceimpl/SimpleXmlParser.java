package org.rb.mm.interfaceimpl;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.rb.mm.interfaces.IStorage;

/**
 * IStorage implementation with Sinple xml parser
 * @author raitis
 * @param <T>
 */
public class SimpleXmlParser<T> implements IStorage<T>{

     private final Class<T> clazz;

    public SimpleXmlParser(Class<T> clazz) {
        this.clazz = clazz;
    }
     
    @Override
    public void save(String filePath, T knBase) throws Exception {
        Serializer serializer = new Persister();
        
        serializer.write(knBase, new File(filePath));
    }

    @Override
    public T load(String xmlFile) throws Exception {
        Persister deserializer = new Persister();
       T knBase = (T) deserializer.read(clazz, new File(xmlFile));
       return knBase;
    }

    @Override
    public T load(InputStream xmlIs) throws Exception {
    Persister deserializer = new Persister();
        T knBase = (T) deserializer.read(clazz, xmlIs);
        return knBase; 
    }

    @Override
    public void save(OutputStream xmlOs, T knBase) throws Exception {
         Serializer serializer = new Persister();
        serializer.write(knBase,xmlOs);
    }
    
}
