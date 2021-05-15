package com.nab.smartchoice.api.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.nab.smartchoice.api.services.ProductService;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

  @InjectMocks
  private ProductController productController;

  @Mock
  private ProductService productService;

  @Test
  public void getProducts_should_callService() {
    String textSearch = "textSearch";
    productController.getProducts(textSearch);
    Mockito.verify(productService).getProductsByName(textSearch);
  }

}