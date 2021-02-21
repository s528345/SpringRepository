package com.example.demo.controller.repository;

import oracle.sql.DATE;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

@Entity
public class DateTable implements DataAccessConversion{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int _ID;

    private Date _dateTime;

    private String _dateString;

    public DateTable(){}

    public DateTable(int id, Date dateTime){
        this._ID = id;
        this._dateTime = dateTime;
    }

    public int get_ID() {
        return _ID;
    }

    public Object get_dateTime() {
        return _dateTime;
    }

    public void set_dateTime(Date _dateTime) {
        this._dateTime = _dateTime;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public String get_dateString() {
        return _dateString;
    }

    public void set_dateString(String _dateString) {
        this._dateString = _dateString;
    }

    public void set_dateString(Date date){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1); // 0 index so add one


        this._dateString = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + "-" +
                String.valueOf(calendar.get(Calendar.MONTH)) + "-" +
                String.valueOf(calendar.get(Calendar.YEAR));
    }

    @Override
    public void updateDataAccessObject(@NotNull Object[] values) {
        // updates instance with given column ordering (pkey, date)
        this._ID = ((BigDecimal)values[0]).intValue();
        System.out.println(values[1]);
        this._dateTime = (Date)values[1];

        this.set_dateString(this._dateTime);
    }
}
