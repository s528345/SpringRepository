package com.example.demo.controller.repository;

import java.util.List;

public interface MyTableRepositoryCustom {
    List getAllStudentsLike(String like);

    List<String> getAllStudentNamesLike(String like);
}
