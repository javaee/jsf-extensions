/*
 * Copyright 2009 Sun Microsystems, Inc.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developer.sun.com/berkeley_license.html
 */


package database;

import exceptions.CategoriesNotFoundException;
import java.util.List;


public class AffableBeanDB {

    // default constructor
    public AffableBeanDB() {}

    public List getCategories() throws CategoriesNotFoundException {
        // TODO: perhaps cache categories
        return new AffableBeanDAO().getCategories();
    }
}
