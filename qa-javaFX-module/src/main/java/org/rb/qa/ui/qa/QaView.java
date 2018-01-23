package org.rb.qa.ui.qa;

import com.airhacks.afterburner.views.FXMLView;

/**
 *
 * @author raitis
 */
public class QaView extends FXMLView {
    
    public QaPresenter getRealPresenter() {
        return (QaPresenter) super.getPresenter();
    }
    
}
