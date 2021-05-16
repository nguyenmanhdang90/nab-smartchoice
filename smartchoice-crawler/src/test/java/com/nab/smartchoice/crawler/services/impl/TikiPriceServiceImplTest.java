package com.nab.smartchoice.crawler.services.impl;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.nab.smartchoice.crawler.dto.ProductPrice;

@RunWith(MockitoJUnitRunner.class)
public class TikiPriceServiceImplTest {

  @InjectMocks
  private TikiPriceServiceImpl tikiPriceService;

  @Test
  public void getProductPrice_shouldReturnAnProduct() {
    Optional<ProductPrice> result = tikiPriceService.getProductPrice("P1");
    Assert.assertTrue(result.isPresent());
  }

}