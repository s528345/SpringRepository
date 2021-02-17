package com.example.demo.controller.repository;

import javax.persistence.*;
import java.util.UUID;

//@Table(name = "MyTable", schema = "Sys")
@Table(name = "\"MyTable\"")
@Entity
public class MyTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name= "\"personName\"")
    private String _personName;

    @Column(name = "\"age\"")
    private int _age;

    public MyTable(UUID id, String personName, int age){
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

    public UUID getId() {
        return id;
    }

    public void set_age(int _age) {
        this._age = _age;
    }

    public void set_personName(String _personName) {
        this._personName = _personName;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}
