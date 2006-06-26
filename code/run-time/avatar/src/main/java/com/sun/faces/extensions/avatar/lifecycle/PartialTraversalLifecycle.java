/*
 * PartialTraversalLifecycle.java
 *
 * Created on May 15, 2006, 4:31 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.sun.faces.extensions.avatar.lifecycle;

import java.io.IOException;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;
import java.util.Map;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ResponseWriter;

/**
 *
 * <p>In order to preserve the ability to use server side template text
 * to aid in formatting the AJAX response, it is necessary to allow the
 * <i>render response phase</i> to execute "mostly" normally.  The
 * departures from normalcy include:</p>
 * 
 * 	<ol>

	  <li><p>The template text above and belowthe &lt;f:view&gt; tag
	  must be discarded.</p></li>

	  <li><p>Any output from
	  <code>ResponseWriter.startDocument()</code> and
	  <code>endDocument()</code> must be discarded.</p></li>

	  <li><p>A distinct set of subtrees of the view are rendered,
	  rather than the whole view.</p></li>
 
	</ol>
 *
 * <p>To accomodate these vagaries, this custom <code>Lifecycle</code>
 * implementation, in the {@link #render} method, if the {@link
 * com.sun.faces.extensions.avatar.lifecycle.AsyncResponse#isAjaxRequest}
 * method returns <code>true</code>, replaces the
 * <code>ResponseWriter</code> and <code>ResponseStream</code> in the
 * current <code>FacesContext</code> with instances that take no action
 * before calling the original <code>render()</code> method.  Otherwise,
 * it simply calls the parent implementation.  Upon return from the render
 * method it replaces the <code>ResponseWriter</code> and
 * <code>ResponseStream</code> instance with their original values.  The
 * vital necessity of this operation being done in a custom
 * <code>Lifecycle</code> vs a mere <code>PhaseListener</code>: there is
 * no control over the ordering in which <code>PhaseListener</code>s are
 * invoked.  Therefore, we need to ensure that <b>none</b> of them write
 * to the response, because doing so would confuse the client.</p>
 *
 * @author  edburns
 */
public class PartialTraversalLifecycle extends Lifecycle {
    
    private Lifecycle parent = null;

    public PartialTraversalLifecycle(Lifecycle parent) {
        this.parent = parent;
    }


    /**
     * <p>Take no specific action.  Delegate to parent
     * implementation.</p>
     */
    public void execute(FacesContext context) throws FacesException {
	parent.execute(context);
    }
    
    public void render(FacesContext context) throws FacesException {
        Map<String,String> headerMap = context.getExternalContext().getRequestHeaderMap();

        if (!AsyncResponse.isAjaxRequest()) {
            parent.render(context);
            return;
        }
        AsyncResponse async = AsyncResponse.getInstance();
        UIViewRoot root = context.getViewRoot();
        ResponseWriter writer = null;
        boolean writeXML = AsyncResponse.isRenderXML();

        try {
            async.installNoOpResponseClasses(context);

            parent.render(context);
            
            if (writeXML) {
                writer = async.getResponseWriter();

                writer.startElement("state", root);
                writer.write("<![CDATA[" + async.getViewState(context) + "]]>");
                writer.endElement("state");
                writer.endElement("partial-response");
            }
        }
        catch (IOException ioe) {
            // PENDING edburns
        }
        finally {
            async.removeNoOpResponseClasses(context);
            AsyncResponse.clearInstance();
        }
        
    }
    

    public void removePhaseListener(PhaseListener phaseListener) {
        parent.removePhaseListener(phaseListener);
    }

    public void addPhaseListener(PhaseListener phaseListener) {
        parent.addPhaseListener(phaseListener);
    }

    public PhaseListener[] getPhaseListeners() {
        PhaseListener [] result = parent.getPhaseListeners();
        return result;
    }
    
  
}
