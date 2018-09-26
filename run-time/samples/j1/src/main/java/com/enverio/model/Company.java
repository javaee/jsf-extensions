/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

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
