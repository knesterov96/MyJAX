package com.nka.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.json.JSONArray;
import org.json.JSONObject;

@Entity
@Table (name = "assignment")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column (name = "subject")
    private String subject;
    
    @Column (name = "text")
    private String text;
    
    @Temporal (TemporalType.DATE)
    @Column (name = "end_date")
    private Date endDate;
    
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (
        name = "head_organization",
        nullable = false
    )
    private HeadOrganization headOrganization;
    
    @OneToMany (
        fetch = FetchType.EAGER,
        mappedBy = "assignment",
        cascade = CascadeType.ALL
    )
    private Set<AssignmentSubdivision> assignmentSubdivisions = new HashSet<AssignmentSubdivision>();
    
    public Assignment () {}
    
    // конструктор для создания информации о поручении
    public Assignment (
        String subject,
        String text,
        Date endDate,
        HeadOrganization headOrganization,
        Set<AssignmentSubdivision> assignmentSubdivisions
    ) {
      this.subject = subject;
      this.text = text;
      this.endDate = endDate;
      this.headOrganization = headOrganization;
      this.assignmentSubdivisions = assignmentSubdivisions;
    }
    
    public int getId () {
      return id;
    }
    
    public void setId (int id) {
      this.id = id;
    }
    
    public String getSubject() {
      return subject;
    }
    
    public void setSubject (String subject) {
      this.subject = subject;
    }
    
    public String getText () {
      return text;
    }
    
    public void setText (String text) {
      this.text = text;
    }
    
    public Date getEndDate () {
      return endDate;
    }
    
    public void setEndDate (Date endDate) {
      this.endDate = endDate;
    }
    
    public HeadOrganization getHeadOrganization () {
      return headOrganization;
    }
    
    public void setHeadOrganization (HeadOrganization headOrganization) {
      this.headOrganization = headOrganization;
    }
    
    public Set<AssignmentSubdivision> getAssignmentSubdivision () {
      return assignmentSubdivisions;
    }
    
    public void setAssignmentSubdivision (Set<AssignmentSubdivision> assignmentSubdivisions) {
      this.assignmentSubdivisions = assignmentSubdivisions;
    }
    
    @Override
    public String toString() {
      return "\r\nОбъект поручения: \"" + subject + "\"" +
             "\r\nТекст поручения: \"" + text + "\"" +
             "\r\n";
    }
    
    // получение краткой информации о поручении
    public JSONObject getShortInfo () {
      JSONObject res = new JSONObject();
      
      res.put("id", id);
      res.put("name", subject);
      
      return res;
    }
    
    // получение полной информации о поручении
    public JSONObject getAllInfo () {
      JSONObject res = new JSONObject();
      res.put("id", id);
      res.put("subject", subject);
      res.put("text", text);
      res.put("endDate", endDate);
      
      res.put("headOrganization", headOrganization.toJSONObject());
      
      JSONArray assignmentSubdivsions = new JSONArray();
      this.assignmentSubdivisions.forEach(as -> {
          assignmentSubdivsions.put(as.getAllInfo());
      });
      res.put("assignmentSubdivsions", assignmentSubdivsions);
      
      return res;
    }
}