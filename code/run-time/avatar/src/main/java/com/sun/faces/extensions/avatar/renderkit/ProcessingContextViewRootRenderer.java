/*
 * $Id: ProcessingContextViewRootRenderer.java,v 1.2 2005/12/08 17:22:18 edburns Exp $
 */

/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at
 * https://javaserverfaces.dev.java.net/CDDL.html or
 * legal/CDDLv1.0.txt.
 * See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at legal/CDDLv1.0.txt.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * [Name of File] [ver.__] [Date]
 *
 * Copyright 2005 Sun Microsystems Inc. All Rights Reserved
 */

package com.sun.faces.extensions.avatar.renderkit;

import com.sun.faces.extensions.avatar.components.ProcessingContext;
import com.sun.faces.extensions.avatar.components.ProcessingContextViewRoot;
import java.util.ArrayList;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

/**
 *
 * @author edburns
 */
public class ProcessingContextViewRootRenderer extends Renderer {
    
    /**
     * Creates a new instance of ProcessingContextViewRootRenderer 
     */
    public ProcessingContextViewRootRenderer() {
    }
    
    public void decode(FacesContext context, UIComponent component) {
	if (null == context || null == component) {
	    throw new NullPointerException();
	}
        ProcessingContextViewRoot pcRoot = (ProcessingContextViewRoot) component;
        List<ProcessingContext> processingContexts = null;
        if (null != (processingContexts = pcRoot.getProcessingContexts())) {
            return;
        }
        processingContexts = new ArrayList<ProcessingContext>();
        
        String param = null;
        String [] pcs = null;
        
        if (null != (param = (String)
                context.getExternalContext().getRequestParameterMap().
                get(ProcessingContextViewRoot.PROCESSING_CONTEXTS_REQUEST_PARAM_NAME))) {
            if (null != (pcs = param.split(",[ \t]*"))) {
                for (String cur : pcs) {
                    processingContexts.add(new ProcessingContext(cur.trim()));
                }
                if (0 < pcs.length) {
                    pcRoot.setProcessingContexts(processingContexts);
                }
            }
        }
    }    

    
}
