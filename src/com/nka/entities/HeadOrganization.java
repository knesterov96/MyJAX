package com.nka.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table (name = "head_organization")
public class HeadOrganization {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column (
             name = "surname",
             length = 50,
             nullable = false
    )
    private String surname;
    
    @Column (
             name = "name",
             length = 50,
             nullable = false
    )
    private String name;
    
    @Column (
             name = "patronymic",
             length = 50,
             nullable = false
    )
    private String patronymic;
    
    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "organization")
    private Organization organization;
    
    @OneToMany (
        fetch = FetchType.LAZY,
        mappedBy = "headOrganization"
    )
    private Set<Assignment> assignments = new HashSet<Assignment>();
    
    public HeadOrganization () { }
    
    public HeadOrganization (
        String surname,
        String name,
        String patronymic,
        Organization organization,
        Set<Assignment> assignments
    ) {
      this.surname = surname;
      this.name = name;
      this.patronymic = patronymic;
      this.organization = organization;
      this.assignments = assignments;
    }
    
    public int getId () {
      return id;
    }
    
    public void setId (int id) {
      this.id = id;
    }
    
    public String getSurname () {
      return surname;
    }
    
    public void setSurname (String surname) {
      this.surname = surname;
    }
    
    public String getName () {
      return name;
    }
    
    public void setName (String name) {
      this.name = name;
    }
    
    public String getPatronymic () {
      return patronymic;
    }
    
    public void setPatronymic (String patronymic) {
      this.patronymic = patronymic;
    }
    
    public Organization getOrganization () {
      return organization;
    }
    
    public void setOrganization (Organization organization) {
      this.organization = organization;
    }
    
    public Set<Assignment> getAssignments () {
      return assignments;
    }
    
    public void setAssignment (Set<Assignment> assignments) {
      this.assignments = assignments;
    }
    
    @Override
    public String toString() {
      return "\r\nФамилия руководителя организации: \"" + surname + "\"" +
             "\r\nИмя руководителя организации: \"" + name + "\"" +
             "\r\nОтчество руководителя организации:\"" + patronymic + "\"" +
             //organization.toString() +
             "\r\n";
    }
    
    public JSONObject toJSONObject() {
      JSONObject res = new JSONObject();
      res.put("id", id);
      res.put("surname", surname);
      res.put("name", name);
      res.put("patronymic", patronymic);
      return res;
    }
    
    public JSONObject toJSONObjectWithPos() {
      JSONObject res = new JSONObject();
      res.put("id", id);
      res.put("surname", surname);
      res.put("name", name);
      res.put("patronymic", patronymic);
      res.put("position", new JSONObject("{\"text\": \"РУКОВОДИТЕЛЬ\"}"));
      return res;
    }
}