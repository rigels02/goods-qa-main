package org.rb.mm.interfaces;

import java.io.InputStream;
import java.io.OutputStream;



/**
 *  Storage Access interface
 * @author raitis
 * @param <T>
 */
public  interface IStorage<T> {

void save(String filePath, T knBase) throws Exception;

 T load(String xmlFile) throws Exception;

 T load(InputStream xmlIs) throws Exception;

 void save(OutputStream xmlOs, T knBase) throws Exception;
 
}
