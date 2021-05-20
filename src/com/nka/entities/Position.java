package com.nka.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table (name = "position")
public class Position {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column (name = "text")
    private String text;
    
    public Position () { }
    
    public Position (
        String text
    ) {
      this.text = text;
    }
    
    public int getId () {
      return id;
    }
    
    public void setId (int id) {
      this.id = id;
    }
    
    public String getText () {
      return text;
    }
    
    public void setText (String text) {
      this.text = text;
    }
    
    @Override
    public String toString() {
      return "\r\nНазвание должности: \"" + text + "\"" +
             "\r\n";
    }
    
    public JSONObject toJSONObject() {
      JSONObject res = new JSONObject();
      res.put("text", text);
      return res;
    }
    
    public JSONObject getOrgStruck() {
      JSONObject res = new JSONObject();
      res.put("text", text);
      return res;
    }
}