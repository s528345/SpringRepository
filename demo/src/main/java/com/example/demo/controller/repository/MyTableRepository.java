package com.example.demo.controller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

@Repository
public interface MyTableRepository extends JpaRepository<MyTable, UUID>, MyTableRepositoryCustom {

}
