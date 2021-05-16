package com.nab.smartchoice.crawler.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.nab.smartchoice.crawler.dto.ProductPrice;
import com.nab.smartchoice.crawler.services.PriceService;

@RunWith(MockitoJUnitRunner.class)
public class PriceControllerTest {

  @InjectMocks
  private PriceController priceController;

  @Mock
  private List<PriceService> priceServices;

  @Test
  public void getProducts_should_callAllThirdPartyService() {
    String product = "product";
    PriceService priceService = Mockito.mock(PriceService.class);
    Mockito.when(priceServices.stream()).thenReturn(Arrays.asList(priceService).stream());
    Mockito.when(priceService.getProductPrice(product)).thenReturn(Optional.of(ProductPrice.builder().build()));
    ResponseEntity<List<ProductPrice>> result = priceController.getProducts(product);
    Assert.assertEquals(1, result.getBody().size());
  }

}