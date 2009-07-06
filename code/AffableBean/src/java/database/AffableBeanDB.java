package database;

import exceptions.CategoriesNotFoundException;
import java.util.List;

/**
 *
 * @author troy
 */
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
