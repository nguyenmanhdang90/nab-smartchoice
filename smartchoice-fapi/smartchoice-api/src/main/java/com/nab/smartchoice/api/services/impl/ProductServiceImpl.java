package com.nab.smartchoice.api.services.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.nab.smartchoice.api.services.ProductService;
import com.nab.smartchoice.db.entities.Product;
import com.nab.smartchoice.db.repo.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

  private static final String NAME = "name";

  @Autowired
  private ProductRepository productRepository;

  @Override
  public Page<Product> getProductsByName(String textSearch) {
    Pageable pageable = PageRequest.of(0, 10);
    if (StringUtils.isBlank(textSearch)) {
      // no text search only return first 10 products
      return productRepository.findAll(pageable);
    }
    Specification<Product> specs = (root, query, builder) -> builder.like(root.get(NAME), "%" + textSearch + "%");
    return productRepository.findAll(specs, pageable);
  }
}
