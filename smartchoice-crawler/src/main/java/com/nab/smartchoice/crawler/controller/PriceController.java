package com.nab.smartchoice.crawler.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nab.smartchoice.crawler.dto.ProductPrice;
import com.nab.smartchoice.crawler.services.PriceService;

@RestController
@RequestMapping("/api/price")
public class PriceController {

  @Autowired
  private List<PriceService> priceServices;

  @GetMapping("/{product}")
  public ResponseEntity<List<ProductPrice>> getProducts(@PathVariable("product") String productCode) {
    List<ProductPrice> productPrices = priceServices.stream()
        .map(service -> service.getProductPrice(productCode))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toList());
    return ResponseEntity.ok(productPrices);
  }
}
