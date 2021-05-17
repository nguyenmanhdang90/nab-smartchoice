package com.nab.smartchoice.api.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPrice implements Serializable {
  private static final long serialVersionUID = 8292485917531702333L;
  private String supplier;
  private String productCode;
  private Double price;
  private Double discount;
  private String description;
}
