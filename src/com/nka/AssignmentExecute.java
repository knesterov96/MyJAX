package com.nka;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.json.JSONObject;

import com.nka.entities.AssignmentSubdivision;

public class AssignmentExecute {
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
  
  public JSONObject execute (JSONObject info) {
    setup();
    
    if(sessionFactory == null) {
      return new JSONObject("{\"status\": false}");
    }
    
    Session session = sessionFactory.openSession();
    
    Query<AssignmentSubdivision> q = session.createQuery(
        "SELECT asub FROM AssignmentSubdivision asub " +
        "WHERE asub.assignment.id = :id " +
        "  AND asub.headSubdivision.id = :hs",
        AssignmentSubdivision.class
    );
    
    q.setParameter("id", info.getInt("assignment"));
    q.setParameter("hs", info.getInt("headSubdivision"));
    
    AssignmentSubdivision as = q.getSingleResult();
    
    if(as == null) {
      session.close();
      
      exit();
      
      return new JSONObject("{\"status\": false}");
    }
    
    as.setExecution(info.getBoolean("execution"));
    session.beginTransaction();
    session.update(as);
    session.getTransaction().commit();
    
    session.close();
    
    exit();
    
    return new JSONObject("{\"status\": true}");
  }
}