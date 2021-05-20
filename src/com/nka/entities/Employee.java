package com.nka.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table (name = "employee")
public class Employee {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column (name = "surname")
    private String surname;
    
    @Column (name = "name")
    private String name;
    
    @Column (name = "patronymic")
    private String patronymic;
    
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (
        name = "subdivision",
        nullable = false
    )
    private Subdivision subdivision;
    
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (
        name = "position",
        nullable = false
    )
    private Position position;
    
    public Employee () { }
    
    public Employee (
        String surname,
        String name,
        String patronymic,
        Subdivision subdivision,
        Position position
    ) {
      this.surname = surname;
      this.name = name;
      this.patronymic = patronymic;
      this.subdivision = subdivision;
      this.position = position;
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
    
    public Position getPosition () {
      return position;
    }
    
    public void setPosition (Position position) {
      this.position = position;
    }
    
    public Subdivision getSubdivision () {
      return subdivision;
    }
    
    public void setSubdivision (Subdivision subdivision) {
      this.subdivision = subdivision;
    }
    
    @Override
    public String toString() {
      return "\r\nФамилия сотрудника: \"" + surname + "\"" +
             "\r\nИмя сотрудника: \"" + name + "\"" +
             "\r\nОтчество сотрудника:\"" + patronymic + "\"" +
             position.toString();
    }
    
    public JSONObject toJSONObject() {
      JSONObject res = new JSONObject();
      res.put("id", id);
      res.put("surname", surname);
      res.put("name", name);
      res.put("patronymic", patronymic);
      //res.put("position", position.toJSONObject());
      return res;
    }
    
    public JSONObject getOrgStruck() {
      JSONObject res = getInfo();
      res.put("position", position.getOrgStruck());
      
      return res;
    }
    
    public JSONObject getInfo() {
      JSONObject res = new JSONObject();
      res.put("id", id);
      res.put("name", surname + " " + name + " " + patronymic);
      return res;
    }
}