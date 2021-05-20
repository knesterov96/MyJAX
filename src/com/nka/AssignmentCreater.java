package com.nka;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.nka.entities.AssignmentSubdivision;
import com.nka.entities.HeadOrganization;
import com.nka.entities.HeadSubdivision;

public class AssignmentCreater {
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
  
  public JSONObject create(JSONObject info) {
    setup();
    
    if(sessionFactory == null) {
      System.out.println("sessionFactory is null");
      return new JSONObject("{\"status\": false}");
    }
    
    String end = info.getString("endDate");
    java.util.Date d = null;
    try {
      d = new SimpleDateFormat("yyyy-MM-dd").parse(end);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    
    if(d == null) {
      return new JSONObject("{\"status\": false}");
    }
    
    Assignment ao = new Assignment();
    ao.setSubject(info.getString("subject"));
    ao.setText(info.getString("text"));
    ao.setEndDate(d);
    ao.setHeadOrganization(getHeadOrganization(info));
    Set<AssignmentSubdivision> set = getAssignmentSubdivision(ao, info.getJSONArray("headSubdivision"));
    ao.setAssignmentSubdivision(set);
    
    Session session = sessionFactory.openSession();
    session.save(ao);
    session.close();
    
    exit();
    return new JSONObject("{\"status\": true}");
  }
  
  private HeadOrganization getHeadOrganization (JSONObject info) {
    Session session = sessionFactory.openSession();
    
    Query<HeadOrganization> q = session
    .createQuery(
        "SELECT ho FROM HeadOrganization ho WHERE ho.id = :id",
        HeadOrganization.class
     );
    
    q.setParameter("id", info.getInt("headOrganization"));
    
    HeadOrganization res = q.getSingleResult();
    
    session.close();
    
    return res;
  }
  
  private Set<AssignmentSubdivision> getAssignmentSubdivision (Assignment ao, JSONArray headSubdivison) {
    Session session = sessionFactory.openSession();
    
    Set<AssignmentSubdivision> res = new HashSet<AssignmentSubdivision>();
    
    Query<HeadSubdivision> q = session
    .createQuery(
        "SELECT hs FROM HeadSubdivision hs WHERE hs.id = :id",
         HeadSubdivision.class
     );
    
    headSubdivison.forEach(item -> {
      JSONObject hs = (JSONObject) item;
      q.setParameter("id", hs.getInt("id"));
      res.add(new AssignmentSubdivision(ao, q.getSingleResult()));
    });
    
    session.close();
    
    return res;
  }
}