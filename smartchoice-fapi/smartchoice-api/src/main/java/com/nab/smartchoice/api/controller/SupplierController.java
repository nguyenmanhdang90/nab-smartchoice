package com.nab.smartchoice.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nab.smartchoice.api.services.SupplierService;
import com.nab.smartchoice.db.entities.Supplier;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {

  @Autowired
  private SupplierService supplierService;

  @GetMapping
  public ResponseEntity<List<Supplier>> getSupplier() {
    return ResponseEntity.ok(supplierService.getAllSupplier());
  }
}
