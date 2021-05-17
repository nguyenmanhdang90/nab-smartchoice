package com.nab.smartchoice.api.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.nab.smartchoice.api.dto.LogAction;
import com.nab.smartchoice.api.services.ExternalLogService;
import com.nab.smartchoice.api.services.ProductService;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

  @InjectMocks
  private ProductController productController;

  @Mock
  private ProductService productService;

  @Mock
  private ExternalLogService externalLogService;

  @Test
  public void getProducts_should_callService() {
    String textSearch = "textSearch";
    productController.getProducts(textSearch);
    Mockito.verify(productService).getProductsByName(textSearch);
    Mockito.verify(externalLogService).sentLog(LogAction.SEARCH_PRODUCT.name(), textSearch);
  }

  @Test
  public void getProductsPrice_should_callService() {
    String productCode = "P1";
    productController.getProductsPrices(productCode);
    Mockito.verify(productService).getProductPrice(productCode);
    Mockito.verify(externalLogService).sentLog(LogAction.GET_PRICE.name(), productCode);
  }

}