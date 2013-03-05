/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.meine.gpf.stripes;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

/**
 *
 * @author Meine Toonen
 */
public class ViewActionBean  implements ActionBean{
    
    private ActionBeanContext context;
    private static final String JSP = "/WEB-INF/jsp/overview.jsp";
    
    @DefaultHandler
    public Resolution view(){

        return new ForwardResolution(JSP);
    }
    
    // <editor-fold desc="Getters and Setters">
    @Override
    public ActionBeanContext getContext() {
        return context;
    }

    @Override
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }
    // </editor-fold>
    
}
