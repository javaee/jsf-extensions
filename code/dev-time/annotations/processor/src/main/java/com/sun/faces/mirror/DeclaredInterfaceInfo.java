/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License).  You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the license at
 * https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * you own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 */

package com.sun.faces.mirror;

import com.sun.mirror.declaration.InterfaceDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;
import com.sun.mirror.type.InterfaceType;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a component class or a non-component base class declared in the current
 * compilation unit. This class offers several different "views" of its properties,
 * which can be useful during source code generation. In addition to providing a
 * map of all declared properties, it provides a map which contains all inherited
 * properties.
 *
 *
 * @author gjmurphy
 */
public class DeclaredInterfaceInfo extends DeclaredTypeInfo {
    
    DeclaredInterfaceInfo(InterfaceDeclaration decl) {
        super(decl);
    }
    
    public boolean equals(Object obj) {
        if (!(obj instanceof DeclaredInterfaceInfo))
            return false;
        DeclaredInterfaceInfo that = (DeclaredInterfaceInfo) obj;
        if (!this.getClassName().equals(that.getClassName()))
            return false;
        if (this.getClassName() == null && that.getClassName() != null)
            return false;
        if (!this.getClassName().equals(that.getClassName()))
            return false;
        return true;
    }
    
}
