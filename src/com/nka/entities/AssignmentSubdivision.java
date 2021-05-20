package com.nka.entities;

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

import org.json.JSONArray;
import org.json.JSONObject;

@Entity
@Table (name = "assignment_subdivision")
public class AssignmentSubdivision {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  
  @Column (name = "control")
  private Boolean control = false;
  
  @Column (name = "execution")
  private Boolean execution = false;
  
  @ManyToOne (fetch = FetchType.LAZY)
  @JoinColumn (
      name = "assignment",
      nullable = false
  )
  private Assignment assignment;
  
  @ManyToOne (fetch = FetchType.EAGER)
  @JoinColumn (
      name = "head_subdivision",
      nullable = false
  )
  private HeadSubdivision headSubdivision;
  
  @OneToMany (
      fetch = FetchType.EAGER,
      mappedBy = "assignmentSubdivision",
      cascade = CascadeType.ALL
  )
  private Set<AssignmentEmployee> assignmentEmployees = new HashSet<AssignmentEmployee>();
  
  public AssignmentSubdivision () { }
  
  public AssignmentSubdivision (
      HeadSubdivision headSubdivision
  ) {
    this.headSubdivision = headSubdivision;
  }
  
  public AssignmentSubdivision (
      Assignment assignment
  ) {
    this.assignment = assignment;
  }
  
  public AssignmentSubdivision (
      Assignment assignment,
      HeadSubdivision headSubdivision
  ) {
    this.assignment = assignment;
    this.headSubdivision = headSubdivision;
  }
  
  public AssignmentSubdivision (
      Assignment assignment,
      HeadSubdivision headSubdivision,
      Set<AssignmentEmployee> assignmentEmployees
  ) {
    this.assignment = assignment;
    this.headSubdivision = headSubdivision;
    this.assignmentEmployees = assignmentEmployees;
  }
  
  public AssignmentSubdivision (
      boolean control,
      boolean execution,
      Assignment assignment,
      HeadSubdivision headSubdivision,
      Set<AssignmentEmployee> assignmentEmployees
  ) {
    this.control = control;
    this.execution = execution;
    this.assignment = assignment;
    this.headSubdivision = headSubdivision;
    this.assignmentEmployees = assignmentEmployees;
  }
  
  public JSONObject getAllInfo () {
    JSONObject res = new JSONObject();
    res.put("id", id);
    res.put("control", control);
    res.put("execution", execution);
    
    res.put("headSubdivision", headSubdivision.toJSONObject());
    
    JSONArray assignmentEmployees = new JSONArray();
    this.assignmentEmployees.forEach(ae -> {
    	assignmentEmployees.put(ae.getAllInfo());
    });
    res.put("assignmentEmployees", assignmentEmployees);
    
    return res;
  }
  
  public void setExecution (boolean execution) {
    this.execution = execution;
  }
  
  public void setControl (boolean control) {
    this.control = control;
  }
}