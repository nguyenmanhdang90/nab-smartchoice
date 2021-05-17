package com.nab.smartchoice.api.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.nab.smartchoice.api.dto.LogAction;
import com.nab.smartchoice.api.services.ExternalLogService;
import com.nab.smartchoice.api.services.SupplierService;

@RunWith(MockitoJUnitRunner.class)
public class SupplierControllerTest {

  @InjectMocks
  private SupplierController supplierController;

  @Mock
  private ExternalLogService externalLogService;

  @Mock
  private SupplierService supplierService;

  @Test
  public void getSupplier_ShouldCallServices() {
    supplierController.getSupplier();
    Mockito.verify(externalLogService).sentLog(LogAction.GET_SUPPLIER.name(), "");
    Mockito.verify(supplierService).getAllSupplier();

  }

}