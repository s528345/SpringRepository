package com.example.demo;

import com.example.demo.validation.CheckCase;
import com.example.demo.validation.CheckCaseEnum;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class Car {

    @NotNull
    private String manufacturer;

    @NotNull
    @Size(min = 2, max = 14)
    @CheckCase(value = CheckCaseEnum.Upper, myTestValue = 0, message = "oops")
    private String licensePlate;

    @Min(2)
    private int seatCount;

    public Car ( String manufacturer, String licencePlate, int seatCount ) {
        this.manufacturer = manufacturer;
        this.licensePlate = licencePlate;
        this.seatCount = seatCount;
    }

    public Optional<Car> getSelfCar(){
        Optional<String> opt = Optional.of("");
        Optional<String> opt1 = Optional.ofNullable(null);
        Optional<Car> opt2 = Optional.ofNullable(this);
        return opt2;
    }

    public Optional<Car> getNullSelfCar(boolean isNull){
        return Optional.ofNullable(isNull? null : this);
    }

    public String getToStringCustom(Car car){return car.toString();}

    @Override
    public String toString(){
        return this.licensePlate + this.manufacturer + this.seatCount;
    }

    public static <T> void sortArray(ArrayList<T> list, Comparator<? super T> comparator){}


    //getters and setters ...
}
