package com.nab.smartchoice.db.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "`supplier`")
@AllArgsConstructor
@NoArgsConstructor
public class Supplier implements Serializable {


  private static final long serialVersionUID = 4833550308234551011L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`id`")
  private int id;

  @Column(name = "`name`")
  private String name;

  @Column(name = "`description`")
  private String description;

  @Column(name = "`token`")
  private String token;
}
