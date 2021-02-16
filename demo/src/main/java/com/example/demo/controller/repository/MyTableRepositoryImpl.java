package com.example.demo.controller.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class MyTableRepositoryImpl implements MyTableRepositoryCustom{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<MyTable> getAllStudentsLike(String like) {
        Query query = entityManager.createNativeQuery(
                "SELECT \"MyTable\".* FROM \"MyTable\" WHERE \"MyTable\".\"PERSONNAME\" like ?");
        query.setParameter(1, "%");

        return query.getResultList();
    }
}
