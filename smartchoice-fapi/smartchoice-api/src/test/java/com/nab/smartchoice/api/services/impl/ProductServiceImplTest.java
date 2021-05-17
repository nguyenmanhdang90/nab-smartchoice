package com.nab.smartchoice.api.services.impl;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.client.RestTemplate;

import com.nab.smartchoice.api.dto.ProductPrice;
import com.nab.smartchoice.db.entities.Product;
import com.nab.smartchoice.db.entities.Supplier;
import com.nab.smartchoice.db.entities.SupplierProduct;
import com.nab.smartchoice.db.repo.ProductRepository;
import com.nab.smartchoice.db.repo.SupplierProductRepository;
import com.nab.smartchoice.db.repo.SupplierRepository;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

  @InjectMocks
  private ProductServiceImpl productService;

  @Mock
  private ProductRepository productRepository;

  @Mock
  private SupplierProductRepository supplierProductRepository;

  @Mock
  private RestTemplate restTemplate;

  @Mock
  private SupplierRepository supplierRepository;

  @Test
  public void getProductsByName_should_return10RecordsWhenTextSearchEmpty() {
    String textSearch = "";
    Pageable pageable = PageRequest.of(0, 10);
    List<Product> result =
        IntStream.range(0, 10).boxed().map(i -> Product.builder().build()).collect(Collectors.toList());
    Page<Product> page = new PageImpl<>(result);
    Mockito.when(productRepository.findAll(pageable)).thenReturn(page);

    Page<Product> response = productService.getProductsByName(textSearch);
    Assert.assertEquals(10, response.getNumberOfElements());
  }

  @Test
  public void getProductsByName_should_return10RecordsWhenTextSearchNotEmpty() {
    String textSearch = "textSearch";
    List<Product> result =
        IntStream.range(0, 10).boxed().map(i -> Product.builder().build()).collect(Collectors.toList());
    Page<Product> page = new PageImpl<>(result);
    Mockito.when(productRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class)))
        .thenReturn(page);

    Page<Product> response = productService.getProductsByName(textSearch);
    Assert.assertEquals(10, response.getNumberOfElements());
  }

  @Test(expected = IllegalArgumentException.class)
  public void getProductPrice_should_ThrowExceptionWhenNoProductFound() {
    String productCode = "P1";
    Mockito.when(productRepository.findByCode(productCode)).thenReturn(Optional.empty());
    productService.getProductPrice(productCode);
  }

  @Test
  public void getProductPrice_fromDB() {
    String productCode = "P1";
    Product product = Product.builder().id(1).build();
    Date twentyFourHourFromNow = Date.from(new Date().toInstant().plus(Duration.ofHours(24)));
    SupplierProduct supplierProduct = SupplierProduct.builder().validUntil(twentyFourHourFromNow).build();
    List<SupplierProduct> supplierProducts = Arrays.asList(supplierProduct);
    Mockito.when(productRepository.findByCode(productCode)).thenReturn(Optional.of(product));
    Mockito.when(supplierProductRepository.findByProductId(product.getId())).thenReturn(supplierProducts);
    List<SupplierProduct> result = productService.getProductPrice(productCode);
    Assert.assertEquals(1, result.size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void getProductPrice_emptyFromDB_NotFoundSupplier() {
    String productCode = "P1";
    Product product = Product.builder().id(1).build();
    ProductPrice[] productPrices = {ProductPrice.builder().supplier("TIKI").build()};

    Mockito.when(productRepository.findByCode(productCode)).thenReturn(Optional.of(product));
    Mockito.when(supplierProductRepository.findByProductId(product.getId())).thenReturn(Collections.emptyList());
    Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any())).thenReturn(productPrices);
    Mockito.when(supplierRepository.findByName("TIKI")).thenReturn(Optional.empty());

    productService.getProductPrice(productCode);
  }

  @Test
  public void getProductPrice_emptyFromDB() {
    String productCode = "P1";
    Product product = Product.builder().id(1).build();
    ProductPrice[] productPrices = {ProductPrice.builder().supplier("TIKI").build()};

    Mockito.when(productRepository.findByCode(productCode)).thenReturn(Optional.of(product));
    Mockito.when(supplierProductRepository.findByProductId(product.getId())).thenReturn(Collections.emptyList());
    Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any())).thenReturn(productPrices);
    Mockito.when(supplierRepository.findByName("TIKI")).thenReturn(Optional.of(Supplier.builder().id(1).build()));

    productService.getProductPrice(productCode);
    Mockito.verify(supplierProductRepository).deleteByProductId(1);
    Mockito.verify(supplierProductRepository).flush();
  }

}