package com.enverio.model;

import java.util.Comparator;

import com.enverio.util.Predicate;

public class Employee {

    private long id;

    private Department department;
    
    private String lastName;
    private String firstName;
    private String phone;
    private String email;
    
    public Employee() {
        super();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    
    
    private static class EmpNamePredicate implements Predicate<Employee> {
        private final String name;

        public EmpNamePredicate(String name) {
            this.name = name.toLowerCase();
        }

        public boolean evaluate(Employee item) {
            return (item.getLastName().toLowerCase().contains(this.name))
                    || (item.getFirstName().toLowerCase().contains(this.name));
        }
    }
    
    public final static Predicate EmpNamePredicate(String name) {
        return new EmpNamePredicate(name);
    }

    public final static Comparator<Employee> EmpNameSort = new Comparator<Employee>() {
        public int compare(Employee o1, Employee o2) {
            int a = o1.getLastName().compareTo(o2.getLastName());
            return (a != 0) ? a : o1.getFirstName()
                    .compareTo(o2.getFirstName());
        }
    };
}
