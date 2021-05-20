package com.nka;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONObject;

import com.nka.entities.Assignment;
import com.nka.entities.Employee;
import com.nka.entities.HeadSubdivision;

// Класс для удаления поркчений
public class AssignmentUpdate {
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
  
  public JSONObject update(JSONObject data) {
    setup();
    
    Session session = sessionFactory.openSession();
    
    Assignment a = session.load(Assignment.class, data.getInt("id"));
    
    if(a == null) {
      session.close();
      exit();
      return new JSONObject("{\"status\": false}");
    }
    
    /*
    a.setSubject(data.getString("subject"));
    a.setText(data.getString("text"));
    a.setControl(data.getBoolean("control"));
    a.setExecution(data.getBoolean("execution"));
    a.setHeadSubdivision(getHeadSubdivisions(data.getJSONArray("headSubdivision")));
    a.setEmployee(getEmployees(data.getJSONArray("employees")));
    */
    session.update(a);
    session.flush();
    
    session.close();
    
    exit();
    
    return new JSONObject("{\"status\": true}");
  }
  
  private Set<HeadSubdivision> getHeadSubdivisions (JSONArray headSubdivision) {
    Set<HeadSubdivision> res = new HashSet<HeadSubdivision>();
    
    Session session = sessionFactory.openSession();
    
    Query<HeadSubdivision> q =
    session
    .createQuery(
        "SELECT hs FROM HeadSubdivision hs WHERE hs.id = :id",
        HeadSubdivision.class
    );
    
    headSubdivision.forEach(e -> {
        q.setParameter("id", e);
        res.add(q.getSingleResult());
    });
    
    session.close();
    
    return res;
  }
  
  private Set<Employee> getEmployees (JSONArray employees) {
    Set<Employee> res = new HashSet<Employee>();
    
    Session session = sessionFactory.openSession();
    
    Query<Employee> q =
    session
    .createQuery(
        "SELECT e FROM Employee e WHERE e.id = :id",
        Employee.class
    );
    
    employees.forEach(e -> {
        q.setParameter("id", e);
        res.add(q.getSingleResult());
    });
    
    session.close();
    
    return res;
  }
}