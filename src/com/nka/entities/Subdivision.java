package com.nka.entities;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.json.JSONArray;
import org.json.JSONObject;

@Entity
@Table (name = "subdivision")
public class Subdivision {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column (name = "name")
    private String name;
    
    @Column (name = "contact")
    private String contact;
    
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "organization")
    private Organization organization;
    
    @OneToOne (mappedBy = "subdivision")
    private HeadSubdivision headSubdivision;
    
    @OneToMany (
        fetch = FetchType.LAZY,
        mappedBy = "subdivision"
    )
    private Set<Employee> employees;
    
    public Subdivision () { }
    
    public Subdivision (
        String name,
        String contact,
        Organization organization,
        HeadSubdivision headSubdivision,
        Set<Employee> employees
    ) {
      this.name = name;
      this.contact = contact;
      this.organization = organization;
      this.headSubdivision = headSubdivision;
      this.employees = employees;
    }
    
    public Subdivision (
        String name,
        String contact
    ) {
      this.name = name;
      this.contact = contact;
    }
    
    public int getId () {
      return id;
    }
    
    public void setId (int id) {
      this.id = id;
    }
    
    public String getName () {
      return name;
    }
    
    public void setName (String name) {
      this.name = name;
    }
    
    public String getContact () {
      return contact;
    }
    
    public void setContact (String contact) {
      this.contact = contact;
    }
    
    public Organization getOrganization () {
      return organization;
    }
    
    public void setOrganization (Organization organization) {
      this.organization = organization;
    }
    
    public HeadSubdivision getHeadSubdivision () {
      return headSubdivision;
    }
    
    public void setHeadSubdivision (HeadSubdivision headSubdivision) {
      this.headSubdivision = headSubdivision;
    }
    
    public Set<Employee> getEmployees () {
      return employees;
    }
    
    public void setEmployee (Set<Employee> employees) {
      this.employees = employees;
    }
    
    @Override
    public String toString() {
      String res =
             "\r\nНаименование подразделения: \"" + name + "\"" +
             "\r\nКонтактные данные: \"" + contact + "\"" +
             headSubdivision.toString();
      if(employees == null) {
        return res;
      }
      
      if(employees.isEmpty()) {
        return res;
      }
      
      for(Iterator<Employee> itar = employees.iterator(); itar.hasNext();) {
         res += itar.next().toString();
      }
      
      return res;
    }
    
    public JSONObject toJSONObject() {
      JSONObject res = new JSONObject();
      res.put("name", name);
      res.put("contact", contact);
      
      /*
      if(employes == null) {
        return res;
      }
      
      JSONArray employesArr = new JSONArray();
      for(Iterator<Employee> itar = employes.iterator(); itar.hasNext();) {
        employesArr.put(itar.next().toJSONObject());
      }
      res.put("employes", employesArr);
      */
      
      return res;
    }
    
    
    public JSONObject getOrgStruck() {
      JSONObject res = toJSONObject();
      res.put("name", name);
      res.put("headSubdivision", headSubdivision.toJSONObject());
      
      JSONArray employees = new JSONArray();
      this.employees.forEach(e -> {;
        employees.put(e.getOrgStruck());
      });
      
      res.put("children", employees);
      
      return res;
    }
}