package com.nab.smartchoice.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nab.smartchoice.api.services.ProductService;
import com.nab.smartchoice.db.entities.Product;

@RestController
@RequestMapping("/api/product")
public class ProductController {

  @Autowired
  private ProductService productService;

  @GetMapping("/{text}")
  public ResponseEntity<Page<Product>> getProducts(@PathVariable("text") String textSearch) {
    return ResponseEntity.ok(productService.getProductsByName(textSearch));
  }
}
