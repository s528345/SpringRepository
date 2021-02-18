package com.example.demo.controller.repository;

import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MyTableRepositoryImpl implements MyTableRepositoryCustom{

//    @PersistenceContext
//    EntityManager entityManager;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Override
    public List<MyTable> getAllStudentsLike(String like) {

        EntityManager em = entityManagerFactory.createEntityManager();

//        EntityTransaction transaction = em.getTransaction();
//
//       transaction.begin();
//        Query query1 = em.createNativeQuery(
//                "Insert into \"MyTable\" (\"personName\", \"age\") values (\'ryan\', 30) "
//        );
//        int result = query1.executeUpdate();
//        System.out.println("result: " + result);
//        transaction.commit();

        Query query = em.createNativeQuery(
                "SELECT \"MyTable\".* FROM \"MyTable\" WHERE \"MyTable\".\"personName\" like ?");
        query.setParameter(1, like + "%");

        List<Object[]> myList = query.getResultList();

        List<MyTable> returnValue = new ArrayList<MyTable>();

        for(int i = 0; i< myList.size(); i++){
            returnValue.add(new MyTable());
            returnValue.get(i).updateDataAccessObject(myList.get(i));
        }

        for(Object myTable : myList)
            System.out.println("print row: " + myTable.toString());

        return returnValue;
    }

    @Override
    public List<String> getAllStudentNamesLike(String like) {

        EntityManager em = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = em.getTransaction();

        Query query = em.createNativeQuery(
                "SELECT \"MyTable\".\"personName\" FROM \"MyTable\" WHERE \"MyTable\".\"personName\" like ?");
        query.setParameter(1, like + "%");

        return query.getResultList();
    }

}
