/*
 * Copyright © 2012 jbundle.org. All rights reserved.
 */
package org.jbundle.android.util.biorhythm;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.DateFormat;
import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * LegendController - This is the biorhythm controller.
 */
public class LegendController extends Controller
	implements /*ActionListener,*/ PropertyChangeListener
{
    
	public final static int DELAY_VALIDATE_MS = 2 * 1000;	// Delay in typing before validation

	protected String m_strLanguage = null;

	public static DateFormat m_df = DateFormat.getDateInstance(DateFormat.MEDIUM);
	public static DateFormat m_dtf = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);

	/**
	 * Creates new Controller.
	 */
	public LegendController(Context context, Model model, Date dateBirth, Date dateStart, Date dateEnd)
	{
	    super(context, model, dateEnd, dateEnd, dateEnd);
//?		this();
		this.init(model, dateBirth, dateStart, dateEnd);
	}
	/**
	 * Creates new Controller.
	 */
	public void init(Context context, Model model, Date dateBirth, Date dateStart, Date dateEnd)
	{
		super.init(model, dateBirth, dateStart, dateEnd);
		this.addComponents();
	}
	/**
	 * Set the label text.
	 */
	public void setComponentText()
	{
//		String string = this.getResources().getString("Birthdate");
//?		m_labelBirthdate.setText(string);
//		string = this.getResources().getString("Start Date");
//?		m_labelStartDate.setText(string);
//		string = this.getResources().getString("End Date");
//?		m_labelEndDate.setText(string);
	}
	/**
	 * Initialize all the panel components.
	 */
	public void addComponents()
	{
	}
	/**
	 * Set the Language.
	 * @param strLanguage The language.
	 */
	public void setLanguage(String strLanguage)
	{
		String oldLanguage = m_strLanguage;
		((LegendView)this.getModel().getView()).setLanguage(strLanguage);
		if (strLanguage != null)
			if (!strLanguage.equals(oldLanguage))
		{
				this.firePropertyChange(LANGUAGE_PARAM, oldLanguage, strLanguage);
		}
		m_strLanguage = strLanguage;
	}
	/**
	 * Get the language.
	 * @return the language.
	 */
	public String getLanguage()
	{
		return m_strLanguage;
	}
	/**
	 * Action performed.
	 */
//	public void actionPerformed(final ActionEvent evt)
	//{
//		if (evt.getSource() == m_timer)
	//		this.parseField(m_objTimerTarget);
		//else
			//this.parseField(evt.getSource());
	//}
//	protected javax.swing.Timer m_timer = null;
	protected Object m_objTimerTarget = null;
	/**
	 * Wait one second, then validate this field.
	 */
	public void startTimer(Object srcField)
	{
//		if (m_timer == null)
		{
	//		m_timer = new javax.swing.Timer(DELAY_VALIDATE_MS, this);
		//	m_timer.setRepeats(false);
		}
//		else
	//		m_timer.restart();
		synchronized (this)
		{
			m_objTimerTarget = srcField;
		}
	}
	/**
	 * Parse this target date field.
	 */
	public void parseField(Object srcField) 
	{
	}
	/**
	 * A propery changed, change the corresponding variable.
	 */
	public void propertyChange(final java.beans.PropertyChangeEvent evt)
	{
		if (evt.getNewValue() instanceof Date)
		{
			if (BIRTHDATE_PARAM.equalsIgnoreCase(evt.getPropertyName()))
				this.setBirthdate((Date)evt.getNewValue());
			if (STARTDATE_PARAM.equalsIgnoreCase(evt.getPropertyName()))
				this.setStartDate((Date)evt.getNewValue());
			if (ENDDATE_PARAM.equalsIgnoreCase(evt.getPropertyName()))
				this.setEndDate((Date)evt.getNewValue());
		}
//		else if (LANGUAGE_PARAM.equals(evt.getPropertyName()))
//		{
//			this.setLanguage((String)evt.getNewValue());
//		}
	}
	/**
	 * The standard property change manager.
	 */
	protected PropertyChangeSupport propertyManager = new PropertyChangeSupport(this);
	/**
	 * Add a new listener.
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		propertyManager.addPropertyChangeListener(listener);
	}
	/**
	 * Add a new listener.
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener)
	{
		propertyManager.removePropertyChangeListener(listener);
	}
	/**
	 * Fire the property change.
	 */
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue)
	{
		if (propertyManager != null)
			propertyManager.firePropertyChange(propertyName, oldValue, newValue);
	}
    SharedPreferences preferences = null;
    public void setPreferences(SharedPreferences preferences)
    {
        Date defDate = new Date();
        long birthdate = preferences.getLong("birthdate", defDate.getTime());

        this.setBirthdate(new Date(birthdate));

        this.preferences = preferences;
    }
    /**
     * Set the birthdate.
     * @param dateBirth The birthdate to analyze.
     */
    public void setBirthdate(Date dateBirth)
    {
        super.setBirthdate(dateBirth);
        
        if (preferences != null)
        {
            Editor editor = preferences.edit();
            editor.putLong("birthdate", dateBirth.getTime());
            editor.commit();
        }
    }
}
