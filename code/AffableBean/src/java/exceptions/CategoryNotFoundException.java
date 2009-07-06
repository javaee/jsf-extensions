/*
 * Copyright 2007 Sun Microsystems, Inc.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developer.sun.com/berkeley_license.html
 */


package exceptions;


/** This application exception indicates that the selected
 *  category number has not been found.
 */
public class CategoryNotFoundException extends Exception {
    public CategoryNotFoundException() {
    }

    public CategoryNotFoundException(String msg) {
        super(msg);
    }
}
