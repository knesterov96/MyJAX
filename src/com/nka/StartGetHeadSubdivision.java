package com.nka;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONObject;

import com.nka.entities.HeadSubdivision;

public class StartGetHeadSubdivision {
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
  
  public JSONObject startGet(JSONObject info) {
    if(has(info, "headOrganization") == false) {
      return new JSONObject("{\"status\": false}");
    }
    
    setup();
    
    if(sessionFactory == null) {
      return new JSONObject("{\"status\": false}");
    }
    
    JSONObject res = startGetHeadSubdivision(info.getInt("headOrganization"));
    
    exit();
    
    return res;
  }
  
  private boolean has(JSONObject jobj, String name) {
    if(jobj.has(name) == false) {
      return false;
    }
    
    return jobj.isNull(name) == false;
  }
  
  private JSONObject startGetHeadSubdivision (int id) {
    Session session = sessionFactory.openSession();
    
    Query<HeadSubdivision> q = session.createQuery(
        "SELECT hs FROM HeadSubdivision hs " +
        "  INNER JOIN Subdivision s " +
        "    ON hs.subdivision.id = s.id " +
        "  INNER JOIN Organization o " +
        "    ON s.organization.id = o.id " +
        "  INNER JOIN HeadOrganization ho " +
        "    ON ho.organization.id = o.id " +
        "WHERE ho.id = :id",
        HeadSubdivision.class
    );
    
    q.setParameter("id", id);
    
    JSONArray headSubdivisions = new JSONArray();
    
    q.getResultList()
    .stream()
    .forEach((hs) -> {
      headSubdivisions.put(hs.toJSONObject());
    });
    
    session.close();
    
    JSONObject res = new JSONObject();
    res.put("headSubdivision", headSubdivisions);
    
    return res;
  }
}