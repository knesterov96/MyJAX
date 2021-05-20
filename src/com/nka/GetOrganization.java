package com.nka;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.json.JSONObject;

import com.nka.entities.Organization;

public class GetOrganization {
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
  
  public JSONObject getOrg(int id, String role) {
    setup();
    if(sessionFactory == null) {
      return new JSONObject("{\"status\": false}");
    }
    
    JSONObject res = getOrganization(id, role);
    
    exit();
    
    return res;
  }
  
  private JSONObject getOrganization (int id, String role) {
    Session session = sessionFactory.openSession();
    
    Query<Organization> q = session.createQuery(getQuery(role), Organization.class);
    q.setParameter("id", id);
    JSONObject res = q.getSingleResult().getOrgStruck();
    
    session.close();
    
    return res;
  }
  
  private String getQuery (String role) {
    String res = null;
    
    switch (role) {
    // получение организации через руководителя организации
    case "headOrganization":
      res = "SELECT o FROM Organization o " +
            "  INNER JOIN HeadOrganization ho " +
            "    ON o.id = ho.organization " +
            "WHERE ho.id = :id";
      break;
    
    // получение организации через начальника отдела
    case "headSubdivision":
      res = "SELECT o FROM Organization o " +
            "  INNER JOIN Subdivision s " +
            "    ON o.id = s.organization" +
            "  INNER JOIN HeadSubdivision hs " +
            "    ON s.id = hs.subdivision " +
            "WHERE hs.id = :id";
      break;
      
      // получение организации через сотрудника
      case "employee":
        res = "SELECT o FROM Organization o " +
              "  INNER JOIN Subdivision s " +
              "    ON o.id = s.organization" +
              "  INNER JOIN Employee e " +
              "    ON s.id = e.subdivision " +
              "WHERE e.id = :id";
        break;
    
    default:
      res = null;
      break;
    }
    
    return res;
  }
}