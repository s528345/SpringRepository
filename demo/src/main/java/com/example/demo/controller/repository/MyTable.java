package com.example.demo.controller.repository;

import javax.persistence.*;
import java.util.UUID;

//@Table(name = "MyTable", schema = "Sys")
@Table(name = "textbook_services.\"MyTable\"")
@Entity
public class MyTable {

    @Id
    @Column(name = "\"pkey\"")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name= "\"personName\"")
    private String _personName;

    @Column(name = "\"age\"")
    private int _age;

    public MyTable(Integer id, String personName, int age){
        this.id = id;
        this._personName = personName;
        this._age = age;
    }

    public MyTable() {

    }

    public int get_age() {
        return _age;
    }

    public String get_personName() {
        return _personName;
    }

    public Integer getId() {
        return id;
    }

    public void set_age(int _age) {
        this._age = _age;
    }

    public void set_personName(String _personName) {
        this._personName = _personName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "person definition: " + this._personName + " " + this._age;
    }
}
