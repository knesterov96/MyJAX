package com.nka;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONObject;

import com.nka.entities.Assignment;

// класс для получения поручений
public class GetAssignment {
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
  
  public JSONArray getAllAssignment(int id, String role) {
    setup();
    if(sessionFactory == null) {
      return new JSONArray();
    }
    
    JSONArray res = new JSONArray();
    JSONObject assig = new JSONObject();
    assig.put("name", "Все поручения");
    assig.put("children", startGetAssignment(id, getQueryAllAssignment(role)));
    res.put(assig);
    
    exit();
    
    return res;
  }
  
  public JSONArray getMyAssignment(int id, String role) {
    setup();
    if(sessionFactory == null) {
      return new JSONArray();
    }
    
    JSONArray res = new JSONArray();
    JSONObject assig = new JSONObject();
    assig.put("name", "Мои поручения");
    assig.put("children", startGetAssignment(id, getQueryMyAssignment(role)));
    res.put(assig);
    
    exit();
    
    return res;
  }
  
  public JSONArray startGetAssignment(int id, String query) {
    Session session = sessionFactory.openSession();
    
    JSONArray res = new JSONArray();
    
    Query<Assignment> q = session.createQuery(
        query,
        Assignment.class
    );
    
    q.setParameter("id", id);
    q.getResultList().forEach(a -> {
      res.put(a.getShortInfo());
    });
    
    session.close();
    
    return res;
  }
  
  private String getQueryMyAssignment (String role) {
    String res = null;
    
    switch (role) {
    // получение организации через руководителя организации
    case "headOrganization":
      res = "SELECT a FROM Assignment a " +
            "WHERE a.headOrganization.id = :id";
      break;
    
    // получение организации через начальника отдела
    case "headSubdivision":
      res = "SELECT a FROM Assignment a " +
            "  INNER JOIN AssignmentSubdivision asub " +
            "    ON a.id = asub.assignment " +
            "WHERE asub.headSubdivision.id = :id";
      break;
    
    // получение организации через сотрудника
    case "employee":
      res = "SELECT a FROM Assignment a " +
              "  INNER JOIN AssignmentSubdivision asub " +
              "    ON a.id = asub.assignment " +
              "  INNER JOIN AssignmentEmployee ae " +
              "    ON asub.id = ae.assignmentSubdivision " +
              "WHERE ae.employee.id = :id";
      break;
    
    default:
      res = null;
      break;
    }
    
    return res;
  }
  
  private String getQueryAllAssignment (String role) {
    String res = null;
    
    switch (role) {
    // получение организации через руководителя организации
    case "headOrganization":
      res = "SELECT a FROM Assignment a " +
            "WHERE a.headOrganization IN (" +
            "  SELECT ho.id FROM HeadOrganization ho " +
            "    INNER JOIN Organization o " +
            "      ON o.id = ho.organization " +
            "  WHERE ho.id = :id " +
            ")";
      break;
    
    // получение организации через начальника отдела
    case "headSubdivision":
      res = "SELECT a FROM Assignment a " +
            "WHERE a.headOrganization IN (" +
            "  SELECT ho.id FROM HeadOrganization ho " +
            "    INNER JOIN Organization o " +
            "      ON o.id = ho.organization " +
            "    INNER JOIN Subdivision s " +
            "      ON o.id = s.organization " +
            "    INNER JOIN HeadSubdivision hs " +
            "      ON s.id = hs.subdivision " +
            "  WHERE hs.id = :id " +
            ")";
      break;
    
    // получение организации через сотрудника
    case "employee":
      res = "SELECT a FROM Assignment a " +
            "WHERE a.headOrganization IN (" +
            "  SELECT ho.id FROM HeadOrganization ho " +
            "    INNER JOIN Organization o " +
            "      ON o.id = ho.organization " +
            "    INNER JOIN Subdivision s " +
            "      ON o.id = s.organization " +
            "    INNER JOIN Employee e " +
            "      ON s.id = e.subdivision " +
            "  WHERE e.id = :id " +
            ")";
      break;
    
    default:
      res = null;
      break;
    }
    
    return res;
  }
}