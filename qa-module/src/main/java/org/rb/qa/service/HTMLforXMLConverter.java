package org.rb.qa.service;

/**
 *
 * @author raitis
 */
public class HTMLforXMLConverter implements IConverter {

    /**
     * Wrap input with CDATA
     * @param input
     * @return 
     */
    @Override
    public String convert(String input) {
       return "<![CDATA[" + input + "]]>";   
    }

    
}
