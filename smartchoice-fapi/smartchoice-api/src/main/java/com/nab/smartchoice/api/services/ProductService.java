package com.nab.smartchoice.api.services;

import org.springframework.data.domain.Page;

import com.nab.smartchoice.db.entities.Product;

public interface ProductService {
  Page<Product> getProductsByName(String textSearch);
}
