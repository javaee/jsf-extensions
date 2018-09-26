/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

/*
 * ExtensionsLifecycleFactoryImpl.java
 *
 * Created on May 15, 2006, 4:27 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.sun.faces.extensions.avatar.lifecycle;

import java.util.Iterator;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;

/**
 *
 * @author edburns
 */
public class ExtensionsLifecycleFactoryImpl extends LifecycleFactory {
    
    private LifecycleFactory parent = null;
    
    /** Creates a new instance of ExtensionsLifecycleFactoryImpl */
    public ExtensionsLifecycleFactoryImpl(LifecycleFactory parent) {
        this.parent = parent;
        //check whether the lifecycleId already exists
        if (alreadyCreated("com.sun.faces.lifecycle.PARTIAL")) {
            return;
        }
        this.parent.addLifecycle("com.sun.faces.lifecycle.PARTIAL",
                new PartialTraversalLifecycle(this.parent.getLifecycle("DEFAULT")));
    }

    public Lifecycle getLifecycle(String string) {
        return parent.getLifecycle(string);
    }

    public void addLifecycle(String string, Lifecycle lifecycle) {
        //check whether the lifecycleId already exists
        if (alreadyCreated(string)) {
            return;
        }
        parent.addLifecycle(string, lifecycle);
    }

    public Iterator<String> getLifecycleIds() {
        return parent.getLifecycleIds();
    }
    
    boolean alreadyCreated(String lifecycleId) {
        Iterator<String> iter = this.getLifecycleIds();
        while(iter.hasNext()) {
            String existingId = iter.next();
            if (existingId.equals(lifecycleId)) {   //let NPE be thrown
                return true;
            }
        }
        return false;
    }
}
