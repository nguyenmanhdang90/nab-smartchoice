package com.nab.smartchoice.db.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nab.smartchoice.db.entities.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

  Optional<Supplier> findByName(String name);
}
