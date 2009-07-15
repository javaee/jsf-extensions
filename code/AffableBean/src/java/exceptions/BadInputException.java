/*
 * Copyright 2009 Sun Microsystems, Inc.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developer.sun.com/berkeley_license.html
 */


package exceptions;


/** This application exception indicates that user
 *  input could not be properly interpreted as a number.
 *
 */
public class BadInputException extends Exception {
    public BadInputException() {
    }

    public BadInputException(String msg) {
        super(msg);
    }
}
