package com.example.demo.controller.repository;

import javax.validation.constraints.NotNull;
import java.util.function.Supplier;

public interface DataAccessConversion {
    public abstract void updateDataAccessObject(@NotNull final Object[] values);
}
