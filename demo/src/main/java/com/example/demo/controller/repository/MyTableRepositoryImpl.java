package com.example.demo.controller.repository;

import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MyTableRepositoryImpl implements MyTableRepositoryCustom, com.example.demo.controller.repository.Repository {

//    @PersistenceContext
//    EntityManager entityManager;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Override
    public List<MyTable> getAllStudentsLike(@NotNull final String like) {

        EntityManager em = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = null;

        final String TABLE_NAME = tableName();

        try {
            transaction = em.getTransaction();

            transaction.begin();
//            Query query1 = em.createNativeQuery(
//                    "Insert into \"MyTable\" (\"personName\", \"age\") values (\'liz\', 50) "
//            );

            Query query1 = em.createNativeQuery("Update \"MyTable\" set \"personName\" = \'nick\'" +
                    "where \"personName\" = \'nickk\' ");

            int result = query1.executeUpdate();
            System.out.println("result from update: " + result);
            transaction.commit();
        }
        catch(RuntimeException ex){
            if(transaction != null && transaction.isActive())
                transaction.rollback();

            // log error return invalid response
        }



        Query query = em.createNativeQuery(
                "SELECT " + tableName() + ".* FROM \"MyTable\" WHERE \"MyTable\".\"personName\" like ?");
        query.setParameter(1, like + "%"); // "n%" "nickdjkafsdklhjaesdlkj%"

        List<Object[]> myList = query.getResultList(); // [ [id, name, age] , ... ]

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
    public List<String> getAllStudentNamesLike(@NotNull final String like) {

        EntityManager em = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = em.getTransaction();

        Query query = em.createNativeQuery(
                "SELECT \"MyTable\".\"personName\" FROM \"MyTable\" WHERE \"MyTable\".\"personName\" like ?");
        query.setParameter(1, like + "%");

        return query.getResultList();
    }

    @Override
    public String tableName() {
        return "\"MyTable\"";
    }
}
