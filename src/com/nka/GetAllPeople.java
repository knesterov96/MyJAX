package com.nka;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import com.nka.entities.Employee;
import com.nka.entities.HeadOrganization;
import com.nka.entities.HeadSubdivision;

public class GetAllPeople {
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
  
  private JSONArray getHeadOrganization () {
    Session session = sessionFactory.openSession();
    
    JSONArray res = new JSONArray();
    session
    .createQuery(
        "SELECT ho FROM HeadOrganization ho",
        HeadOrganization.class
     )
    .getResultList()
    .stream()
    .forEach(hs -> {
       res.put(hs.toJSONObject());
     });
    
    session.close();
    return res;
  }
  
  private JSONArray getHeadSubdivision () {
    Session session = sessionFactory.openSession();
    
    JSONArray res = new JSONArray();
    session
    .createQuery(
        "SELECT hs FROM HeadSubdivision hs",
        HeadSubdivision.class
     )
    .getResultList()
    .stream()
    .forEach(hs -> {
      res.put(hs.toJSONObject());
    });
    
    session.close();
    return res;
  }
  
  private JSONArray getEmployee () {
    Session session = sessionFactory.openSession();
    
    JSONArray res = new JSONArray();
    session
    .createQuery(
        "SELECT e FROM Employee e",
        Employee.class
     )
    .getResultList()
    .stream()
    .forEach(hs -> {
      res.put(hs.toJSONObject());
    });
    
    session.close();
    return res;
  }
  
  public JSONObject getPeople () {
    setup();
    
    if(sessionFactory == null) {
      System.out.println("sessionFactory is null");
      return new JSONObject("{\"status\": false}");
    }
    
    JSONObject allPeople = new JSONObject();
    
    allPeople.put("headOrganization", getHeadOrganization());
    
    allPeople.put("headSubdivision", getHeadSubdivision());
    
    allPeople.put("employee", getEmployee());
    
    exit();
    
    JSONObject res = new JSONObject();
    res.put("allPeople", allPeople);
    
    return res;
  }
}
