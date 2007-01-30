/*
 * $Id: Panel.java,v 1.32 2006/09/01 01:22:17 tony_robertson Exp $
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

package fireAjaxTrans;

import java.io.IOException;
import java.util.List;

import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;

public class Panel extends UIPanel {
    
    public static final String COMPONENT_TYPE = "Panel";
    
    // ------------------------------------------------------------ Constructors
    
    /**
     * <p>Create a new {@link Panel} instance with default property
     * values.</p>
     */
    public Panel() {
        super();
        setRendererType(null);
    }
    
    public void decode(FacesContext context) {
        super.decode(context);
        getAttributes().put("styleClass", "execute");
    }
    
    public void encodeBegin(FacesContext context) throws IOException {
        String style = (String)getAttributes().get("styleClass");
        if (style == null) {
            getAttributes().put("styleClass", "initial-render");
        } else {
            if (style.equals("execute")) {
                getAttributes().put("styleClass", "execute-render");
            } else {
                getAttributes().put("styleClass", "render");
            }
        }
        super.encodeBegin(context);
    }
}
