package com.nab.smartchoice.db.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nab.smartchoice.db.entities.SupplierProduct;

@Repository
public interface SupplierProductRepository extends JpaRepository<SupplierProduct, Integer> {

  void deleteByProductId(Integer productId);

  List<SupplierProduct> findByProductId(Integer ProductId);
}
