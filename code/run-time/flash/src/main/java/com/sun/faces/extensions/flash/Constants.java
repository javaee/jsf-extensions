
/*
 * $Id: Constants.java,v 1.3 2005/12/14 19:22:23 edburns Exp $
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


package com.sun.faces.extensions.flash;

/**
 *
 * @author edburns
 */
public class Constants {
    
    private Constants() {
    }

    public static final String PREFIX = "com.sun.faces.extensions.flash";

    public static final String FLASH_ATTRIBUTE_NAME = PREFIX + "FLASH";

    public static final String FLASH_THIS_REQUEST_ATTRIBUTE_NAME = PREFIX + "ThisRequest";

    public static final String FLASH_POSTBACK_REQUEST_ATTRIBUTE_NAME = PREFIX + "PostbackRequest";

    public static final String CURRENT_PHASE_ID_ATTRIBUTE_NAME = PREFIX + "CurrentPhaseId";

    
}
