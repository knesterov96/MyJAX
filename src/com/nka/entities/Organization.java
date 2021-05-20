package com.nka.entities;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.json.JSONArray;
import org.json.JSONObject;

@Entity
@Table (name = "organization")
public class Organization {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column (
             name = "name",
             length = 200,
             nullable = false
    )
    private String name;
    
    @Column (
             name = "physical",
             length = 200,
             nullable = false
    )
    private String physical;
    
    @Column (
             name = "legal",
             length = 200,
             nullable = false
    )
    private String legal;
    
    @OneToOne (mappedBy = "organization")
    private HeadOrganization headOrganization;
    
    @OneToMany (
        fetch = FetchType.LAZY,
        mappedBy = "organization"
    )
    private Set<Subdivision> subdivisions = new HashSet<Subdivision>();
    
    public Organization () { }
    
    public Organization (
        String name,
        String physical,
        String legal,
        HeadOrganization headOrganization,
        Set<Subdivision> subdivisions
    ) {
      this.name = name;
      this.physical = physical;
      this.legal = legal;
      this.headOrganization = headOrganization;
      this.subdivisions = subdivisions;
    }
    
    public int getId () {
      return id;
    }
    
    public void setId (int id) {
      this.id = id;
    }
    
    public String getName () {
      return name;
    }
    
    public void setName (String name) {
      this.name = name;
    }
    
    public String getPhysical () {
      return physical;
    }
    
    public void setPhysical (String physical) {
      this.physical = physical;
    }
    
    public String getLegal () {
      return legal;
    }
    
    public void setLegal (String legal) {
      this.legal = legal;
    }
    
    public HeadOrganization getHeadOrganization () {
      return headOrganization;
    }
    
    public void setHeadOrganization (HeadOrganization headOrganization) {
      this.headOrganization = headOrganization;
    }
    
    public Set<Subdivision> getSubdivisions () {
      return subdivisions;
    }
    
    public void setSubdivisions (Set<Subdivision> subdivisions) {
      this.subdivisions = subdivisions;
    }
    
    @Override
    public String toString() {
      String res =
             "\r\nНазвание: \"" + name + "\"" +
             "\r\nФизический адрес: \"" + physical + "\"" +
             "\r\nЮридический адрес:\"" + legal + "\"" +
             headOrganization.toString();
      
      if(subdivisions == null) {
        return res;
      }
      
      if(subdivisions.isEmpty()) {
        return res;
      }
      
      res += "\r\nПодразделения";
      for(Iterator<Subdivision> itar = subdivisions.iterator(); itar.hasNext();) {
        res += itar.next().toString();
      }
      
      return res;
    }
    
    public JSONObject toJSONObject() {
      JSONObject res = new JSONObject();
      res.put("name", name);
      res.put("physical", physical);
      res.put("legal", legal);
      
      if(headOrganization != null) {
        res.put("headOrganization", headOrganization.toJSONObject());
      }
      
      /*
      if(subdivisions == null) {
        return res;
      }
      
      JSONArray subdivisionesArr = new JSONArray();
      for(Iterator<Subdivision> itar = subdivisions.iterator(); itar.hasNext();) {
        subdivisionesArr.put(itar.next().toJSONObject());
      }
      res.put("subdivisions", subdivisionesArr);
      */
      
      return res;
    }
    
    public JSONObject getOrgStruck() {
      JSONObject res = toJSONObject();
      
      JSONArray subdivisions = new JSONArray();
      this.subdivisions.forEach(s -> {
        subdivisions.put(s.getOrgStruck());
      });
      
      res.put("children", subdivisions);
      
      return res;
    }
}