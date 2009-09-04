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

    private AffableBeanDBAO database = null;
    
    public AffableBeanDB() {
    }

    public void setDatabase(AffableBeanDBAO database) {
        this.database = database;
    }

    public List getCategories() throws CategoriesNotFoundException {
        return database.getCategories();
    }
}
