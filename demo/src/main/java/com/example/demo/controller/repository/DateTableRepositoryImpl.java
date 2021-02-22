package com.example.demo.controller.repository;

import com.example.demo.DataAccessConversionException;
import org.hibernate.Transaction;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Repository
public class DateTableRepositoryImpl implements DateTableRepositoryCustom{

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Override
    public List<DateTable> getAll() {

        // tuple test
        Pair<Integer, Integer> pair = DateTable.giveDummyTuple();

//          Object[] testArray = new Object[2];
//        String testValue = (String)testArray[0];

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

//        Query query2 = em.createNativeQuery("SELECT \"NWTXDT\".* FROM \"NWTXDT\" ");
//        List<Object[]> myList1 = query2.getResultList();
//        for(Object object : myList1.get(0))
//            System.out.println(object == null ? "Yes it's null" : "Not here buckeroo");

        for(Object[] objects : myList)
            System.out.println("size: " + objects.length + "\ndate: " + objects[1]);

        List<DateTable> returnValue = new ArrayList<DateTable>();

        DateTableRepositoryImpl.createDataAccessObjects(myList, returnValue, DateTable::new);

//        for(int i = 0; i< myList.size(); i++){
//            returnValue.add(new DateTable());
//            returnValue.get(i).updateDataAccessObject(myList.get(i));
//        }

        for(Object myTable : myList)
            System.out.println("print row: " + myTable.toString());

        return returnValue;
    }

    public static <U extends List<T>, T extends DataAccessConversion> void createDataAccessObjects(
            @NotNull final List<? extends Object[]> data, @NotNull final U dataList,
            @NotNull final Supplier<T> typeConstructor) throws DataAccessConversionException, IllegalArgumentException {

        if(dataList.size() != 0)
            throw new IllegalArgumentException("list size of data access must be 0");

        try{
            for(int i = 0; i < data.size(); i++){
                dataList.add(typeConstructor.get());
                dataList.get(i).updateDataAccessObject(data.get(i));
            }
        }
        catch(Exception e){
            throw new DataAccessConversionException("" +
                    "DataAccess type conversion failed. Make sure Object[] matches the column" +
                    "order of the table's DDL implementation and that the DataAccessConversion implementation" +
                    "matches the table's DDL implementation.");
        }

    }

    public static <T extends DataAccessConversion> T createDataAccessObject(
            @NotNull final Object[] data, @NotNull final T dataList,
            @NotNull final Supplier<T> typeConstructor) throws DataAccessConversionException, IllegalArgumentException {

        try{
            T returnValue = typeConstructor.get();
            returnValue.updateDataAccessObject(data);
            return returnValue;
        }
        catch(Exception e){
            throw new DataAccessConversionException("" +
                    "DataAccess type conversion failed. Make sure Object[] matches the column" +
                    "order of the table's DDL implementation and that the DataAccessConversion implementation" +
                    "matches the table's DDL implementation.");
        }

    }
}
