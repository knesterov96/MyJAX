package com.nka.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table (name = "head_subdivision")
public class HeadSubdivision {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column (name = "surname")
    private String surname;
    
    @Column (name = "name")
    private String name;
    
    @Column (name = "patronymic")
    private String patronymic;
    
    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn (
        name = "subdivision",
        nullable = false
    )
    private Subdivision subdivision;
    
    public HeadSubdivision () { }
    
    public HeadSubdivision (
        String surname,
        String name,
        String patronymic,
        Subdivision subdivision
    ) {
      this.surname = surname;
      this.name = name;
      this.patronymic = patronymic;
      this.subdivision = subdivision;
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
    
    @Override
    public String toString() {
      return "\r\nФамилия начальника отдела: \"" + surname + "\"" +
             "\r\nИмя начальника отдела: \"" + name + "\"" +
             "\r\nОтчество начальника отдела:\"" + patronymic + "\"" +
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
      res.put("position", new JSONObject("{\"text\": \"НАЧАЛЬНИК ОТДЕЛА\"}"));
      return res;
    }
}