package org.rb.qa.restful;

import static org.rb.qa.restful.KNBaseResource.writeAccCounter;

/**
 *
 * @author raitis
 */
public interface IWriteAccessCounter {

    public static int getWriteAccCounter() {
        return writeAccCounter.get();
    }    
}
