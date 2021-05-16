package com.nab.smartchoice.crawler.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.nab.smartchoice.crawler.dto.ProductPrice;
import com.nab.smartchoice.crawler.services.PriceService;

@Service
public class LazadaPriceServiceImpl implements PriceService {

  private static final List<ProductPrice> MOCK_DATA_FROM_LAZADA = IntStream.range(0, 20).boxed()
      .map(i -> ProductPrice.builder()
          .supplier("Lazada")
          .productCode("P" + i)
          .price(Math.random())
          .discount(new Random().nextDouble() * (0.5 - 0.0) + 0.0)
          .description("Description for product #" + i)
          .build()
      ).collect(Collectors.toList());

  @Override
  public Optional<ProductPrice> getProductPrice(String productCode) {
    return MOCK_DATA_FROM_LAZADA.stream()
        .filter(product -> productCode.equals(product.getProductCode()))
        .findFirst();
  }
}
