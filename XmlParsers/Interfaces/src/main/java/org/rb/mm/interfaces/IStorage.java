package org.rb.mm.interfaces;



/**
 *  Storage Access interface
 * @author raitis
 * @param <T>
 */
public  interface IStorage<T> {

void save(String filePath, T knBase) throws Exception;

 T load(String xmlFile) throws Exception;
 
}
