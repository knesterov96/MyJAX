package com.nka;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.json.JSONObject;

import com.nka.entities.Assignment;

public class AssignmentGet {
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
  
  public JSONObject get(JSONObject jobj) {
    setup();
    
    if(sessionFactory == null) {
      exit();
      return new JSONObject("{\"status\": false}");
    }
    
    Session session = sessionFactory.openSession();
    
    Query<Assignment> q = session
    .createQuery(
        "SELECT a FROM Assignment a WHERE a.id = :id",
        Assignment.class
    );
    
    q.setParameter("id", jobj.getInt("id"));
    
    Assignment a = q.getSingleResult();
    
    session.close();
    
    exit();
    
    return a.getAllInfo();
  }
}
