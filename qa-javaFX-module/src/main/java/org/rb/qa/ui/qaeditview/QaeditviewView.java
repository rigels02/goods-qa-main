package org.rb.qa.ui.qaeditview;

import com.airhacks.afterburner.views.FXMLView;

/**
 *
 * @author raitis
 */
public class QaeditviewView extends FXMLView {
    
    public QaeditviewPresenter getRealPresenter() {
        return (QaeditviewPresenter) super.getPresenter();
    }
    
}
