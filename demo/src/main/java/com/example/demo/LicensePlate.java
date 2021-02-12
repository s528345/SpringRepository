package com.example.demo;

import com.example.demo.validation.CheckCase;
import com.example.demo.validation.CheckCaseEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.*;
import com.example.demo.validation.*;

@LicensePlateClassValidatorInterface(value = 22, myTestValue = "Nick")
public class LicensePlate {

    // demo class to show how custom validation can be applied to fields and classes

    @NotNull
    private String ownerName;

    @NotNull
    @CheckCase(value = CheckCaseEnum.Upper, myTestValue = 0)
    private String licensePlate;

    @Min(0)
    @Max(100)
    private int yearsOwned;

    // constructors MUST be public for binding
    public LicensePlate(String ownerName, String licensePlate, int yearsOwned){
        this.ownerName = ownerName;
        this.licensePlate = licensePlate;
        this.yearsOwned = yearsOwned;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getYearsOwned() {
        return yearsOwned;
    }

    public void setYearsOwned(int yearsOwned) {
        this.yearsOwned = yearsOwned;
    }
}
