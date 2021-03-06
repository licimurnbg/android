/*
 * Copyright © 2012 jbundle.org. All rights reserved.
 */
package org.jbundle.base.screen.view.android;

/**
 * @(#)ScreenField.java   0.00 12-Feb-97 Don Corley
 *
 * Copyright © 2012 tourgeek.com. All Rights Reserved.
 *      don@tourgeek.com
 */
import java.awt.Component;

import javax.swing.JTextField;

import org.jbundle.base.model.DBConstants;
import org.jbundle.base.screen.model.ScreenField;
import org.jbundle.model.screen.ScreenComponent;
import org.jbundle.thin.base.util.Application;
import org.jbundle.util.muffinmanager.MuffinManager;


/**
 * An edit box that only accepts numerics.
 */
public class ANumberText extends AEditText
{

    /**
     * Constructor.
     */
    public ANumberText()
    {
        super();
    }
    /**
     * Constructor.
     * @param model The model object for this view object.
     * @param bEditableControl Is this control editable?
     */
    public ANumberText(ScreenField model, boolean bEditableControl)
    {
        this();
        this.init(model, bEditableControl);
    }
    /**
     * Constructor.
     * @param model The model object for this view object.
     * @param bEditableControl Is this control editable?
     */
    public void init(ScreenComponent model, boolean bEditableControl)
    {
        super.init(model, bEditableControl);
    }
    /**
     * Free.
     */
    public void free()
    {
        if (this.getControl(DBConstants.CONTROL_TO_FREE) != null)
            if (m_bEditableControl)
        {
            this.getControl(DBConstants.CONTROL_TO_FREE).removeFocusListener(this);
            this.getControl(DBConstants.CONTROL_TO_FREE).removeKeyListener(this);
            m_bEditableControl = false;     // Don't remove more listeners in super
        }
        super.free();
    }
    /**
     * Create the physical control.
     * @param bEditableControl Is this control editable?
     * @return The new control.
     */
    public Component setupControl(boolean bEditableControl)
    {
        int cols = 10;
        if (this.getScreenField().getConverter() != null)
            cols = this.getScreenField().getConverter().getMaxLength();
        if (cols > 40)
            cols = 40;
        JTextField control = new JTextField(cols);
        control.setHorizontalAlignment(JTextField.RIGHT);

        Application application = (Application)this.getTask().getApplication();
        MuffinManager muffinManager = application.getMuffinManager();
        if (muffinManager != null)
        {
            if (bEditableControl)
                muffinManager.replaceClipboardAction(control, "cut");
            muffinManager.replaceClipboardAction(control, "copy");
            if (bEditableControl)
                muffinManager.replaceClipboardAction(control, "paste");
        }        

        if (bEditableControl)
        {
            control.addFocusListener(this);
            control.addKeyListener(this);
        }
        return control;
    }
}
