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
 * $Id: Constants.java,v 1.3 2005/12/14 19:22:23 edburns Exp $
 */

package com.sun.faces.extensions.flash;

/**
 *
 * @author edburns
 */
public class Constants {
    
    private Constants() {
    }
    
    // PENDING(edburns): consider making this an enum.

    public static final String PREFIX = "com.sun.faces.extensions.flash";

    public static final String FLASH_ATTRIBUTE_NAME = PREFIX + ".FLASH";

    public static final String FLASH_THIS_REQUEST_ATTRIBUTE_NAME = PREFIX + ".ThisRequest";

    public static final String FLASH_POSTBACK_REQUEST_ATTRIBUTE_NAME = PREFIX + ".PostbackRequest";

    public static final String CURRENT_PHASE_ID_ATTRIBUTE_NAME = PREFIX + ".CurrentPhaseId";
    
    static final String FLASH_KEEP_ALL_REQUEST_SCOPED_DATA_ATTRIBUTE = 
            PREFIX + ".KeepAllRequestScopedData";
    
    static final String FACES_MESSAGES_ATTRIBUTE_NAME = 
            PREFIX + ".FacesMessages";

    static final String REDIRECT_AFTER_POST_ATTRIBUTE_NAME = 
            PREFIX + ".RedirectAfterPost";
    
    static final String THIS_REQUEST_IS_GET_AFTER_REDIRECT_AFTER_POST_ATTRIBUTE_NAME = 
            PREFIX + ".ThisRequestIsGetAfterRedirectAfterPost";

    static final String UIVIEWROOT_ATTRIBUTE_NAME = 
            PREFIX + ".UiViewRoot";
    
    
}
