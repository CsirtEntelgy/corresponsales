package com.isban.corresponsalia.listener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.isban.ebe.commons.beans.ArchitechSessionBean;



public class CbHttpSessionListener implements HttpSessionListener {

	/* (sin Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	/**
	 * sessionCreated
	 * @param arg0 Argumento
	 */
	public void sessionCreated(HttpSessionEvent arg0) {		
	}

	/* (sin Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	/**
	 * sessionDestroyed
	 * @param arg0 Argumento
	 */
	public void sessionDestroyed(HttpSessionEvent arg0) {
		
		ArchitechSessionBean bean = (ArchitechSessionBean) arg0.getSession().getAttribute("ArchitechSession");
		
		if (bean != null && arg0.getSession()!= null) {
					arg0.getSession().removeAttribute("ArchitechSession");
					arg0.getSession().invalidate();
		}
	}

}
