package com.nab.smartchoice.api.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.nab.smartchoice.db.entities.Product;
import com.nab.smartchoice.db.entities.SupplierProduct;

public interface ProductService {
  Page<Product> getProductsByName(String textSearch);

  List<SupplierProduct> getProductPrice(String productCode);
}
