package com.enverio.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.enverio.util.Projections;

public class Department implements Serializable {

    private long id;
    private String name;
    
    private List<Employee> employees = new ArrayList<Employee>();
    
    public Department() {
        super();
    }
    
    public Department(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
    
    public void addEmployee(Employee emp) {
        this.employees.add(emp);
    }
    
    public Collection<Employee> suggestEmployees(String input) {
        Collection<Employee> emps = Projections.in(this.getEmployees(),
                Employee.EmpNamePredicate(input));
        List<Employee> result = new ArrayList<Employee>(emps);
        Collections.sort(result, Employee.EmpNameSort);
        return result.subList(0, Math.min(result.size(), 5));
    }

}
