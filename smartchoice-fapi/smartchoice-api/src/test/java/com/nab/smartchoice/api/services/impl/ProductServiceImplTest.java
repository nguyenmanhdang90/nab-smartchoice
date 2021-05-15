package com.nab.smartchoice.api.services.impl;

import java.util.List;
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

import com.nab.smartchoice.db.entities.Product;
import com.nab.smartchoice.db.repo.ProductRepository;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

  @InjectMocks
  private ProductServiceImpl productService;

  @Mock
  private ProductRepository productRepository;

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

}