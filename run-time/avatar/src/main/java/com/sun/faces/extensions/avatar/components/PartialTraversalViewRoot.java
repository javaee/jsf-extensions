/*
 * PartialTraversalViewRoot.java
 *
 * Created on January 23, 2007, 12:51 PM
 *
 */

/*
 * $Id: PartialTraversalViewRoot.java,v 1.4 2005/12/21 22:38:09 edburns Exp $
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

package com.sun.faces.extensions.avatar.components;

import java.io.IOException;
import javax.faces.context.FacesContext;

/**
 *
 * @author edburns
 * @author Ken Paulsen (ken.paulsen@sun.com)
 */
public interface PartialTraversalViewRoot {
    void encodePartialResponseBegin(FacesContext context) throws IOException;

    void encodePartialResponseEnd(FacesContext context) throws IOException;

    void postExecuteCleanup(FacesContext context);

    // Adding UIViewRoot / UIComponent methods b/c there is no interface to
    // extend and I want to avoid casting every time I need one of these
    // methods.  Casting is still needed for methods expecting a UIViewRoot
    // or UIComponent, though.
    void encodeBegin(FacesContext context) throws IOException;
    void encodeChildren(FacesContext context) throws IOException;
    void encodeEnd(FacesContext context) throws IOException;
    boolean getRendersChildren();
    void processDecodes(FacesContext context);
    void processUpdates(FacesContext context);
    void processValidators(FacesContext context);
}
