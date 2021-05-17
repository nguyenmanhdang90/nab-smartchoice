package com.nab.smartchoice.log.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nab.smartchoice.log.entities.Logs;

@Repository
public interface LogsRepository extends JpaRepository<Logs, Integer> {
}
