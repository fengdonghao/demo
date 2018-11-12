package com.vissun.BackEnd_vissun.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;
import java.util.HashSet;

/**
 * @ Author:fdh
 * @ Description:
 * @ Date： Create in 16:23 2018/11/9
 */
@WebListener
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {
    private static Logger logger = LoggerFactory.getLogger(SessionListener.class);
    @Override
    public void  attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
        logger.info("--attributeAdded--");
        logger.info("key----:"+httpSessionBindingEvent.getName());
        logger.info("value---:"+httpSessionBindingEvent.getValue());

    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {
        logger.info("--attributeRemoved--");
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {
//        logger.info("--attributeReplaced--");
    }

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        logger.info("---sessionCreated----");

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) throws ClassCastException {
        logger.info("---sessionDestroyed----");
        HttpSession session = event.getSession();
        logger.info("deletedSessionId: "+session.getId());
        System.out.println(session.getCreationTime());
        System.out.println(session.getLastAccessedTime());

    }

}
