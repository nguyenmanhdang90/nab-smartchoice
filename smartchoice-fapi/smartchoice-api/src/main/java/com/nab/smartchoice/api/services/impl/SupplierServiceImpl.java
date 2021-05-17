package com.nab.smartchoice.api.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nab.smartchoice.api.services.SupplierService;
import com.nab.smartchoice.db.entities.Supplier;
import com.nab.smartchoice.db.repo.SupplierRepository;

@Service
public class SupplierServiceImpl implements SupplierService {
  @Autowired
  private SupplierRepository supplierRepository;


  @Override
  public List<Supplier> getAllSupplier() {
    return supplierRepository.findAll();
  }
}
