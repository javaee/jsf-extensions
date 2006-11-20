package com.enverio.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.digester.Digester;

import com.enverio.util.Predicate;
import com.enverio.util.Projections;
import com.enverio.util.Transformer;

public class Company {
    
    public static void main(String[] argv) {
        Company c = new Company();
        System.out.println(c.getCatalog().getProducts().size());
    }

    private List<Department> departments = new ArrayList<Department>();
    private Catalog catalog = null;

    public Company() {
        this.loadFromXML();
    }

    public List<Department> getDepartments() {
        return departments;
    }
    
    public void setCatalog(Catalog c) {
        if (c != null) {
            this.catalog = c;
        }
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public Collection<Employee> suggestEmployees(String input) {
        Collection<Employee> emps = Projections.in(this.getAllEmployees(),
                Employee.EmpNamePredicate(input));
        List<Employee> result = new ArrayList<Employee>(emps);
        Collections.sort(result, Employee.EmpNameSort);
        return result.subList(0, Math.min(result.size(), 5));
    }

    public List<Employee> getAllEmployees() {
        List<Employee> emps = new ArrayList<Employee>();
        for (Department d : this.departments) {
            for (Employee e : d.getEmployees()) {
                emps.add(e);
            }
        }
        return emps;
    }
    
    public void addDepartment(Department d) {
        this.departments.add(d);
    }

    private void loadFromXML() {
        Digester d = new Digester();

        d.addObjectCreate("company/department", Department.class);
        d.addSetProperties("company/department");
        d.addSetNext("company/department", "addDepartment");
        d.addObjectCreate("company/department/employee", Employee.class);
        d.addSetProperties("company/department/employee");
        d.addSetNext("company/department/employee", "addEmployee");
        d.addObjectCreate("company/catalog", Catalog.class);
        d.addSetNext("company/catalog", "setCatalog");
        d.addObjectCreate("company/catalog/product", Product.class);
        d.addSetProperties("company/catalog/product");
        d.addSetNext("company/catalog/product", "addProduct");
        d.addObjectCreate("company/catalog/product/uom", Uom.class);
        d.addSetProperties("company/catalog/product/uom");
        d.addSetNext("company/catalog/product/uom", "addUom");
        d.push(this);

        try {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            d.parse(cl.getResourceAsStream("db.xml"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Catalog getCatalog() {
        
        return catalog;
    }
}
