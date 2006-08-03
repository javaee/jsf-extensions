/*
 * ConstructorWrapper.java
 *
 * Created on August 2, 2006, 7:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.sun.faces.extensions.avatar.lifecycle;

import java.lang.reflect.Constructor;

/**
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
