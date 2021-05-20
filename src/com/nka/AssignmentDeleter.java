package com.nka;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.json.JSONObject;

import com.nka.entities.Assignment;

// Класс для удаления поркчений
public class AssignmentDeleter {
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
  
  public JSONObject delete(JSONObject data) {
    setup();
    
    Session session = sessionFactory.openSession();
    
    Assignment a = session.load(Assignment.class, data.getInt("id"));
    
    if(a == null) {
      session.close();
      exit();
      return new JSONObject("{\"status\": false}");
    }
    
    session.delete(a);
    session.flush();
    session.close();
    
    exit();
    
    return new JSONObject("{\"status\": true}");
  }
}