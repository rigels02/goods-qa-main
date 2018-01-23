package org.rb.qa.service;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

/**
 *
 * @author raitis
 */
class MarkdownConverter implements IConverter{

    @Override
    public String convert(String input) {
       
        Parser parser = Parser.builder().build();
        Node document = parser.parse(input.trim());
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);

    }
    
}
