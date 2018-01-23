
package org.rb.qa.service;

/**
 *
 * @author raitis
 */
class ConverterFactory {

    static ConverterFactory take(String input) {
        return new ConverterFactory(input);
    }
 
    private String input;

    private ConverterFactory(String input) {
        this.input = input.trim();
    }
    
    /**
     * Get converter for XML-> HTML
     * @return 
     */
    IConverter getConverter(){
        
        switch(getDocType()){
            case Markdown:
                return new MarkdownConverter();
               
            case HTML:
                return new DefaultConverter();
            default:
                
                
        }
        return new DefaultConverter();
     }
    /**
     * Get converter for xml-> html or
     * html(markdown etc) -> xml
     * @param toXML true if convertion direction is from html(markdown txt etc)
     * to XML
     * @return 
     */
    IConverter getConverter(boolean toXML){
       if(!toXML){
        return getConverter();
       }
       switch(getDocType()){
            case Markdown:
                return new DefaultConverter();
               
            case HTML:
                return new HTMLforXMLConverter();
            default:
                
                
        }
        return new DefaultConverter();
    }
    DocType getDocType(){
      boolean html = input.trim().startsWith("<html>") && input.trim().endsWith("</html>");
      if(html) return DocType.HTML;
      return DocType.Markdown;
    }
}
