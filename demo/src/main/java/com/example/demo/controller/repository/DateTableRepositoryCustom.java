package com.example.demo.controller.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DateTableRepositoryCustom {
    List<DateTable> getAll();
}
