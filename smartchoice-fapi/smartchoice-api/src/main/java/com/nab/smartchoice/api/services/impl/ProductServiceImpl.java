package com.nab.smartchoice.api.services.impl;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nab.smartchoice.api.dto.ProductPrice;
import com.nab.smartchoice.api.services.ProductService;
import com.nab.smartchoice.db.entities.Product;
import com.nab.smartchoice.db.entities.Supplier;
import com.nab.smartchoice.db.entities.SupplierProduct;
import com.nab.smartchoice.db.repo.ProductRepository;
import com.nab.smartchoice.db.repo.SupplierProductRepository;
import com.nab.smartchoice.db.repo.SupplierRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

  private static final String NAME = "name";

  @Value("${crawler.url}")
  private String crawlerURL;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private SupplierProductRepository supplierProductRepository;

  @Autowired
  private SupplierRepository supplierRepository;

  @Autowired
  @Qualifier("restTemplateForCrawler")
  private RestTemplate restTemplate;

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

  @Override
  public List<SupplierProduct> getProductPrice(String productCode) {
    Product product = productRepository.findByCode(productCode)
        .orElseThrow(() -> new IllegalArgumentException("Product code not valid"));
    List<SupplierProduct> supplierProductsFromDB = supplierProductRepository.findByProductId(product.getId());

    boolean isAnyPriceOutDate =
        supplierProductsFromDB.stream().map(SupplierProduct::getValidUntil).anyMatch(new Date()::after);

    if (supplierProductsFromDB.isEmpty() || isAnyPriceOutDate) {
      return getFromCrawler(product);
    }
    return supplierProductsFromDB;
  }

  private List<SupplierProduct> getFromCrawler(Product product) {
    List<ProductPrice> productPrices =
        Arrays.asList(
            restTemplate.getForObject(crawlerURL + product.getCode(), ProductPrice[].class));
    return storeToDB(product, productPrices);
  }

  private List<SupplierProduct> storeToDB(Product product, List<ProductPrice> productPrices) {
    Date validUntil = Date.from(new Date().toInstant().plus(Duration.ofHours(24)));
    supplierProductRepository.deleteByProductId(product.getId());
    supplierProductRepository.flush();
    List<SupplierProduct> toSave = productPrices.stream().map(productPrice -> SupplierProduct.builder()
        .supplierId(supplierRepository.findByName(productPrice.getSupplier())
            .map(Supplier::getId)
            .orElseThrow(() -> new IllegalArgumentException("Wrong supplier")))
        .productId(product.getId())
        .price(productPrice.getPrice())
        .discount(productPrice.getDiscount())
        .description(productPrice.getDescription())
        .validUntil(validUntil)
        .build())
        .collect(Collectors.toList());
    return supplierProductRepository.saveAll(toSave);
  }
}
