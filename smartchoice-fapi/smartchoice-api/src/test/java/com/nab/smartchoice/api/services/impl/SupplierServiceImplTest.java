package com.nab.smartchoice.api.services.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.nab.smartchoice.db.repo.SupplierRepository;

@RunWith(MockitoJUnitRunner.class)
public class SupplierServiceImplTest {

  @InjectMocks
  private SupplierServiceImpl supplierService;

  @Mock
  private SupplierRepository supplierRepository;

  @Test
  public void getAll_shouldCallSupplierRepo() {
    supplierService.getAllSupplier();
    Mockito.verify(supplierRepository).findAll();
  }

}