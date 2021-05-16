package com.nab.smartchoice.crawler.services;

import java.util.Optional;

import com.nab.smartchoice.crawler.dto.ProductPrice;

public interface PriceService {
  Optional<ProductPrice> getProductPrice(String productCode);
}
