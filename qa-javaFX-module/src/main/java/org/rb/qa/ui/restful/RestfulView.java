package org.rb.qa.ui.restful;

import com.airhacks.afterburner.views.FXMLView;

/**
 *
 * @author raitis
 */
public class RestfulView extends FXMLView {
    
    public RestfulPresenter getRealPresenter() {
        return (RestfulPresenter) super.getPresenter();
    }
    
}
