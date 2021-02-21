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
public class DateTableRepositoryImpl implements DateTableRepositoryCustom{

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Override
    public List<DateTable> getAll() {

        EntityManager em = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = null;

//        try {
//            transaction = em.getTransaction();
//
//            transaction.begin();
//            Query query1 = em.createNativeQuery(
//                    "Insert into \"MyTestDateTable\" (\"date\") values (to_date('08-06-1997', 'DD-MM-RRRR'))"
//            );
//            int result = query1.executeUpdate();
//            System.out.println("result: " + result);
//            transaction.commit();
//        }
//        catch(RuntimeException ex){
//            if(transaction != null && transaction.isActive())
//                transaction.rollback();
//
//            // log error return invalid response
//        }



        Query query = em.createNativeQuery(
                "SELECT \"MyTestDateTable\".* FROM \"MyTestDateTable\"");

        List<Object[]> myList = query.getResultList(); // [ [id, name, age] , ... ]

        List<DateTable> returnValue = new ArrayList<DateTable>();

        for(int i = 0; i< myList.size(); i++){
            returnValue.add(new DateTable());
            returnValue.get(i).updateDataAccessObject(myList.get(i));
        }

        for(Object myTable : myList)
            System.out.println("print row: " + myTable.toString());

        return returnValue;
    }
}
