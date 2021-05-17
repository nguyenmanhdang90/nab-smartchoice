package com.nab.smartchoice.log.entities;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "`logs`")
@AllArgsConstructor
@NoArgsConstructor
public class Logs implements Serializable {
  private static final long serialVersionUID = -6286396777131546640L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`id`")
  private int id;

  @Column(name = "`username`")
  private String username;

  @Column(name = "`action`")
  private String action;

  @Column(name = "`details`")
  private String details;

  @Column(name = "`timestamp`")
  private Date timestamp;

}
