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

        /*
                Query query = em.createNativeQuery(
                "SELECT " + tableName() + ".* FROM \"MyTable\" WHERE (\"MyTable\".\"personName\" like ?) And " +
                        "(\"MyTable\".\"age\" >= 30)");
         */

        String queryToChange = "SELECT tableName.* FROM tableName WHERE (tableName.\"personName\" like ?) And " +
                "(tableName.\"age\" >= ?) AND (tableName.\"personName\" != ?)";

        String queryToUse = MyTableRepositoryImpl.insertTableNameIntoQuery(queryToChange, tableName());

        System.out.println(queryToUse);

        Query query = em.createNativeQuery(queryToUse);
        query.setParameter(1, like + "%"); // "n%" "nickdjkafsdklhjaesdlkj%"
        query.setParameter(2, 30);
        query.setParameter(3, "ryan");
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

    /**
     * Replaces the targeted query with all nominal place holders (tableName)
     * with the selected table targeted for the repository.
     * @param query: String query to manipulate (final member) so new copy given
     * @param tableName: String name of table for which entity repository corresponds with
     * @return amended query with table names inserted at placeholders.
     */
    private static @NotNull String insertTableNameIntoQuery(
            @NotNull final String query,
            @NotNull final String tableName){

        return query.replaceAll("tableName",tableName );
    }
}
