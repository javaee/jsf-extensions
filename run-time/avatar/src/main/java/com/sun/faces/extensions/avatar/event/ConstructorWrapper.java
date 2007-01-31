/*
 * ConstructorWrapper.java
 *
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
 * Copyright 2006 Sun Microsystems Inc. All Rights Reserved
 *
 */

package com.sun.faces.extensions.avatar.event;

import com.sun.faces.extensions.avatar.lifecycle.*;
import java.lang.reflect.Constructor;

/**
 *
 * This "struct"-like JavaClass fills a gap in the feature set of
 * <code>Constructor</code>: provide the runtime capability to query the
 * paramater list for this ctor instance.
 *
 * @author edburns
 */
class ConstructorWrapper {
    
    /** Creates a new instance of ConstructorWrapper */
    ConstructorWrapper(Constructor ctor, Class [] argClasses) {
        this.constructor = ctor;
        this.argClasses = argClasses;
    }

    /**
     * Holds value of property constructor.
     */
    private Constructor constructor;

    /**
     * Getter for property constructor.
     * @return Value of property constructor.
     */
    public Constructor getConstructor() {
        return this.constructor;
    }

    /**
     * Holds value of property argClasses.
     */
    private Class [] argClasses;

    /**
     * Getter for property argClasses.
     * @return Value of property argClasses.
     */
    public Class [] getArgClasses() {
        return this.argClasses;
    }

  
}
