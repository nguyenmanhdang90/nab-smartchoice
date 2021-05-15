package com.nab.smartchoice.db.entities;

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
@Table(name = "`supplier_product`")
@AllArgsConstructor
@NoArgsConstructor
public class SupplierProduct implements Serializable {
  private static final long serialVersionUID = -894206350964002047L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`id`")
  private int id;

  @Column(name = "`supplier_id`")
  private Integer supplierId;

  @Column(name = "`product_id`")
  private Integer productId;

  @Column(name = "`price`")
  private Double price;

  @Column(name = "`discount`")
  private Double discount;

  @Column(name = "`description`")
  private String description;

  @Column(name = "`valid_until`")
  private Date validUntil;


}
