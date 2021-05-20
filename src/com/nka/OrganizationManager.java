package com.nka;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.json.JSONArray;

import com.nka.entities.Organization;

public class OrganizationManager {
  private SessionFactory sessionFactory;
  
  private void setup() {
    final StandardServiceRegistry registry =
            new StandardServiceRegistryBuilder()
            .configure("/resources/hibernate.cfg.xml")
            .build();
    try {
      sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    } catch (Exception ex) {
      StandardServiceRegistryBuilder.destroy(registry);
    }
    
    System.out.println("session factory after setup: " + sessionFactory);
  }
  
  private void exit() {
    if(sessionFactory == null) {
      return;
    }
    
    if(sessionFactory.isClosed() == false) {
      sessionFactory.close();
    }
  }
  
  public void read() {
    Session session = sessionFactory.openSession();
    
    Object org = session.get(Organization.class, 1);
    System.out.println(org);
    System.out.print("Is org contains class Organization: ");
    System.out.println(org instanceof Organization);
    
    List<Organization> orgs = session.createQuery("SELECT o FROM Organization o", Organization.class).getResultList();
    System.out.println("Count org is " + orgs.size());
    Iterator<Organization> itar = orgs.iterator();//session.createQuery("FROM Organization").iterate();
    System.out.println("Has one more " + itar.hasNext());
    
    while(itar.hasNext()) {
      System.out.println(itar.next().toString());
    }
    
    session.close();
  }
  
  public JSONArray getJSONArrayOrganization () {
    setup();
    
    JSONArray res = getJSONArray();
    
    exit();
    
    return res;
  }
  
  private JSONArray getJSONArray() {
    if(sessionFactory == null) {
      return new JSONArray();
    }
    
    Session session = sessionFactory.openSession();
    
    JSONArray res = new JSONArray();
    
    List<Organization> orgs = session.createQuery("SELECT o FROM Organization o", Organization.class).getResultList();
    for(Iterator<Organization> itar = orgs.iterator(); itar.hasNext();) {
      res.put(itar.next().toJSONObject());
    }
    //orgs.stream().forEach(organization -> res.put(organization.toJSONObject()));
    //List<Organization> orgs = session.createQuery("SELECT o FROM Organization o", Organization.class).getResultList();
    
    session.close();
    
    return res;
  }
  
  public static void main(String[] args) {
    System.out.println("Start");
      
    OrganizationManager manager = new OrganizationManager();
    manager.setup(); 
    
    //manager.create();
    manager.read();
    
    manager.exit();
    System.out.println("End");
  }
}