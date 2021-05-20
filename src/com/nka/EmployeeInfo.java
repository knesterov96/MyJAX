package com.nka;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.json.JSONObject;

import com.nka.entities.Employee;
import com.nka.entities.HeadOrganization;
import com.nka.entities.HeadSubdivision;

public class EmployeeInfo {
  private int headOrganization = -1;
  private int headSubdivision = -1;
  private int employee = -1;
  
  public EmployeeInfo () { }
  
  public EmployeeInfo (
      int headOrganization,
      int headSubdivision,
      int employee
  ) {
    this.headOrganization = headOrganization;
    this.headSubdivision = headSubdivision;
    this.employee = employee;
  }
  
  private SessionFactory sessionFactory;
  
  private void setup() {
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
          .configure("/resources/hibernate.cfg.xml") // configures settings from hibernate.cfg.xml
          .build();
    try {
      sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    } catch (Exception ex) {
      StandardServiceRegistryBuilder.destroy(registry);
    }
  }
  
  private void exit() {
    if(sessionFactory == null) {
      return;
    }
    
    if(sessionFactory.isClosed() == false) {
      sessionFactory.close();
    }
  }
  
  public JSONObject getInfo() {
    setup();
    
    if(sessionFactory == null) {
      return new JSONObject("\"status\": false");
    }
    
    JSONObject res = null;
    if(headOrganization != -1) {
      res = getHeadOrganization();
    }
    
    if(headSubdivision != -1) {
      res = getHeadSubdivision();
    }
    
    if(employee != -1) {
      res = getEmployee();
    }
    
    exit();
    
    return res;
  }
  
  private JSONObject getHeadOrganization () {
    Session session = sessionFactory.openSession();
    
    Query<HeadOrganization> q = 
    session
    .createQuery(
        "SELECT ho FROM HeadOrganization ho WHERE ho.id = :id",
        HeadOrganization.class
     );
    
    q.setParameter("id", headOrganization);
    
    JSONObject res = q.getSingleResult().toJSONObject();
    res.put("role", "headOrganization");
    
    session.close();
    
    return res;
  }
  
  private JSONObject getHeadSubdivision () {
    Session session = sessionFactory.openSession();
    
    Query<HeadSubdivision> q = 
    session
    .createQuery(
        "SELECT hs FROM HeadSubdivision hs WHERE hs.id = :id",
        HeadSubdivision.class
     );
    
    q.setParameter("id", headSubdivision);
    
    JSONObject res = q.getSingleResult().toJSONObject();
    res.put("role", "headSubdivision");
    
    session.close();
    
    return res;
  }
  
  private JSONObject getEmployee () {
    Session session = sessionFactory.openSession();
    
    Query<Employee> q = 
    session
    .createQuery(
        "SELECT e FROM Employee e WHERE e.id = :id",
        Employee.class
     );
    
    q.setParameter("id", employee);
    
    JSONObject res = q.getSingleResult().toJSONObject();
    res.put("role", "employee");
    
    session.close();
    
    return res;
  }
}