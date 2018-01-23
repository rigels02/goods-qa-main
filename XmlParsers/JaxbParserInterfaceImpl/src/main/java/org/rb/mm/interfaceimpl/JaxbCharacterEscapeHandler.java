package org.rb.mm.interfaceimpl;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author raitis
 */
public class JaxbCharacterEscapeHandler implements CharacterEscapeHandler {

    @Override
    public void escape(char[] chars, int start, int len, boolean isAttValue, Writer out) throws IOException {
    
        for (int i = start; i < start + len; i++) {
                        char ch = chars[i];
                        out.write(ch);
                }
    }
    
}