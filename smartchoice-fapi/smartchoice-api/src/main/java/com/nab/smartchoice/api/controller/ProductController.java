package com.nab.smartchoice.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nab.smartchoice.api.dto.LogAction;
import com.nab.smartchoice.api.services.ExternalLogService;
import com.nab.smartchoice.api.services.ProductService;
import com.nab.smartchoice.db.entities.Product;
import com.nab.smartchoice.db.entities.SupplierProduct;

@RestController
@RequestMapping("/api/product")
public class ProductController {

  @Autowired
  private ProductService productService;

  @Autowired
  private ExternalLogService externalLogService;

  @GetMapping("/{text}")
  public ResponseEntity<Page<Product>> getProducts(@PathVariable("text") String textSearch) {
    externalLogService.sentLog(LogAction.SEARCH_PRODUCT.name(), textSearch);
    return ResponseEntity.ok(productService.getProductsByName(textSearch));
  }

  @GetMapping("/price/{product}")
  public ResponseEntity<List<SupplierProduct>> getProductsPrices(@PathVariable("product") String productCode) {
    externalLogService.sentLog(LogAction.GET_PRICE.name(), productCode);
    return ResponseEntity.ok(productService.getProductPrice(productCode));
  }
}
