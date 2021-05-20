package com.nka.entities;

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
@Table (name = "assignment_employee")
public class AssignmentEmployee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  
  @ManyToOne (fetch = FetchType.EAGER)
  @JoinColumn (
      name = "head_subdivision",
      nullable = false
  )
  private AssignmentSubdivision assignmentSubdivision;
  
  @ManyToOne (fetch = FetchType.EAGER)
  @JoinColumn (
      name = "employee",
      nullable = false
  )
  private Employee employee;
  
  public AssignmentEmployee () { }
  
  public AssignmentEmployee (
      AssignmentSubdivision assignmentSubdivision,
      Employee employee
  ) {
    this.assignmentSubdivision = assignmentSubdivision;
    this.employee = employee;
  }
  
  public JSONObject getAllInfo() {
    return employee.getInfo();
  }
}